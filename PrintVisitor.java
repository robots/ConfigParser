
/**
 * Konkretni implementace interface IniVisitor.
 * 
 * Ukazka implementacie IniVisitor-a na kontrolu/zpracovani
 * konfiguracniho stromu
 *
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class PrintVisitor implements IniVisitor {

	@Override
	public void visit(IniParser parser) {
		System.out.println("Visiting Parser");
		System.out.println();

	}

	@Override
	public void visit(IniSection section) {
		System.out.println("Visiting Section: " + section.getName());
	}

	@Override
	public void visit(IniOption option) {
		
		// Zjisteni zda se jedna o jednohodnotovou volbu 
		// nebo list volbu a vyvolani prislusne obsluhy
		
		if(option.isList())
			visitListValueOption(option);
		else 
			visitSingleValueOption(option);

	}
	
	/**
	 * Navstiveni jednohodnotove volby
	 * @param option zpracovavana volba
	 */
	private void visitSingleValueOption(IniOption option)
	{
		try {
			
			String value = "Not Defined";
			
			// Zpracovani volby podle typu metodami pro pristup
			// k jednohodnotove volbe
			switch(option.getType()) {
			
			case BOOLEAN:
				boolean b = option.getValueBool();
				value = Boolean.toString(b);
				break;
				
			case STRING:
				value = option.getValueString();
				break;
				
			case SIGNED:
				value = option.getValueSigned().toString();
				break;
				
			case UNSIGNED: 
				value = option.getValueUnsigned().toString();
				break;
			
			case ENUM:
				value = option.getValueEnum();
				break;
			
			case FLOAT:
				value = Float.toString(option.getValueFloat());
				break;
				
			default:
				System.out.println("UNTYPED");
				value = option.getValueUntyped();
			
			
			}
			
			System.out.println("Option: " + option.getName() + " Value: " + value + " Type: " + option.getType());			
		
		}
		catch(IniException e)
		{
			System.out.println(e.toString());
		}
	}

	/**
	 * Zpracovani lsit-volby
	 * @param option zpracovavana volba
	 */
	private void visitListValueOption(IniOption option)
	{
	try {
			
			String value = "Not Defined";
			
			// Zpracovani volby podle typu metodami pro pristup
			// k list-volbe
			switch(option.getType()) {
			
			case BOOLEAN:
				value = option.getValueListBool().toString();
				break;
				
			case STRING:
				value = option.getValueListString().toString();
				break;
				
			case SIGNED:
				value = option.getValueListSigned().toString();
				break;
				
			case UNSIGNED: 
				value = option.getValueListUnsigned().toString();
				break;
			
			case ENUM:
				value = option.getValueListEnum().toString();
				break;
			
			case FLOAT:
				value = option.getValueListFloat().toString();
				break;
				
			default:
				System.out.println("UNTYPED");
				value = option.getValueUntyped();
			
			
			}
			
			System.out.println("Option: " + option.getName() + " Value: " + value + " Type: " + option.getType());			
		
		}
		catch(IniException e)
		{
			System.out.println(e.toString());
		}
	}
}
