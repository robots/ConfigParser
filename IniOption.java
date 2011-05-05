


import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class IniOption {
	private String name;
	private boolean isList;
	private OptionType type;
	private boolean mandatory;
	private List<Element> defaultValueList;
	private List<Element> valueList;
	private String enumName;
	private char delimiter;
	
	private void init(String name, OptionType type, boolean isList )
	{
		this.name = name;
		this.defaultValueList = new LinkedList<Element>();
		this.mandatory = false;
		this.type = type;
		this.isList = isList;
	}
	
	public IniOption(String name, OptionType type, boolean isList) {
		init(name, type, isList);
	}
	
	public IniOption(String name, OptionType type, boolean isList, boolean mandatory) {
		init(name, type, isList);
		this.mandatory = mandatory;
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
		
		return Boolean.parseBoolean(this.getValue());
	}
	
	public BigInteger getValueSigned() throws BadTypeException {
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		return new BigInteger(this.getValue());
	}
	
	public BigInteger getValueUnsigned() throws BadTypeException {		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of type Unsigned");
		
		return new BigInteger(this.getValue());
	}
	
	public float getValueFloat() throws BadTypeException {
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of type Float");
		
		return Float.parseFloat(this.getValue());
	}
	
	public String getValueEnum() throws BadTypeException {
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		
		return this.getValue();
	}
	
	public String getValueString() throws BadTypeException {
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of type String");
		
		return this.getValue();
	}
	
	public String getValueUntyped() throws BadTypeException {
		return getValue();
	}
	
	public List<Boolean> getValueListBool() throws BadTypeException {
		
		if(this.type != OptionType.BOOLEAN)
			throw new BadTypeException("Requested option is not of type Boolean");
		
		List<Element> elementValues = this.getValueList();
		List<Boolean> resultList = new LinkedList<Boolean>();
		
		for(Element element : elementValues)
		{
			resultList.add(Boolean.parseBoolean(element.getValue()));
		}
		
		return resultList;
		
	}

	public List<String> getValueListString() throws BadTypeException {
		
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of type String");
		
		List<Element> elementValues = this.getValueList();
		List<String> resultList = new LinkedList<String>();
		
		for(Element element : elementValues)
		{
			resultList.add(element.getValue());
		}
		
		return resultList;
		
	}

	public List<Float> getValueListFloat() throws BadTypeException {
		
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of type Float");
		
		List<Element> elementValues = this.getValueList();
		List<Float> resultList = new LinkedList<Float>();
		
		for(Element element : elementValues)
		{
			resultList.add(Float.parseFloat(element.getValue()));
		}
		
		return resultList;
		
	}

	public List<BigInteger> getValueListSigned() throws BadTypeException {
		
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		List<Element> elementValues = this.getValueList();
		List<BigInteger> resultList = new LinkedList<BigInteger>();
		
		for(Element element : elementValues)
		{
			resultList.add( new BigInteger(element.getValue()));
		}
		
		return resultList;
		
	}
	
	public List<BigInteger> getValueListUnsigned() throws BadTypeException {
		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of type Unsigned");
		
		List<Element> elementValues = this.getValueList();
		List<BigInteger> resultList = new LinkedList<BigInteger>();
		
		for(Element element : elementValues)
		{
			resultList.add( new BigInteger(element.getValue()));
		}
		
		return resultList;
		
	}
	
	public List<String> getValueListEnum() throws BadTypeException {
		
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		
		List<Element> elementValues = this.getValueList();
		List<String> resultList = new LinkedList<String>();
		
		for(Element element : elementValues)
		{
			resultList.add(element.getValue());
		}
		
		return resultList;
		
	}
	
	public void setValue(String value) {
		this.valueList.clear();
		this.valueList.add(new Element(value));
	}
	
	public void setValue( BigInteger value) {
		this.setValue(value.toString());
	}
	
	public void setValue(float value) {
		this.setValue(Float.toString(value));
	}
	
	public void setValue(boolean value)	{
		this.setValue(Boolean.toString(value));
	}
	
	public void accept(IniVisitor visitor){
		visitor.visit(this);		
	}

	public String getName() {	
		return name;
	}
	
	public OptionType getType() {
		return this.type;
	}
	
	private String getValue() throws BadTypeException
	{
		if(this.isList())
			throw new BadTypeException("List option accessed as single-value option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;
		
		if(listVal == null)
			return null;
		
		return listVal.get(0).getValue();
		
	}

	private List<Element> getValueList() throws BadTypeException {
		
		if( ! this.isList())
			throw new BadTypeException("Single-value option accessed as list option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;
		
		return listVal;
		
	}
	
	public boolean isList()
	{
		return this.isList;
	}
}
