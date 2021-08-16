package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) {
        System.out.println("== Cliente ==\n");
        System.out.println("Comandos disponíveis:\n" +
                "'readdir' - Lista os arquivos e pastas de um diretório\n" +
                "'rename ' - Renomeia um arquivo disponível\n" +
                "'create  - Cria um arquivo disponível\n" +
                "'remove  - Deleta um arquivo disponível\n");


        try {
            Socket socket = new Socket("localhost", 6000);

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            while (true) {
                System.out.print(">> ");
                Scanner keyboard = new Scanner(System.in);
                String command = keyboard.nextLine();
                dos.writeUTF(command);

                String receivedMessage = dis.readUTF();

                if (!receivedMessage.equals("")) System.out.println(receivedMessage);
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}