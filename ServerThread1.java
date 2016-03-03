
//package Networking;
/* 
 * ServerThread1.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerThread1 code has the logic for all the clients
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class ServerThread1 extends Thread {

    Socket socket;
    static Server s = new Server();
    String wordselected;
    boolean done = false;
    String guesses = "";                                                        // initializing guess string
    int chances = 8;
    StringBuffer dashes;
    
    int index = 0;
    String guess;
    char letter;
    int score = 0;
    String final_points = "";
    int clientno;
    String clientname = "client" + clientno;
    static int points_table[] = new int[2];

    ServerThread1(Socket socket, String wordselected, int clientno) {
        this.socket = socket;
        this.wordselected = wordselected;
        this.clientno = clientno;
    }

    ServerThread1() {

    }

    /**
     * The run() method contains the hangman logic and computation of the
     * winner. from the server.
     */
    public void run() {

        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("in server thread   ");

            //wordselected=select_random();
            while (!done) {
                dashes = makeDashes(wordselected);
                while (!done) {

                    out.writeUTF("Here is your word : " + dashes);
                    out.writeUTF(("Guesses so far : " + guesses));
                    out.writeUTF("Enter a letter  : ");
                    guess = in.readUTF();
                  //System.out.println(guess+"    "+guess.length());

                    if (guess.length() < 1) {                                   // user can give only one character at a time.
                        if (guess.equals(wordselected)) {
                            out.writeUTF("\n you win!");
                        } else {
                            out.writeUTF("\n you lose!");
                        }
                        out.writeUTF("");
                        out.writeUTF("");
                        out.writeUTF("");
                        out.writeUTF("");
                        out.writeUTF("");

                        done = true;
                    } else {                                                    // if user inputs 1 alphabet at a time

                        letter = guess.charAt(0);                               // assigning the character in guess to character letter
                       

                        if (wordselected.indexOf(letter) < 0 && guesses.indexOf(letter) < 0) { // to handle the scoring for a single alphabet entered multiple times.

                            --chances;                                                          // decremented on a wrong guess
                            out.writeUTF("\n bad guess - ");
                            score = score - 5;                                                   // every wrong alphabet score is reduced by 5
                            out.writeUTF("\n score : " + score);                         // print the score

                        } else                                                          // if letter is a part of the string choosen                       
                        if (guesses.indexOf(letter) < 0) {
                                                                                              // letter is in the word n put it in dashes where it belongs
                            matchLetter(wordselected, dashes, letter);                        // method call to match letter
                            score = score + 10;                                                   // increments by 10 for a correct guess
                            out.writeUTF("score : " + score);                           // displays score
                        }

                        out.writeUTF(chances + " chances are left");                 // displays the number of chances left                
                        final_points = final_points + score + " ";                              // stores the final points scored by a player

                        if (chances == 0) {                                                // due to incorrect guesses chances over
                            out.writeUTF("\n Out of chances ,score is " + score);
                            out.writeUTF("The word was : " + wordselected);              // displays the actual word
                            done = true;                                                     // terminates the while loop
                        } else {

                            out.writeUTF("");
                            out.writeUTF("");

                        }

                        if (wordselected.equals(dashes.toString())) {                      // word is correct if the entered letters match the actual word
                            out.writeUTF("\n Your score is :!" + score);
                            done = true;                                                    // terminates the while loop
                        } else {

                            out.writeUTF("");

                        }

                        guesses = guesses + letter;                                        // guesses will keep a track of all the entered alphabets
                    }

                }
            }

            synchronized (points_table) {
                points_table.notify();
                points_table[clientno] = score;
                System.out.println("client is  " + clientno + "   " + points_table[clientno]);
                points_table.wait();
                points_table.notify();
            }
            if (points_table[0] == points_table[1]) {
                out.writeUTF("DRAW!");
                socket.close();
            } else if (points_table[0] > points_table[1]) {
                out.writeUTF("winner is Player " + "0");
                socket.close();

            } else {
                out.writeUTF("winner is Player " + "1");
                socket.close();

            }

        } catch (IOException ex) {

        } catch (InterruptedException ex) {

        }

    }

    /**
     * The makeDashes() returns the dashes which corresponds to the length of
     * the word selected.
     *
     * @return dashes which is a Stringbuffer
     */
    public static StringBuffer makeDashes(String s) {
        StringBuffer dashes = new StringBuffer(s.length());
        for (int count = 0; count < s.length(); count++) {
            dashes.append('-');                                              // beginning dashes are displayed according to the length of the randomly selected word
        }
        return dashes;
    }

    /**
     * The matchLetter() method performs the matching.
     *
     * @param wordselected String
     * @param dashes Stringbuffer
     * @param letter character
     */
    public void matchLetter(String wordselected, StringBuffer dashes, char letter) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        for (int index = 0; index < wordselected.length(); index++) // traverse through the string word
        {
            if (wordselected.charAt(index) == letter) // checks if the letter is present in the string word for the particular index
            {
                dashes.setCharAt(index, letter);                               // replacing the dash with the respective letter
            }
        }
        out.writeUTF("good guess - ");
    }

}
