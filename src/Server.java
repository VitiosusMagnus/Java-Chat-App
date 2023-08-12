package src;


import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private  ServerSocket serverSocket;
    private  ExecutorService pool = Executors.newCachedThreadPool();

    public Server(ServerSocket serverSocket){
        this.serverSocket  = serverSocket;
    }

    public void start(){
        try{
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("New User Connected");
                ConnectionHandler connectionHandler = new ConnectionHandler(socket);
                pool.execute(connectionHandler);
            }
        }catch (Exception e){
            System.out.println("Error at starting server");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try{
            Server server = new Server(new ServerSocket(9999));
            server.start();
        }catch (Exception e){
            System.out.println("Error in server main");
            System.out.println(e.getMessage());
        }
    }
}
