

import java.io.*;
import java.math.BigInteger;


public interface IniParser {
	
	// Reads ini config from file
	public void readFile(String fileName) throws IOException;
	public void writeFile(String fileName) throws IOException;
	public void readStream(InputStream inStream);	
	public void writeStream(OutputStream outStream);
	public String writeString();
	public void readString(String inString);
	
	
	public String getString(String sectionName, String option);
	public float getFloat(String sectionName, String option);
	public boolean getBoolean(String sectionName, String option);
	public void getSigned(String sectionName, String option);
	public void getUnsigned(String sectionName, String option);
	public String getEnum(String sectionName, String option);
	
	public void setString(String sectionName, String option, String value);
	public void setFloat(String sectionName, String option, float value);
	public void setBoolean(String sectionName, String option, boolean value);
	public void setSigned(String sectionName, String option, BigInteger value);
	public void setUnsigned(String sectionName, String option, BigInteger value);
	public void setEnum(String sectionName, String option, String value);
	
	public IniSection addSection(String sectionName);
	public IniSection getSection(String sectionName);
	
	public void createEnumType(String enumName, String[] values);
	public boolean isValidForEnum(String enumName, String value);
	
	

}
