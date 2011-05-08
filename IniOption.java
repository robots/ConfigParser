


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
	 * Identifikator volby
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
	 * @throws IniAccessException V pripade pouziti na list-volbu
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
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public Element getDefaultElement() throws IniAccessException {
		if (this.isList())
			throw new IniAccessException("List option accessed as single-value option");
		
		return defaultValueList.get(0);
	}
	
	/**
	 * Nastaveni defaultni hodnoty pro list-volbu
	 * @param defaultValues defaultni seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public void setDefaultElementList(List<Element> defaultValues) throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("Single-value option accessed as list option");
		
		this.defaultValueList = defaultValues;
	}
	
	/**
	 * Zjisteni defaultni hodnoty pro list-volbu
	 * @return defaultni seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Element> getDefaultElementList() throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("List option accessed as single-value option.");
		
		return this.defaultValueList;
	}

	/**
	 * Zjisteni hodnoty volby
	 * @return hodnota
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public Element getElement() throws IniAccessException{
		if (this.isList())
			throw new IniAccessException("Single-value option accessed as list option.");

		return valueList.get(0);
	}

	/**
	 * Nastaveni hodnoty volby
	 * @param value hodnota
	 * @throws IniAccessException V pripade pouziti na list-volbu
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
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Element> getElementList() throws IniAccessException	{
		if(!this.isList())
			throw new IniAccessException("Single-value option accessed as list option.");
		
		return this.valueList;
	}
	
	/**
	 * Nastaveni seznamu hodnot list-volby
	 * @param values seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
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
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 * @throws BadValueException Pri pokusu nastavit neplatnou hodnotu delimiteru. 
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
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public char getDelimiter() throws IniAccessException {
		if(!this.isList())
			throw new IniAccessException("Getting delimiter from single-valued option.");
		
		return delimiter;
	}
	
	/**
	 * Ziskani hodnoty z volby typu boolean
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public boolean getValueBool() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.BOOLEAN)
			throw new BadTypeException("Requested option is not of type Boolean");
		
		return Boolean.parseBoolean(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu signed
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public BigInteger getValueSigned() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		return new BigInteger(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu unsigned
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public BigInteger getValueUnsigned() throws BadTypeException, IniAccessException {		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of type Unsigned");
		
		return new BigInteger(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu float
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public float getValueFloat() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of type Float");
		
		return Float.parseFloat(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu enum
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public String getValueEnum() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		// TODO check enum value
		return this.getValue();
	}
	
	/**
	 * Ziskani hodnoty z volby typu string
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public String getValueString() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of type String");
		
		return this.getValue();
	}
	
	/**
	 * Ziskani hodnoty bez kontroly typu. Vracena hodnota je retezec
	 * @return retezec s hodnotou volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public String getValueUntyped() throws IniAccessException {
		return getValue();
	}
	
	/**
	 * Ziskani seznamu hodnot z list-volby typu boolean
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Boolean> getValueListBool() throws BadTypeException, IniAccessException {
		
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

	/**
	 * Ziskani seznamu hodnot z list-volby typu string
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<String> getValueListString() throws BadTypeException, IniAccessException {
		
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

	/**
	 * Ziskani seznamu hodnot z list-volby typu float
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Float> getValueListFloat() throws BadTypeException, IniAccessException {
		
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

	/**
	 * Ziskani seznamu hodnot z list-volby typu signed
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<BigInteger> getValueListSigned() throws BadTypeException, IniAccessException {
		
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
	
	/**
	 * Ziskani seznamu hodnot z list-volby typu unsigned
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<BigInteger> getValueListUnsigned() throws BadTypeException, IniAccessException {
		
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
	
	/**
	 * Ziskani seznamu hodnot z list-volby typu enum
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<String> getValueListEnum() throws BadTypeException, IniAccessException {
		
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
	
	/**
	 * Nastaveni hodnoty pro volby typu string a enum
	 * V pripade enum volby se provadi kontrola hodnoty vuci prislusnemu vyctovemu typu
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(String value) throws BadTypeException, IniAccessException {
		
		if((getType() != OptionType.STRING ) && (getType() != OptionType.ENUM) )
			throw new BadTypeException("Assigning string value to " + getType() + " option");
		// TODO enum values check
		setValueUntyped(value);
	}
	
	/**
	 * Nastaveni hodnoty pro volby typu signed a unsigned
	 * V pripade unsigned volby se provadi kontrola hodnoty na pozitivnost
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(BigInteger value) throws BadTypeException, IniAccessException {
		if((getType() != OptionType.SIGNED ) && (getType() != OptionType.UNSIGNED) )
			throw new BadTypeException("Assigning integer value to " + getType() + " option");
		// TODO check for unsigned
		this.setValueUntyped(value.toString());
	}
	
	/**
	 * Nastaveni hodnoty pro volbu typu float
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(float value) throws BadTypeException, IniAccessException {
		if(this.getType() != OptionType.FLOAT)
			throw new BadTypeException("Assigning float value to " + getType() + " option");
		
		this.setValueUntyped(Float.toString(value));
	}
	
	/**
	 * Nastaveni hodnoty pro volbu typu boolean
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(boolean value)	throws BadTypeException, IniAccessException {
		if(this.getType() != OptionType.BOOLEAN)
			throw new BadTypeException("Assigning boolean value to " + getType() + " option");
		
		this.setValueUntyped(Boolean.toString(value));
	}
	
	/**
	 * Nastaveni hodnoty pro volbu bez kontroly typu
	 * @param value hodnota volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	protected void setValueUntyped(String value) throws IniAccessException {
		if(this.isList())
			throw new IniAccessException("Setting single value to list-type option");
		
		this.valueList.clear();
		this.valueList.add(new Element(value));
	}
	
	/**
	 * Vstupni metoda pro visitora (Visitor design pattern)
	 * @param visitor instance visitora
	 */
	public void accept(IniVisitor visitor){
		visitor.visit(this);		
	}

	/**
	 * Zjisteni identifikatoru volby
	 * @return identifikator volby
	 */
	public String getName() {	
		return name;
	}
	
	/**
	 * Zjisteni typu volby
	 * @return typ volby
	 */
	public OptionType getType() {
		return this.type;
	}
	
	/**
	 * Zjisteni hodnoty volby bez kontroly typu
	 * @return hodnota volby
	 * @throws IniAccessException V pripade pouziti na list-volby
	 */
	protected String getValue() throws IniAccessException
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

	/**
	 * Zjisteni seznamu hodnot volby
	 * @return seznam hodnot volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	protected List<Element> getValueList() throws IniAccessException {
		
		if( ! this.isList())
			throw new IniAccessException("Single-value option accessed as list option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;
		
		return listVal;
		
	}
	
	/**
	 * Zjisteni, zda jde o list-volbu
	 * @return true u list-volby, false u jednohodnotove volby
	 */
	public boolean isList()
	{
		return this.isList;
	}

	/**
	 * Nastaveni radkoveho komentare k volbe
	 * @param inlineComment komentar
	 */
	public void setInlineComment(String inlineComment) {
		this.inlineComment = inlineComment;
	}

	/**
	 * Zjisteni radkoveho komentare prislusejiciho volbe
	 * @return radkovy komentar
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
	
	/**
	 * Zjisti, zda byla hodnota volby uvedena v puvodnim ini souboru,
	 * nebo zda se pouziva defaultni hodnota
	 * @return true, pokud byla hodnota definovana, false jinak
	 */
	public boolean hasDefinedValue()
	{
		if(valueList == null)
			return false;
		
		return true;
	}
}
