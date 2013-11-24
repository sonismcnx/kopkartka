/**
 * Original filename : ConnData.java
 * Created at 10:24:50 PM on Aug 20, 2013
 */
package lib;

/**
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class ConnData {

	private String jsessid;
	private String username;
	private String password;
	private String userrole;
	
	/**
	 * 
	 */
	public ConnData() {
		this(new String(), new String(), new String(), new String());
	}
	
	public ConnData(String jSessid, String userName, String userPass, String userRole) {
		setJsessid(jSessid);
		setUsername(userName);
		setUserrole(userRole);
	}

	/**
	 * @return the jsessid
	 */
	public String getJsessid() {
		return jsessid;
	}

	/**
	 * @param jsessid the jsessid to set
	 */
	public void setJsessid(String jsessid) {
		this.jsessid = jsessid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userrole
	 */
	public String getUserrole() {
		return userrole;
	}

	/**
	 * @param userrole the userrole to set
	 */
	public void setUserrole(String userrole) {
		this.userrole = userrole;
	}

}
