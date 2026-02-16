SE3020 – Distributed Systems
Lab 2 – Java RMI

Student ID: IT23594586
Name: Hanaan M.F.A.S

------------------------------------------------------------
Lab Objective
------------------------------------------------------------

The objective of this lab is to understand Java RMI (Remote Method Invocation), 
how remote objects are created, how clients communicate with the server, 
and how concurrency is handled in RMI.

------------------------------------------------------------
Step 8 – Long Running Loop Observation
------------------------------------------------------------

The divide() method was modified by uncommenting a long running loop.

Observation:

• When one client calls the divide() method, that client blocks until 
  the method execution finishes.
• Other clients can still connect and invoke other methods.
• This shows that RMI creates a separate thread for each client request.
• Communication in RMI is synchronous and blocking.
• The client waits until the server completes the method execution.

Conclusion:

The RMI server is multi-threaded and can handle multiple clients concurrently.
However, each client call is synchronous.

------------------------------------------------------------
Step 9 – Client Count Feature
------------------------------------------------------------

A global variable was added to MathServer class:

    private int clientCount = 0;

A new remote method was added:

    public synchronized int incrementClientCount() throws RemoteException

This method:
• Increments the client count.
• Prints the current client count.
• Returns the updated count to the client.

The client calls incrementClientCount() before performing calculations.

When multiple clients are started, the count increases globally:

Client 1 -> Count = 1
Client 2 -> Count = 2
Client 3 -> Count = 3

------------------------------------------------------------
Thread Safety Explanation
------------------------------------------------------------

The incrementClientCount() method was declared as synchronized.

Reason:

• Multiple clients can connect at the same time.
• All threads share the same clientCount variable.
• Without synchronization, race conditions may occur.
• Two threads may update the value simultaneously causing incorrect results.

Using synchronized ensures:

• Only one thread can update the shared variable at a time.
• The client count is always accurate.
• Thread safety is maintained.

------------------------------------------------------------
Step 10 – Server Object Instantiation
------------------------------------------------------------

The client count feature shows that the server object uses 
Singleton instantiation.

Reason:

• Only one MathServer object is created.
• All clients share the same object.
• The clientCount variable is shared among all clients.
• The count increases globally.

------------------------------------------------------------
How to Make Per Client Instantiation
------------------------------------------------------------

To make the server per-client:

• Create a Factory class.
• Bind the factory object in the RMI registry.
• Each client requests a new MathServer instance from the factory.
• Each client will then get a separate server object.
• Shared variables will not be shared anymore.

------------------------------------------------------------
How to Make Per Call Instantiation
------------------------------------------------------------

To make the server per-call:

• Create a new object for every remote method call.
OR
• Use RMI Activation framework.

In this case:

• No shared state exists.
• Each method call works independently.




------------------------------------------------------------
Conclusion
------------------------------------------------------------

This lab demonstrates:

• How Java RMI works.
• How remote objects are registered and accessed.
• How RMI handles multiple clients using threads.
• The importance of thread safety in distributed systems.
• Different object instantiation models in RMI 
  (Singleton, Per Client, Per Call).

