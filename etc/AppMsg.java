package etc;

/**
*
* @author cnx
*/
public class AppMsg {
   
   private Object[][] msg;
   
   public AppMsg() {
       
       this.msg = new String[][]{{
           "100",
           "Error",
           "Class tidak dapat ditemukan!"
       }, {
           "101",
           "Error",
           "Null Pointer!"
       },
       {
           "102",
           "Error",
           "Array Index Out of Bound!"
       },
       {
           "103",
           "Error",
           "I/O Error"
       },
       {
           "104",
           "Error",
           "Tidak dapat me-load XML parser. Parser Configuration Exception!"
       },
       {
           "105",
           "Error",
           "Format file XML tersebut tidak valid!"
       },
       {
           "999",
           "Error",
           "Undefined Exception!"
       }}; // String[][] End
       
   } // AppMsg() End
   
   public Object[] getMsgArr(int msgCode) throws java.lang.NumberFormatException {
       Object[] msgError;
       
       msgError = msg[msg.length-1];
       String msgCode_str = java.text.NumberFormat.getInstance().format(msgCode).toString();
       String msgNum;
       
       for(int i = 0; i < msg.length; i++) {
           msgNum = (String) msg[i][0];
           if( msgCode_str.equals(msgNum) ) {
               return ( msg[i] );
           }
       }
       return msgError;
   } // getMsgArr(int msgCode) End
   
   public Object getMsgCategory(int msgCode) throws java.lang.NumberFormatException {
       Object msgError;
       
       msgError = msg[msg.length-1][1];
       String msgCode_str = java.text.NumberFormat.getInstance().format(msgCode).toString();
       String msgNum;
       
       for(int i = 0; i < msg.length; i++) {
           msgNum = (String) msg[i][0];
           if( msgCode_str.equals(msgNum) ) {
               return ( msg[i][1] );
           }
       }
       return msgError;
   } // public String getMsgCategory(int msgCode) End
   
   public Object getMsgContent(int msgCode) throws java.lang.NumberFormatException {
       Object msgError;
       
       msgError = msg[msg.length-1][2];
       String msgCode_str = java.text.NumberFormat.getInstance().format(msgCode).toString();
       String msgNum;
       
       for(int i = 0; i < msg.length; i++) {
           msgNum = (String) msg[i][0];
           if( msgCode_str.equals(msgNum) ) {
               return ( msg[i][2] );
           }
       }
       return msgError;
   } // public String getMsgContent(int msgCode) End
   
}
