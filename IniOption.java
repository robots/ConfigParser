


import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Trida reprezentujici jednu volbu v .ini souboru
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class IniOption {
	/**
	 * Nazev volby
	 */
	private String name;
	
	/**
	 * Urcuje, zda je volba seznamem (true), nebo ma pouze jedinou hodnotu (false)
	 */
	private boolean isList;
	/**
	 * Typ hodnot teto volby
	 */
	private OptionType type;
	/**
	 * Urcuje, zda je volba povinna
	 */
	private boolean mandatory;
	/**
	 * Seznam defaultnich hodnot (pokud je volba jednohodnotova, 
	 * bere se pouze prvni prvek)
	 */
	private List<Element> defaultValueList;
	/**
	 * Seznam hodnot (pokud je volba jednohodnotova, 
	 * bere se pouze prvni prvek)
	 */
	private List<Element> valueList;
	/**
	 * Nazev vyctoveho typu, k nemuz hodnota patri.
	 * Pokud nepatri k zadnemu, null
	 */
	private String enumName;
	/**
	 * Oddelovac jednotlivych prvku seznamu hodnot
	 */
	private char delimiter;
	
	/**
	 * Komentar vztahujici se k volbe.
	 * Jedna se o komentar uvedeny na stejnem radku jako volba.
	 */
	private String inlineComment;
	
	/**
	 * Seznam komentaru predchazejicich volbe.
	 * Kazda radka je ulozena v samostatnem stringu.
	 */
	private List<String> priorComments;
	
	/**
	 * Metoda pro inicializaci private promennych
	 * @param name nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 */
	private void init(String name, OptionType type, boolean isList )
	{
		this.name = name;
		this.defaultValueList = new LinkedList<Element>();
		this.mandatory = false;
		this.type = type;
		this.isList = isList;
	}
	
	/**
	 * 
	 * @param name Nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 */
	public IniOption(String name, OptionType type, boolean isList) {
		init(name, type, isList);
	}
	
	/**
	 * 
	 * @param name Nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 * @param mandatory zda je volba povinna
	 */
	public IniOption(String name, OptionType type, boolean isList, boolean mandatory) {
		init(name, type, isList);
		this.mandatory = mandatory;
	}

	/**
	 * Test na povinnost volby
	 * @return true, pokud je volba povinna, jinak false
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * Nastaveni povinnosti volby
	 * @param mandatory povinnost volby
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * Nastaveni defaultni hodnoty
	 * @param defaultValue defaultni hodnota
	 */
	public void setDefaultElement(Element defaultValue) throws IniAccessException {
		if(this.isList())
			throw new IniAccessException("Setting single value to list-type option");
		
		this.defaultValueList.clear();
		this.defaultValueList.add(defaultValue);
	}

	/**
	 * Zjisteni defaultni hodnoty
	 * @return defaultni hodnota
	 */
	public Element getDefaultElement() throws IniAccessException {
		if (this.isList())
			throw new IniAccessException("List option accessed as single-value option");
		
		return defaultValueList.get(0);
	}
	
	/**
	 * Nastaveni defaultni hodnoty pro list-volbu
	 * @param defaultValues defaultni seznam hodnot
	 */
	public void setDefaultElementList(List<Element> defaultValues) throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("Single-value option accessed as list option");
		
		this.defaultValueList = defaultValues;
	}
	
	/**
	 * Zjisteni defaultni hodnoty pro list-volbu
	 * @return defaultni seznam hodnot
	 */
	public List<Element> getDefaultElementList() throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("List option accessed as single-value option.");
		
		return this.defaultValueList;
	}

	/**
	 * Zjisteni hodnoty volby
	 * @return hodnota
	 */
	public Element getElement() throws IniAccessException{
		if (this.isList())
			throw new IniAccessException("Single-value option accessed as list option.");

		return valueList.get(0);
	}

	/**
	 * Nastaveni hodnoty volby
	 * @param value hodnota
	 */
	public void setElement(Element value) throws IniAccessException {
		if(this.isList())
			throw new IniAccessException("List option accessed as single-value option.");
				
		if (this.valueList == null) {
			this.valueList = new LinkedList<Element>();
		}
		
		this.valueList.clear();
		this.valueList.add(value);
	}
	
	/**
	 * Zjisteni seznamu hodnot list-volby
	 * @return seznam hodnot, null v pripade jednohodnotove volby
	 */
	public List<Element> getElementList() throws IniAccessException	{
		if(!this.isList())
			throw new IniAccessException("Single-value option accessed as list option.");
		
		return this.valueList;
	}
	
	/**
	 * Nastaveni seznamu hodnot list-volby
	 * @param values seznam hodnot
	 */
	public void setElementList(List<Element> values) throws IniAccessException {
		if(!this.isList())
			throw new IniAccessException("Single-value option accessed as list option.");
		
		this.valueList = values;
	}

	/**
	 * Nastveni nazvu vyctoveho typu, ke kteremu patri hodnoty volby
	 * @param enumName nazev vyctoveho typu
	 */
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	/**
	 * Zjisteni nazvu vyctoveho typu, k nemuz patri hodnoty volby
	 * @return nazev vyctoveho typu
	 */
	public String getEnumName() {
		return enumName;
	}

	/**
	 * Nastaveni oddelovace pro list-volbu
	 * @param delimiter oddelovac. Pripustne hodnoty ',' a ':' 
	 */
	public void setDelimiter(char delimiter) throws IniAccessException, BadValueException {
		if(! this.isList())
			throw new IniAccessException("Setting delimiter for single-valued option.");
		
		if((delimiter != ':') && (delimiter != ','))
			throw new BadValueException("Invalid value for delimiter: \"" + delimiter +" \"");
		
		this.delimiter = delimiter;
	}

	/**
	 * Zjisteni oddelovace pro list-volbu
	 * @return oddelovac
	 */
	public char getDelimiter() throws IniAccessException {
		if(!this.isList())
			throw new IniAccessException("Getting delimiter from single-valued option.");
		
		return delimiter;
	}
	
	/**
	 * Ziskani hodnoty z volby typu boolean
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ
	 */
	public boolean getValueBool() throws IniException {
		if(this.type != OptionType.BOOLEAN)
			throw new BadTypeException("Requested option is not of type Boolean");
		
		return Boolean.parseBoolean(this.getValue());
	}
	
	public BigInteger getValueSigned() throws IniException {
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		return new BigInteger(this.getValue());
	}
	
	public BigInteger getValueUnsigned() throws IniException {		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of type Unsigned");
		
		return new BigInteger(this.getValue());
	}
	
	public float getValueFloat() throws IniException {
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of type Float");
		
		return Float.parseFloat(this.getValue());
	}
	
	public String getValueEnum() throws IniException {
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		
		return this.getValue();
	}
	
	public String getValueString() throws IniException {
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of type String");
		
		return this.getValue();
	}
	
	public String getValueUntyped() throws IniException {
		return getValue();
	}
	
	public List<Boolean> getValueListBool() throws IniException {
		
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

	public List<String> getValueListString() throws IniException {
		
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

	public List<Float> getValueListFloat() throws IniException {
		
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

	public List<BigInteger> getValueListSigned() throws IniException {
		
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
	
	public List<BigInteger> getValueListUnsigned() throws IniException {
		
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
	
	public List<String> getValueListEnum() throws IniException {
		
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
	
	public void setValue(String value) throws IniException {
		
		if((getType() != OptionType.STRING ) && (getType() != OptionType.ENUM) )
			throw new BadTypeException("Assigning string value to " + getType() + " option");
		
		setValueUntyped(value);
	}
	
	public void setValue(BigInteger value) throws IniException {
		if((getType() != OptionType.SIGNED ) && (getType() != OptionType.UNSIGNED) )
			throw new BadTypeException("Assigning integer value to " + getType() + " option");
		
		this.setValueUntyped(value.toString());
	}
	
	public void setValue(float value) throws IniException {
		if(this.getType() != OptionType.FLOAT)
			throw new BadTypeException("Assigning float value to " + getType() + " option");
		
		this.setValueUntyped(Float.toString(value));
	}
	
	public void setValue(boolean value)	throws IniException {
		if(this.getType() != OptionType.BOOLEAN)
			throw new BadTypeException("Assigning boolean value to " + getType() + " option");
		
		this.setValueUntyped(Boolean.toString(value));
	}
	
	protected void setValueUntyped(String value) throws IniAccessException {
		if(this.isList())
			throw new IniAccessException("Setting single value to list-type option");
		
		this.valueList.clear();
		this.valueList.add(new Element(value));
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
	
	public String getValue() throws IniAccessException
	{
		if(this.isList())
			throw new IniAccessException("List option accessed as single-value option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;
		
		if(listVal == null)
			return null;
		
		return listVal.get(0).getValue();
		
	}

	public List<Element> getValueList() throws IniAccessException {
		
		if( ! this.isList())
			throw new IniAccessException("Single-value option accessed as list option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;
		
		return listVal;
		
	}
	
	public boolean isList()
	{
		return this.isList;
	}

	/**
	 * Nastaveni radkoveho komentare k volbe
	 * @param comment komentar
	 */
	public void setInlineComment(String inlineComment) {
		this.inlineComment = inlineComment;
	}

	/**
	 * Zjisteni komentare prislusejiciho volbe
	 * @return
	 */
	public String getInlineComment() {
		return inlineComment;
	}

	/**
	 * Nastaveni seznamu komentaru predchazejicich volbe.
	 * Kazda radka je ulozena v jednom stringu.
	 * @param priorComments seznam komentaru predchazejicich volbe
	 */
	public void setPriorComments(List<String> priorComments) {
		this.priorComments = priorComments;
	}

	/**
	 * Zjisteni seznamu komentaru predchazejicich volbe.
	 * Kazda radka je ulozena v jednom stringu.
	 * @return seznam komentaru predchazejicich volbe
	 */
	public List<String> getPriorComments() {
		return priorComments;
	}
}
