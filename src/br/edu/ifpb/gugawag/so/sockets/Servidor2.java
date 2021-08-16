package br.edu.ifpb.gugawag.so.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor2{

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);

            System.out.println("=== Servidor iniciado ===\n");

            while (true) {
                Socket client = serverSocket.accept();
                String clientAddress = client.getInetAddress().getHostAddress();
                System.out.println(clientAddress + " Servidor Conectado!");

                DataOutputStream dos = new DataOutputStream(client.getOutputStream());
                DataInputStream dis = new DataInputStream(client.getInputStream());

                while (!client.isClosed()) {
                    String command = dis.readUTF();

                    String[] list = new String[0];
                    switch (list[0]) {

                        case "readdir":
                            String response = "";

                            File path = new File(list[1]);

                            if (path.exists()) {
                                File[] files = path.listFiles();
                                for (File file : files) {
                                    response += file.getName() + " ";
                                }
                            }
                            dos.writeUTF(response);
                            break;

                        case "rename":
                            File fileToRename = new File(list[1]);
                            File newName = new File(list[2]);

                            if(fileToRename.exists()) {
                                fileToRename.renameTo(newName);
                                dos.writeUTF("");
                            }
                            break;
                        case "create":
                            File newFile = new File(list[1]);
                            if (!newFile.exists()) {
                                newFile.createNewFile();
                                dos.writeUTF("");
                            }

                            break;
                        case "remove":
                            File fileToRemove = new File(list[1]);
                            if (fileToRemove.exists()) {
                                fileToRemove.delete();
                                dos.writeUTF("");
                            }
                            break;
                    }
                }

                client.close();
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
}