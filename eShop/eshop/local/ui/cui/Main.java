package eshop.local.ui.cui;

import java.io.IOException;

import eshop.local.ui.Sitzung;

public class Main {
	
	public static void main(String[] args) {
		Sitzung stz;
		try {
			stz = new Sitzung("BST_B.txt");
			Sitzung.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	//TODO: login, registrieren, Warenkorb-cleanup, Ein-Auslagerung-Eventmanager, Warenkorb (komplett) leeren, 

}


