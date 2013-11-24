package lib;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author cnx
 */
public class DOMUtil {
    public static Element getFirstElement( Element element, String name ) {
              NodeList nl = element.getElementsByTagName( name );
              if ( nl.getLength(  ) < 1 ) {
            throw new RuntimeException(
               "Element: "+element+" does not contain: "+name);
        }
              return (Element)nl.item(0);
           }

           public static String getSimpleElementText( Element node, String name )
           {
              Element namedElement = getFirstElement( node, name );
              return getSimpleElementText( namedElement );
           }

           public static String getSimpleElementText( Element node )
           {
              StringBuilder sb = new StringBuilder(  );
              NodeList children = node.getChildNodes(  );
              for(int i=0; i<children.getLength(  ); i++) {
                 Node child = children.item(i);
                 if ( child instanceof Text ) {
                      sb.append( child.getNodeValue(  ) );
                  }
              }
              return sb.toString(  );
           }
}