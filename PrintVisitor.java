import java.util.List;


public class PrintVisitor implements IniVisitor {

	@Override
	public void visit(IniParserImpl parser) {
		System.out.println("Visiting Parser");
		System.out.println();

	}

	@Override
	public void visit(IniSectionImpl section) {
		System.out.println("Visiting Section: " + section.getName());
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
			
			String value = "Not Defined";
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
		catch(BadTypeException e)
		{
			System.out.println(e.toString());
		}
	}

	private void visitListValueOption(IniOption option)
	{
	try {
			
			String value = "Not Defined";
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
		catch(BadTypeException e)
		{
			System.out.println(e.toString());
		}
	}
}
