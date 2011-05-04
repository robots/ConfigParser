

import java.math.BigInteger;

public class Element {

	private String value;
	
	public boolean getValueBool() {
		return Boolean.parseBoolean(value);
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public BigInteger getValueSigned() {
		return new BigInteger(value);
	}
	
	public BigInteger getValueUnsigned() {
		return new BigInteger(value);
	}
	
	public float getValueFloat() {
		return Float.parseFloat(value);
	}
	
	public String getValueEnum() {
		return value;
	}
	
	public String getValueString() {
		return value;
	}
	
	public boolean isReference() {
		// TODO: regexp  "${[^#]*#[^}]*}
		
		return false;
	}
}
