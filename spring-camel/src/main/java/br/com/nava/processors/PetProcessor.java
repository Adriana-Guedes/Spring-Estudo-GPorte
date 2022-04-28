package br.com.nava.processors;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.nava.entities.PetEntity;


public class PetProcessor implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		String petStr = exchange.getIn().getBody(String.class);
		System.out.println("petStr" + petStr);
		
		//CONVERTER UMA STRING EM UM OBJETO DO TIPO PETENTITY
		ObjectMapper mapper = new ObjectMapper();
		
		PetEntity pet = mapper.readValue(petStr, PetEntity.class);
		System.out.println("petObj" + pet);
		
		//SALVAR PET NO BANCO DE DADOS ( EXERCICIO)
		
	}

}
