import java.util.List;
import java.util.LinkedList;
import java.util.ListIterator;


public class StringVisitor implements IniVisitor {
	private StringBuilder sb;

	public StringVisitor()
	{
		sb = new StringBuilder();
	}

	public String getString()
	{
		return sb.toString();
	}

	private void addPriorComment(List<String> comments)
	{
		if ((comments == null) || (comments.isEmpty())) {
			return;
		}

		ListIterator itr = comments.listIterator();

		while (itr.hasNext()) {
			sb.append(IniParserImpl.DELIM_COMENT + " " + itr.next() + "\n");
		}
	}

	private void addInlineComment(String comment)
	{
		if ((comment == null) || (comment.length() == 0))
			return;

		sb.append(" " + IniParserImpl.DELIM_COMENT + " " + comment);
	}

	@Override
	public void visit(IniParserImpl parser) {
	}

	@Override
	public void visit(IniSectionImpl section) {
		sb.append("\n\n");

		addPriorComment(section.getPriorComments());
		sb.append("[" + section.getName() + "]");

		String comment = section.getInlineComment();
		addInlineComment(comment);

		sb.append("\n");
	}

	@Override
	public void visit(IniOption option) {

		addPriorComment(option.getPriorComments());

		if(option.isList())
			visitListValueOption(option);
		else
			visitSingleValueOption(option);

		String comment = option.getInlineComment();
		addInlineComment(comment);

		sb.append("\n");
	}
	
	private void visitSingleValueOption(IniOption option)
	{
		try {
			String value = option.getValue();

			sb.append(addBs(option.getName()) + " " + IniParserImpl.DELIM_OPTION + " " + addBs(value));
		} catch (Exception e) {
		}
	}

	private void visitListValueOption(IniOption option)
	{
		try {
			String value = "";
			List<Element> listElem = option.getValueList();
			
			ListIterator itr = listElem.listIterator();

			while (itr.hasNext()) {
				Element elem = (Element)itr.next();
				value = value + addBs(elem.getValue());

				if (itr.hasNext()) {
					value = value + option.getDelimiter();
				}
			}
			
			sb.append(addBs(option.getName()) + " " + IniParserImpl.DELIM_OPTION + " " + value);
		} catch (Exception e) {
		}
	}

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
