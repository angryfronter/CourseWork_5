import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private static Socket clientDialog;
    public MonoThreadClientHandler(Socket client) {
        MonoThreadClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try {
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());


            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream создан");

            System.out.println("DataOutputStream  создан");

            while (!clientDialog.isClosed()) {
                System.out.println("Сервер читает из канала");

                String entry = in.readUTF();


                System.out.println("READ from clientDialog message - " + entry);

                if (entry.equalsIgnoreCase("exit")) {

                    System.out.println("Клиент инициализирует подключение ...");
                    out.writeUTF("Ответ сервера - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }

                System.out.println("Сервер пытается записать в канал");
                out.writeUTF("Ответ сервера - " + entry + " - OK");
                System.out.println("Сервер написал сообщение clientDialog.");

                out.flush();

            }

            System.out.println("Клиент отключился");
            System.out.println("Соединение закрыто & каналы.");

            in.close();
            out.close();

            clientDialog.close();

            System.out.println("Соединение закрыто & каналы - ГОТОВО.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}