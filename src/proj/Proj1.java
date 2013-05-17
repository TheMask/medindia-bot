package proj;
import org.jsoup.nodes.*;
import org.jsoup.select.*;  
import org.jsoup.Jsoup;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author raj
 */
public class Proj1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        
        Document doc = Jsoup.connect("http://www.medindia.net/patients/diseasefaqs/a").timeout(0).get();
        Elements lett=doc.select("div.letterlink > a");
        
        for (Element l:lett)
        {
           // System.out.println(l.attr("href")); 
            eachletter(l.attr("href"));
        }
        
    }
    public  static void eachletter(String u) throws Exception
    {
        
        Document doc = Jsoup.connect(u).timeout(0).get();
        List<String> dis = new ArrayList<String>();
       Elements commondis=doc.getElementsByClass("content16");
       // Elements commondis= doc.select("a[href]");
        for(Element link: commondis)
        {   
            String linkstr = link.attr("href");
          // System.out.println(linkstr); 
            dis.add(linkstr.toString());
        }
        getfaqs(dis);
    }
    
    public  static void getfaqs (List<String> a) throws Exception
    {int i=0;
        Iterator it=a.iterator();
       while(it.hasNext() && i++!=10)
        { 
            String str1=(String)it.next();
            //System.out.println(str1);
            /*URL u = new URL (str1 ); 
   HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
   huc.setRequestMethod ("GET"); 
   huc.connect () ; 
  // OutputStream os = huc.getOutputStream (  ) ; 
   System.out.println(huc.getResponseCode()); 
  if(huc.getResponseCode()!=404 )
            {        //System.out.println(str1); 
            * 
            * */
            String str2;
            str2=str1.replaceAll(" ","%20");
               //System.out.println(str2); 
                //Socket s = new Socket();
     // System.out.println(s.getSoTimeout());
               
                 Document doc = Jsoup.connect(str2).timeout(0).get();
                 findfaq(doc);
                              
                 
                
        }
    }
    
    public static void findfaq(Document d) throws Exception
    {
                 int n;
                 Element symq;
                 String syma="";
                   String symstr="";
           Elements item= d.getElementsByClass("content16").select("*");
              Element res = item.get(0);
                 { System.out.println(res.text()); 
                    Elements item1=d.getElementsByClass("title11");
                   // System.out.println(item1.text()); 
                     // Elements item2= res.select("div#s1");
                    //Elements item3= item2.select("span>*");
                      n=0;
                     
                     // Element syma= item3.get(n+1);
                          //System.out.println(res.text()); 
                          
                           symstr=res.toString(); 
                           n++;
                          
                             
                                 
            
                     // System.out.println(symstr); 
                       insertdb(item1.text(),symstr);
                 }   
              
            
        }
    public static Connection conn = null;
     public static String TABLE = "faq";
    public static void insertdb(String about,String qa) throws Exception
    {
           
            String url = "jdbc:mysql://localhost:3306";
             String dbName = "/crawl";
             String driver = "com.mysql.jdbc.Driver";
              String userName = "root";
             String password = "";
             
             qa=qa.replaceAll("\"","'");
           Class.forName(driver).newInstance();
    conn = DriverManager.getConnection(url+dbName,userName,password);
   // qa=qa.replaceAll("'","/'");
     System.out.println(qa);
          String insertString = "INSERT INTO " + TABLE + " VALUES (\""+about+"\",\""+qa+"\")";
  System.out.println(insertString);
  
    Statement stmt = conn.createStatement();
    stmt.executeUpdate(insertString);
    stmt.close();
  } 
  }
       