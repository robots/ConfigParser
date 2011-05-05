import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class IniSectionImpl implements IniSection {

	private Map<String, IniOption> optionMap;
	private String name;
	private IniParser parser;

	public IniSectionImpl(String name, IniParser parser) {
		this.parser = parser;
		optionMap = new HashMap<String, IniOption>();
	}

	@Override
	public IniOption defineOptBoolean(String option) {
		IniOption opt = new IniOption(OptionType.BOOLEAN, false, true);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String option, boolean defaultValue) {
		IniOption opt = new IniOption(OptionType.BOOLEAN, false);
		Element defVal = new Element();

		defVal.setValue(Boolean.toString(defaultValue));
		opt.setDefaultElement(defVal);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String option, String enumName) {
		IniOption opt = new IniOption(OptionType.ENUM, false, true);
		opt.setEnumName(enumName);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String option, String enumName,
			String defaultValue) throws Exception {
		IniOption opt = new IniOption(OptionType.ENUM, false);
		
		if (! parser.isValidForEnum(enumName, defaultValue))
			throw new Exception("Invalid value");
		
		Element defVal = new Element();
		defVal.setValue(defaultValue);
		
		opt.setDefaultElement(defVal);
		opt.setEnumName(enumName);
		
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String option) {
		IniOption opt = new IniOption(OptionType.FLOAT, false, true);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String option, float defaultValue) {
		IniOption opt = new IniOption(OptionType.FLOAT, false);
		Element defVal = new Element();

		defVal.setValue(Float.toString(defaultValue));
		opt.setDefaultElement(defVal);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String option) {
		IniOption opt = new IniOption(OptionType.SIGNED, false, true);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String option, BigInteger defaultValue) {
		IniOption opt = new IniOption(OptionType.SIGNED, false);
		Element defVal = new Element();

		defVal.setValue(defaultValue.toString());
		opt.setDefaultElement(defVal);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptString(String option) {
		IniOption opt = new IniOption(OptionType.STRING, false, true);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptString(String option, String defaultValue) {
		IniOption opt = new IniOption(OptionType.STRING, false);
		Element defVal = new Element();

		defVal.setValue(defaultValue);
		opt.setDefaultElement(defVal);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String option) {
		IniOption opt = new IniOption(OptionType.UNSIGNED, false, true);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String option, BigInteger defaultValue) {
		IniOption opt = new IniOption(OptionType.UNSIGNED, false);
		Element defVal = new Element();

		defVal.setValue(defaultValue.toString());
		opt.setDefaultElement(defVal);
		optionMap.put(option, opt);

		return opt;
	}

	/*
	 * @Override public IniOption defineOptList(String option, String code, char
	 * delim) { // TODO Auto-generated method stub return null; }
	 * 
	 * @Override public IniOption defineOptList(String option, String code, char
	 * delim, List<String> defaultValues) { // TODO Auto-generated method stub
	 * return null; }
	 */
	@Override
	public IniOption defineOptListBoolean(String option, char delimiter) {
		IniOption opt = new IniOption(OptionType.BOOLEAN, true, true);
		opt.setDelimiter(delimiter);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String option, char delimiter, 
		List<Boolean> defaultValue) {
		
		IniOption opt = new IniOption(OptionType.BOOLEAN, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);
		
		for (Boolean boolVal : defaultValue) {
			Element element = new Element();
			element.setValue(boolVal.toString());
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String option, String enumName, 
		char delimiter) {
		
		IniOption opt = new IniOption(OptionType.ENUM, true, true);
		opt.setEnumName(enumName);
		opt.setDelimiter(delimiter);
		
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String option, String enumName,
		char delimiter, List<String> defaultValue) throws Exception{
		
		IniOption opt = new IniOption(OptionType.ENUM, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);

		for (String strVal : defaultValue) {
			Element element = new Element();
			
			if(! parser.isValidForEnum(enumName, strVal))
				throw new Exception("Invalid Value");
			
			element.setValue(strVal);
			defaultValues.add(element);
		}

		opt.setEnumName(enumName);
		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String option, char delimiter) {
		IniOption opt = new IniOption(OptionType.FLOAT, true, true);
		opt.setDelimiter(delimiter);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String option, char delimiter, 
		List<Float> defaultValue) {
		
		IniOption opt = new IniOption(OptionType.FLOAT, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);

		for (Float floatVal : defaultValue) {
			Element element = new Element();
			element.setValue(Float.toString(floatVal));
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String option, char delimiter) {
		IniOption opt = new IniOption(OptionType.SIGNED, true, true);
		opt.setDelimiter(delimiter);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String option,
		char delimiter,	List<BigInteger> defaultValue) {
		
		IniOption opt = new IniOption(OptionType.SIGNED, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);

		for (BigInteger signedVal : defaultValue) {
			Element element = new Element();
			element.setValue(signedVal.toString());
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String option, char delimiter) {
		IniOption opt = new IniOption(OptionType.STRING, true, true);
		opt.setDelimiter(delimiter);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String option,
		char delimiter, List<String> defaultValue) {
		
		IniOption opt = new IniOption(OptionType.STRING, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);

		for (String strVal : defaultValue) {
			Element element = new Element();
			element.setValue(strVal);
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String option, char delimiter) {
		IniOption opt = new IniOption(OptionType.UNSIGNED, true, true);
		opt.setDelimiter(delimiter);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String option,
		char delimiter,	List<BigInteger> defaultValue) {
		
		IniOption opt = new IniOption(OptionType.UNSIGNED, true);
		LinkedList<Element> defaultValues = new LinkedList<Element>();
		opt.setDelimiter(delimiter);

		for (BigInteger unsignedVal : defaultValue) {
			Element element = new Element();
			element.setValue(unsignedVal.toString());
			defaultValues.add(element);
		}

		opt.setDefaultElementList(defaultValues);
		optionMap.put(option, opt);

		return opt;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public IniOption getOption(String option) {
		IniOption opt = optionMap.get(option);
		
		return opt;
	}

	@Override
	public void accept(IniVisitor visitor) {
		visitor.visit(this);
		
		for(IniOption opt : optionMap.values())
		{
			opt.accept(visitor);
		}
		
	}
}
