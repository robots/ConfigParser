


import java.util.LinkedList;
import java.util.List;

public class IniOption {
	private boolean isList;
	private OptionType type;
	private boolean mandatory;
	private List<Element> defaultValue;
	private List<Element> value;
	private String enumName;
	private char delimiter;
	
	public IniOption(OptionType type, boolean isList) {
		this.defaultValue = new LinkedList<Element>();
		this.mandatory = false;
		this.type = type;
		this.isList = isList;
	}
	
	public IniOption(OptionType type, boolean isList, boolean mandatory) {
		this.defaultValue = new LinkedList<Element>();
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

	public void setDefaultValue(Element defaultValue) {
		this.defaultValue.clear();
		this.defaultValue.add(defaultValue);
	}

	public Element getDefaultValue() {
		if (!this.isList)
			return defaultValue.get(0);
		
		return null;
	}
	
	public void setDefaultValues(List<Element> defaultValues) {
		this.defaultValue = defaultValues;
	}

	public List<Element> getDefaultValues() {
		return this.defaultValue;
	}

	public Element getValue() {
		if (!this.isList)
			return value.get(0);

		return null;
	}

	public void setValue(Element value) {
		this.value.clear();
		this.value.add(value);
	}
	
	public List<Element> getValues()
	{
		return this.value;
	}
	
	public void setValues(List<Element> values)
	{
		this.value = values;
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

}
