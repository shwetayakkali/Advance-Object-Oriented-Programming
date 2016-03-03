
//package UDP4;
/* 
 * UDPServer4.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;
/** 
 * Server class which send and receives the UDP packets from the client. 
 * 
 * @author      Shweta Yakkali
 * @author      Aishwarya Desai
 */
public class UDPServer4 {

    static String wordselected = "";
    String words[] = new String[7];
    int index = 0;
    static int ctr = 0;
    static UDPServer4 s = new UDPServer4();

    public static void main(String args[]) throws SocketException, IOException {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        //wordselected = s.select_random();
        wordselected = "a";
        while (ctr < 4) {

            new UDPServerThread4(serverSocket, wordselected, ctr).start();
            ctr++;

        }

    }
    
    /**
     * The select_random() method selects the random word.
     * array. to all the clients.
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
