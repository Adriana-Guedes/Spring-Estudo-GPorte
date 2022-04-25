package br.com.nava.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeiroController {

	@GetMapping("bem-vindo-spring")//ENDEREÇO DA PAGINA
	
	//METODO GET
	public String bemVindo() { 
		return "Bem Vindo" ;
	}
	
	//METODO POST -- NÃO DA PRA TESTAR NO NAVEGADOR TEM QUE SER NO EXECUTAVEL DO POSTMAN
	@PostMapping("bem-vindo-spring")
	public String bemVindoPost() {
		return "Bem Vindo" ; 
	
 }
}
