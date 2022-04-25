package br.com.nava.entities;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import br.com.nava.dtos.UsuarioDTO;
import lombok.AllArgsConstructor;


//ESSES IMPORTS JÁ GERALIZAM OS GETTERS E SETTERS

////////SUBSTITUI OS CONTRUTORES /////////////////
@Data 
@AllArgsConstructor // CONSTRUTOR COM ARGUMENTOS 
@NoArgsConstructor// CONSTRUTOR SEM ARGUMENTOS 

@Entity  //PRO SPRING ENTENDER QUE ISSO É UMA ENTIDADE

//ESSA ANOTAÇÃO REALIZA MAPEAMENTO E ASSOCIA ESSA TABELA COM ESSA ENTIDADE
@Table(name = "USUARIOS") // CHAVE PARA ELE PROCURAR A TABELA CORRETA, SE NÃO ELE VAI PROCURAR A CLASSE
public class UsuarioEntity {
	
	//CHAVE PRIMARIA
	@Id 
	//DIZER PRO SPRING COMO SERÁ GERADO A CHAMVE PRIMARIA ( NO CASO O BANCO)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//@Column(name = "nome_usuario")// O ATRIBUTO ABAIXO NOME NA TABELA SERÁ nome_usuario
	private String nome;
	private String email;
	
	
	public UsuarioDTO toDTO() {
		ModelMapper mapper = new ModelMapper();
		
		UsuarioDTO dto = mapper.map(this, UsuarioDTO.class);
		
		return dto;
	
	}
	
	//-------------------------------------------//RELACIONAMENTO DE CLASSES//---------------------------------------------
	
	
			 //CLASSE MAIS FORTE, É ONDE SE COLOCA O NOME DO ATRIBUTO DA CLASSE QUE MAPEIA ENDEREÇO(USUARIO) - SPRING FARA UM INNER JOIN
	
	@OneToOne 
	@JoinColumn(name = "endereco_id")
	private EnderecoEntity endereco;
	
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<VendaEntity> vendas;


	
	
}


	
	
		

