package eshop.local.valueobjects;

public class Thing implements ValueobjectInterface {

	String name;
	int nummer;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;		
	}
	
	public int getNummer() {
		return this.nummer;
	}
	
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	
	public boolean copy(Object anderesThing) {
		if(anderesThing instanceof ValueobjectInterface) {
			return true;
		}
		else return false;
	}
}
