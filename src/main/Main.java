package main;

import java.util.Scanner;

import javax.swing.UIManager;

// magic witch select 4
// can select unit after selecting
//can attack friendly unit/blank square
//if it kills itself, don't crash.
//don't use template
//This is a test

public class Main {

	private static final Scanner inputScanner;

	static {
		inputScanner = new Scanner(System.in);
		try {
			// UIManager.LookAndFeelInfo[] looks =
			// UIManager.getInstalledLookAndFeels();
			// javax.swing.plaf.metal.MetalLookAndFeel
			// javax.swing.plaf.nimbus.NimbusLookAndFeel
			// com.sun.java.swing.plaf.motif.MotifLookAndFeel
			// com.sun.java.swing.plaf.windows.WindowsLookAndFeel
			// com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		boolean test = true;
		if (test) {
			Main.test();
			return;
		}
	}

	public static String getStringInput() {
		synchronized (Main.inputScanner) {
			return Main.inputScanner.nextLine();
		}
	}

	public static int getIntInput() {
		synchronized (Main.inputScanner) {
			return Main.inputScanner.nextInt();
		}
	}

	public static synchronized void print(String s) {
		System.out.println(Thread.currentThread() + ": " + s);
	}

	public static void test() {
		Thread servThread = new Thread() {
			@Override
			public void run() {
				Server.main("");
			}
		};
		Thread client1Thead = new Thread() {
			@Override
			public void run() {
				Client.main("localhost", "uk246", "password");
			}
		};
		Thread client2Thead = new Thread() {
			@Override
			public void run() {
				Client.main("localhost", "akarsh", "password");
			}
		};
		servThread.start();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		client1Thead.start();
		client2Thead.start();
	}
}