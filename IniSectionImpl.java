import java.math.BigInteger;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.List;

/**
 * Trida implementujici interface IniSection
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class IniSectionImpl implements IniSection {

	/**
	 * Seznam voleb v sekci
	 */
	private List<IniOption> optionList;
	
	/**
	 * Identifikator sekce
	 */
	private String name;
	
	/**
	 * Reference na parser, do ktereho sekce patri
	 */
	private IniParser parser;
	
	/** 
	 * Komentar vztahujici se k sekci.
	 * Jedna se o komentar uvedeny na stejnem radku jako sekce.
	 */
	private String inlineComment;
	
	/**
	 * Seznam komentaru predchazejicich sekci.
	 * Kazda radka je ulozena v samostatnem sekci.
	 */
	private List<String> priorComments;

	/**
	 * Kontstruktor sekce
	 * @param name nazev sekce
	 * @param parser parser, do ktereho sekce patri
	 */
	public IniSectionImpl(String name, IniParser parser) {
		this.parser = parser;
		optionList = new LinkedList<IniOption>();
		this.name = name;
	}

	@Override
	public IniOption defineOptBoolean(String option) {
		IniOption opt = new IniOption(option, OptionType.BOOLEAN, false , 
				parser, true);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String option, boolean defaultValue) {
		IniOption opt = new IniOption(option, OptionType.BOOLEAN, false, 
				parser);
		Element defVal = new Element();

		defVal.setValue(Boolean.toString(defaultValue));
		try {
			opt.setDefaultElement(defVal);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String option, String enumName) {
		IniOption opt = new IniOption(option, OptionType.ENUM, false, 
				parser, true);
		opt.setEnumName(enumName);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String option, String enumName,
			String defaultValue) throws BadValueException, IniAccessException {
		IniOption opt = new IniOption(option, OptionType.ENUM, false, 
				parser);
		
		if (! parser.isValidForEnum(enumName, defaultValue))
			throw new BadValueException("Invalid value");
		
		Element defVal = new Element();
		defVal.setValue(defaultValue);
		
		opt.setDefaultElement(defVal);
		opt.setEnumName(enumName);
		
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String option) {
		IniOption opt = new IniOption(option, OptionType.FLOAT, false, 
				parser, true);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String option, float defaultValue) {
		IniOption opt = new IniOption(option, OptionType.FLOAT, false, parser);
		Element defVal = new Element();

		defVal.setValue(Float.toString(defaultValue));
		try {
			opt.setDefaultElement(defVal);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String option) {
		IniOption opt = new IniOption(option, OptionType.SIGNED, false, 
				parser, true);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String option, BigInteger defaultValue) {
		IniOption opt = new IniOption(option, OptionType.SIGNED, false, 
				parser);
		Element defVal = new Element();

		defVal.setValue(defaultValue.toString());
		try {
			opt.setDefaultElement(defVal);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptString(String option) {
		IniOption opt = new IniOption(option, OptionType.STRING, false, 
				parser, true);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptString(String option, String defaultValue) {
		IniOption opt = new IniOption(option, OptionType.STRING, false, 
				parser);
		Element defVal = new Element();

		defVal.setValue(defaultValue);
		try {
			opt.setDefaultElement(defVal);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String option) {
		IniOption opt = new IniOption(option, OptionType.UNSIGNED, false, 
				parser, true);
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String option, 
			BigInteger defaultValue) {
		IniOption opt = new IniOption(option, OptionType.UNSIGNED, false,
				parser);
		Element defVal = new Element();

		defVal.setValue(defaultValue.toString());
		try {
			opt.setDefaultElement(defVal);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String option, char delimiter) {
		IniOption opt = new IniOption(option, OptionType.BOOLEAN, true, 
				parser, true);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String option, char delimiter, 
		List<Boolean> defaultValue) {
		
		IniOption opt = new IniOption(option, OptionType.BOOLEAN, true, 
				parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		try {
			opt.setDelimiter(delimiter);
		
		for (Boolean boolVal : defaultValue) {
			Element element = new Element();
			element.setValue(boolVal.toString());
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionList.add(opt);
		
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String option, String enumName, 
		char delimiter) {
		
		IniOption opt = new IniOption(option, OptionType.ENUM, true, 
				parser, true);
		opt.setEnumName(enumName);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String option, String enumName,
		char delimiter, List<String> defaultValue) throws BadValueException {
		
		IniOption opt = new IniOption(option, OptionType.ENUM, true, parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		
		try {
			opt.setDelimiter(delimiter);
			
			for (String strVal : defaultValue) {
				Element element = new Element();
				
				if(! parser.isValidForEnum(enumName, strVal))
					throw new BadValueException("Invalid Value " + strVal + 
							" for enum " + enumName);
				
				element.setValue(strVal);
				defaultValues.add(element);
			}
	
			opt.setEnumName(enumName);
			opt.setDefaultElementList(defaultValues);
				
		} catch (IniAccessException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String option, char delimiter) {
		IniOption opt = new IniOption(option, OptionType.FLOAT, true, 
				parser, true);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String option, char delimiter, 
		List<Float> defaultValue) {
		
		IniOption opt = new IniOption(option, OptionType.FLOAT, true, parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		try {
		opt.setDelimiter(delimiter);

		for (Float floatVal : defaultValue) {
			Element element = new Element();
			element.setValue(Float.toString(floatVal));
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionList.add(opt);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String option, char delimiter) {
		IniOption opt = new IniOption(option, OptionType.SIGNED, true, 
				parser, true);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String option,
		char delimiter,	List<BigInteger> defaultValue) {
		
		IniOption opt = new IniOption(option, OptionType.SIGNED, true, parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		try {
			opt.setDelimiter(delimiter);

			for (BigInteger signedVal : defaultValue) {
				Element element = new Element();
				element.setValue(signedVal.toString());
				defaultValues.add(element);
			}
	
			opt.setDefaultElementList(defaultValues);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String option, char delimiter) {
		IniOption opt = new IniOption(option, OptionType.STRING, true, 
				parser, true);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String option,
		char delimiter, List<String> defaultValue) {
		
		IniOption opt = new IniOption(option, OptionType.STRING, true, parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		try {
			opt.setDelimiter(delimiter);
	
			for (String strVal : defaultValue) {
				Element element = new Element();
				element.setValue(strVal);
				defaultValues.add(element);
			}
	
			opt.setDefaultElementList(defaultValues);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String option, char delimiter) {
		IniOption opt = new IniOption(option, OptionType.UNSIGNED, true, 
				parser, true);
		try {
			opt.setDelimiter(delimiter);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String option,
		char delimiter,	List<BigInteger> defaultValue) {
		
		IniOption opt = new IniOption(option, OptionType.UNSIGNED, true, 
				parser);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		try {
			opt.setDelimiter(delimiter);
	
			for (BigInteger unsignedVal : defaultValue) {
				Element element = new Element();
				element.setValue(unsignedVal.toString());
				defaultValues.add(element);
			}
	
			opt.setDefaultElementList(defaultValues);
		} 
		catch(IniException e) {
			System.err.println(e.toString());
		}
		optionList.add(opt);

		return opt;
	}

	@Override
	public String getName() {
		return this.name;
	}


	@Override
	public IniOption getOption(String option) {

		ListIterator<IniOption> itr = optionList.listIterator();

		while (itr.hasNext()) {
			IniOption opt = itr.next();

			if (opt.getName().equals(option)) {
				return opt;
			}
		}

		return null;
	}

	@Override
	public void accept(IniVisitor visitor) {
		visitor.visit(this);
		
		for(IniOption opt : optionList)
		{
			opt.accept(visitor);
		}
		
	}

	/**
	 * Nastaveni radkoveho komentare k sekci
	 * @param inlineComment radkovy komentar prislusejici sekci 
	 */
	@Override
	public void setInlineComment(String inlineComment) {
		this.inlineComment = inlineComment;
		
	}

	/**
	 * Zjisteni radkoveho komentare k sekci
	 * @return radkovy komentar prislusejici sekci 
	 */
	@Override
	public String getInlineComment() {
		return this.inlineComment;
	}

	/**
	 * Nastaveni seznamu komentaru predchazejicich sekci.
	 * Kazda radka je ulozena v jednom stringu.
	 * @param priorComments seznam komentaru predchazejicich sekci
	 */
	@Override
	public void setPriorComments(List<String> priorComments) {
		this.priorComments = priorComments;
		
	}

	/**
	 * Zjisteni seznamu komentaru predchazejicich sekci.
	 * Kazda radka je ulozena v jednom stringu.
	 * @return seznam komentaru predchazejicich sekci
	 */
	@Override
	public List<String> getPriorComments() {
		return this.priorComments;
	}

}
