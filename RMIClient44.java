
//package RMI;
/* 
 * RMIClient44.java 
 * 
 * Version: 1.1
 *     
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
/**
 * RMIClient44 is the Client.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class RMIClient44 extends UnicastRemoteObject {

    static ServerInterface4 stub;
    StringBuffer dashes;
    String guesses = "";
    Scanner scanner = new Scanner(System.in);
    Boolean done = false;
    char letter = ' ';
    String wordselected;
    int score = 0;
    String scoring;
    int chances = 8;
    String result;

    public RMIClient44() throws RemoteException, NotBoundException, MalformedURLException, FileNotFoundException {
        super();
        stub = (ServerInterface4) Naming.lookup("rmi://localhost:5000/RMIServer4");
        wordselected = stub.displayword();
    }

    int clientid;
    static Boolean flag = false;

    public RMIClient44(int clientid) throws RemoteException {

        this.clientid = clientid;
    }

    static int ctr = 0;
    /**
     * The main method.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String args[]) throws NotBoundException, MalformedURLException, RemoteException, FileNotFoundException, IOException {
        //ServerInterface stub = (ServerInterface) Naming.lookup("rmi://localhost:5000/RMIServer");

        RMIClient44 client = new RMIClient44();
        System.out.println("Client4  Conected \n ");
        String serverOutput;

        serverOutput = stub.counterCheck(4);

        if (serverOutput.contains("Start ")) {
            client.clientmethod();

        } else {
            System.out.println("wait for your turn");
        }

        //System.out.println("client2 here!");
        System.exit(0);

    }
    
    /**
     * The clientmethod contains the client logic.
     *
     */
    
    public void clientmethod() {
        try {

            while (!done) {
                dashes = stub.makeDashes(wordselected);
                while (!done) {

                    System.out.println("Here is your word   :" + dashes);
                    guesses = stub.displayguesses(guesses);
                    System.out.println("Guesses so far      :" + stub.displayguesses(guesses));
                    System.out.println("Enter a letter  : ");
                    String guess = scanner.next();

                    if (guess.length() < 1) {                                   // user can give only one character at a time.                       
                        done = stub.checkwithwordselected(guess);
                        //System.out.println("done ki value" + done);
                        if (done == true) {
                            System.out.println("You Win!");
                        } else {
                            System.out.println("\n you lose!");
                        }

                    } else {
                        letter = guess.charAt(0);
                        scoring = stub.goodbadguesscheck(letter, guesses, dashes);
                        System.out.println(stub.goodbadguesscheck(letter, guesses, dashes));
                        if (scoring.contains("good ")) {
                            score = score + 10;
                            dashes = stub.returndashes(dashes);
                            System.out.println("if dashes  " + dashes);
                        } else {
                            --chances;
                            score = score - 5;
                            //dashes = stub.returndashes2(dashes);
                            System.out.println("else dashes  " + dashes);
                        }

                        System.out.println(" Score is   :" + score);
                        //dashes = stub.returndashes2(dashes);

                        chances = stub.displaychances(chances);

                        System.out.println(" Chances left are  :" + chances);
                        if (chances == 0) {                                                // due to incorrect guesses chances over

                            System.out.println(stub.scoreandword());
                            done = true;                                                     // terminates the while loop
                        }

                        if (stub.wonthegame(dashes)) {
                            System.out.println(stub.returnscore() + score);
                            done = stub.returndone();
                        }

                    }
                    guesses = guesses + letter;
                }
                break;
            }
            //int a=2;

            System.out.println("Final score of Player 4  is:" + "  " + score);

            int ctr = stub.putscore(score, 4);

            System.out.println("waiting for other players to finish");
            while (ctr != 4) {
                //System.out.println(ctr+"   ctr value in client2");
                ctr = stub.putscore(score, 4);
            }

            if (ctr == 4) {
                System.out.println("enter if");
                String display = stub.comparescore();
                System.out.println("winner is Player:  " + display);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
