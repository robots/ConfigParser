import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class IniParserImpl implements IniParser {
	
	private Map<String, IniSection> sectionMap;
	private Map<String, Set<String> > enumMap;
	
	public IniParserImpl()
	{
		sectionMap = new HashMap<String, IniSection>();
		enumMap = new HashMap<String, Set<String>> ();
	}

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
	public boolean getBoolean(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueBool();
				
	}

	@Override
	public String getEnum(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueEnum();
	}

	@Override
	public float getFloat(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueFloat();
	}

	@Override
	public IniSection getSection(String sectionName) {
		
		return getSection(sectionName, false);
	}
	
	protected IniSection getSection(String sectionName, boolean autoCreate) {
		
		IniSection section = sectionMap.get(sectionName);
		
		if(autoCreate && (section == null))
			section = addSection(sectionName);
		
		return section;
		
	}

	@Override
	public BigInteger getSigned(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueSigned();
	}

	@Override
	public String getString(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueString();
	}

	@Override
	public BigInteger getUnsigned(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueUnsigned();
	}
	
	@Override
	public String getUntyped(String sectionName, String option) throws BadTypeException {
		IniSection section = sectionMap.get(sectionName);
		IniOption opt = section.getOption(option);
		
		return opt.getValueUntyped();
	}


	@Override
	public boolean isValidForEnum(String enumName, String value){

		Set<String> enumValues = enumMap.get(enumName);
		
		if(enumValues == null)
			return false;
		
		return enumValues.contains(value);
	
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
	
		IniOption opt = this.getOption(sectionName, option);
		
		opt.setValue(value);
		

	}

	@Override
	public IniOption defineOptString(String sectionName, String option) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptString(option);
		
		return opt;
	}

	@Override
	public IniOption defineOptString(String sectionName, String option,
			String defaultValue) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptString(option, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptBoolean(option);
		
		return opt;
	}

	@Override
	public IniOption defineOptBoolean(String sectionName, String option,
			boolean defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptBoolean(option, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptFloat(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptFloat(option);
		
		return opt;
	}

	@Override
	public IniOption defineOptFloat(String sectionName, String option,
			float defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptFloat(option, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptSigned(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptSigned(option);
		
		return opt;
	}

	@Override
	public IniOption defineOptSigned(String sectionName, String option,
			BigInteger defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptSigned(option, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String sectionName, String option) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptUnsigned(option);
		
		return opt;
	}

	@Override
	public IniOption defineOptUnsigned(String sectionName, String option,
			BigInteger defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptUnsigned(option, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptEnum(String sectionName, String option,
			String enumName) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptEnum(option, enumName);
		
		return opt;
	}

	@Override
	public IniOption defineOptEnum(String sectionName, String option,
			String enumName, String defaultValue) throws Exception {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptEnum(option, enumName, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListString(String sectionName, String option,
			char delimiter) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListString(option, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListString(String sectionName, String option,
			char delimiter, List<String> defaultValue) {
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListString(option, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String sectionName, String option,
			char delimiter) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListBoolean(option, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListBoolean(String sectionName, String option,
			char delimiter, List<Boolean> defaultValue) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListBoolean(option, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String sectionName, String option,
			char delimiter) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListFloat(option, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListFloat(String sectionName, String option,
			char delimiter, List<Float> defaultValue) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListFloat(option, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String sectionName, String option,
			char delimiter) {
		
		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListSigned(option, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListSigned(String sectionName, String option,
			char delimiter, List<BigInteger> defaultValue) {

		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListSigned(option, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String sectionName, String option,
			char delimiter) {

		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListUnsigned(option, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListUnsigned(String sectionName, String option,
			char delimiter, List<BigInteger> defaultValue) {

		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListUnsigned(option, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String sectionName, String option,
			String enumName, char delimiter) {

		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListEnum(option, enumName, delimiter);
		
		return opt;
	}

	@Override
	public IniOption defineOptListEnum(String sectionName, String option,
			String enumName, char delimiter, List<String> defaultValue)
			throws Exception {

		IniSection section = getSection(sectionName, true);
		IniOption opt = section.defineOptListEnum(option, enumName, delimiter, defaultValue);
		
		return opt;
	}

	@Override
	public void setEnum(String sectionName, String option, String enumName, String value) {
		IniOption opt = this.getOption(sectionName, option);
		
		opt.setValue(value);
		opt.setEnumName(enumName);
	}

	@Override
	public void setFloat(String sectionName, String option, float value) {
	
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setSigned(String sectionName, String option, BigInteger value) {
		
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setString(String sectionName, String option, String value) {
		
		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
	}

	@Override
	public void setUnsigned(String sectionName, String option, BigInteger value) {

		IniOption opt = this.getOption(sectionName, option);
		opt.setValue(value);
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
	
	protected IniOption getOption(String sectionName, String option)
	{
		return getSection(sectionName).getOption(option);
	}

	@Override
	public void accept(IniVisitor visitor) {
		visitor.visit(this);
		
		for( IniSection section : sectionMap.values())
		{
			section.accept(visitor);
		}
		
	}

}
