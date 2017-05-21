package src;

import java.awt.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;






import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;




public class Tietovarasto extends HttpServlet { //TALLENNETAAN VIESTIT SQL TIETOKANTAAN
	
    private String ajuri="com.mysql.jdbc.Driver";
    private String url="jdbc:mysql://localhost/guestbook";
    private String kayttaja="root";
    private String salasana="";
    
    private int vika;
    
    private String postauksentallennusSQL = "insert into kirjoittaja (kirjoittajaID,nimi,postaus,postausAika) values (?,?,?,?)";
    private String viestinHakuSQL = "select * from kirjoittaja where kirjoittajaID=?";
    
	private static final long serialVersionUID = 1L;

	public Tietovarasto() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	//ajax pyynnön vastaanottaminen
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {

		String tallennaViesti = request.getParameter("tallennaViesti");
		String viestiMaara = request.getParameter("viestiMaara");
		 //katsotaan tuleeko parametrit ajaxista tänne asti
			Map<String, String[]> params = request.getParameterMap(); 
			System.out.println("params: "+params);
			String[][] result = params.values().toArray(new String[0][0]); //muutetaan mappi arrayksi, ja sitä kautta saadaan arvot			
			String str = result[0][0];
			String a;
			String a2;
			String nimi = "nimi";
			a = str.replaceAll("\"", "");
			a2 = a.replaceAll("arr=", "");
			String[] t = a2.split(",");
			System.out.println("t: "+Arrays.toString(t));
		
			try {
			if(t[0]==null || t[1] == null){
				
			}else{
			System.out.println(t[0]+" t0 , t1 -> "+t[1]);
			}
			}
			catch(ArrayIndexOutOfBoundsException exception) {
			   System.out.println("Array virhe");
			}
			

		System.out.println("Post do ..... "); //tarkastusta vain
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		
		//////////////////////////////////////////////////////////////////////////////////////viestien määrä
		if(viestiMaara=="" || viestiMaara ==null){  //TAI NULL
			System.out.println("älä tee mitään");
		}else{
		
		vika = vikaId();
		
		Postaus post = etsiPostaus(vika);

		JsonElement viestiObj = gson.toJsonTree(post);
		if (post.getnimi() == null) {  //MUUTA
			myObj.addProperty("success", false);
		} else {
			myObj.addProperty("success", true);
		}
		myObj.add("kirjoittaja", viestiObj);
		out.println(myObj.toString());
		out.close();
		}
		///////////////////////////////////////////////////////////////////////////////////////viestin tallennus
		
		if(tallennaViesti=="" || tallennaViesti ==null){  //TAI NULL
			System.out.println("älä tee mitään");
		}else{
		
		vika = vikaId()+1; //listään 1 jotta tietokantaan tulee 1 indeksi lisää
		
		System.out.println("vika: "+vika);
		tallennaTietokantaan(vika, nimi, tallennaViesti, pvMaara());
		System.out.println("tallennettu!");
		Postaus post = etsiPostaus(vika);
		
		JsonElement viestiObj = gson.toJsonTree(post);
		if (post.getnimi() == null) {  //MUUTA
			myObj.addProperty("success", false);
		} else {
			myObj.addProperty("success", true);
		}
		myObj.add("kirjoittaja", viestiObj);
		out.println(myObj.toString());
		out.close();
		}
		///////////////////////////////////////////////////////////////////////////////////////////kaikki viestit
		
		if(t[0]==null){
			System.out.println("kolmas on nulL!!");
			//kolmas = "null";
		}else{ 
			
		ArrayList<Postaus> list = kaikkiPostaukset();	

		JsonElement viestiObj = gson.toJsonTree(list);
		if (t[0] == null) {  //MUUTA testausta vain
			myObj.addProperty("success", false);
		} else {
			myObj.addProperty("success", true);
		}
		myObj.add("kirjoittaja", viestiObj);
		out.println(myObj.toString());
		out.close();

		}
	
	}
	
	public String pvMaara() {  //OTETAAN TALTEEN PVMÄÄRÄ
	    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(vika));
	}
	
	
	//SQL kanssa tarvittavat metodit
	public void tallennaTietokantaan(int id, String nimi, String kirjoittaja, String pvmaara){
		
	    Connection yhteys = null;
	    PreparedStatement viestinLisays=null;
	    try{
	        yhteys=YhteydenHallinta.avaaYhteys(ajuri,url,kayttaja,salasana);
	        viestinLisays = yhteys.prepareStatement(postauksentallennusSQL);
	        viestinLisays.setInt(1, id);
	        viestinLisays.setString(2, nimi);
	        viestinLisays.setString(3, kirjoittaja);
	        viestinLisays.setString(4, pvmaara);

	        viestinLisays.executeUpdate();

	    }catch(Exception e){
	        System.out.println("Virhe lisäyksessä"+ e);
	    }finally{
	        YhteydenHallinta.suljeLause(viestinLisays);
	        YhteydenHallinta.suljeYhteys(yhteys);
	    }
		
		
	}

	
	public Postaus etsiPostaus(int p){
		System.out.println("etsitään postaus...");
	    Connection yhteys = null;
	    PreparedStatement viestinHaku=null;
	    Postaus a1 = null;
	    ResultSet viestiTulos = null;
	    try{
	        yhteys=YhteydenHallinta.avaaYhteys(ajuri,url,kayttaja,salasana);
	        viestinHaku = yhteys.prepareStatement(viestinHakuSQL);
	        viestinHaku.setInt(1, p);
	        viestiTulos = viestinHaku.executeQuery();

	         while(viestiTulos.next()){
	            int id = viestiTulos.getInt(1);
	            String nimi = viestiTulos.getString(2);
	            String post = viestiTulos.getString(3);
	            String pvmaara = viestiTulos.getString(4);
	            a1 = new Postaus(id,nimi,post,pvmaara);
	        }

	    }catch(Exception e){
	        e.printStackTrace();
	    }finally{
	        YhteydenHallinta.suljeTulosjoukko(viestiTulos);
	        YhteydenHallinta.suljeLause(viestinHaku);
	        YhteydenHallinta.suljeYhteys(yhteys);

	    }
	    System.out.println("postaus löydetty!");
	    return a1;
	}
	
	
	public int vikaId(){
		
		int viimeinenId = 0;
		
	 ArrayList<Postaus> postaukset = new ArrayList<Postaus>();
     Connection yhteys = YhteydenHallinta.avaaYhteys(ajuri, url, kayttaja, salasana);
     if(yhteys!=null){
         PreparedStatement hakulause = null;
  
         ResultSet tulosjoukko = null;
      
         try{
        	 
         String hakulauseSQL ="select * from kirjoittaja";
        
         hakulause = yhteys.prepareStatement(hakulauseSQL);
      
         tulosjoukko = hakulause.executeQuery();
       

         while(tulosjoukko.next()){
             int id = tulosjoukko.getInt(1);
             String nimi = tulosjoukko.getString(2);
             String post = tulosjoukko.getString(3);
             String pv = tulosjoukko.getString(4);
             Postaus a1 = new Postaus(id,nimi,post, pv);

             viimeinenId = id;
             
             postaukset.add(a1);
     }

         }catch(Exception e){
             e.printStackTrace();
         }finally{
             YhteydenHallinta.suljeTulosjoukko(tulosjoukko);
             YhteydenHallinta.suljeLause(hakulause);
             YhteydenHallinta.suljeYhteys(yhteys);
         }

     }
     return viimeinenId;
 }
	
	
	public String viimeisinId(ArrayList a){
		
		Object viimeinen = null;
		String vika = null;
		int id;
		
		if (a != null && !a.isEmpty()) {
			viimeinen = a.get(a.size()-1);
			}
		if(viimeinen!=null){
		vika = viimeinen.toString();
		}

		return null;
	}
	
public ArrayList<Postaus> kaikkiPostaukset(){
		
	 ArrayList<Postaus> postaukset = new ArrayList<Postaus>();
     Connection yhteys = YhteydenHallinta.avaaYhteys(ajuri, url, kayttaja, salasana);
     if(yhteys!=null){
         PreparedStatement hakulause = null;
  
         ResultSet tulosjoukko = null;
      
         try{
        	 
         String hakulauseSQL ="select * from kirjoittaja";
        
         hakulause = yhteys.prepareStatement(hakulauseSQL);
      
         tulosjoukko = hakulause.executeQuery();
       

         while(tulosjoukko.next()){
             int id = tulosjoukko.getInt(1);
             String nimi = tulosjoukko.getString(2);
             String post = tulosjoukko.getString(3);
             String pvmaara = tulosjoukko.getString(4);
             Postaus a1 = new Postaus(id,nimi,post, pvmaara);
     
             postaukset.add(a1);
     }

         }catch(Exception e){
             e.printStackTrace();
         }finally{
             YhteydenHallinta.suljeTulosjoukko(tulosjoukko);
             YhteydenHallinta.suljeLause(hakulause);
             YhteydenHallinta.suljeYhteys(yhteys);
         }

     }
     return postaukset;
 }

}
