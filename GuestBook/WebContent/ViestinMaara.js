var vm = 0;  //tätä tarvitaan jotta saadaan ladattua kaikki vanhat viestit

$(document).ready(function() { //LADATAAN VIESTIEN SUMMA ETUSIVULLE

	
    $("#ekaSec").submit(function(e){
           e.preventDefault();
    });
   

    $(function(e){
    	 setTimeout(function () {
    	    }, 1);
    	 $("#viimeisin").hide();
            dataString = "viestiMaara=" + 1;
            
            $.ajax({  //ajax pyyntö
                type: "POST",
                url: "Viesti",
                data: dataString,
                dataType: "json",
               
                success: function( data, textStatus, jqXHR) {

                     if(data.success){ //jos saatiin servuun yhteys
                    	 
                    	 console.log("nappia painettu");
                    	 
                         $("#viestiMaara1").html("");
                         $("#viestiMaara1").append("<b>Viestejä yhteensä:</b> " );
                         vm = data.kirjoittaja.kirjoittajaID.toString();
                         $("#viestiMaara2").html(vm);

                    
                     } 
                     else {
                    	 console.log("ei löydy");
                         $("#viestiMaara").html("<div><b>Täytä nimi sekä viesti!</b></div>");
                     }
                     console.log("valmista");

                }
     
            });        
    });

});