$(document).ready(function() {

    $("#ekaSec").submit(function(e){
           e.preventDefault();
    });

    $("#myButton").click(function(e){
          
    	
            dataString = $("#ekaSec").serialize();
            $("#viimeisin").show();
            var tallennaViesti = $("textarea#tallennaViesti").val(); 
            var nimi = $("input#nimi").val(); 
            dataString = "tallennaViesti=" + tallennaViesti;
            
            var arr1 = [dataString , nimi];
            
            var arr = JSON.stringify(arr1);
           
            $.ajax({  //tehdään ajax pyyntö jotta saadaan tallenttua ja haettu viestejä
                type: "POST",
                url: "Viesti",
                data: arr,
                dataType: "json",
               
             
                
                success: function( data, textStatus, jqXHR) { //jos saadaan yhteys servuun
                	
                     if(data.success){//jos päästiin tänne asti, niin näytetään viimeisin viesti
                    	 
                         $("#ajaxViesti").html("");
                         console.log("eka....");
                         console.log(data.kirjoittaja.kirjoittajaId+data.kirjoittaja.nimi+data.kirjoittaja.postaus+data.kirjoittaja.pvmaara);
                         $("#ajaxViesti").append("<b>Viesti numero:</b> " + data.kirjoittaja.kirjoittajaID + "<br/>");
                         $("#ajaxViesti").append("<b>Kirjoittaja:</b> " + data.kirjoittaja.nimi + "<br/>");
                         $("#ajaxViesti").append("<b>Viesti:</b> " + data.kirjoittaja.postaus + "<br/>");
                         $("#ajaxViesti").append("<b>Aika:</b> " + data.kirjoittaja.postausAika + "<br/>");
        
                     } 
                    
                     else {
                    	 console.log("ei löydy");
                         alert("Täytä nimi sekä viesti!");
                     }
                     console.log("valmista");
                },
               
                //jos ei saada yhteyttä servulle
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Ei Yhteyttä!" + textStatus);
                     $("#ajaxViesti").html("<div><b>reallly bad</b></div>");
                      $("#ajaxViesti").html(jqXHR.responseText);
                },
               
                //pyyntö ennen kuin on lähetetty servulle
                beforeSend: function(jqXHR, settings){
                    settings.data += "&testingg";
                   //otetaan nappi pois käytöstä kunnes ollaan saatu vastaus serveletistä
                    $('#myButton').attr("disabled", true);
                }, 
               
                //kun kaikki on mennyt ilman ongelmia
                complete: function(jqXHR, textStatus){
                	
                	//nappula käyttöön koko loopin jälkeen
                	
                	alert("Viesti lähetetty!");
                	$("textarea#tallennaViesti").val("");
                	$("input#nimi").val("");
                    $('#myButton').attr("disabled", false);
                }
     
            });        
    });

});