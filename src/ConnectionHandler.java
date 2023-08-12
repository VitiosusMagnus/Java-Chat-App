package src;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectionHandler implements Runnable{
    public static ArrayList<ConnectionHandler> clients = new ArrayList<>();
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    public ConnectionHandler(Socket socket){
        try{
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            clients.add(this);
        }catch (Exception e){
            System.out.println("Error in connection handler constructor");
            System.out.println(e.getMessage());
            try{
                this.socket.close();
                in.close();
                out.close();
            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }
        }
    }

    @Override
    public void run() {
        try{
            out.println("Enter nickname: ");
            this.name = in.readLine();
            broadcast("[SERVER] " + this.name + " joined the chat");
            String message;
            while (socket.isConnected()){
                message = in.readLine();
                broadcast(this.name + ": " +message);
            }
        }catch (Exception e){
            System.out.println("Error in connectionHandler run method");
            System.out.println(e.getMessage());
        }



    }

    public void broadcast(String message){
        for (ConnectionHandler connection: clients) {
            if (!connection.name.equals(name)){
                connection.out.println(message);
            }
        }
    }
}
