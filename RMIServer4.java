
//package RMI;
/* 
 * RMIServer4.java 
 * 
 * Version: 1.1
 *     
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Scanner;
/**
 * RMIServer4 is the Server which all the clients access.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class RMIServer4 extends UnicastRemoteObject implements ServerInterface4{
    static String wordselected = "tab";
    String words[] = new String[7];
    int index = 0;
    Boolean done=false;
    StringBuffer dashes1;
    String guesses="";
    String guess="";
    char letter;
    int score = 0;
    String final_points = "";
    int chances = 8;
    static int playerCounter=1;
    static int pointstable[]=new int[4];
    static int scorectr=0;
    static Boolean flag1=false;
    static Boolean flag2=false;
    static Boolean flag3=false;
    static Boolean flag4=false;
    static int winner=0;
    static int max=0;
    
    public RMIServer4() throws RemoteException{
        super();
    }
    
    /**
     * The main method.
     *
     * @param args command line arguments (ignored)
     */
    
    public static void main(String args[]) throws FileNotFoundException, RemoteException{
        RMIServer4 s=new RMIServer4();
        System.out.println("HANGMAN");
        
    }
    
    /**
     * The counterCheck method is used for checking the proper client.
     *
     * @param int client number
     * @return String
     */
    
    public String counterCheck(int a){
        if (a==playerCounter){
            if(playerCounter!=4)
                playerCounter++;
            
            else 
                playerCounter=1;
                        
            return ("Start the game:");
        }
        else
            return "Not your turn right now";
    
    }
    
    /**
     * The displayword method is used sending the word.
     *
     * @return String
     */
    
    public String displayword() throws FileNotFoundException, RemoteException{
        RMIServer4 s=new RMIServer4();
        
        return wordselected;
    
    }
    
    /**
     * The makeDashes method makes the dashes for a particular word.
     *
     * @return String
     */
    
    public StringBuffer makeDashes(String s) {
        StringBuffer dashes = new StringBuffer(s.length());
        for (int count = 0; count < s.length(); count++) {
            dashes.append('-');                                              // beginning dashes are displayed according to the length of the randomly selected word
        }
        return dashes;
    }
    
    /**
     * The checkwithwordselected() method makes the dashes for a particular word.
     *
     * @param String guess
     * @return Boolean
     */
    
    public Boolean checkwithwordselected(String guess)throws RemoteException{
        if (guess.equals(wordselected)) {
                            return true;
                        } 
        return false;
    }
    
    /**
     * The goodbadguesscheck method makes the dashes for a particular word.
     *
     * @param char letter
     * @param String guesses
     * @param StringBuffer dashes
     * @return String
     */
    
    public String goodbadguesscheck(char letter,String guesses,StringBuffer dashes) throws IOException,RemoteException{
        String scoring="";
        if (wordselected.indexOf(letter) < 0 && guesses.indexOf(letter) < 0) { // to handle the scoring for a single alphabet entered multiple times.

                            --chances;                                                          // decremented on a wrong guess
                            
                            score = score - 5;                                                   // every wrong alphabet score is reduced by 5
                                                  
                            
                            scoring="bad guess";
                            

                        } else                                                          // if letter is a part of the string choosen                       
                        if (guesses.indexOf(letter) < 0) {
                                                                                              // letter is in the word n put it in dashes where it belongs
                            this.dashes1=matchLetter(wordselected, dashes, letter);                        // method call to match letter
                            score = score + 10;                                                   // increments by 10 for a correct guess
                            
                            
                            scoring="good guess";
                        }
    
        return scoring;
    }
    
    /**
     * The returndashes() returns the dashes to client
     *
     * @param StringBuffer dashes
     * @return String
     */
    
    public StringBuffer returndashes(StringBuffer dashes){
        
        dashes=this.dashes1;
        return dashes;
    }
    

    /**
     * The putscore() method puts the score in the points table.
     *
     * @param int score
     * @param int index
     * @return String
     */
    
    public int putscore(int score,int index){
        
        pointstable[index-1]=score;
        
        if(index==1){
            if(flag1==false){
                    
                    scorectr++;
                    flag1=true;
            }
        }
        
        else if(index==2){
            if(flag2==false){
                
                    scorectr++;
                    flag2=true;
            }
        }
        
        else if(index==3){
            if(flag3==false){
                
                    scorectr++;
                    flag3=true;
            }
        }
        
        else if(index==4){
            if(flag4==false){
                
                    scorectr++;
                    flag4=true;
            }
        }
        
        
        return scorectr;
    }
    
    /**
     * The comparescore() method compares the score.
     *
     * @return String
     */
    
    public String comparescore(){
              
        
        for(int i=0;i<pointstable.length;i++){
            
            if(max<pointstable[i]){
                max=pointstable[i];
                winner=i+1;
            }
        }
        
        return winner+"";
     
    }
    
    /**
     * The displaychances() returns the chances
     *
     * @param int chances
     * @return int
     */
    
    public int displaychances(int chances)throws RemoteException{
        return chances;
    }
    
    /**
     * The scoreandword() returns the chances
     *
     * @return Srring
     */
    
    public String scoreandword()throws RemoteException{
         
        return "\n Out of chances  " + "\n The word was : " + wordselected;
    }
    
    /**
     * The wonthegame() returns the flag value 
     *
     * @param StringBuffer dashes
     * @return Boolean
     */
    
    public Boolean wonthegame(StringBuffer dashes)throws RemoteException{
        Boolean flag=false;
        if(wordselected.equals(dashes.toString()))
            return true;
        return flag;
    }
    
    /**
     * The returnscore() returns the score string
     *
     * @return String
     */
    
    public String returnscore()throws RemoteException{
        return "\n Your score is : ";
    }
    
    /**
     * The returndone() returns true
     *
     * @return Boolean
     */
    
    public Boolean returndone(){
        return true;
    }
    
    /**
     * The displayguesses() returns true
     *
     * @return String
     */
    
    
    public String displayguesses(String guesses){
        
        return guesses;
    }
    
    
    /**
     * The matchLetter() method matches the letter with the String.
     *
     * @param String wordselected
     * @param StringBuffer dashes
     * @param char letter
     * @return String
     */
    
    
    public StringBuffer matchLetter(String wordselected, StringBuffer dashes, char letter) throws IOException {
       
        for (int index = 0; index < wordselected.length(); index++) // traverse through the string word
        {
            if (wordselected.charAt(index) == letter) // checks if the letter is present in the string word for the particular index
            {
                dashes.setCharAt(index, letter);                               // replacing the dash with the respective letter
                
                
            }
        }
        return dashes;
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

