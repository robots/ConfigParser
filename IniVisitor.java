
public interface IniVisitor {
	
	public void visit(IniParserImpl parser);
	
	public void visit(IniSectionImpl section);
	
	public void visit(IniOption option);
}
