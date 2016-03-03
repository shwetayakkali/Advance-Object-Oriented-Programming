
//package Networking4;
/* 
 * Client4.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Client program common to all the clients.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class Client4 {

    /**
     * The main() method contains the logic where the client reads the messages
     * from the server.
     *
     * @exception EOFException End Of File Exception
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("localhost", 2511);
        Scanner sc = new Scanner(System.in);
        ServerThread4 st = new ServerThread4();
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        //ObjectInputStream o = new ObjectInputStream(socket.getInputStream());

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
           
        } catch (EOFException e) {
        }
    }
}
