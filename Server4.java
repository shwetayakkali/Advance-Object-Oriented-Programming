
//package Networking4;
/* 
 * Server4.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * Server code which creates 4 threads each for each player.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class Server4 {

    static Server4 s = new Server4();
    static int ctr = 0;
    static String wordselected = "";
    String words[] = new String[7];
    int index = 0;
    static int clientno = 0;

    /**
     * The main() method contains the logic where the server proceeds the game.
     * from the server.
     */
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2511);
        System.out.println("HANGMAN GAME");
        wordselected = s.select_random();
        while (ctr < 4) {

            Socket socket = server.accept();
            System.out.println("connection estalished!");

            new ServerThread4(socket, wordselected, clientno).start();
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
