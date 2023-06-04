# LiveShare
A Live Share Application developed based on Socket (TCP) to transfer messages between devices in a local network (LAN)
LAN Message Transfer App
Introduction

The LAN Message Transfer App is an application that allows clients within a local area network to send and receive messages between each other. This document serves as a guide for setting up and developing the app.
Features

The LAN Message Transfer App includes the following features:

Discovery of other clients on the LAN
Sending and receiving messages between clients
Real-time updates for new messages
User-friendly interface

# Technologies and Tools

The following technologies and tools are recommended for developing the LAN Message Transfer App:

    Programming Language: Choose a language that supports network communication, such as Python, Java, or C#.
    Networking: Familiarity with socket programming or a network communication library (e.g., Socket.io in JavaScript).
    User Interface: Choose a suitable framework or library for creating the user interface (e.g., PyQt for Python, JavaFX for Java, or WPF for C#).
    Version Control: Git for source code management.
    IDE: An integrated development environment (IDE) for the chosen programming language.

# Architecture

The LAN Message Transfer App can be built using a client-server architecture. The server acts as a central hub, facilitating message transfer between clients. Here's an overview of the components:

    Server: Responsible for handling client connections, managing message transfer, and broadcasting messages to connected clients.
    Clients: Connect to the server to send and receive messages.

# Setup and Installation

To set up the LAN Message Transfer App, follow these steps:

    Clone the repository: Start by cloning the repository from GitHub using the following command:

bash

git clone <repository_url>

Install dependencies: Install any required dependencies or libraries specified in the project's documentation.

Configure the server: Set up the server's IP address and port number, ensuring it is accessible within the LAN.

Build and run the server: Compile and run the server application, which will listen for client connections and manage message transfer.

Build and run the client: Compile and run the client application, providing the server's IP address and port number as configuration parameters.

Test the app: Verify that the clients can connect to the server, send messages, and receive real-time updates.

# Conclusion

The LAN Message Transfer App is a versatile tool that enables clients on a local area network to communicate with each other. By following the steps outlined in this document, you can get started with the development of this app. Feel free to customize the app's functionality and design to suit your specific requirements.

Please note that this document provides only a high-level overview of the app's development process. Detailed implementation steps and code examples are beyond the scope of this document.

Best of luck with your project! If you have any further questions, feel free to ask.
