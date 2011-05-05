

import java.io.*;
import java.math.BigInteger;
import java.util.List;


public interface IniParser {
	
	// Reads ini config from file
	public void readFile(String fileName) throws IOException;
	public void writeFile(String fileName) throws IOException;
	public void readStream(InputStream inStream);	
	public void writeStream(OutputStream outStream);
	public String writeString();
	public void readString(String inString);
	
	public String getString(String sectionName, String option) throws BadTypeException;
	public float getFloat(String sectionName, String option) throws BadTypeException;
	public boolean getBoolean(String sectionName, String option) throws BadTypeException;
	public BigInteger getSigned(String sectionName, String option) throws BadTypeException;
	public BigInteger getUnsigned(String sectionName, String option) throws BadTypeException;
	public String getEnum(String sectionName, String option) throws BadTypeException;
	public String getUntyped(String sectionName, String option);
	
	public void setString(String sectionName, String option, String value);
	public void setFloat(String sectionName, String option, float value);
	public void setBoolean(String sectionName, String option, boolean value);
	public void setSigned(String sectionName, String option, BigInteger value);
	public void setUnsigned(String sectionName, String option, BigInteger value);
	public void setEnum(String sectionName, String option, String enumName, String value);
	
	public IniOption defineOptString(String sectionName, String option);
	public IniOption defineOptString(String sectionName, String option, String defaultValue);
	public IniOption defineOptBoolean(String sectionName, String option);
	public IniOption defineOptBoolean(String sectionName, String option, boolean defaultValue);
	public IniOption defineOptFloat(String sectionName, String option);
	public IniOption defineOptFloat(String sectionName, String option, float defaultValue);
	public IniOption defineOptSigned(String sectionName, String option);
	public IniOption defineOptSigned(String sectionName, String option, BigInteger defaultValue);
	public IniOption defineOptUnsigned(String sectionName, String option);
	public IniOption defineOptUnsigned(String sectionName, String option, BigInteger defaultValue);
	public IniOption defineOptEnum(String sectionName, String option, String enumName);
	public IniOption defineOptEnum(String sectionName, String option, String enumName, String defaultValue) throws Exception;

	public IniOption defineOptListString(String sectionName, String option, char delimiter);
	public IniOption defineOptListString(String sectionName, String option, char delimiter, List<String> defaultValue);
	public IniOption defineOptListBoolean(String sectionName, String option, char delimiter);
	public IniOption defineOptListBoolean(String sectionName, String option, char delimiter, List<Boolean> defaultValue);
	public IniOption defineOptListFloat(String sectionName, String option, char delimiter);
	public IniOption defineOptListFloat(String sectionName, String option, char delimiter, List<Float> defaultValue);
	public IniOption defineOptListSigned(String sectionName, String option, char delimiter);
	public IniOption defineOptListSigned(String sectionName, String option, char delimiter, List<BigInteger> defaultValue);
	public IniOption defineOptListUnsigned(String sectionName, String option, char delimiter);
	public IniOption defineOptListUnsigned(String sectionName, String option, char delimiter, List<BigInteger> defaultValue);
	public IniOption defineOptListEnum(String sectionName, String option, String enumName, char delimiter);
	public IniOption defineOptListEnum(String sectionName, String option, String enumName, char delimiter, List<String> defaultValue) throws Exception;
	
	public IniSection addSection(String sectionName);
	public IniSection getSection(String sectionName);
	
	public void createEnumType(String enumName, String[] values);
	public boolean isValidForEnum(String enumName, String value);
	
	

}
