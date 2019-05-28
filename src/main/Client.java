package main;

import java.io.IOException;
import java.net.Socket;

import io.Communication;

public class Client {

	// GOAL FOR CURRENT: finish basic challenge stuff

	public Communication lobbyServComm;

	public long randomSeed;

	public String serverIP;
	public String name;
	public String password;

	public Client(String serverIP, String name, String password) {
		this.serverIP = serverIP;
		this.name = name;
		this.password = password;
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
		client.start();
	}

	public void start() {
		try {
			Socket sock = new Socket(this.serverIP, LobbyServer.LOBBY_PORT);
			System.out.println("Client connected to " + this.serverIP + ":" + LobbyServer.LOBBY_PORT + "!");

			this.lobbyServComm = new Communication(sock);

			if (!LobbyServer.INIT_LOBBY_STRING.equals(this.lobbyServComm.recieveObject())) {
				System.err.println("Could not verify server");
				return;
			}
			this.lobbyServComm.sendObject(this.name);
			this.lobbyServComm.sendObject(this.password);

			boolean success = (boolean) this.lobbyServComm.recieveObject();
			if (!success) {
				System.err.println("Could not connect to server - invalid login");
				return;
			}

			while (true) {

				Object obj = this.lobbyServComm.recieveObject();
				if (obj.getClass() == String[].class) {
					String[] lobbyNames = (String[]) obj;

					System.out.println("In lobby server: ");
					for (String s : lobbyNames) {
						System.out.println("\t" + s);
					}
					System.out.println("\n");
				} else {
					String oppName = (String) obj;
					System.out.println("Matched with " + oppName);
					break;
				}
			}

			int gamePort = (int) this.lobbyServComm.recieveObject();

			this.newGame(this.serverIP, gamePort, this.name);

		} catch (Exception e) {
			throw new RuntimeException("Something went wrong with client", e);
		}
	}

	public void clickedName(String name) {
		this.lobbyServComm.sendObject(name);
	}

	public void newGame(String gameServerIP, int gameServerPort, String name) {
		try {
			Socket gameSock = new Socket(gameServerIP, gameServerPort);
			Communication gameServComm = new Communication(gameSock);

			gameServComm.sendObject(name);

			if (!GameServer.INIT_GAME_STRING.equals(gameServComm.recieveObject())) {
				System.err.println("Could not verify game server");
				return;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
