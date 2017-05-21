package src;

public class Postaus {

	    private int kirjoittajaID;
	    private String nimi;
	    private String postaus;
	    private String postausAika;

	    public Postaus(int kirjoittajaID, String nimi, String postaus, String pvmaara){
	        this.kirjoittajaID=kirjoittajaID;
	        this.nimi=nimi;
	        this.postaus=postaus;
	        this.postausAika=pvmaara;
	    }
	    public int getID(){
	        return this.kirjoittajaID;
	    }
	     public String getnimi(){
	        return this.nimi;
	    }
	     public String getPostaus(){
	    	 
	    	 return this.postaus;
	     }
	     
	      public String getPvmaara(){
	        return this.postausAika;
	    }
	      
	    @Override
	      public String toString(){
	          return "ID: "+this.kirjoittajaID+" nimi: "+this.nimi+" puhelin: "+this.postausAika;
	      }
	
}
