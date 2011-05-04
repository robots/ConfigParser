import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class IniParserImpl implements IniParser {
	
	private Map<String, IniSection> sectionMap;
	private Map<String, Set<String> > enumMap;

	@Override
	public IniSection addSection(String sectionName) {
		IniSection section = new IniSectionImpl(sectionName, this);	
		sectionMap.put(sectionName, section);
		
		return section;
	}

	@Override
	public void createEnumType(String enumName, String[] values) {
		Set<String> enumValues = new HashSet<String>();
		
		for(String val : values)
			enumValues.add(val);
		
		enumMap.put(enumName, enumValues);

	}

	@Override
	public boolean getBoolean(String sectionName, String option) {
		IniSection section = sectionMap.get(sectionName);
		
	}

	@Override
	public String getEnum(String sectionName, String option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFloat(String sectionName, String option) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IniSection getSection(String sectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getSigned(String sectionName, String option) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getString(String sectionName, String option) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getUnsigned(String sectionName, String option) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValidForEnum(String enumName, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void readFile(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readStream(InputStream inStream) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readString(String inString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBoolean(String sectionName, String option, boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEnum(String sectionName, String option, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloat(String sectionName, String option, float value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSigned(String sectionName, String option, BigInteger value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setString(String sectionName, String option, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUnsigned(String sectionName, String option, BigInteger value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeFile(String fileName) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeStream(OutputStream outStream) {
		// TODO Auto-generated method stub

	}

	@Override
	public String writeString() {
		// TODO Auto-generated method stub
		return null;
	}

}
