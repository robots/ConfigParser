


import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class IniOption {
	private boolean isList;
	private OptionType type;
	private boolean mandatory;
	private List<Element> defaultValueList;
	private List<Element> valueList;
	private String enumName;
	private char delimiter;
	
	public IniOption(OptionType type, boolean isList) {
		this.defaultValueList = new LinkedList<Element>();
		this.mandatory = false;
		this.type = type;
		this.isList = isList;
	}
	
	public IniOption(OptionType type, boolean isList, boolean mandatory) {
		this.defaultValueList = new LinkedList<Element>();
		this.mandatory = mandatory;
		this.type = type;
		this.isList = isList;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public void setDefaultElement(Element defaultValue) {
		this.defaultValueList.clear();
		this.defaultValueList.add(defaultValue);
	}

	public Element getDefaultElement() {
		if (!this.isList)
			return defaultValueList.get(0);
		
		return null;
	}
	
	public void setDefaultElementList(List<Element> defaultValues) {
		this.defaultValueList = defaultValues;
	}

	public List<Element> getDefaultElementList() {
		if(this.isList)
			return this.defaultValueList;
		
		return null;
	}

	public Element getElement() {
		if (!this.isList)
			return valueList.get(0);

		return null;
	}

	public void setElement(Element value) {
		this.valueList.clear();
		this.valueList.add(value);
	}
	
	public List<Element> getElementList()
	{
		if(this.isList)
			return this.valueList;
		
		return null;
	}
	
	public void setElementList(List<Element> values)
	{
		this.valueList = values;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getEnumName() {
		return enumName;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public char getDelimiter() {
		return delimiter;
	}
	
	public boolean getValueBool() throws BadTypeException {
		if(this.type != OptionType.BOOLEAN)
			throw new BadTypeException("Requested option is not of type Boolean");
		
		return Boolean.parseBoolean(getElement().getValue());
	}
	
	public BigInteger getValueSigned() throws BadTypeException {
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		return new BigInteger(getElement().getValue());
	}
	
	public BigInteger getValueUnsigned() throws BadTypeException {		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of type Unsigned");
		
		return new BigInteger(getElement().getValue());
	}
	
	public float getValueFloat() throws BadTypeException {
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of type Float");
		
		return Float.parseFloat(getElement().getValue());
	}
	
	public String getValueEnum() throws BadTypeException {
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		
		return getElement().getValue();
	}
	
	public String getValueString() throws BadTypeException {
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of type String");
		
		return getElement().getValue();
	}
	
	public String getValueUntyped() {
		return getElement().getValue();
	}
	
	
	public void setValue(String value) {
		this.valueList.clear();
		this.valueList.add(new Element(value));
	}
	
	public void setValue( BigInteger value) {
		this.setValue(value.toString());
	}
	
	public void setValue(float value)
	{
		this.setValue(Float.toString(value));
	}
	
	public void setValue(boolean value)
	{
		this.setValue(Boolean.toString(value));
	}
	public void accept(IniVisitor visitor){
		visitor.visit(this);		
	}

}
