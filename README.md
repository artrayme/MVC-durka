# MVC-durka

## Overview 
This application demonstrate how to connect two java applications using standard java-sockets.

Server provides information about patients in some hospital. Each patient has the same structure (see file data_example.xml).
The client app connected to the server and display requested data.

## How to start 
### Server 
You can start the server by running the Main class or by running the gradle task 
```bash
cd server
gradle run
```
Then you can set the port and click on start button to start the server.

### Client 
You can start the client by running the gui/Main class or by running the gradle task
```bash
cd client
gradle run
```
Then you can set the port and click first button on the toolbar to connecting to the server.
