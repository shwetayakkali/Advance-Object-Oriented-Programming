
//package RMI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface4 extends Remote {
    
    public String displayword() throws FileNotFoundException, RemoteException;
    public StringBuffer makeDashes(String s)throws RemoteException;
    public String displayguesses(String guesses)throws RemoteException;
    
    public String counterCheck(int a)throws RemoteException;;
    public StringBuffer returndashes(StringBuffer dashes)throws RemoteException;
    
    public Boolean checkwithwordselected(String guess)throws RemoteException;
    public String goodbadguesscheck(char letter,String guesses,StringBuffer dashes) throws IOException,RemoteException;
    
    public int displaychances(int chances)throws RemoteException;
    public String scoreandword()throws RemoteException;
    public Boolean wonthegame(StringBuffer dashes)throws RemoteException;
    public String returnscore()throws RemoteException;
    public Boolean returndone()throws RemoteException;
    public int putscore(int score,int index)throws RemoteException;
    public String comparescore()throws RemoteException;
}
