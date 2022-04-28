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

import br.com.nava.entities.VendaEntity;



@DataJpaTest
@ExtendWith(SpringExtension.class)
 class VendaRepositoryTests {
	
	

	
	@Autowired
	private VendaRepository vendaRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// VAI PERSISTIR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		// AO FINAL DO TESTES, ESTA ENTIDADE SERÁ DELETADA
		testEntityManager.persist(vendaEntidade);
		
		// BUSCAR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		
		// EXECUÇÃO DO findById
		Optional<VendaEntity> venda = vendaRepository.findById( vendaEntidade.getId() );
		
		// VALIDANDO A RESPOSTA SE O OBJETO ENCONTRAQDO NÃO É NULO 
		assertThat( venda ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		// BUSCAR UMA ENTIDADE NA QUAL NÃO EXISTE NO BANCO DE DADOS
		Optional<VendaEntity> venda = vendaRepository.findById(1);
		
		// TEMOS QUE VERIFICAR SE O OPCIONAL NÃO POSSUI VALORES, OU SEJA, isPresent() POSSUI VALOR FALSO
		assertThat( venda.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(vendaEntidade);
		
		//EXECUÇÃO
		List<VendaEntity> venda = this.vendaRepository.findAll();
		
		// VERIFICAR
		assertThat( venda.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(vendaEntidade);
		
		//EXECUÇÃO
		VendaEntity vendaSalvo = vendaRepository.save(vendaEntidade);
		
		//VALIDAÇÃO
		assertThat( vendaSalvo.getId() ).isEqualTo( vendaSalvo.getId() );
		assertThat( vendaSalvo.getValorTotal()).isEqualTo( vendaSalvo.getValorTotal() );
		
		
		
	}
	
	@Test
	void deleteById() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		VendaEntity vendaTemporario = testEntityManager.persist(vendaEntidade);
		
		//EXECUÇÃO
		vendaRepository.deleteById( vendaEntidade.getId() );
		
		// VERIFICAÇÃO 
		//BUSQUEI O USUARIO DELETADO E COMPAREI A RESPOSTA  
		
		Optional<VendaEntity> deletado = vendaRepository.findById( vendaTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	
	private VendaEntity createValidVenda() {
		
		// INSTANCIANDO O NOBO OBJETO DO TIPO ProdutoEntity
		VendaEntity vendaEntidade = new VendaEntity();
		
		//COLOCANDO VALORES NOS ATRIBUTOS DE  UsuarioEntity
		vendaEntidade.setValorTotal(Float.valueOf(200));	
	  //vendaEntidade.setId(1); = NÃO INSERIR ID POIS O BANCO ESTA FAZENDO ISSO SOZINHO

		
		// RETORNANDO ESTE NOVO OBJETO CRIADO
		return vendaEntidade;
	}


}
