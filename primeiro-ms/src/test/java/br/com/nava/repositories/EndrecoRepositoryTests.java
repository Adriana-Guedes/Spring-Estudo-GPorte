package br.com.nava.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nava.entities.EnderecoEntity;





@DataJpaTest
@ExtendWith(SpringExtension.class)
   class EndrecoRepositoryTests {



	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	 void findByIdWhenFoundTest() {
		
		EnderecoEntity enderecoEntidade = createValidEndereco();
		
		// VAI PERSISTIR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		// AO FINAL DO TESTES, ESTA ENTIDADE SERÁ DELETADA
		testEntityManager.persist(enderecoEntidade);
		
		// BUSCAR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		
		// EXECUÇÃO DO findById
		Optional<EnderecoEntity> endereco = enderecoRepository.findById( enderecoEntidade.getId() );
		
		// VALIDANDO A RESPOSTA SE O OBJETO ENCONTRAQDO NÃO É NULO 
		assertThat( endereco ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		// BUSCAR UMA ENTIDADE NA QUAL NÃO EXISTE NO BANCO DE DADOS
		Optional<EnderecoEntity> endereco = enderecoRepository.findById(1);
		
		// TEMOS QUE VERIFICAR SE O OPCIONAL NÃO POSSUI VALORES, OU SEJA, isPresent() POSSUI VALOR FALSO
		assertThat( endereco.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		EnderecoEntity enderecoEntidade = createValidEndereco();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(enderecoEntidade);
		
		//EXECUÇÃO
		List<EnderecoEntity> endereco = this.enderecoRepository.findAll();
		
		// VERIFICAR
		assertThat( endereco.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		EnderecoEntity enderecoEntidade = createValidEndereco();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(enderecoEntidade);
		
		//EXECUÇÃO
		EnderecoEntity enderecoSalvo = enderecoRepository.save(enderecoEntidade);
		
		//VALIDAÇÃO
		
		assertThat( enderecoSalvo.getId() ).isEqualTo( enderecoEntidade.getId() );
		assertThat( enderecoSalvo.getRua() ).isEqualTo( enderecoEntidade.getRua() );
		assertThat( enderecoSalvo.getNumero() ).isEqualTo( enderecoEntidade.getNumero() );
		assertThat( enderecoSalvo.getCep() ).isEqualTo( enderecoEntidade.getCep() );
		assertThat( enderecoSalvo.getCidade() ).isEqualTo( enderecoEntidade.getCidade() );
		
		
		
	}
	
	@Test
	void deleteById() {
		
		EnderecoEntity enderecoEntidade = createValidEndereco();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		EnderecoEntity enderecoTemporario = testEntityManager.persist(enderecoEntidade);
		
		//EXECUÇÃO
		enderecoRepository.deleteById( enderecoEntidade.getId() );
		
		// VERIFICAÇÃO 
		//BUSQUEI O USUARIO DELETADO E COMPAREI A RESPOSTA  
		
		Optional<EnderecoEntity> deletado = enderecoRepository.findById( enderecoTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	
	private EnderecoEntity createValidEndereco() {
		
		// INSTANCIANDO O NOBO OBJETO DO TIPO EnderecoEntity
		EnderecoEntity enderecoEntidade = new EnderecoEntity();
		
		//COLOCANDO VALORES NOS ATRIBUTOS DE  UsuarioEntity
		enderecoEntidade.setRua("Rua do Teste 9");
		enderecoEntidade.setNumero(33);
		enderecoEntidade.setCep("02540230000");
		enderecoEntidade.setCidade("São Paulo");
	  //enderecoEntidade.setId(1); = NÃO INSERIR ID POIS O BANCO ESTA FAZENDO ISSO SOZINHO

		
		// RETORNANDO ESTE NOVO OBJETO CRIADO
		return enderecoEntidade;
	}
}





