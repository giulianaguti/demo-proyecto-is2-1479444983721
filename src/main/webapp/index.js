	var fila =2;
	var columna =65;
	var columnaEstatica=65;
	var filaEstatica=2;
	var contadorrr =1;
	var grupoFila=1;

var verificarsinoesnumero = function validarSiNumero(numero){
    if (!/^([0-9])*$/.test(numero)){
      return 0;
    }else{
    	return 1;
    }
  }




var VerificarLetras= function tiene_numeros(texto){
	var numeros="0123456789";

	if(texto==""){
	texto=0;
}
   for(i=0; i<texto.length; i++){
      if (numeros.indexOf(texto.charAt(i),0)!=-1){
         return 1;
      }
   }
   return 0;
};



var llenardato = function (dato){
    var suma2 = 0;
var suma = $("#"+dato).val();

var rpta=suma.split(" ");




var vali = rpta[0];
if( vali=="=SUMA"){
console.log(rpta);
var rpta2 = rpta[1];
var rpta3 = rpta2.split(":");
// captura de la primera part antes de los :
var letra1=rpta3[0].charAt(0);
var numero1="";
for(var i=1;i<=rpta3[0].length-1;i++){
numero1 = numero1 + rpta3[0].charAt(i);
}
// captura de la segunda parte despues de los :

var letra2 = rpta3[1].charAt(0);
var numero2="";

for(var i=1;i<=rpta3[1].length-1;i++){
numero2 = numero2 + rpta3[1].charAt(i);
}
//FIN DE LA PARTE 2
var letranuero1 = letra1.charCodeAt(0);
var letraNuero2 = letra2.charCodeAt(0);
var boleano = 0;

//comienza centencia 

if(letraNuero2<=columna && numero2<=fila){







//ACA DE HACE LA OPERACION;
console.log(letranuero1);
console.log(letraNuero2);
console.log(numero1);
console.log(numero2);
console.log("abajo sale lo capturado")
for(var i=letranuero1;i<=letraNuero2;i++){

for(var j=numero1;j<=numero2;j++){
var TransformadorLetra =String.fromCharCode(i); 

console.log($("#"+TransformadorLetra+j).val());

if(verificarsinoesnumero($("#"+TransformadorLetra+j).val())==0 ){
	i=letraNuero2+1;
	j=numero2+1;
	$("#"+dato).val("Error Letra Encontrada ");
	console.log("entro aca");

}else{

var capturado = $("#"+TransformadorLetra+j).val();
if(capturado==""){
	capturado=0;
	console.log("Entro aca para ponerlo a 0")
}

suma2=suma2+parseInt(capturado);
$("#"+dato).attr('value',suma2);
$("#"+dato).val(suma2);

}






}

}

// aca termina la operacion ;








}else{

$("#"+dato).val("Error Fuera de Rango");
}
//termina sentencia






}else{
$("#"+dato).attr('value', suma);
}









};


	 $(document).ready(function(){



	//aca agrega filas

	$( "#botonfila" ).click(function(e) {

	  var columna1 = String.fromCharCode(columna);


	  if(fila==filaEstatica){
	  	$("#2").append('<td>'+ fila+'</td>')
		$("#2").append('<td><input class="enter" id="'+columna1+fila+'" value="" /></td>');
		var filaTEMP= fila;
		fila=fila+1;

		$("#Linea").append('<tr id="'+fila+'"></tr>');
	  
		






	if(columna!=columnaEstatica){
	  	var limitador2 =columna - columnaEstatica;
	  	var i = 1;
	  
	  	for(i;i<limitador2+1;i++){
	 	var temporal1 = String.fromCharCode(temporal);
	  		$("#"+filaTEMP).append('<td><input class="enter" id="'+temporal1+i+'" value="" /></td>');
	  		temporal=temporal+1;
	  	}
	temporal=columnaEstatica;
	  }
	  }else{

	  	var columna1 = String.fromCharCode(columnaEstatica);
	  	$("#"+fila).append('<td>'+fila+'</td>');
	  	$("#"+fila).append('<td><input class="enter" id="'+columna1+fila+'" value=""/></td>');
	  	var filaTEMP= fila;
	 	fila=fila+1;
		$("#Linea").append('<tr id="'+fila+'"></tr>');
		var temporal = columnaEstatica +1;
	  if(columna!=columnaEstatica){
	  	var limitador2 =columna - columnaEstatica;
	  	var i = 1;
	  	for(i;i<limitador2+1;i++){
	 	var temporal1 = String.fromCharCode(temporal);
	 	var i2=i+1;
	  		$("#"+filaTEMP).append('<td><input  class="enter" id="'+temporal1+filaTEMP+'" value="" /></td>');
	  		temporal=temporal+1;
	  	}
	temporal=columnaEstatica;
	  }
	}
		


	})






	//aca se hacen las columnas

	$( "#botoncolumna" ).click(function() {
//llegar hasta Z
var validador= columna+1;


if(validador==90){
	$( "#botoncolumna" ).prop( "disabled", true );
};

	  if(columna==columnaEstatica){
	  	columna=columna+1;
	var columna1 = String.fromCharCode(columna);

	  $("#1").append('<td><input  class="enter" id="'+columna1+grupoFila+'" /></td>');
	  $("#letras").append('<th>'+ columna1+ '</th>');
	  if(fila!=filaEstatica){
	var limitador = fila  - filaEstatica;
	var contador = 1;
	  	for(var i=2;i<=limitador+1;i++){
	  		$("#"+i).append('<td><input class="enter" id="' +columna1+i+'"/></td>')
	  		
	  	}
	  }
	  }else{

	  	columna=columna+1;
	var columna1 = String.fromCharCode(columna);
	  $("#1").append('<td><input  class="enter" id="'+columna1+grupoFila+'" /></td>');
	   $("#letras").append('<th>'+ columna1+ '</th>');
	var limite = fila - filaEstatica;
	// crear las faltantes
	  if(fila!=filaEstatica){
	var limitador = fila  - filaEstatica;
	  	for(var i=2;i<=limitador+1;i++){
	  		$("#"+i).append('<td><input  class="enter" id="' +columna1+i+'"/></td>')
	  	
	  	}
	  }	



	  $("input").change(function(){
	var valor= $(this).attr('id');
	
	while(contadorrr==1){
		console.log(valor);
		llenardato(valor);
		contadorrr=contadorrr+1;
	}
	

console.log(contadorrr);
	        
	    });


	  };



 


	});





$( "body" ).click(function() {
  contadorrr=1;
console.log("aca esta en "+ contadorrr);
});









	});

