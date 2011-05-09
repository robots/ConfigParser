import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.BufferedReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.*;
import java.util.LinkedList;

/**
 * Konkretni implementace interface IniParser
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class IniParserImpl implements IniParser {
	
	/**
	 * Seznam sekci
	 */
	private List<IniSection> sectionList;
	
	/**
	 * Mapa vyctovych typu <Nazev typu, Mnozina pripustnych hodnot>
	 */
	private Map<String, Set<String> > enumMap;
	
	/**
	 * Seznam komentaru na konci ini souboru
	 * Kazda radka je ulozena v samostatnem stringu
	 */
	private List<String> closingComments;
	
	/**
	 * Znakova konstanta reprezentujici zpetne lomitko
	 */
	public static final char CHAR_BACKSLASH = '\\';
	
	/**
	 * Znakova konstanta reprezentujici mezeru
	 */
	public static final char CHAR_SPACE = ' ';
	
	/**
	 * Znakova konstanta reprezentujici oddelovac komentaru
	 */
	public static final char DELIM_COMENT = ';';
	
	/**
	 * Znakova konstanta reprezentujici prirazovaci znak
	 */
	public static final char DELIM_OPTION = '=';

	/** 
	 * Parsovana sekce, do ktere patri pridavane volby
	 */
	private IniSection parsedSection = null;
	
	/**
	 * Seznam naparsovanych komentaru - musi se porzdrzet,
	 * nebot nalezi k objektu ktery jeste nebyl naparsovan
	 */
	private LinkedList<String> parsedCommentsList = null;
	
	/**
	 * Parsovany radkovy komentar
	 */
	private String parsedInlineComment = null;
	
	/**
	 * Rezim parseru
	 */
	private ParserAttitude parserAttitude = ParserAttitude.STRICT;
	
	/**
	 * Konstruktor, defaultni rezim STRICT
	 */
	public IniParserImpl()
	{
		sectionList = new LinkedList<IniSection>();
		enumMap = new HashMap<String, Set<String>> ();

		parserAttitude = ParserAttitude.STRICT;
	}

	@Override
	public void setAttitude(ParserAttitude attitude) {
		this.parserAttitude = attitude;
	}

	@Override
	public IniSection addSection(String sectionName) throws BadValueException {
		
		if(getSection(sectionName, false) != null)
			throw new BadValueException("Section " + sectionName + 
					" already exists");
		
		IniSection section = new IniSectionImpl(sectionName, this);
		sectionList.add(section);

		return section;
	}

	@Override
	public void createEnumType(String enumName, String[] values) 
		throws BadValueException{
		
		if(enumMap.containsKey(enumName))
			throw new BadValueException("Enum " + enumName + 
					" already exists");
		
		Set<String> enumValues = new HashSet<String>();

		for(String val : values)
			enumValues.add(val);

		enumMap.put(enumName, enumValues);

	}

	
	@Override
	public IniSection getSection(String sectionName) {
		return getSection(sectionName, false);
	}

	/**
	 * Nalezne sekci podle jejiho nazvu. V auto-create rezimu pokud nenalezne
	 * zadanou sekci, automaticky ji vytvori
	 * @param sectionName nazev sekce
	 * @param autoCreate rezim automatickeho vytvareni
	 * @return hledana sekce, nebo null pokud neni zapnut auto-create rezim
	 */
	protected IniSection getSection(String sectionName, boolean autoCreate) {
		IniSection section = findSection(sectionName);

		
		if(autoCreate && (section == null))
			try {
				section = addSection(sectionName);
			} catch (BadValueException e) {
				System.err.println(e.toString());				
			}

		return section;
	}

	
	@Override
	public String getUntyped(String sectionName, String option){
		IniSection section = findSection(sectionName);
		IniOption opt = section.getOption(option);

		String value = null;
		
		try {
			value = opt.getValueUntyped();
		} catch (IniAccessException e)
		{
			System.err.println(e.toString());
		}
			
		return value;
	}


	@Override
	public boolean isValidForEnum(String enumName, String value){

		Set<String> enumValues = enumMap.get(enumName);

		if(enumValues == null)
			return false;

		return enumValues.contains(value);

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
			String enumName, String defaultValue) throws BadValueException {
		IniSection section = getSection(sectionName, true);
		
		IniOption opt = null;
		
		try {
			opt = section.defineOptEnum(option, enumName, defaultValue);
		} catch (IniAccessException e) {
			System.err.println(e.toString());
		}

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
		IniOption opt = section.defineOptListString(option, delimiter, 
				defaultValue);

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
		IniOption opt = section.defineOptListBoolean(option, delimiter, 
				defaultValue);

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
		IniOption opt = section.defineOptListFloat(option, delimiter, 
				defaultValue);

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
		IniOption opt = section.defineOptListSigned(option, delimiter, 
				defaultValue);

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
		IniOption opt = section.defineOptListUnsigned(option, delimiter, 
				defaultValue);

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
			throws BadValueException {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListEnum(option, enumName, delimiter,
				defaultValue);

		return opt;
	}

	@Override
	public IniOption getOption(String sectionName, String option)
	{
		return getSection(sectionName).getOption(option);
	}

	@Override
	public void accept(IniVisitor visitor) {
		if(visitor == null)
			return;
		
		for( IniSection section : sectionList)
		{
			section.accept(visitor);
		}
		
		visitor.visit(this);
	}

	@Override
	public void readFile(String fileName) throws IOException, ParserException {
		File file = new File(fileName);
		FileInputStream inStream = new FileInputStream(file);

		this.readStream(inStream);
		inStream.close();
	}

	@Override
	public void readStream(InputStream inStream) 
		throws IOException, ParserException {
		
		BufferedReader reader = new BufferedReader(
				new	InputStreamReader(inStream));
		
		StringBuilder builder = new StringBuilder();
		String line = null;

		while ((line = reader.readLine()) != null) {
			builder.append(line + "\n");
		}

		this.readString(builder.toString());
	}

	@Override
	public void readString(String inString) throws ParserException {
		BufferedReader reader = new BufferedReader(new StringReader(inString));
        
		try {
			String line;
			int linenum = 0;
			while ((line = reader.readLine()) != null) {
				linenum ++;

				boolean success = parseLine(line);
				if (success == false) {
					throw new ParserException("Fatal error on line " + 
							linenum);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			parseFinish();
		}

		CheckVisitor checkVisitor = new CheckVisitor();
		this.accept(checkVisitor);

		if (!checkVisitor.isOK()) {
			throw new ParserException("Mandatory options not defined");
		}
	}

	@Override
	public void writeFile(String fileName, boolean includeDefaultValues) 
		throws IOException,	ParserException {
		
		File file = new File(fileName);
		FileOutputStream outStream = new FileOutputStream(file);

		this.writeStream(outStream, includeDefaultValues);
		outStream.close();
	}

	@Override
	public void writeStream(OutputStream outStream, boolean includeDefaultValues) 
		throws IOException, ParserException {
		
		String outString = this.writeString(includeDefaultValues);
		outStream.write(outString.getBytes());
	}

	@Override
	public String writeString(boolean includeDefaultValues) throws ParserException {
		CheckVisitor checkVisitor = new CheckVisitor();
		this.accept(checkVisitor);

		if (!checkVisitor.isOK()) {
			throw new ParserException("Mandatory options not defined");
		}

		StringVisitor stringVisitor = new StringVisitor(includeDefaultValues);
		this.accept(stringVisitor);

		return stringVisitor.getString();
	}

	/**
	 * Uprace po parseri.
	 *
	 */
	private void parseFinish() {
		this.setClosingComments(parsedCommentsList);

		parsedSection = null;
		parsedCommentsList = null;
		parsedInlineComment = "";
	}

	/**
	 * Ulozi komentar do zoznamu komentarov.
	 *
	 * Tie sa neskor priradia k nejakej sekcii/volbe.
	 *
	 * @param comment Obsah komentaru
	 */
	private void parseSaveComment(String comment) {
		if ((comment == null) || (comment.length() == 0)) {
			return;
		}

		if (parsedCommentsList == null) {
			parsedCommentsList = new LinkedList<String>();
		}

		parsedCommentsList.add(comment);
	}

	/**
	 * Sparsuje riadok. Rozhozne ci je na riadku sekcia, 
	 * alebo volba s hodnotou, popripade iba komentar.
	 *
	 * @param input Obsah riadku
	 * @return vysledok parsovania - true/false
	 */
	private boolean parseLine(String input) {
		String strComment = new String("");

		int idxComent = input.indexOf(DELIM_COMENT);
		if (idxComent != -1) {
			strComment = input.substring(idxComent + 1).trim();

			if (idxComent != 0) {
				input = input.substring(0, idxComent - 1);
			} else {
				input = "";
			}
		}

		input = trimSpacesSpecial(input);

		parsedInlineComment = strComment;

		Pattern patSection = Pattern.compile(Patterns.PATTERN_SECTION_STRICT);
		Matcher matSection = patSection.matcher(input);

		boolean success = false;
		if (input.length() == 0) {
			success = true;
		} else if (matSection.find()) {
			String in_match = matSection.toMatchResult().group();
			success = parseSection(in_match);
		} else if (input.indexOf(DELIM_OPTION) != -1) {
			success = parseOption(input);
		}

		parseSaveComment(parsedInlineComment);

		return success;
	}

	/**
	 * Sparsuje cast riadku, kde je definovany nazov Sekcie
	 *
	 * @param input cast riadku
	 * @return vysledok parsovania - true/false
	 */
	private boolean parseSection(String input) {
		Pattern idPattern = Pattern.compile(Patterns.PATTERN_ID);
		Matcher  idMatcher= idPattern.matcher(input);

		if (!idMatcher.find()) {
			System.err.println("Section identifier expected");
			return false;
		}

		String sectionID = idMatcher.toMatchResult().group();

		boolean autocreate = false;
		if (parserAttitude != ParserAttitude.STRICT) {
			autocreate = true;
		}
		
		parsedSection = this.getSection(sectionID, autocreate);

		if (parsedSection == null) {
			System.err.println("no such section defined - '" + sectionID +
					"'");
			return false;
		}

		parsedSection.setPriorComments(parsedCommentsList);
		parsedSection.setInlineComment(parsedInlineComment);
		parsedCommentsList = null;
		parsedInlineComment = "";

		return true;
	}

	/**
	 * Sparsuje cast riadku, kde je definovana hodnota optiony.
	 *
	 * @param input cast riadku
	 * @return vysledok parsovania - true/false
	 */
	private boolean parseOption(String input) {
		String[] parts = input.split(Character.toString(DELIM_OPTION));

		if (parts.length != 2) {
			System.err.println(" '" + DELIM_OPTION + "' missing, or more than " +
					"once on line");
			return false;
		}

		Pattern idPattern = Pattern.compile(Patterns.PATTERN_ID_STRICT);
		Matcher idMatcher = idPattern.matcher(parts[0]);

		if (!idMatcher.find()) {
			System.err.println("Identifier expected");
			return false;
		}

		String optionID = idMatcher.toMatchResult().group();
		String optionValue = parts[1];

		if (parsedSection == null) {
			System.err.println("Not in section !! ");
			return false;
		}

		IniOption parsedOption = parsedSection.getOption(optionID);

		if (parsedOption == null) {
			if (parserAttitude == ParserAttitude.STRICT) {
				System.err.println("no such option '" + optionID + 
						"' defined in section '" + 
						parsedSection.getName() + "'");
				
				return false;
			} else {
				parsedOption = parsedSection.defineOptString(optionID);	
			}
		}

		parsedOption.setPriorComments(parsedCommentsList);
		parsedOption.setInlineComment(parsedInlineComment);
		parsedCommentsList = null;
		parsedInlineComment = "";

		if (parsedOption.isList() == false) {
			try {
				Element elem = new Element(optionValue);
				parsedOption.setElement(elem);
			}
			catch(IniException e) {
				System.err.println(e.toString());
				return false;
			}
		} else {
			try {
				String delim = Character.toString(parsedOption.getDelimiter());

				String[] values = optionValue.split(delim);

				LinkedList<Element> listElem = new LinkedList<Element>();
				for (String value : values) {
					Element elem = new Element(value);
					listElem.add(elem);
				}

				parsedOption.setElementList(listElem);
			} 
			catch(IniException e) {
				System.err.println(e.toString());
				return false;
			}
		}
		return true;
	}

	/**
	 * Vyhod z retazca zbytocne medzery.
	 * Medzery, ktore su "backslashnute" v retzci zostavaju.
	 * @param input vstupny retazec
	 * @return vycisteny vystup
	 */
	private String trimSpacesSpecial(String input) {
		boolean isBackslash = false;

		String output = new String();

		for (char c : input.toCharArray()) {
			if (c == CHAR_BACKSLASH) {
				isBackslash = true;
				continue;
			}

			if (!isBackslash) {
				if (c == CHAR_SPACE) {
					continue;
				}
			}

			isBackslash = false;

			output += c;
		}
		return output;
	}

	/**
	 * Nastaveni seznamu komentaru na konci ini souboru.
	 * Kazda radka je ulozena v samostatnem stringu.
	 * @param closingComments seznam komentaru na konci ini souboru
	 */
	public void setClosingComments(List<String> closingComments) {
		this.closingComments = closingComments;
	}

	/**
	 * Zjisteni seznamu komentaru na konci ini souboru.
	 * Kazda radka je ulozena v samostatnem stringu.
	 * @return seznam komentaru na konci ini souboru
	 */
	public List<String> getClosingComments() {
		return closingComments;
	}
	
	/**
	 * Najde sekci podle zadaneho jmena sekce
	 * @param sectionName jmeno hledane sekce
	 * @return hledana sekce, nebo null pokud takova sekce neexistuje
	 */
	private IniSection findSection(String sectionName)
	{
		
		for(IniSection section : sectionList)
		{
			if(section.getName().equals(sectionName))
				return section;
		}

		return null;
	}
}
