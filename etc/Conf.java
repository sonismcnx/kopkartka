package etc;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lib.DOMUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author Soni Setiawan Marta <sonismcnx@gmail.com>
 *
 */
public class Conf {
    
    private static String appTitle;
    private static String appVerMaj;
    private static String appVerMin;
    private static String appVerRev;
    private static String appAuthor;
    private static String appEmail;
    private static String appCompany;
    private static String[] appDesc;
    private static String appSrvHost;
    private static String appSrvURL;
    private static String appSrvSocket;
    private static String appThemeName;
    private static String[][] appThemeImages;
    private static String[][] appThemeIcons;
    
    public Conf() throws ParserConfigurationException, SAXException, IOException {
        getConf();
    }
    
    /**
     *
     * Method ini berfungsi untuk membaca file konfigurasi yang ber-format XML.
     * 
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void getConf() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder parser = factory.newDocumentBuilder();
        
        Document document = parser.parse(Conf.class.getResource("/etc/conf.xml").openStream());
        
        Element application = document.getDocumentElement();
        Element about = DOMUtil.getFirstElement(application, "about");
        
        appTitle = DOMUtil.getSimpleElementText(about, "title");
        Element version = DOMUtil.getFirstElement(about, "version");
        appVerMaj = DOMUtil.getSimpleElementText(version, "major");
        appVerMin = DOMUtil.getSimpleElementText(version, "minor");
        appVerRev = DOMUtil.getSimpleElementText(version, "revision");
        appAuthor = DOMUtil.getSimpleElementText(about, "author");
        appEmail = DOMUtil.getSimpleElementText(about, "email");
        appCompany = DOMUtil.getSimpleElementText(about, "company");
        Element description = DOMUtil.getFirstElement(about, "description");
        NodeList desclines = description.getElementsByTagName("line");
        appDesc = new String[desclines.getLength()];
        for( int i = 0; i < desclines.getLength(); i++ ) {
            appDesc[i] = DOMUtil.getSimpleElementText( (Element) desclines.item(i) );
        }
        Element configuration = DOMUtil.getFirstElement(application, "configuration");
        appSrvHost = DOMUtil.getSimpleElementText(configuration, "srvhost");
        appSrvURL = DOMUtil.getSimpleElementText(configuration, "srvurl");
        appSrvSocket = DOMUtil.getSimpleElementText(configuration, "srvsocket");
        
        Element theme = DOMUtil.getFirstElement(configuration, "theme");
        appThemeName = DOMUtil.getSimpleElementText(theme, "swingtheme");
        
        Element images = DOMUtil.getFirstElement(theme, "images");
        NodeList image = images.getElementsByTagName("image");
        appThemeImages = new String[image.getLength()][2];
        for(int i = 0; i < image.getLength(); i++) {
            Element elImg = (Element) image.item(i);            
            appThemeImages[i][0] = DOMUtil.getSimpleElementText( (Element) image.item(i));
            appThemeImages[i][1] = elImg.getAttribute("url");
        }
        
        Element icons = DOMUtil.getFirstElement(theme, "icons");
        NodeList icon = icons.getElementsByTagName("icon");
        appThemeIcons = new String[icon.getLength()][2];
        for(int i = 0; i < icon.getLength(); i++) {
            Element elImg = (Element) icon.item(i);            
            appThemeIcons[i][0] = DOMUtil.getSimpleElementText( (Element) icon.item(i));
            appThemeIcons[i][1] = elImg.getAttribute("url");
        }
        
    } // End public static void getConf() throws ParserConfigurationException, SAXException, IOException
    
    public static String getAppTitle() {
        return appTitle;
    } // End public static String getAppTitle()
    
    public static String getAppVer() {
        return appVerMaj + "." + appVerMin + "." + appVerRev;
    } // End public static String getAppVer()
    
    public static String[] getAppDesc() {
        return appDesc;
    } // End public static String getAppDesc()
    
    /**
	 * @return the appSrvHost
	 */
	public static String getAppSrvHost() {
		return appSrvHost;
	}

	public static String getAppSrvURL() {
        return appSrvURL;
    } // End public static String getAppSrvURL()
    
    public static String getAppSrvSocket() {
        return appSrvSocket;
    } // End public static String getAppSrvSocket()

	/**
	 * @return the appVerMaj
	 */
	public static String getAppVerMaj() {
		return appVerMaj;
	}

	/**
	 * @return the appVerMin
	 */
	public static String getAppVerMin() {
		return appVerMin;
	}

	/**
	 * @return the appVerRev
	 */
	public static String getAppVerRev() {
		return appVerRev;
	}

	/**
	 * @return the appAuthor
	 */
	public static String getAppAuthor() {
		return appAuthor;
	}

	/**
	 * @return the appEmail
	 */
	public static String getAppEmail() {
		return appEmail;
	}

	/**
	 * @return the appCompany
	 */
	public static String getAppCompany() {
		return appCompany;
	}

	/**
	 * @return the appThemeName
	 */
	public static String getAppThemeName() {
		return appThemeName;
	}

	/**
	 * @return the appThemeImages
	 */
	public static String[][] getAppThemeImages() {
		return appThemeImages;
	}

	/**
	 * @return the appThemeIcons
	 */
	public static String[][] getAppThemeIcons() {
		return appThemeIcons;
	}
    
}