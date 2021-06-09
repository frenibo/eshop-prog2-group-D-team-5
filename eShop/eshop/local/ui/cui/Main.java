package eshop.local.ui.cui;

import java.io.IOException;

import eshop.local.domain.exceptions.ArtikelExistiertBereitsException;
import eshop.local.ui.eShop;

public class Main {
	
	public static void main(String[] args) throws ArtikelExistiertBereitsException {
		eShop esp;
		try {
			esp = new eShop("BST_B.txt", "USR_B.txt", "RCH_B.txt", "INP_B.txt", "LAG_B.txt", "LAG_TEMP_B.txt");
			eShop.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
	}
	
	//TODO: save & quit, Ereigniselement, remove saves from running program, 
	// Sitzung in eShop umbenennen, eShopArtikelFunktionen + eShopUserFunktionen usw.
	//Sitzungsnummer zu valueobject machen, dass nicht mehr pro eShop bzw Sitzungs-Objekt erstellt wird, sondern pro Warenkorb
	//Warum muss jede Methode in Sitzung/eShop static sein?
	//Sitzung()
}