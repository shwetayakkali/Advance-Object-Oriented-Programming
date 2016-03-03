
//package UDP4;
/* 
 * UDPClient4.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/** 
 * Client class which send and receives the UDP packets. 
 * 
 * @author      Shweta Yakkali
 * @author      Aishwarya Desai
 */
public class UDPClient4 {
   /**
    * The main method where the client receives the data and proceedings of the game from the server.
    *
    */
    public static void main(String args[]) throws UnknownHostException, SocketException, IOException {
        Scanner sc = new Scanner(System.in);
        UDPServerThread4 st = new UDPServerThread4();
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[256];
        String sentence = "Hello";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);

        while (!st.done) {

            //dashed word receive:
            byte receiveData[] = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String display = new String(receivePacket.getData());
            System.out.println(display);

            //guess receive:
            byte guessreceive[] = new byte[256];
            DatagramPacket guess = new DatagramPacket(guessreceive, guessreceive.length);
            clientSocket.receive(guess);
            display = new String(guess.getData());
            System.out.println(display);

            //enter a letter receive:
            byte lettertoenter[] = new byte[256];
            DatagramPacket lettersen = new DatagramPacket(lettertoenter, lettertoenter.length);
            clientSocket.receive(lettersen);
            display = new String(lettersen.getData());
            System.out.println(display);

            //user enters the letter:
            String guessbyuser = sc.next();
            byte sendletter[] = new byte[256];
            sendletter = guessbyuser.getBytes();
            DatagramPacket sendtheletter = new DatagramPacket(sendletter, sendletter.length, IPAddress, 9876);
            clientSocket.send(sendtheletter);

            byte buf1[] = new byte[128];
            DatagramPacket packet1 = new DatagramPacket(buf1, buf1.length);
            clientSocket.receive(packet1);
            display = new String(packet1.getData());
            System.out.println(display);

            byte buf2[] = new byte[128];
            DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length);
            clientSocket.receive(packet2);
            display = new String(packet2.getData());
            System.out.println(display);

            byte buf3[] = new byte[128];
            DatagramPacket packet3 = new DatagramPacket(buf3, buf3.length);
            clientSocket.receive(packet3);
            display = new String(packet3.getData());
            System.out.println(display);

            byte buf4[] = new byte[128];
            DatagramPacket packet4 = new DatagramPacket(buf4, buf4.length);
            clientSocket.receive(packet4);
            display = new String(packet4.getData());
            System.out.println(display);

            byte buf5[] = new byte[128];
            DatagramPacket packet5 = new DatagramPacket(buf5, buf5.length);
            clientSocket.receive(packet5);
            display = new String(packet5.getData());
            System.out.println(display);

            byte buf6[] = new byte[128];
            DatagramPacket packet6 = new DatagramPacket(buf6, buf6.length);
            clientSocket.receive(packet6);
            display = new String(packet6.getData());
            System.out.println(display);

        }

        byte buf7[] = new byte[128];
        DatagramPacket packet7 = new DatagramPacket(buf7, buf7.length);
        clientSocket.receive(packet7);
        String display = new String(packet7.getData());
        System.out.println(display);

        sc.close();
        clientSocket.close();

    }
}
