/**
 * Interface visitora pro pruchod struktury parseru.
 * Visitor design pattern
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public interface IniVisitor {
	
	//TODO rozmyslet, zda nestaci navstevovat interface misto Impl.
	
	/**
	 * Navstiveni parseru
	 * @param parser navstevovany parser
	 */
	public void visit(IniParser parser);
	
	/**
	 * Navstiveni sekce
	 * @param section navstevovana sekce
	 */
	public void visit(IniSection section);
	
	/**
	 * Navstiveni volby
	 * @param option navstevovana volba
	 */
	public void visit(IniOption option);
}
