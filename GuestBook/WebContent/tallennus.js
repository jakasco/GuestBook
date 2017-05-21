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
           
            $.ajax({  //tehd��n ajax pyynt� jotta saadaan tallenttua ja haettu viestej�
                type: "POST",
                url: "Viesti",
                data: arr,
                dataType: "json",
               
             
                
                success: function( data, textStatus, jqXHR) { //jos saadaan yhteys servuun
                	
                     if(data.success){//jos p��stiin t�nne asti, niin n�ytet��n viimeisin viesti
                    	 
                         $("#ajaxViesti").html("");
                         console.log("eka....");
                         console.log(data.kirjoittaja.kirjoittajaId+data.kirjoittaja.nimi+data.kirjoittaja.postaus+data.kirjoittaja.pvmaara);
                         $("#ajaxViesti").append("<b>Viesti numero:</b> " + data.kirjoittaja.kirjoittajaID + "<br/>");
                         $("#ajaxViesti").append("<b>Kirjoittaja:</b> " + data.kirjoittaja.nimi + "<br/>");
                         $("#ajaxViesti").append("<b>Viesti:</b> " + data.kirjoittaja.postaus + "<br/>");
                         $("#ajaxViesti").append("<b>Aika:</b> " + data.kirjoittaja.postausAika + "<br/>");
        
                     } 
                    
                     else {
                    	 console.log("ei l�ydy");
                         alert("T�yt� nimi sek� viesti!");
                     }
                     console.log("valmista");
                },
               
                //jos ei saada yhteytt� servulle
                error: function(jqXHR, textStatus, errorThrown){
                     console.log("Ei Yhteytt�!" + textStatus);
                     $("#ajaxViesti").html("<div><b>reallly bad</b></div>");
                      $("#ajaxViesti").html(jqXHR.responseText);
                },
               
                //pyynt� ennen kuin on l�hetetty servulle
                beforeSend: function(jqXHR, settings){
                    settings.data += "&testingg";
                   //otetaan nappi pois k�yt�st� kunnes ollaan saatu vastaus serveletist�
                    $('#myButton').attr("disabled", true);
                }, 
               
                //kun kaikki on mennyt ilman ongelmia
                complete: function(jqXHR, textStatus){
                	
                	//nappula k�ytt��n koko loopin j�lkeen
                	
                	alert("Viesti l�hetetty!");
                	$("textarea#tallennaViesti").val("");
                	$("input#nimi").val("");
                    $('#myButton').attr("disabled", false);
                }
     
            });        
    });

});