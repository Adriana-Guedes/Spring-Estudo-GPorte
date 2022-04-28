package br.com.nava.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nava.dtos.ProfessorDTO;
import br.com.nava.entities.ProfessorEntity;
import br.com.nava.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;

		
	public List<ProfessorDTO> getAll(){
		List<ProfessorEntity> lista = professorRepository.findAll();
		
		List<ProfessorDTO> listaDTO = new ArrayList<>();

		for (ProfessorEntity professorEntity : lista) {

			listaDTO.add(professorEntity.toDTO());
		}
		
		return listaDTO;
		
		
		
		
	}
	

	
	public ProfessorDTO getOne(int id) {
		Optional<ProfessorEntity> optional = professorRepository.findById(id);
		ProfessorEntity professor = optional.orElse( new ProfessorEntity());
		return professor.toDTO();
	}
	
	public ProfessorDTO save(ProfessorEntity professor) {
		return professorRepository.save(professor).toDTO();		
	}
	
	
	public ProfessorDTO update(int id, ProfessorEntity professor) {

		// 1º PASSO: VERIFICAR SE O REGISTRO EXISTE NO BANCO DE DADOS
		Optional<ProfessorEntity> optional = professorRepository.findById(id);
		// SE EXISTE NO BANCO
		if(optional.isPresent() == true) {
			// ATUALIZA O OBJETO EXISTENTE
			ProfessorEntity professorBD = optional.get();
			professorBD.setNome(professor.getNome());
			professorBD.setCep(professor.getCep());
			professorBD.setCpf(professor.getCpf());
			professorBD.setNumero(professor.getNumero());
			professorBD.setRua(professor.getRua());
			
			return professorRepository.save(professorBD).toDTO();
		}
		// CASO CONTRÁRIO, RETORNA UM OBJETO VAZIO
		else {
			return new ProfessorEntity().toDTO();
		}
	}
	
	
	public void delete(int id) {
		
		professorRepository.deleteById(id);
	}
	
	
	
public List<ProfessorDTO> searchByName(String nome){
		List<ProfessorEntity> lista =  professorRepository.searchByNomeNativeSQL(nome);// SQL NATIVO
		
		List<ProfessorDTO> dtos = new ArrayList<>();
		
		for (ProfessorEntity professorEntity : lista) {
			dtos.add( professorEntity.toDTO() );
		}
		
		return dtos;
	}
	
}