package br.com.nava.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import br.com.nava.processors.PetProcessor;

@Component
public class PetPolling  extends RouteBuilder{
	
	@Override
	public void configure() throws Exception{
		
		from("timer:pet?period=5000")
		.setHeader("id", simple("${random(0,3)}"))
		.to("rest:get:pets/{id}?host=http://localhost:8080")
		.process(new PetProcessor() )
		.log("${body}"); //MOSTRA A ROTA DE ONDE ESTA VINDO A INFORMAÇÃO
		

 }
}
