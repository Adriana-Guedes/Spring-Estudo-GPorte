package br.com.nava.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


//SINALIZA AO SPRING QUE QUEM IRA REALIZAR A INJEÇÃO DE DEPENDENCIA
@Component
public class CopyFiles extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		//PARA MOVER OS ARQUIVOS DA PASTA DE INPUT PARA PASTA OUTPUT
		from("file:input")
		.to("file:output");
		
	}
	
	

}
