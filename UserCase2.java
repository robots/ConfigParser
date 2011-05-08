import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;


public class UserCase2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IniParser iniP = new IniParserImpl();
		
		String [] enumVal = {"A", "B" };
		List<Boolean> listBool = new LinkedList<Boolean>();
		listBool.add(true);
		listBool.add(false);
		
		List<String> listEnum = new LinkedList<String>();
		listEnum.add("A");
		listEnum.add("B");
		
		List<Float> listFloat = new LinkedList<Float>();
		listFloat.add(1.0f);
		listFloat.add(2.0f);
		
		List<BigInteger> listBig = new LinkedList<BigInteger>();
		listBig.add(new BigInteger("150"));
		listBig.add(new BigInteger("151"));
		
		iniP.createEnumType("EnumT", enumVal);
		
		IniSection section = null;
		try{
		section = iniP.addSection("Default");
		section.defineOptBoolean("defBool", true);
		section.defineOptEnum("defEnum", "EnumT", "A");
		}catch( Exception e) { System.out.println(e.toString()); }
		section.defineOptFloat("defFloat", 1.0f);
		section.defineOptListBoolean("defListBool", ':', listBool);
		try{
		section.defineOptListEnum("defListEnum", "EnumT", ':', listEnum);
		}catch( Exception e) { System.out.println(e.toString()); }
		section.defineOptListFloat("defListFloat", ',', listFloat);
		section.defineOptListSigned("defListSigned", ':', listBig);
		section.defineOptListString("defListString", ',', listEnum);
		section.defineOptListUnsigned("defListUnsigned", ':', listBig);
		section.defineOptSigned("defSigned", new BigInteger("-15"));
		section.defineOptString("defString", "Ah oj");
		section.defineOptUnsigned("defUnsigned", new BigInteger("20"));
		
		System.out.println("Default section created");
		
		//IniVisitor visitor = new PrintVisitor();
		//StringVisitor sv = new StringVisitor();
		//iniP.accept(visitor);
		//iniP.accept(sv);

		try {
			iniP.readFile("test.ini");
		} catch (Exception e) {
			e.printStackTrace();
		}

		StringVisitor sv = new StringVisitor();
		//iniP.accept(sv);
		//iniP.accept(visitor);

		try {
			iniP.writeFile("output.ini");
		} catch (Exception e) {
			e.printStackTrace();
		}

		//System.out.print(sv.getString());
	}

}
