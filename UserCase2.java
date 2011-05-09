import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;


public class UserCase2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		IniParser iniP = new IniParserImpl();
		
		//////////////////////////////////////////////////////////
		// Priprava defaultnich hodnot - nezajimave
		
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
		//////////////////////////////////////////////////////////
		
		IniSection section = null;
		
		// Jednotlive definice
		try{
			
			// Vytvoreni vyctoveho typu 
			iniP.createEnumType("EnumT", enumVal);
			
			section = iniP.addSection("Default");
			section.defineOptBoolean("defBool", true);
			section.defineOptEnum("defEnum", "EnumT", "A"); 
			section.defineOptFloat("defFloat", 1.0f);
			section.defineOptListBoolean("defListBool", ':', listBool);
			section.defineOptListEnum("defListEnum", "EnumT", ':', listEnum);
			section.defineOptListFloat("defListFloat", ',', listFloat);
			section.defineOptListSigned("defListSigned", ':', listBig);
			section.defineOptListString("defListString", ',', listEnum);
			section.defineOptListUnsigned("defListUnsigned", ':', listBig);
			section.defineOptSigned("defSigned", new BigInteger("-15"));
			section.defineOptString("defString", "Ah oj");
			section.defineOptUnsigned("defUnsigned", new BigInteger("20"));
			
			section = iniP.addSection("Section2");
			section.defineOptSigned("secSigned");
			section.defineOptSigned("secSigned2");
			section.defineOptSigned("secSigned3");
			
		
		}catch( Exception e) { System.out.println(e.toString()); }
		
		// Nacteni konfigurace
		try {
			iniP.readFile("test.ini");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Zapsani konfigurace
		try {
			iniP.writeFile("output.ini", false);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
