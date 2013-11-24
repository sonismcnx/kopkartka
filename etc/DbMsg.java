package etc;

/**
*
* @author cnx
*/
public class DbMsg {
   
   private String[][] msg;
   
   public DbMsg() {
       this.msg = new String[][]{{
           "0", 
           "Sukses",
           "Data berhasil ditambahkan."
       }, {
           "1", 
           "Sukses",
           "Data berhasil diupdate."
       }, {
           "50", 
           "Gagal",
           "Format data tidak benar."
       }, {
           "51", 
           "Gagal",
           "Data dengan ID tersebut sudah ada."
       }, {
           "99", 
           "Gagal",
           "Query gagal!"
       }, {
           "100", 
           "Gagal",
           "Driver Database tidak dapat ditemukan!"
       }, {
           "101", 
           "Gagal",
           "Gagal melakukan koneksi ke database!"
       }, {
           "999",
           "Gagal",
           "Undefined Error! (999)"
       }};
   }
   
   public String[] getMsgArr(int msgCode) throws java.lang.NumberFormatException {
       String[] msgError;
       
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
   
   public String getMsgCategory(int msgCode) throws java.lang.NumberFormatException {
       String msgError;
       
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
   
   public String getMsgContent(int msgCode) throws java.lang.NumberFormatException {
       String msgError;
       
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
