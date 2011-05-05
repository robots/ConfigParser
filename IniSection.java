


import java.math.BigInteger;
import java.util.List;

public interface IniSection {
	
	public String getName();

	public IniOption defineOptString(String option);
	public IniOption defineOptString(String option, String defaultValue);
	
	public IniOption defineOptBoolean(String option);
	public IniOption defineOptBoolean(String option, boolean defaultValue);
	
	public IniOption defineOptFloat(String option);
	public IniOption defineOptFloat(String option, float defaultValue);
	
	public IniOption defineOptSigned(String option);
	public IniOption defineOptSigned(String option, BigInteger defaultValue);
	
	public IniOption defineOptUnsigned(String option);
	public IniOption defineOptUnsigned(String option, BigInteger defaultValue);
	
	public IniOption defineOptEnum(String option, String enumName);
	public IniOption defineOptEnum(String option, String enumName, String defaultValue) throws Exception;

	public IniOption defineOptListString(String option, char delimiter);
	public IniOption defineOptListString(String option, char delimiter, List<String> defaultValue);
	
	public IniOption defineOptListBoolean(String option, char delimiter);
	public IniOption defineOptListBoolean(String option, char delimiter, List<Boolean> defaultValue);
	
	public IniOption defineOptListFloat(String option, char delimiter);
	public IniOption defineOptListFloat(String option, char delimiter, List<Float> defaultValue);
	
	public IniOption defineOptListSigned(String option, char delimiter);
	public IniOption defineOptListSigned(String option, char delimiter, List<BigInteger> defaultValue);
	
	public IniOption defineOptListUnsigned(String option, char delimiter);
	public IniOption defineOptListUnsigned(String option, char delimiter, List<BigInteger> defaultValue);
	
	public IniOption defineOptListEnum(String option, String enumName, char delimiter);
	public IniOption defineOptListEnum(String option, String enumName, char delimiter, List<String> defaultValue) throws Exception;

	public IniOption getOption(String option);
	
	public void accept (IniVisitor visitor);
	/*
	public IniOption defineOptList(String option, String code, char delim);
	public IniOption defineOptList(String option, String code, char delim, List<String> defaultValues);
	 */
	
	/*
	public IniOption defineOpiontList(String option, char delim, List<Element> list);
	public IniOption defineOpiontList(String option, char delim, List<Element> list, List<Element> defaultValue);
	*/
}
