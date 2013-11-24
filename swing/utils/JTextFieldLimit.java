/**
 * Original filename : JTextFieldLimit.java
 * Created at 12:53:55 AM on Aug 23, 2013
 */
package swing.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class JTextFieldLimit extends PlainDocument {

	private int limit;
	/**
	 * 
	 */
	public JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}
	
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		
		if( str == null ) return;
		
		if((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}

}
