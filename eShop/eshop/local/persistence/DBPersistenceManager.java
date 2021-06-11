package eshop.local.persistence;

import java.io.IOException;

import eshop.local.valueobjects.Artikel;
import eshop.local.valueobjects.Inputevent;
import eshop.local.valueobjects.Lagerungsevent;
import eshop.local.valueobjects.Rechnung;
import eshop.local.valueobjects.User;
import eshop.local.valueobjects.Sitzung;

public class DBPersistenceManager implements PersistenceManager {

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Artikel ladeArtikel() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void openForReading(String datenquelle) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void openForWriting(String datenquelle) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean speichereArtikel(Object a) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User ladeUser() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereUser(User u) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Rechnung ladeRechnung() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereRechnung(Rechnung r) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Inputevent ladeInputevents() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereInputevents(Inputevent e) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Lagerungsevent ladeLagerungsevents() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereLagerungsevents(Lagerungsevent e) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sitzung ladeSitzungen() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean speichereSitzungen(Sitzung e) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}