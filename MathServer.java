import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;

public class MathServer extends UnicastRemoteObject implements MathService {

    private int clientCount = 0;

    public MathServer() throws RemoteException {
        super();
    }

    public synchronized int incrementClientCount() throws RemoteException {
        clientCount++;  // increase count
        System.out.println("New client connected. Total clients: " + clientCount);
        return clientCount;  // return updated count
    }

    public int add(int a, int b) throws RemoteException {
        System.out.println("Adding " + a + " and " + b + " in the Server");
        return a + b;
    }

    public int subtract(int a, int b) throws RemoteException {
        System.out.println("Subtracting " + a + " and " + b + " in the Server");
        return a - b;
    }

    public int multiply(int a, int b) throws RemoteException {
        System.out.println("Multiplying " + a + " and " + b + " in the Server");
        return a * b;
    }

    public int test(int a) {
        System.out.println("This is a test method");
        return 0;
    }

    public int divide(int a, int b) throws RemoteException {

        System.out.println("Dividing " + a + " and " + b + " in the Server");

        // ============================================================
        // Uncomment this loop to observe blocking behavior
        // This simulates a long-running task
        // ============================================================
        /*
        for(double i = 0; i < 10000000000000000.0; i++){
            System.out.println("I'm doing something that takes a long time.");
        }
        */

        // Division by zero check
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }

        return a / b;
    }

    public static void main(String[] args) {

        // Set the security policy file
        System.setProperty("java.security.policy", "file:allowall.policy");

        try {

            // Create server object (Singleton instance)
            MathServer svr = new MathServer();

            // Get registry
            Registry registry = LocateRegistry.getRegistry();

            // Bind server object to registry
            registry.bind("CalculatorService", svr);

            System.out.println("Service started....");

        } catch (RemoteException re) {
            System.err.println(re.getMessage());
        } catch (AlreadyBoundException abe) {
            System.err.println(abe.getMessage());
        }
    }
}
