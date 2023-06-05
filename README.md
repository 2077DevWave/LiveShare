# LiveShare

A Live Share Application developed based on Socket (TCP) to allows clients within a local area network (LAN) to send and receive messages between each other.

## Features

- The LiveShare App includes the following features:

    - Sending and receiving messages between clients
    - Real-time updates for new messages
    - User-friendly interface
    - Create and mannage groups
    - Send and recieve message in groups
    - Define client role as : User, Permium user and Admin

## Technologies and Tools

- The following technologies are used in Application to work currectly:

    - **TCP connection** ( java.net.ServerSocket )
    - **Json decode and encode module** ( java.org.json )
    - **JFrame** ( javax.swing.JFrame )
    - **MYSQLConnectorJ** ( java.sql.Connection )

## Architecture

- in LiveShare architecture The server acts as a central hub to facilitating and transfer message between clients. Here's an overview of the components:

    - Server: handle new Request incoming from client and send responde bakc into client
    - Clients: Connect into a Server to send Request and recieve Responde from that

## Setup and Installation

- To set up the LiveShare App, follow these steps:

    - Clone the repository: Start by cloning the repository from GitHub using the following command:
     
      ``` 
      git clone https://github.com/2077DevWave/LiveShare/
      ```
      
    - Install Java: Install java Environment to run the project ( [Download](https://www.oracle.com/java/technologies/downloads/) )

    - Configure the server: change file Config in server folder (LiveShare/server/config.java) and change option into suitable value

    - Configure the DataBase: create a mysql server and change ` MYSQL_IPV4 | MYSQL_PORT | MYSQL_DATABASE | MYSQL_USERNAME | MYSQL_PASSWORD ` in Config file (LiveShare/server/config.java)

    - Build and run the server: Compile and run the server application, which will listen for client connections and manage message transfer.

    - Build and run the client: Compile and run the client application, providing the server's IP address and port number as configuration parameters in ClientDemo (LiveShare/ClientDemo).

    - Test the app: run the app and test it.

## Class Overview

In the LiveShare App, several classes are used to manage the different components and functionalities. Here's an overview of the main classes:

### Server Package

  - Description: include all server side classes
  
  - Classes:
      - Admin:
        
        - Description: this class provide an object for user with Admin rule, its extends PremiumUser and can be cast into User and PremiumUser.
        - Feature: this class can see message in any group
          
      - Authenticator:
        
        - Description: After receiving a new connection, this class starts working and verifies the new connections using database information.
          
      - Client:
        
        - Description: This class is used to manage logged-in connections and users who have an active connection.
          
      - Config:
        
        - Description: It is easy to change server settings using this class. Some database information is stored in this file, so refrain from sharing it and ensure appropriate access permissions.
        
      - Group:
        
        - Description: This class is used to create an object from a group. It inherits from the Room class and is used for larger communities, allowing simultaneous messaging to multiple individuals.

      - LiveShare:
        
        - Description: The main class of the software runs on a separate thread and receives and manages incoming connections.

      - LiveShareDB:
        
        - Description: This class is used for communication between the software and the database. It inherits from the Database class and inherits its communication capabilities.

      - PremiumUser:
        
        - Description: The objects of this class have the same capabilities as the User class and can join a group or create a new one.

      - Request:
        
        - Description: All requests sent from the server are defined in this class.

      - RequestHandler:
        
        - Description: All requests sent to the server are received and managed in this class, which is executed as multiple threads.

      - Room:
        
        - Description: This class is used to create a room consisting of two users. The maximum number of users that can be in a room is two, and it is used for communication between room users.

     - User:
        
        - Description: This class is designed to define regular users and the tasks they can perform.

### Client Package

- Description: include all Client side classes

- Classes:
    - Chatroom:
    
      - Description: A user interface is used to display received messages and send messages graphically, where each object of it is used for a group or a room.
    
    - Dashboard:
    
      - Description: The dashboard includes options to utilize software features, such as displaying groups, creating groups, joining groups, etc.
     
    - GroupList:
    
      - Description: A graphical user interface is used to retrieve and display the list of groups the user is a part of.
    
    - LoginPage:
    
      - Description: Initially, this user interface is used for user authentication.
    
    - Request:
    
      - Description: All requests sent from the user to the server are defined in this class.
    
    - RequestHandler:

      - Description: It is used to manage incoming packets from the server. This class receives the packets and utilizes them within the software based on their types.

### Lib Package

- Description: 

- Classes:
    
    - DataBase:
    
      - Description: This class is used to establish a connection with the database and has functions for data insertion and selection from the database.
    
    - Error:
    
      - Description: All custom errors used in the software, which are manually created, exist in this class.
    
    - Logger:
    
      - Description: To provide a better and simpler display of all server operations, this class is created to write logs in it by receiving a log stream.
    
    - RequestType:
   
      - Description: The types of requests sent and received between the server and the user are defined in this class.
   
    - Secure:
      
      - Description: For increased software security, this class is defined to encrypt the data before sending it and decrypt it upon receiving.
   
    - SocketPacketHandler:

       - Description: This class is used for receiving and sending packets on both the user and server sides.

### Demo

- Description: This class handles the user interface of the LAN Message Transfer App.

- Classes:
    
    - ClientDemo:
    
      - Description: To create an instance of a user, you can execute this class.
    
    - LiveShareDemo:
    
      - Description: To create a new server, you can execute this class.
    
    - test:
    
      - Description: This class can be used to test the software's capabilities.
    

## Conclusion

This software is truly a powerful tool for reducing traffic in companies, organizations, and universities.
It can significantly reduce the outgoing traffic volume from these institutions. Additionally, by encrypting data and transmitting it within the internal network, this software prevents data leakage outside the network and unauthorized access to this information.
As a result, both the security and speed of data transmission within the network are improved.
