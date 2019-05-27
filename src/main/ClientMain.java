package main;
import java.io.*;
import java.net.*;

public class ClientMain {
	
	BufferedReader connection;
	
	public void connect(){
		try{
			Socket socket = new Socket("",); // conect to server
			
			connection = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
	}
	
	public void read
	
	public static void main(String[] args) {
		ClientMain client = new ClientMain();
		client.connect();
	}

}
