
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {


    public static void main(String[] args) throws InterruptedException {


        try(Socket socket = new Socket("localhost", 3345);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {

            System.out.println("Клиент присоединился к сокету.");
            System.out.println();
            System.out.println("Канал для клиента.");

            while(!socket.isOutputShutdown()){

                if(br.ready()){

                    System.out.println("Клиент начал писать в канале...");
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();

                    oos.writeUTF(clientCommand);
                    oos.flush();
                    System.out.println("Клиент отправил " + clientCommand + " на сервер.");
                    Thread.sleep(1000);

                    if(clientCommand.equalsIgnoreCase("exit")){

                        System.out.println("Клиент оборвал соединение");
                        Thread.sleep(2000);

                        if(ois.read() > -1)     {
                            System.out.println("чтение...");
                            String in = ois.readUTF();
                            System.out.println(in);
                        }

                        break;
                    }

                    System.out.println("Клиент отправил сообщение & ожидает данные с сервера...");
                    Thread.sleep(2000);

                    if(ois.read() > -1)     {

                        System.out.println("reading...");
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }

            System.out.println("Соединение закрыто & каналы на стороне клиента - ГОТОВО.");

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }



}