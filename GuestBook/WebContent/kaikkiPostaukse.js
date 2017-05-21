 var viestiMaara = ""; //ladataan viestienmaarasta jotta saadaan looppiin näiden summa

$(document).ready(function() {

    $("#ekaSec").submit(function(e){
           e.preventDefault();
    });
   
    function createDiv() {
        var boardDiv = document.createElement("div");

        boardDiv.className = "uusi-div";
        boardDiv.innerText = "uusi div";

        return boardDiv;
      }


    $(function(e){  //("#button3").click(function(e) <- napin painaessa
    	var fileInterval = setInterval(function(){ 
    	    if (vm!=0){ //tarkastetaan että viestien määrä on latautunut, jotta voidaan laittaa kaikki viestit näkyville

    	    clearInterval(fileInterval);
    
    		viestiMaara = vm;
            dataString = $("#kolmasSec").serialize();
            
            var numero = 1;
            var n = numero.toString(); 
            dataString = "numero="+n;

            var board = document.getElementById("kaikkiViestit"),
      	  	myDivs = [];

            var max = parseInt(viestiMaara) ;
            for(numero; numero<max; numero++){  //tehdään uudet divit joihin saa vanhat viestit
            	
                myDivs.push(createDiv());
      	      	board.appendChild(myDivs[numero-1]);  //-1 koska alkaa nollasta
      	      	myDivs[numero-1].innerText = "loading...";

      	      var num = numero-1;
      	      var jokuId = "myDiv"+num;
      	    myDivs[numero-1].id = jokuId;
      	    console.log("id: "+myDivs[numero-1].id);
            }// FOR loppuu
            
            numero = 1; //Nollaa numero jottei seuraavassa loopissa tule ongelmia
            
            arr1 = [];
            var i = 0;
            for(i;i<49;i++){ //
            	arr1[i] = "arr="+i.toString();  //Muuta stringiksi, jotta tietovarasto osaa lukea sen "request parameter"...
            	console.log(arr1[i]);
            }

            var arr = JSON.stringify(arr1);
            
            console.log("jos ei toimi, niin data ei mene tietovarastoon ja requestparameter on null");
            $.ajax({
                type: "POST",
                url: "Viesti",
                data: arr,
                dataType: "json",

                success: function( data, textStatus, jqXHR) {

                     if(data.success){

                    	 // TEE UUDET DIVIT
                    	 var pituus = data.kirjoittaja.length;
                    	 console.log("Pituus : "+pituus);
                    	 for(i=0; i<pituus; i++){
                    		 var nro = i+1;
                    		 console.log("i = "+i);
                    myDivs[i].innerText = "Viesti nro: "+data.kirjoittaja[i].kirjoittajaID+" Nimi: "+data.kirjoittaja[i].nimi+" Aika: "+data.kirjoittaja[i].postausAika+" || "+data.kirjoittaja[i].postaus;;            
                    myDivs[i].append;
                     }

                    	 } //if data success loppuu

                     else {
                    	 console.log("ei löydykaikkiPostaukset");
                     }
                     console.log("valmista");

                },
              
            });       
    	    }//time intervall
    	},100); // tarkastetaan joka 100millisekka että vm on eri kuin 0
    });

});