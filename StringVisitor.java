import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Konkretni implementace interface IniVisitor.
 * 
 * Stara sa o vygenerovanie obsahu konfiguracneho suboru.
 *
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class StringVisitor implements IniVisitor {
	
	/**
	 * String builder pro konstrukci vypisovaneho retezce
	 */
	private StringBuilder builder;
	
	/**
	 * Ulozeni informace, zda se maji vypisovat i defaultni hodnoty
	 */
	private boolean includeDefaultValues;

	/**
	 * Konstruktor visitoru pro vypis konfigurace
	 * @param includeDefaultValues zda se maji vypisovat defaultni konfigurace
	 */
	public StringVisitor(boolean includeDefaultValues)
	{
		builder = new StringBuilder();
		this.includeDefaultValues = includeDefaultValues;
	}

	/**
	 * Vrati vygenerovany obsah konfiguracneho suboru. 
	 * @return obsah konfiguracneho suboru
	 */
	public String getString()
	{
		return builder.toString();
	}

	/**
	 * Prida komentare na koniec obsahu.
	 * @param comments zoznam komentarov
	 */
	private void addPriorComment(List<String> comments)
	{
		if ((comments == null) || (comments.isEmpty())) {
			return;
		}

		ListIterator<String> itr = comments.listIterator();

		while (itr.hasNext()) {
			builder.append(IniParserImpl.DELIM_COMENT + " " + 
					itr.next() + "\n");
		}
	}

	/**
	 * Prida inline komentar k danemu riadku.
	 * @param comment text komentaru
	 */
	private void addInlineComment(String comment)
	{
		if ((comment == null) || (comment.length() == 0))
			return;

		builder.append(" " + IniParserImpl.DELIM_COMENT + " " + comment);
	}

	/**
	 * Metoda volana pri navstiveni "IniParseru".
	 * Prida komentar, z parseru.
	 * @param parser referencia na parser
	 */
	@Override
	public void visit(IniParser parser) {
		addPriorComment(parser.getClosingComments());
	}

	/**
	 * Metoda volana pri navstiveni "IniSection".
	 * Prida hlavicku sekcie + inline komentar, a komentare pred sekciou
	 * @param section referencia na IniSection
	 */
	@Override
	public void visit(IniSection section) {
		builder.append("\n\n");

		addPriorComment(section.getPriorComments());
		builder.append("[" + section.getName() + "]");

		String comment = section.getInlineComment();
		addInlineComment(comment);

		builder.append("\n");
	}

	/**
	 * Metoda volana pri navstiveni "IniSection".
	 * Prida riadok s volbou, jej hodnotu/hodnoty, inline komentar
	 * a komentare pred
	 * @param option referencia na IniOption
	 */
	@Override
	public void visit(IniOption option)
	{
		addPriorComment(option.getPriorComments());

		if(option.isList())
			visitListValueOption(option);
		else
			visitSingleValueOption(option);

		String comment = option.getInlineComment();
		addInlineComment(comment);

		builder.append("\n");
	}
	
	/**
	 * Metoda volana pri skalarnej hodnote.
	 * Vypise sa hodnota prvku
	 * @param option referencia na IniOption
	 */
	private void visitSingleValueOption(IniOption option)
	{
		if(!includeDefaultValues() && !option.hasDefinedValue()) 
			return;
		
		try {
			String value = option.getValue();

			builder.append(addBackslash(option.getName()) + " " + 
					IniParserImpl.DELIM_OPTION + " " + addBackslash(value));
		} catch (Exception e) {
		}
	}

	/**
	 * Metoda volana pri zozname hodnot.
	 * Vypisu sa hodnoty prvkov oddelene spravnym oddelovacom.
	 * @param option referencia na IniOption
	 */
	private void visitListValueOption(IniOption option)
	{
		if(!includeDefaultValues() && !option.hasDefinedValue()) 
			return;
		
		try {
			String value = "";
			List<Element> listElem = option.getValueList();
			
			ListIterator<Element> itr = listElem.listIterator();

			while (itr.hasNext()) {
				Element elem = itr.next();
				value = value + addBackslash(elem.getValue());

				if (itr.hasNext()) {
					value = value + option.getDelimiter();
				}
			}
			
			builder.append(addBackslash(option.getName()) + " " + 
					IniParserImpl.DELIM_OPTION + " " + value);
		} catch (Exception e) {
		}
	}

	/**
	 * Metoda prida backslash pred kazdu medzeru vstupneho stringu.
	 * @param input vstupny retazec.
	 * @return backslashovany vystup
	 */
	private String addBackslash(String input)
	{
		String output = new String();

		for (char c : input.toCharArray()) {
			if (c == ' ') {
				output += IniParserImpl.CHAR_BACKSLASH;
			}
			output += c;
		}
		return output;
	}
	
	/**
	 * Zjisti, zda se maji vypisovat i defaultni hodnoty
	 * @return true, pokud se maji vypisovat defaultni hodnoty.
	 * false jinak
	 */
	private boolean includeDefaultValues() {
		return this.includeDefaultValues;
	}
}
