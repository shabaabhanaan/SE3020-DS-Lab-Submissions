SE3020 – Lab 2: Java RMI  

Student Name: M.F.A.S Hanaan 
Registration ID: IT23594586 


1. Thread Safety in Client Count

In the MathServer class, we added a global variable to keep track of the number of connected clients:

private int clientCount = 0;

To safely update this shared variable when multiple clients connect concurrently, we implemented the incrementClientCount() method as synchronized:

public synchronized int incrementClientCount() throws RemoteException {
    clientCount++;
    return clientCount;
}

Explanation:  
- The synchronized keyword ensures that only one thread at a time can execute this method.  
- Without synchronization, if two clients call this method at the same time, both could read the same value, increment it, and write it back incorrectly (race condition).  
- By synchronizing the method, each client’s connection is processed atomically, ensuring the correct client count.


2. Server Object Instantiation Analysis

The behavior of the server with the client count feature shows that the MathServer object is a Singleton. This means that one server object is created and shared among all clients.  

- Singleton: One server object handles all clients, and shared variables like clientCount are visible to all clients. This is the current implementation in the lab.  
- Per Client: Each client would have a separate server object, so the client count would be maintained separately for each client. To implement this, you could create a new MathServer object for each client and bind each object to the RMI registry with a unique name.  
- Per Call: Each remote method call would create a new MathServer object, so no shared state would exist between calls. This can be implemented using UnicastRemoteObject.exportObject() for each call.

Conclusion:  
- The current MathServer is Singleton, which allows shared variables to be accessed by all clients.  
- Synchronization is necessary to maintain thread safety when multiple clients access shared resources concurrently.
