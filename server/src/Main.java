import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);


    public static void main(String[] args) {


        try (ServerSocket server = new ServerSocket(8081);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Сокет создан, консоль ожидает серверные команды");

            while (!server.isClosed()) {
                if (br.ready()) {
                    System.out.println("Сервер нашел сообщения в канале");


                    String serverCommand = br.readLine();
                    if (serverCommand.equalsIgnoreCase("exit")) {
                        System.out.println("Сервер запущен...");
                        server.close();
                        break;
                    }
                }

                Socket client = server.accept();

                executeIt.execute(new MonoThreadClientHandler(client));
                System.out.print("Соединение установлено.");
            }

            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}