import java.io.*;
import java.math.BigInteger;
import java.util.List;


public interface IniParser {
	public void setAttitude(ParserAttitude attitude);

	// Reads ini config from file
	public void readFile(String fileName) throws IOException;
	public void writeFile(String fileName) throws IOException;
	public void readStream(InputStream inStream) throws IOException;
	public void writeStream(OutputStream outStream) throws IOException;
	public String writeString();
	public void readString(String inString);

	public String getString(String sectionName, String option) throws IniException;
	public float getFloat(String sectionName, String option) throws IniException;
	public boolean getBoolean(String sectionName, String option) throws IniException;
	public BigInteger getSigned(String sectionName, String option) throws IniException;
	public BigInteger getUnsigned(String sectionName, String option) throws IniException;
	public String getEnum(String sectionName, String option) throws IniException;
	public String getUntyped(String sectionName, String option) throws IniException;

	public void setString(String sectionName, String option, String value) throws IniException;
	public void setFloat(String sectionName, String option, float value) throws IniException;
	public void setBoolean(String sectionName, String option, boolean value) throws IniException;
	public void setSigned(String sectionName, String option, BigInteger value) throws IniException;
	public void setUnsigned(String sectionName, String option, BigInteger value) throws IniException;
	public void setEnum(String sectionName, String option, String enumName, String value) throws IniException;

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
	public IniOption defineOptEnum(String sectionName, String option, String enumName, String defaultValue) throws BadValueException, IniAccessException;

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
	public IniOption defineOptListEnum(String sectionName, String option, String enumName, char delimiter, List<String> defaultValue) throws BadValueException, IniAccessException;

	public IniSection addSection(String sectionName);
	public IniSection getSection(String sectionName);

	public void createEnumType(String enumName, String[] values);
	public boolean isValidForEnum(String enumName, String value);

	public void accept (IniVisitor visitor);
}
