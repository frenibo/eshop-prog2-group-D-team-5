package eshop.local.domain.exceptions;

import eshop.local.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {

		private Artikel artikel;
		
		/**
		 * Konstruktor
		 * 
		 * @param buch Das bereits existierende Buch
		 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
		 */
		public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
			super("Artikel mit Namen " + artikel.getName() + " und Nummer " + artikel.getNummer() 
					+ " existiert bereits" + zusatzMsg);
			this.artikel = artikel;
		}

		public Artikel getArtikel() {
			return artikel;
		}
}