package eshop.local.valueobjects;

public class Thing implements Valueobject {

	String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;		
	}
	
	public boolean copy(Object anderesThing) {
		if(anderesThing instanceof Valueobject) {
			return true;
		}
		else return false;
	}
}
