
/**
 * Konkretni implementace interface IniVisitor.
 *
 * Stara sa o kontrolu mandatory poloziek.
 *
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class CheckVisitor implements IniVisitor {
	private boolean checkOK = true;
	private IniSection lastSection = null;

	public boolean isOK() {
		return checkOK;
	}

	@Override
	public void visit(IniParser parser) {
	}

	/**
	 * Metoda volana pri navstiveni "IniSection".
	 *
	 * Odlozi referenciu na sekciu.
	 *
	 * @param section referencia na IniSection
	 */
	@Override
	public void visit(IniSection section) {
		lastSection = section;
	}

	/**
	 * Metoda volana pri navstiveni "IniSection".
	 *
	 * Testuje ci, mandatory volby maju definovane hodnoty.
	 *
	 * @param option referencia na IniOption
	 */
	@Override
	public void visit(IniOption option)
	{
		if (option.isMandatory() == false) {
			return;
		}

		if(option.hasDefinedValue()) {
			return;
		}

		this.checkOK = false;

		System.err.print("In section '" + lastSection.getName() + "' option '"
			+ option.getName() + "' is mandatory, yet no value defined!!");
	}
}
