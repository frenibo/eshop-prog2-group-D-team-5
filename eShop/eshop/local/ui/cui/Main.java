package eshop.local.ui.cui;

import java.io.IOException;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.Sitzung;

public class Main {
	
	public static void main(String[] args) throws ArtikelExistiertBereitsException {
		Sitzung stz;
		try {
			stz = new Sitzung("BST_B.txt", "USR_B.txt", "RCH_B.txt", "EVT_B.txt");
			Sitzung.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	//TODO: save & quit, Ereigniselement, remove systemout von Verwaltungsklassen, remove saves from running program, 
	// Sitzung in eShop umbenennen, eShopArtikelFunktionen + eShopUserFunktionen usw.
	//Sitzungsnummer zu valueobject machen, dass nicht mehr pro eShop bzw Sitzungs-Objekt erstellt wird, sondern pro Warenkorb
	//Eingabeverarbeitung resistent machen und zurück gehen ermöglichen.
	//Warum muss jede Methode in Sitzung/eShop static sein?
	//Sitzung.speichern();
}