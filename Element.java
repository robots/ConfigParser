

public class Element {

	private String value;
	
	public Element() {}
	
	public Element(String value)
	{
		this.value = value;
	}
	
	public boolean isReference() {
		// TODO: regexp  "${[^#]*#[^}]*}
		
		return false;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
