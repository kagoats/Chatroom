package main;

import java.io.IOException;
import java.net.Socket;

import encryption.RSA;
import encryption.SHA256;
import io.Communication;

public class Client {

	public Communication lobbyServComm;
	
	private RSA myRSA;
	private RSA servRSA;

	private String serverIP;
	private String username;
	private String password;

	private boolean connectedToServer;
	private boolean loggedInToServer;

	public Thread commandThread;

	public Client(String serverIP, String username, String password) {
		this.serverIP = serverIP;
		this.username = username;
		this.password = password;

		connectedToServer = false;
		loggedInToServer = false;
	}

	public static void main(String... args) {
		String serverIP = null;
		String name = null;
		String password = null;
		try {
			serverIP = args[0];
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("Type in serverIP: ");
			serverIP = Main.getStringInput();
		}
		try {
			name = args[1];
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("Type in your username: ");
			name = Main.getStringInput();
		}
		try {
			password = args[2];
		} catch (NullPointerException | IndexOutOfBoundsException e) {
			System.out.println("Type in your password: ");
			password = Main.getStringInput();
		}
		Client client = new Client(serverIP, name, password);
		client.startClient();
	}

	public void startClient() {
		this.commandThread = Thread.currentThread();

		String inp = "";
		do {
			if (inp.equals("help")) {
				System.out.println("listserver");
				System.out.println("server [new server]");
				System.out.println("connect");
				System.out.println("disconnect");
				System.out.println("listusername");
				System.out.println("username [new username]");
				System.out.println("password [new password]");
				System.out.println("help");
				System.out.println("exit");
			} else if (inp.equals("listserver")) {

			} else if (inp.equals("connect")) {

			} else if (inp.equals("disconnect")) {

			}

			inp = Main.getStringInput();
		} while (!inp.equals("exit"));
	}

	public void connect() {
		if (connectedToServer) {
			System.out.println("Already connected to server!");
			return;
		}
		try {
			Socket sock = new Socket(this.serverIP, LobbyServer.LOBBY_PORT);

			this.lobbyServComm = new Communication(sock);

			if (!LobbyServer.INIT_LOBBY_STRING.equals(this.lobbyServComm.recieveObject())) {
				System.err.println("Could not verify server");
				return;
			}

			connectedToServer = true;

			System.out
					.println("Client successfully connected to " + this.serverIP + ":" + LobbyServer.LOBBY_PORT + "!");

		} catch (IOException e) {
			System.err.println("Something went wrong when connecting to server!");
			e.printStackTrace();
			connectedToServer = false;
		}
	}

	public void login() {
		String usernameHash = SHA256.bytesToHex(SHA256.hash(this.username));
		this.lobbyServComm.sendObject(usernameHash);
		String passwordHash = SHA256.bytesToHex(SHA256.hash(this.password));
		this.lobbyServComm.sendObject(passwordHash);

		boolean success = (boolean) this.lobbyServComm.recieveObject();
		if (!success) {
			System.err.println("Could not login to server - invalid login");
			return;
		}
	}

}
