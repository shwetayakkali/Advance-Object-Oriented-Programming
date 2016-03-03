
/* 
 * ServerDriver4.java 
 * 
 * Version: 1.1
 *     
 */
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 * ServerDriver4 is the driver used for rebind.
 *
 * @author Shweta Yakkali
 * @author Aishwarya Desai
 */
public class ServerDriver4 {

    /**
     * The main program.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String args[]) throws RemoteException, MalformedURLException {
        ServerInterface4 stub = new RMIServer4();
        Naming.rebind("rmi://localhost:5000/RMIServer4", stub);

    }
}
