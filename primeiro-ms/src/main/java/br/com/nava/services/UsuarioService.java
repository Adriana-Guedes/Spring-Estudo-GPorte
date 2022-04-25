package br.com.nava.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nava.dtos.UsuarioDTO;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public void mostrar() {
		System.out.println("Mostrar");
	}
	
	public List<UsuarioDTO> getAll(){
		
		List<UsuarioEntity> lista = usuarioRepository.findAll();
		
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		for (UsuarioEntity usuarioEntity : lista) {
			listaDTO.add(usuarioEntity.toDTO());
			}
			
			return listaDTO;
	}
	

	
	public UsuarioDTO getOne(int id) {
		Optional<UsuarioEntity> optional = usuarioRepository.findById(id);
		UsuarioEntity usuario = optional.orElse( new UsuarioEntity());
		return usuario.toDTO();
			
	}
	
	public UsuarioDTO save(UsuarioEntity usuario) {
		return usuarioRepository.save(usuario).toDTO();		
	}

		
		
		public UsuarioDTO update(int id, UsuarioEntity usuario) {
			
			 //VERIFICAR SE O REGISTRO EXISTE
			Optional<UsuarioEntity> optional = usuarioRepository.findById(id);
		
			 //SE EXISTIR
			if(optional.isPresent() == true) {
				//PEGAR O OBJETO DENTRO O OPTIONAL VINDO DO BANCO E SETO COM O VALOR DE ENTRADA NO PARAMETRO DA FUNÇÃO
				UsuarioEntity usuarioBD = optional.get();
				usuarioBD.setNome(usuario.getNome());
				usuarioBD.setEmail(usuario.getEmail());
				
				return usuarioRepository.save(usuarioBD).toDTO();
			}
		
			else {
				return new UsuarioEntity().toDTO();
			}
		}
		
		
		public void delete(int id) {
			usuarioRepository.deleteById(id);
		}


}
