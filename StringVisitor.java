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

	@Override
	public void visit(IniParserImpl parser) {
	}

	@Override
	public void visit(IniSectionImpl section) {
		sb.append("[" + section.getName() + "]\n");
	}

	@Override
	public void visit(IniOption option) {
		
		if(option.isList())
			visitListValueOption(option);
		else 
			visitSingleValueOption(option);

	}
	
	private void visitSingleValueOption(IniOption option)
	{
		try {
			String value = option.getValue();

			sb.append(addBs(option.getName()) + " = " + addBs(value) + "\n");
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
			
			sb.append(addBs(option.getName()) + " = " + value + "\n");
		} catch (Exception e) {
		}
	}

	private String addBs(String input)
	{
		String output = new String();

		for (char c : input.toCharArray()) {
			if (c == ' ') {
				output += '\\';
			}
			output += c;
		}
		return output;
	}
}
