
//package Networking;
/* 
 * Server.java 
 * 
 * Version: 1.1
 *     
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * The Server class contains the main this is where it restricts the number of
 * player to 2 .On running this class the server will run waiting for the client
 * to connect.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class Server {

    static Server s = new Server();
    static int ctr = 0;
    static String wordselected = "";
    String words[] = new String[7];
    int index = 0;
    static int clientno = 0;

    /**
     * The main program.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2511);
        System.out.println("HANGMAN GAME");
        wordselected = s.select_random();
        while (ctr < 2) {

            Socket socket = server.accept();
            System.out.println("connection estalished!");
            new ServerThread1(socket, wordselected, clientno).start();
            ctr++;
            clientno++;
        }

    }

    /**
     * The select_random() method returns the random word selected to all the
     * clients.
     *
     * @ return wordselected which is random word from dictionary.
     */
    public String select_random() throws FileNotFoundException {
        model1();
        Random rand = new Random();                                      // creating a reference of class random
        int random_index = rand.nextInt(7);                              // random function for selecting the index randomly and hence the word stored in that index will be selected.

        wordselected = words[random_index];

        return wordselected;

    }

    /**
     * The model1() method stores the words from the dictionary into the words[]
     * array. to all the clients.
     *
     * @ return wordselected which is random word from dictionary.
     */
    void model1() throws FileNotFoundException {
        Scanner sc1 = new Scanner(new File("C:\\javaprog\\hw 3\\dictionary.txt"));
        while (sc1.hasNextLine()) {
            String line = sc1.nextLine();
            words[index] = line;
            index++;
        }
    }

}
