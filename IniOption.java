


import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	 * Urcuje, zda je volba seznamem (true), nebo ma pouze jedinou 
	 * hodnotu (false)
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
	 * Reference na parser, do ktereho volba patri.
	 * Dulezite pro vyhledavani referenci a pripustnych hodnot
	 */
	private IniParser parser;
	
	/**
	 * Pripustne pravdive hodnoty volby typu boolean
	 */
	private final String[] specialBooleanTrue = 
		{"true", "1", "t", "y", "on", "yes", "enabled",};
	
	/**
	 * Pripustne nepravdive hodnoty vobly typu boolean 
	 */
	private final String[] specialBooleanFalse =
		{"false", "0", "f", "n", "off", "no", "disabled" };
	
	/**
	 * Metoda pro inicializaci private promennych
	 * @param name nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 */
	private void init(String name, OptionType type, boolean isList, 
			IniParser parser)
	{
		this.name = name;
		this.defaultValueList = new LinkedList<Element>();
		this.valueList = new LinkedList<Element>();
		this.priorComments = new LinkedList<String>();
		this.mandatory = false;
		this.type = type;
		this.isList = isList;
		this.parser = parser;
	}
	
	/**
	 * 
	 * @param name Nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 * @param parser reference na parser, do ktereho volba patri
	 */
	public IniOption(String name, OptionType type, boolean isList, 
			IniParser parser) {
		init(name, type, isList, parser);
	}
	
	/**
	 * 
	 * @param name Nazev volby
	 * @param type typ volby
	 * @param isList zda je volba seznamem hodnot
	 * @param parser reference na parser, do ktereho volba patri
	 * @param mandatory zda je volba povinna
	 */
	public IniOption(String name, OptionType type, boolean isList, 
			IniParser parser,  boolean mandatory) {
		init(name, type, isList, parser);
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
	public void setDefaultElement(Element defaultValue) 
		throws IniAccessException {
		
		if(this.isList())
			throw new IniAccessException("Setting single value to " +
					"list-type option");
		
		if(this.defaultValueList == null)
			this.defaultValueList = new LinkedList<Element>();
		
		this.defaultValueList.clear();
		
		if(defaultValue == null)
			return;
		
		this.defaultValueList.add(defaultValue);
	}

	/**
	 * Zjisteni defaultni hodnoty
	 * @return defaultni hodnota
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public Element getDefaultElement() throws IniAccessException {
		if (this.isList())
			throw new IniAccessException("List option accessed as " +
					"single-value option");
		
		if(this.defaultValueList == null)
			return null;
		
		return defaultValueList.get(0);
	}
	
	/**
	 * Nastaveni defaultni hodnoty pro list-volbu
	 * @param defaultValues defaultni seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public void setDefaultElementList(List<Element> defaultValues) 
		throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("Single-value option accessed as " +
					"list option");
		
		this.defaultValueList = defaultValues;
	}
	
	/**
	 * Zjisteni defaultni hodnoty pro list-volbu
	 * @return defaultni seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Element> getDefaultElementList() throws IniAccessException {
		if(! this.isList())
			throw new IniAccessException("List option accessed as " +
					"single-value option.");
		
		return this.defaultValueList;
	}

	/**
	 * Zjisteni hodnoty volby
	 * @return hodnota
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public Element getElement() throws IniAccessException{
		if (this.isList())
			throw new IniAccessException("Single-value option accessed as " +
					"list option.");

		if(this.valueList == null)
			return null;

		return valueList.get(0);
	}

	/**
	 * Nastaveni hodnoty volby
	 * @param value hodnota
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setElement(Element value) throws IniAccessException {
		if(this.isList())
			throw new IniAccessException("List option accessed as " +
					"single-value option.");
				
		if (this.valueList == null) {
			this.valueList = new LinkedList<Element>();
		}
		
		this.valueList.clear();
		
		if(value == null)
			return;
		
		this.valueList.add(value);
	}
	
	/**
	 * Zjisteni seznamu hodnot list-volby
	 * @return seznam hodnot, null v pripade jednohodnotove volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<Element> getElementList() throws IniAccessException	{
		if(!this.isList())
			throw new IniAccessException("Single-value option accessed as " +
					"list option.");
		
		return this.valueList;
	}
	
	/**
	 * Nastaveni seznamu hodnot list-volby
	 * @param values seznam hodnot
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public void setElementList(List<Element> values) 
		throws IniAccessException {
		if(!this.isList())
			throw new IniAccessException("Single-value option accessed as " +
					"list option.");
		
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
	public void setDelimiter(char delimiter) 
		throws IniAccessException, BadValueException {
		if(! this.isList())
			throw new IniAccessException("Setting delimiter for " +
					"single-valued option.");
		
		if((delimiter != ':') && (delimiter != ','))
			throw new BadValueException("Invalid value for delimiter: \"" + 
					delimiter +" \"");
		
		this.delimiter = delimiter;
	}

	/**
	 * Zjisteni oddelovace pro list-volbu
	 * @return oddelovac
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public char getDelimiter() throws IniAccessException {
		if(!this.isList())
			throw new IniAccessException("Getting delimiter from " +
					"single-valued option.");
		
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
			throw new BadTypeException("Requested option is not of " +
					"type Boolean");
		
		boolean result = false;
		
		try {
			result = getSpecialBooleanValue(this.getValue());
		} catch (BadValueException e) {
			System.err.println(e.toString());
		}
		
		return result; 
	}
	
	/**
	 * Ziskani hodnoty z volby typu signed
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public BigInteger getValueSigned() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of type Signed");
		
		return createBigIntFromString(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu unsigned
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public BigInteger getValueUnsigned() 
		throws BadTypeException, IniAccessException {		
		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of " +
					"type Unsigned");
		
		return createBigIntFromString(this.getValue());
	}
	
	/**
	 * Ziskani hodnoty z volby typu float
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public float getValueFloat() throws BadTypeException, IniAccessException {
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of " +
					"type Float");
		
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

		return this.getValue();
	}
	
	/**
	 * Ziskani hodnoty z volby typu string
	 * @return hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public String getValueString() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of " +
					"type String");
		
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
	public List<Boolean> getValueListBool() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.BOOLEAN)
			throw new BadTypeException("Requested option is not of " +
					"type Boolean");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
		List<Boolean> resultList = new LinkedList<Boolean>();
		
		for(Element element : elementValues)
		{
			try {
			resultList.add(getSpecialBooleanValue(element.getValue()));
			} catch (BadValueException e) {
				System.err.println(e.toString());
			}
		}
		
		return resultList;
		
	}

	/**
	 * Ziskani seznamu hodnot z list-volby typu string
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<String> getValueListString() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.STRING)
			throw new BadTypeException("Requested option is not of " +
					"type String");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
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
	public List<Float> getValueListFloat() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.FLOAT)
			throw new BadTypeException("Requested option is not of " +
					"type Float");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
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
	public List<BigInteger> getValueListSigned() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.SIGNED)
			throw new BadTypeException("Requested option is not of " +
					"type Signed");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
		List<BigInteger> resultList = new LinkedList<BigInteger>();
		
		for(Element element : elementValues)
		{
			resultList.add( createBigIntFromString(element.getValue()));
		}
		
		return resultList;
		
	}
	
	/**
	 * Ziskani seznamu hodnot z list-volby typu unsigned
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<BigInteger> getValueListUnsigned() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.UNSIGNED)
			throw new BadTypeException("Requested option is not of " +
					"type Unsigned");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
		List<BigInteger> resultList = new LinkedList<BigInteger>();
		
		for(Element element : elementValues)
		{
			resultList.add( createBigIntFromString(element.getValue()));
		}
		
		return resultList;
		
	}
	
	/**
	 * Ziskani seznamu hodnot z list-volby typu enum
	 * @return seznam hodnot volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	public List<String> getValueListEnum() 
		throws BadTypeException, IniAccessException {
		
		if(this.type != OptionType.ENUM)
			throw new BadTypeException("Requested option is not of type Enum");
		
		List<Element> elementValues = this.getValueList();
		
		if(elementValues == null)
			return null;
		
		List<String> resultList = new LinkedList<String>();
		
		for(Element element : elementValues)
		{
			resultList.add(element.getValue());
		}
		
		return resultList;
		
	}
	
	/**
	 * Nastaveni hodnoty pro volby typu string a enum
	 * V pripade enum volby se provadi kontrola hodnoty vuci prislusnemu 
	 * vyctovemu typu
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(String value) 
		throws BadTypeException, IniAccessException, BadValueException {
		
		if((getType() != OptionType.STRING ) && 
			(getType() != OptionType.ENUM) )
			throw new BadTypeException("Assigning string value to " 
					+ getType() + " option");
		
		if((getType() == OptionType.ENUM) && 
			(!parser.isValidForEnum(enumName, value)))
			throw new BadValueException("Assigning value: " + value + 
					" to enum " + enumName);
		
		setValueUntyped(value);
	}
	
	/**
	 * Nastaveni hodnoty pro volby typu signed a unsigned
	 * V pripade unsigned volby se provadi kontrola hodnoty na pozitivnost
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(BigInteger value) 
		throws BadTypeException, IniAccessException, BadValueException {
		
		if((getType() != OptionType.SIGNED ) && 
			(getType() != OptionType.UNSIGNED) )
			throw new BadTypeException("Assigning integer value to " + 
					getType() + " option");
		
		if((getType() == OptionType.UNSIGNED ) && 
			(value.signum() == -1))
			throw new BadValueException("Assigning negative value to " +
					"UNSIGNED option");
		
		this.setValueUntyped(value.toString());
	}
	
	/**
	 * Nastaveni hodnoty pro volbu typu float
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(float value) 
		throws BadTypeException, IniAccessException {
		if(this.getType() != OptionType.FLOAT)
			throw new BadTypeException("Assigning float value to " + 
					getType() + " option");
		
		this.setValueUntyped(Float.toString(value));
	}
	
	/**
	 * Nastaveni hodnoty pro volbu typu boolean
	 * @param value hodnota volby
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby
	 * @throws IniAccessException V pripade pouziti na list-volbu
	 */
	public void setValue(boolean value)
		throws BadTypeException, IniAccessException {
		
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
		
		if(this.valueList == null)
			this.valueList = new LinkedList<Element>();
		
		this.valueList.clear();
		
		if(value == null)
			return;
		
		this.valueList.add(new Element(value));
	}
	
	/**
	 * Vstupni metoda pro visitora (Visitor design pattern)
	 * @param visitor instance visitora
	 */
	public void accept(IniVisitor visitor){
		if(visitor == null)
			return;
		
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
	protected String getValue() throws IniAccessException {
		
		if(this.isList())
			throw new IniAccessException("List option accessed as " +
					"single-value option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null || listVal.isEmpty())
			listVal = defaultValueList;
		
		if(listVal == null || listVal.isEmpty())
			return null;
		
		Element elementValue = listVal.get(0);
		
		return solveReference(elementValue);
		
	}

	/**
	 * Zjisteni seznamu hodnot volby
	 * @return seznam hodnot volby
	 * @throws IniAccessException V pripade pouziti na jednohodnotovou volbu
	 */
	protected List<Element> getValueList() throws IniAccessException {
		
		if( ! this.isList())
			throw new IniAccessException("Single-value option accessed " +
					"as list option");
		
		List<Element> listVal = valueList;
		
		if(listVal== null)
			listVal = defaultValueList;

		LinkedList<Element> outList = new LinkedList<Element>();
		ListIterator<Element> itr = listVal.listIterator();

		// solve references in list
		while (itr.hasNext()) {
			Element elem = (Element)itr.next();
			
			if (elem.isReference()) {
				outList.add(new Element(solveReference(elem)));
			} else {
				outList.add(elem);
			}
		}
		
		return outList;
		
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
	
	/**
	 * Resi referencovane hodnoty volby. V pripade ze element neobsahuje odkaz
	 * vrati jeho primou hodnotu. V pripade ze obsahuje odkaz, nahradi tento
	 * odkaz skutecnou hodnotou;
	 * @param element Zkoumana hodnota volby
	 * @return hodnota elementu s nahrazenymi pripadnymi referencemi
	 */
	private String solveReference(Element element)
	{
		if(element == null)
			return null;
		
		// Pokud element neni referencovany, vratime primo hodnotu
		if(! element.isReference())
			return element.getValue();
		
		String referencedSection = null;
		String referencedOption = null;
		
		// Find the reference-part of value
		Pattern idPattern = Pattern.compile(Patterns.PATTERN_ID);
		Matcher idMatcher = idPattern.matcher(
				element.getValue().substring(1));
		
		// Find first identifier = section name
		if (!idMatcher.find()) {
			System.err.println("Fatal error");
		}

		referencedSection = idMatcher.toMatchResult().group();
	
		// Find second identifier = option name
		if (!idMatcher.find()) {
			System.err.println("Fatal error");
		}

		referencedOption = idMatcher.toMatchResult().group();

		// Find referenced value
		String referencedValue = parser.getUntyped(referencedSection, 
				referencedOption);
	    
		// Replace reference with value
		String dereferencedValue = element.getValue().replace(
				"${"+referencedSection+"#"+referencedOption+"}", 
				referencedValue);
	    
		return dereferencedValue;
	}
	
	/**
	 * Nastavi hodnoty list-volby ze zadaneho pole hodnot. 
	 * Provadi se kontrola podle typu.	
	 * @param values pole nastavovanych hodnot
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby 
	 * @throws IniAccessException V pripade pouziti na jednohodotovou volbu
	 * @throws BadValueException V pripade nastavovani zapornych hodnot do 
	 * UNSIGNED volby
	 */
	public void setListValue(BigInteger[] values) 
		throws BadTypeException, IniAccessException, BadValueException {
		
		if((getType() != OptionType.SIGNED) && 
			(getType() != OptionType.UNSIGNED))
			throw new BadTypeException("Assigning integer values to " + 
					getType() + " option");
		
		
		List<Element> elementList = new LinkedList<Element>();
		
		for(BigInteger value : values) {
			if((getType() == OptionType.UNSIGNED) && 
			(value.signum() == -1))
				throw new BadValueException("Assigning negative value to " +
						"unsigned type");
				
			Element element = new Element(value.toString());
			elementList.add(element);
		}
		
		this.setElementList(elementList);
	}
	
	/**
	 * Nastavi hodnoty list-volby ze zadaneho pole hodnot. 
	 * Provadi se kontrola podle typu.	
	 * @param values pole nastavovanych hodnot
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby 
	 * @throws IniAccessException V pripade pouziti na jednohodotovou volbu
	 * @throws BadValueException V pripade nastavovani neplatnych hodnot do 
	 * ENUM volby
	 */
	public void setListValue(String[] values) 
		throws BadTypeException, IniAccessException, BadValueException {
		
		if((getType() != OptionType.STRING) && (getType() != OptionType.ENUM))
			throw new BadTypeException("Assigning string values to " + 
					getType() + " option");		
		
		List<Element> elementList = new LinkedList<Element>();
		
		for(String value : values) {
			if((getType() == OptionType.ENUM) && 
				(!parser.isValidForEnum(enumName, value)))
				throw new BadValueException("Assigning value: " + value + 
						" to enum " + enumName);
				
			Element element = new Element(value);
			elementList.add(element);
		}
		
		this.setElementList(elementList);
	}
	
	/**
	 * Nastavi hodnoty list-volby ze zadaneho pole hodnot. 
	 * Provadi se kontrola podle typu.	
	 * @param values pole nastavovanych hodnot
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby 
	 * @throws IniAccessException V pripade pouziti na jednohodotovou volbu
	 */
	public void setListValue(float[] values) 
		throws BadTypeException, IniAccessException {
		
		if(getType() != OptionType.FLOAT)
			throw new BadTypeException("Assigning float values to " + 
					getType() + " option");		
		
		List<Element> elementList = new LinkedList<Element>();
		
		for(float value : values) {
			Element element = new Element(Float.toString(value));
			elementList.add(element);
		}
		
		this.setElementList(elementList);
	}
	
	/**
	 * Nastavi hodnoty list-volby ze zadaneho pole hodnot. 
	 * Provadi se kontrola podle typu.	
	 * @param values pole nastavovanych hodnot
	 * @throws BadTypeException V pripade pouziti na nespravny typ volby 
	 * @throws IniAccessException V pripade pouziti na jednohodotovou volbu
	 */
	public void setListValue(boolean[] values) 
		throws BadTypeException, IniAccessException {
		
		if(getType() != OptionType.BOOLEAN)
			throw new BadTypeException("Assigning boolean values to " + 
					getType() + " option");		
		
		List<Element> elementList = new LinkedList<Element>();
		
		for(boolean value : values) {
			Element element = new Element(Boolean.toString(value));
			elementList.add(element);
		}
		
		this.setElementList(elementList);
	}
	
	/**
	 * Vrati boolean value na zaklade ruznych moznych stringu reprezentujicich
	 * boolean hodnoty. 
	 * Pro true jsou pripustne stringy uvedeny v poli specialBooleanTrue
	 * Pro false jsou pripustne stringy uvedeny v poli specialBooleanFalse
	 * @param stringValue retezec, z nehoz se parsuje boolean hodnota
	 * @return true pokud retezec patri mezi retezce povolene pro true,
	 * false pokud retezec patri mezi retezce povolene pro false
	 * @throws BadValueException V pripade ze retezec je pro typ boolean
	 * neplatny
	 */
	private boolean getSpecialBooleanValue(String stringValue) 
		throws BadValueException
	{
		for(String trueVal : specialBooleanTrue)
		{
			if(stringValue.equals(trueVal))
				return true;
		}
		
		for(String falseVal: specialBooleanFalse)
		{
			if(stringValue.equals(falseVal))
				return false;
		}
		
		throw new BadValueException("Bad value of boolean option: " + stringValue);
	}
	
	/**
	 * Naparsuje BigInteger za zadaneho retezce podle nasledujici konvence:
	 * prefix 0x urcuje sestnactkovy zapis
	 * prefix 0b urcuje binarni zapis
	 * prefix 0 urcuje osmickovy zapis
	 * ostatni se povazuje za desitkovy zapis
	 * @param value retezec, z nehoz se parsuje hodnota
	 * @return hodnota 
	 */
	private BigInteger createBigIntFromString(String value) {
		
		// Sestnactkova soustava
		if(value.startsWith("0x"))
			return new BigInteger(value.substring(2), 16);
		
		// Dvojkova soustava
		if(value.startsWith("0b"))
			return new BigInteger(value.substring(2), 2);
		
		// Osmickova soustava
		if(value.startsWith("0"))
			return new BigInteger(value.substring(1), 8);
		
		return new BigInteger(value);
		
	}
	
	
}
