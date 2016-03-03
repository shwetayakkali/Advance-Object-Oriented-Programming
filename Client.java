
//package Networking;
/* 
 * Client.java 
 * 
 * Version: 1.1
 *     
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * The Client class contains the main. It also connects to the server port and
 * using the datainput and outputstream it reads lines written by the server
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class Client {

    /**
     * The main program.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) throws UnknownHostException, IOException, EOFException {
        Socket socket = new Socket("localhost", 2511);
        Scanner sc = new Scanner(System.in);
        ServerThread1 st = new ServerThread1();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        try {
            while (!st.done) {

                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                String guess = sc.next();
                out.writeUTF(guess);

                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());
                System.out.println(in.readUTF());

            }
            System.out.println(in.readUTF());
            sc.close();
            socket.close();
        } 
        catch (EOFException e) {
        }
    }
}
