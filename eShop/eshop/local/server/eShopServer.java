package eshop.local.server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.eShop;

public class eShopServer  {
	int port = 123;  // f�r diese Test-Anwendung...
	ServerSocket listenSocket=null; 
	
	eShopInterface eS;	// EIN Objekt f�r alle Anfragen
	
	public eShopServer() throws IOException, ArtikelExistiertBereitsException {
		eS = new eShop();	// EchoDienst "implementiert" EchoDienstInterface, also ok. 
		
		System.out.println("--> S: Server versucht zu starten");
        try { 
        	listenSocket = new ServerSocket(port); 
        }
        catch (IOException e) {
			System.out.println("-->S: Eine Ausnahme trat beim Anlegen des Server Sockets auf:"+e);
		}        
		System.out.println("Server lauscht auf Port " + port);
    }
	
	//...
	public void run() {
		try {
		
			// Ein Server-Objekt wartet auf Verbindungsanfrage durch einen (Client-)Prozess.
			while(true) {
				//Das I/O dieser Verbindungs wird �ber ein Socket 
				// = (Datenstrom zwischen Prozessen) realisiert.
				Socket client_socket = listenSocket.accept(); // wartet hier
				new Connection(client_socket, eS);  // jede Connection bekommt gleichen "eS" mit
			}
		}
		catch (IOException e) {
			System.out.println("-->S: "+e+ "Fehler waehrend des Lauschens auf Verbindungen");
		}
	}

	public static void main (String[] args) throws IOException, ArtikelExistiertBereitsException{
		new eShopServer().run();
	}
}
