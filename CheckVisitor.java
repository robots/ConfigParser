
/**
 * Konkretni implementace interface IniVisitor.
 *
 * Stara sa o kontrolu mandatory poloziek.
 *
 * @author Vladimir Fiklik, Michal Demin
 *
 */
public class CheckVisitor implements IniVisitor {
	private boolean checkStatus = true;
	private IniSection lastSection = null;

	/**
	 * Zjisti, zda byly po pruchodu strukturou splneny pozadavky na pritomnost
	 * mandatory voleb
	 * @return true, pokud byly pozadavky na mandatory polozky splneny
	 * false jinak
	 */
	public boolean mandatoryCheck() {
		return checkStatus;
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

		this.checkStatus = false;

		System.err.print("In section '" + lastSection.getName() + "' option '"
			+ option.getName() + "' is mandatory, yet no value defined!!");
	}
}
