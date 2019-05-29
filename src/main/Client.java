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
				System.out.println("login");
				System.out.println("logout");
				System.out.println("registeruser");
				System.out.println("deleteuser");
				System.out.println("listusername");
				System.out.println("username [new username]");
				System.out.println("password [new password]");
				System.out.println("help");
				System.out.println("exit");
			} else if (inp.equals("listserver")) {
				System.out.println(serverIP);
			} else if (inp.startsWith("server")) {
				String[] inps = inp.split(" ");
				setServer(inps[1]);
			} else if (inp.equals("connect")) {
				connect();
			} else if (inp.equals("disconnect")) {
				disconnect();
			} else if (inp.equals("login")) {
				login();
			} else if (inp.equals("logout")) {
				logout();
			} else if (inp.equals("registeruser")) {
				registerUser();
			} else if (inp.equals("deleteuser")) {
				deleteUser();
			} else if (inp.equals("listusername")) {
				System.out.println(username);
			} else if (inp.startsWith("username")) {
				String[] inps = inp.split(" ");
				setUsername(inps[1]);
			} else if (inp.startsWith("password")) {
				String[] inps = inp.split(" ");
				setPassword(inps[1]);
			}

			inp = Main.getStringInput();
		} while (!inp.equals("exit"));
	}

	public void setServer(String serverIP) {
		this.serverIP = serverIP;
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

	public void disconnect() {
		connectedToServer = false;
		loggedInToServer = false;
	}

	public void login() {
		if (!connectedToServer) {
			System.out.println("Cannot login because not connected to server");
			return;
		}
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

	public void logout() {

	}

	public void registerUser() {

	}

	public void deleteUser() {

	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
