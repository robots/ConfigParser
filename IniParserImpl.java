import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;
import java.util.LinkedList;


public class IniParserImpl implements IniParser {
	
	private Map<String, IniSection> sectionMap;
	private Map<String, Set<String> > enumMap;

	private static final char CHAR_BACKSLASH = '\\';
	private static final char CHAR_SPACE = ' ';
	private static final char DELIM_COMENT = ';';
	private static final char DELIM_OPTION = '=';

	private static IniSection parsedSection = null;

	public static final String PATTERN_ID = "[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*";
	public static final String PATTERN_ID_STRICT = "^[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*$";
	public static final String PATTERN_REFER = "\\$\\{" + PATTERN_ID + "\\#" + PATTERN_ID + "\\}";
	public static final String PATTERN_SECTION = "\\[[^\\]]*\\]"; 
	public static final String PATTERN_SECTION_STRICT = "\\[[a-zA-Z\\.\\:\\$][a-zA-Z0-9\\_\\~\\-\\.\\:\\$\\ ]*\\]";
	
	public IniParserImpl()
	{
		sectionMap = new HashMap<String, IniSection>();
		enumMap = new HashMap<String, Set<String>> ();
	}

	@Override
	public IniSection addSection(String sectionName) {
		IniSection section = new IniSectionImpl(sectionName, this);
		sectionMap.put(sectionName, section);

		return section;
	}

	@Override
	public void createEnumType(String enumName, String[] values) {
		Set<String> enumValues = new HashSet<String>();

		for(String val : values)
			enumValues.add(val);

		enumMap.put(enumName, enumValues);

	}

	@Override
	public boolean getBoolean(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueBool();

	}

	@Override
	public String getEnum(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueEnum();
	}

	@Override
	public float getFloat(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueFloat();
	}

	@Override
	public IniSection getSection(String sectionName) {
		return getSection(sectionName, false);
	}

	protected IniSection getSection(String sectionName, boolean autoCreate) {
		IniSection section = sectionMap.get(sectionName);

		if(autoCreate && (section == null))
			section = addSection(sectionName);

		return section;
	}

	@Override
	public BigInteger getSigned(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueSigned();
	}

	@Override
	public String getString(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueString();
	}

	@Override
	public BigInteger getUnsigned(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueUnsigned();
	}

	@Override
	public String getUntyped(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);

		return opt.getValueUntyped();
	}


	@Override
	public boolean isValidForEnum(String enumName, String value){

		Set<String> enumValues = enumMap.get(enumName);

		if(enumValues == null)
			return false;

		return enumValues.contains(value);

	}

	@Override
	public void setBoolean(String sectionName, String option, boolean value) {
		IniOption opt = this.getOption(sectionName, option);

		opt.setValue(value);
	}

	@Override
	public IniOption defineOptString(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptString(option);

		return opt;
	}

	@Override
	public IniOption defineOptString(String sectionName, String option,
			String defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptString(option, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptBoolean(option);

		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String sectionName, String option,
			boolean defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptBoolean(option, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptFloat(option);

		return opt;
	}

	@Override
	public IniOption defineOptFloat(String sectionName, String option,
			float defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptFloat(option, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptSigned(option);

		return opt;
	}

	@Override
	public IniOption defineOptSigned(String sectionName, String option,
			BigInteger defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptSigned(option, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptUnsigned(option);

		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String sectionName, String option,
			BigInteger defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptUnsigned(option, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String sectionName, String option,
			String enumName) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptEnum(option, enumName);

		return opt;
	}

	@Override
	public IniOption defineOptEnum(String sectionName, String option,
			String enumName, String defaultValue) throws Exception {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptEnum(option, enumName, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListString(option, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListString(String sectionName, String option,
			char delimiter, List<String> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListString(option, delimiter, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListBoolean(option, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String sectionName, String option,
			char delimiter, List<Boolean> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListBoolean(option, delimiter, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListFloat(option, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String sectionName, String option,
			char delimiter, List<Float> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListFloat(option, delimiter, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListSigned(option, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String sectionName, String option,
			char delimiter, List<BigInteger> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListSigned(option, delimiter, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListUnsigned(option, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String sectionName, String option,
			char delimiter, List<BigInteger> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListUnsigned(option, delimiter, defaultValue);

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String sectionName, String option,
			String enumName, char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListEnum(option, enumName, delimiter);

		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String sectionName, String option,
			String enumName, char delimiter, List<String> defaultValue)
			throws Exception {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListEnum(option, enumName, delimiter, defaultValue);

		return opt;
	}

	@Override
	public void setEnum(String sectionName, String option, String enumName, String value) {
		IniOption opt = this.getOption(sectionName, option);

		opt.setValue(value);
		opt.setEnumName(enumName);
	}

	@Override
	public void setFloat(String sectionName, String option, float value) {
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setSigned(String sectionName, String option, BigInteger value) {
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setString(String sectionName, String option, String value) {
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setUnsigned(String sectionName, String option, BigInteger value) {
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	protected IniOption getOption(String sectionName, String option)
	{
		return getSection(sectionName).getOption(option);
	}

	@Override
	public void accept(IniVisitor visitor) {
		visitor.visit(this);

		for( IniSection section : sectionMap.values())
		{
			section.accept(visitor);
		}
	}

	@Override
	public void readFile(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readStream(InputStream inStream) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readString(String inString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFile(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeStream(OutputStream outStream) {
		// TODO Auto-generated method stub

	}

	@Override
	public String writeString() {
		// TODO Auto-generated method stub
		return null;
	}

	private void parseReset() {
		parsedSection = null;
	}

	private boolean parseLine(String input) {
		String strComent = new String("");

		int idxComent = input.indexOf(';');
		if (idxComent != -1) {
			strComent = input.substring(idxComent + 1).trim();
System.out.println("komentar je '" + strComent + "'");

			if (idxComent == 0) {
				// only comment on line
				return true;
			}

			input = input.substring(0, idxComent - 1);
		}

		input = trim_special(input);

		if (input.length() == 0) {
			if (strComent.length() != 0) {
				// only comment
				return true;
			}

			return false;
		}

		Pattern patSection = Pattern.compile(IniParserImpl.PATTERN_SECTION);
		Matcher matSection = patSection.matcher(input);

		if (matSection.find()) {
			String in_match = matSection.toMatchResult().group();
			return parseSection(in_match);
		} else if (input.indexOf(DELIM_OPTION) != -1) {
			return parseOption(input);
		}

		System.out.println("Something feels wrong");
		return false;
	}

	private boolean parseSection(String input) {
		Pattern patId = Pattern.compile(IniParserImpl.PATTERN_ID);
		Matcher m = patId.matcher(input);

		if (!m.find()) {
			System.err.println("Section identifier expected");
			return false;
		}

		String sectionID = m.toMatchResult().group();

System.out.println(" Sekcia '" + sectionID + "'"); 

		parsedSection = this.getSection(sectionID);

		if (parsedSection == null) {
			System.err.println("no such section defined - '" + sectionID + "'");
			return false;
		}

		return true;
	}

	private boolean parseOption(String input) {
		String[] parts = input.split(Character.toString(DELIM_OPTION));

		if (parts.length != 2) {
			System.err.println(" '" + DELIM_OPTION + "' missing, or more than once on line");
			return false;
		}

		Pattern patId = Pattern.compile(IniParserImpl.PATTERN_ID);
		Matcher m_id = patId.matcher(parts[0]);

		if (!m_id.find()) {
			System.err.println("Identifier expected");
			return false;
		}

		String optionID = m_id.toMatchResult().group();
		String optionValue = parts[1];

		if (parsedSection == null) {
			System.err.println("Not in section !! ");
			return false;
		}

System.out.print("Option '" + optionID + "' = ");
		IniOption iniOpt = parsedSection.getOption(optionID);

		if (iniOpt == null) {
			System.err.println("no such option '" + optionID + "' defined in section '" + parsedSection.getName() + "'");
			return false;
		}

		if (iniOpt.isList() == false) {
			Element elem = new Element(optionValue);
			iniOpt.setElement(elem);
System.out.println(" '" + optionValue + "' ");
		} else {
			String delim = Character.toString(iniOpt.getDelimiter());
			String[] values = optionValue.split(delim);

			LinkedList<Element> listElem = new LinkedList<Element>();

			for (String value : values) {
				Element elem = new Element(value);

				listElem.add(elem);
System.out.println(" '" + value + "' ");
			}

			iniOpt.setElementList(listElem);
		}
		return true;
	}

	private String trim_special(String input) {
		boolean isBS = false;

		String output = new String();

		for (char c : input.toCharArray()) {
			if (c == CHAR_BACKSLASH) {
				isBS = true;
				continue;
			}

			if (!isBS) {
				if (c == CHAR_SPACE) {
					continue;
				}
/*
				if ((c == DELIM_OPTION) || (c == DELIM_COMENT)) {
					break;
				}
*/
			}

			isBS = false;

			output += c;
		}
		return output;
	}
}
