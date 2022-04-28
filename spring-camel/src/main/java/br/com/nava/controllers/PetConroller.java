package br.com.nava.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nava.entities.PetEntity;

@RestController 
@RequestMapping(value = "pets")
public class PetConroller {
	
	
	private final String[] PETS = new String[] {
			"Floquinho", "Bolinha", "Tulipa"
	};
	
	
	@GetMapping( value = "{id}")
	public ResponseEntity<PetEntity> getOne(@PathVariable int id){
		
		if(id >=0 && id < PETS.length) {
			PetEntity pet = new PetEntity(id, PETS[id]);
			System.out.println(pet);
			return ResponseEntity.ok().body(pet);
			
		}
		return null;
	}
	

}
