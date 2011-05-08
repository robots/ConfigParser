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
	private StringBuilder sb;

	public StringVisitor()
	{
		sb = new StringBuilder();
	}

	/**
	 * Vrati vygenerovany obsah konfiguracneho suboru. 
	 * @return obsah konfiguracneho suboru
	 */
	public String getString()
	{
		return sb.toString();
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
			sb.append(IniParserImpl.DELIM_COMENT + " " + itr.next() + "\n");
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

		sb.append(" " + IniParserImpl.DELIM_COMENT + " " + comment);
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
		sb.append("\n\n");

		addPriorComment(section.getPriorComments());
		sb.append("[" + section.getName() + "]");

		String comment = section.getInlineComment();
		addInlineComment(comment);

		sb.append("\n");
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

		sb.append("\n");
	}
	
	/**
	 * Metoda volana pri skalarnej hodnote.
	 * Vypise sa hodnota prvku
	 * @param option referencia na IniOption
	 */
	private void visitSingleValueOption(IniOption option)
	{
		if(!option.hasDefinedValue()) 
			return;
		
		try {
			String value = option.getValue();

			sb.append(addBs(option.getName()) + " " + 
					IniParserImpl.DELIM_OPTION + " " + addBs(value));
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
		if(!option.hasDefinedValue()) 
			return;
		
		try {
			String value = "";
			List<Element> listElem = option.getValueList();
			
			ListIterator<Element> itr = listElem.listIterator();

			while (itr.hasNext()) {
				Element elem = itr.next();
				value = value + addBs(elem.getValue());

				if (itr.hasNext()) {
					value = value + option.getDelimiter();
				}
			}
			
			sb.append(addBs(option.getName()) + " " + 
					IniParserImpl.DELIM_OPTION + " " + value);
		} catch (Exception e) {
		}
	}

	/**
	 * Metoda prida backslash pred kazdu medzeru vstupneho stringu.
	 * @param input vstupny retazec.
	 * @return backslashovany vystup
	 */
	private String addBs(String input)
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
}
