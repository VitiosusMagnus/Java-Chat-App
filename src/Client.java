package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private  Socket socket;
    private  BufferedReader in;
    private  PrintWriter out;


    public Client(Socket socket){
        try{
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }catch (Exception e){
            System.out.println("Error in client constructor");
            System.out.println(e.getMessage());
        }
    }


    public void readMessage() {
        new Thread(() -> {
            String message;
            try{
                while (socket.isConnected()){
                    message = in.readLine();
                    System.out.println(message);
                }
            }catch (Exception e){
                System.out.println("Error in readMessage method");
                System.out.println(e.getMessage());
            }
        }).start();

    }

    public void sendMessage(){
        new Thread(() ->{
            try{

                String message;
                Scanner sc = new Scanner(System.in);
                while (socket.isConnected()){
                    message = sc.nextLine();
                    out.println(message);
                }
            }catch (Exception e){
                System.out.println("Error in sendMessage");
                System.out.println(e.getMessage());
            }
        }).start();

    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",9999);
        Client client = new Client(socket);
        client.readMessage();
        client.sendMessage();
    }
}
