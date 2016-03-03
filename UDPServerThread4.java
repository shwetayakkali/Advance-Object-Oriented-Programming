
//package UDP4;
/* 
 * UDPServerThread4.java 
 * 
 * Version: 
 *     $1.1$ 
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ServerThreadClass4 class handles each client thread.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class UDPServerThread4 extends Thread {

    DatagramSocket serverSocket;
    static String wordselected;
    StringBuffer dashes;
    String guesses = "";
    String guess;
    boolean done = false;
    char letter;
    int chances = 8;
    int score = 0;
    String strdashes;
    String final_points = "";
    int clientno;
    static int points_table[] = new int[4];
    static int counter = 0;
    static int winner;
    static int max = 0;
    static int storeport[] = new int[4];
    static InetAddress storeaddress[] = new InetAddress[4];
    static Object o = new Object();
    static int threadctr = 0;

    UDPServerThread4(DatagramSocket serverSocket, String wordselected, int clientno) {
        this.serverSocket = serverSocket;
        this.wordselected = wordselected;
        this.clientno = clientno;
    }

    UDPServerThread4() {
    }

    /**
     * The run method has the hangman logic for each client and displays the
     * winner to the clients.
     *
     */
    public void run() {
        byte receiveData[] = new byte[256];
        byte[] sendData = new byte[256];  //sending random word
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            serverSocket.receive(receivePacket);
            String receive = new String(receivePacket.getData());
            System.out.println("received from client   :" + receive);
        } catch (IOException ex) {

        }

        InetAddress IPAddress = receivePacket.getAddress();
        storeaddress[clientno] = IPAddress;
        int port = receivePacket.getPort();
        storeport[clientno] = port;

        System.out.println("word is:" + wordselected);
        while (!done) {
            //dashed word:
            dashes = makeDashes(wordselected);

            while (!done) {

                synchronized (o) {

                    strdashes = new String(dashes);
                    sendData = ("Here is your word : " + strdashes).getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, storeaddress[clientno], storeport[clientno]);
                    try {
                        serverSocket.send(sendPacket);
                    } catch (IOException ex) {

                    }
                    //guess string:
                    byte guessbuf[] = new byte[256];
                    guessbuf = ("guesses so far:   " + guesses).getBytes();
                    DatagramPacket sendguess = new DatagramPacket(guessbuf, guessbuf.length, storeaddress[clientno], storeport[clientno]);
                    try {
                        serverSocket.send(sendguess);
                    } catch (IOException ex) {

                    }
                    //enter a letter:
                    byte enterletter[] = new byte[256];
                    enterletter = ("enter a letter:").getBytes();
                    DatagramPacket letterenter = new DatagramPacket(enterletter, enterletter.length, storeaddress[clientno], storeport[clientno]);
                    try {
                        serverSocket.send(letterenter);
                    } catch (IOException ex) {

                    }

                    //receive the letter:
                    byte receiveletter[] = new byte[256];
                    DatagramPacket receivetheletter = new DatagramPacket(receiveletter, receiveletter.length);
                    try {
                        serverSocket.receive(receivetheletter);
                    } catch (IOException ex) {

                    }
                    guess = new String(receivetheletter.getData());
                    //System.out.println("letter entered by user   :"+guess);

                    if (guess.length() < 1) {                                   // user can give only one character at a time.
                        if (guess.equals(wordselected)) {
                            byte buf1[] = new byte[128];
                            buf1 = ("you win!").getBytes();
                            DatagramPacket packet1 = new DatagramPacket(buf1, buf1.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet1);
                            } catch (IOException ex) {
                            }

                        } else {

                            byte buf2[] = new byte[128];
                            buf2 = ("you lose!").getBytes();
                            DatagramPacket packet2 = new DatagramPacket(buf2, buf2.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet2);
                            } catch (IOException ex) {
                            }

                        }
                        byte buf3[] = new byte[128];
                        buf3 = ("").getBytes();
                        DatagramPacket packet3 = new DatagramPacket(buf3, buf3.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet3);
                        } catch (IOException ex) {
                        }

                        byte buf4[] = new byte[128];
                        buf4 = ("").getBytes();
                        DatagramPacket packet4 = new DatagramPacket(buf4, buf4.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet4);
                        } catch (IOException ex) {
                        }

                        byte buf5[] = new byte[128];
                        buf5 = ("").getBytes();
                        DatagramPacket packet5 = new DatagramPacket(buf5, buf5.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet5);
                        } catch (IOException ex) {
                        }

                        byte buf6[] = new byte[128];
                        buf6 = ("").getBytes();
                        DatagramPacket packet6 = new DatagramPacket(buf6, buf6.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet6);
                        } catch (IOException ex) {
                        }

                        byte buf7[] = new byte[128];
                        buf7 = ("").getBytes();
                        DatagramPacket packet7 = new DatagramPacket(buf7, buf7.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet7);
                        } catch (IOException ex) {
                        }

                        done = true;
                    } else {

                        letter = guess.charAt(0);
                        if (wordselected.indexOf(letter) < 0 && guesses.indexOf(letter) < 0) { // to handle the scoring for a single alphabet entered multiple times.

                            --chances;                                                          // decremented on a wrong guess
                            byte buf8[] = new byte[128];
                            buf8 = ("bag guess!").getBytes();
                            DatagramPacket packet8 = new DatagramPacket(buf8, buf8.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet8);
                            } catch (IOException ex) {
                            }

                            score = score - 5;                                                   // every wrong alphabet score is reduced by 5
                            byte buf9[] = new byte[128];
                            buf9 = ("score is: " + score).getBytes();
                            DatagramPacket packet9 = new DatagramPacket(buf9, buf9.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet9);
                            } catch (IOException ex) {
                            }

                        } else // if letter is a part of the string choosen                       
                        if (guesses.indexOf(letter) < 0) {
                            try {

                                dashes = matchLetter(wordselected, dashes, letter, storeaddress[clientno], storeport[clientno]);                        // method call to match letter

                            } catch (IOException ex) {
                            }

                            score = score + 10;                                                   // increments by 10 for a correct guess
                            byte buf10[] = new byte[128];
                            buf10 = ("score is: " + score).getBytes();
                            DatagramPacket packet10 = new DatagramPacket(buf10, buf10.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet10);
                            } catch (IOException ex) {
                            }

                        }

                        byte buf12[] = new byte[128];
                        buf12 = (chances + " chances are left").getBytes();
                        DatagramPacket packet12 = new DatagramPacket(buf12, buf12.length, storeaddress[clientno], storeport[clientno]);
                        try {
                            serverSocket.send(packet12);
                        } catch (IOException ex) {
                        }
                        // displays the number of chances left                
                        final_points = final_points + score + " ";

                        if (chances == 0) {                                                // due to incorrect guesses chances over
                            byte buf13[] = new byte[128];
                            buf13 = ("Out of chances ,score is " + score).getBytes();
                            DatagramPacket packet13 = new DatagramPacket(buf13, buf13.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet13);
                            } catch (IOException ex) {
                            }

                            byte buf14[] = new byte[128];
                            buf14 = ("The word was : " + wordselected).getBytes();
                            DatagramPacket packet14 = new DatagramPacket(buf14, buf14.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet14);
                            } catch (IOException ex) {
                            }
                            threadctr++;
                            o.notifyAll();
                            done = true;                                                     // terminates the while loop

                        } else {
                            byte buf15[] = new byte[128];
                            buf15 = ("").getBytes();
                            DatagramPacket packet15 = new DatagramPacket(buf15, buf15.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet15);
                            } catch (IOException ex) {
                            }
                            byte buf16[] = new byte[128];
                            buf16 = ("").getBytes();
                            DatagramPacket packet16 = new DatagramPacket(buf16, buf16.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet16);
                            } catch (IOException ex) {
                            }

                        }

                        if (wordselected.equals(dashes.toString())) {                      // word is correct if the entered letters match the actual word

                            byte buf17[] = new byte[128];
                            buf17 = ("Your score is :!" + score).getBytes();
                            DatagramPacket packet17 = new DatagramPacket(buf17, buf17.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet17);
                            } catch (IOException ex) {
                            }
                            threadctr++;
                            o.notifyAll();
                            done = true;                                                    // terminates the while loop
                        } else {
                            byte buf18[] = new byte[128];
                            buf18 = ("").getBytes();
                            DatagramPacket packet18 = new DatagramPacket(buf18, buf18.length, storeaddress[clientno], storeport[clientno]);
                            try {
                                serverSocket.send(packet18);
                            } catch (IOException ex) {
                            }

                        }

                        guesses = guesses + letter;

                    }

                    o.notifyAll();
                    try {
                        System.out.println("threadctr: " + threadctr);
                        if (threadctr != 4) {
                            o.wait();
                        }
                    } catch (InterruptedException ex) {

                    }

                }

            }

        }

        synchronized (points_table) {

            System.out.println(clientno);
            points_table[clientno] = score;
            System.out.println("client is  " + clientno + "   " + points_table[clientno]);

            counter++;

            if (counter == 4) {
                for (int i = 0; i < 4; i++) {
                    if (max < points_table[i]) {
                        max = points_table[i];
                        winner = i;
                    }
                }

                byte buf19[] = new byte[128];
                buf19 = ("Winner is Player  " + winner).getBytes();
                DatagramPacket packet19 = new DatagramPacket(buf19, buf19.length, storeaddress[clientno], storeport[clientno]);
                try {
                    serverSocket.send(packet19);
                } catch (IOException ex) {
                }

                points_table.notifyAll();
                //serverSocket.close();
            } else {
                try {
                    points_table.wait();
                } catch (InterruptedException ex) {
                }

                byte buf19[] = new byte[128];
                buf19 = ("Winner is Player  " + winner).getBytes();
                DatagramPacket packet19 = new DatagramPacket(buf19, buf19.length, storeaddress[clientno], storeport[clientno]);
                try {
                    serverSocket.send(packet19);
                } catch (IOException ex) {
                }

                //serverSocket.close();
            }
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
     * @param IPAddress InetAddress
     * @param port Integer
     * @return dashes
     */
    public StringBuffer matchLetter(String wordselected, StringBuffer dashes, char letter, InetAddress IPAddress, int port) throws IOException {

        for (int index = 0; index < wordselected.length(); index++) // traverse through the string word
        {
            if (wordselected.charAt(index) == letter) // checks if the letter is present in the string word for the particular index
            {
                dashes.setCharAt(index, letter);                               // replacing the dash with the respective letter
            }
        }

        byte buf11[] = new byte[128];
        buf11 = ("good guess!").getBytes();
        DatagramPacket packet11 = new DatagramPacket(buf11, buf11.length, storeaddress[clientno], storeport[clientno]);
        try {
            serverSocket.send(packet11);
        } catch (IOException ex) {
        }

        return dashes;

    }

}
