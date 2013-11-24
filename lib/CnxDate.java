/**
 * Original filename : CnxDate.java
 * Created at 8:30:20 PM on Oct 2, 2013
 */
package lib;

import java.util.Locale;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class CnxDate {

	private Locale locale;
	private String currentTime;
	private String currentDate;
	/**
	 * 
	 */
	public CnxDate() {
		locale = new Locale("DE", "de");
	}
	
	public CnxDate(Locale locale) {
		this.locale = locale;
	}
	
	public String getCurrentTime() {
		return null;
	}

}
