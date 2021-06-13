package eshop.local.client;
import java.io.*;
import java.net.Socket;
	
public class eShopFassade implements eShopInterface{
	private Socket s = null;
	private BufferedReader sin; 
	private PrintStream sout; 
	
	public eShopFassade(String h, int p) {
		try {
			s = new Socket(h, p);
			sin = new BufferedReader(new InputStreamReader(s.getInputStream()));
			sout = new PrintStream(s.getOutputStream());
			System.out.println("ServerMsg:"+sin.readLine());  // Begr��ung durch Server auf Konsole ausgeben
		} catch (Exception e) {
        		System.err.println("Fehler ---> " + e.getMessage());
	    }
		
	}
	 // alle Methoden des Interface implementieren: 
	
	public String echo(String s) throws Exception {
		try {
			sout.println(s);		// schreibe s an Server
			return sin.readLine();	// lese von Server und gibt das als return zur�ck
		} catch (IOException e) {}
		return null;
	}
}
