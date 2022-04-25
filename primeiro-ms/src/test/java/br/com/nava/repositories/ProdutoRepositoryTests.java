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

import br.com.nava.entities.ProdutoEntity;


@DataJpaTest
@ExtendWith(SpringExtension.class)
public class ProdutoRepositoryTests {
	
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// VAI PERSISTIR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		// AO FINAL DO TESTES, ESTA ENTIDADE SERÁ DELETADA
		testEntityManager.persist(produtoEntidade);
		
		// BUSCAR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		
		// EXECUÇÃO DO findById
		Optional<ProdutoEntity> produto = produtoRepository.findById( produtoEntidade.getId() );
		
		// VALIDANDO A RESPOSTA SE O OBJETO ENCONTRAQDO NÃO É NULO 
		assertThat( produto ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		// BUSCAR UMA ENTIDADE NA QUAL NÃO EXISTE NO BANCO DE DADOS
		Optional<ProdutoEntity> produto = produtoRepository.findById(1);
		
		// TEMOS QUE VERIFICAR SE O OPCIONAL NÃO POSSUI VALORES, OU SEJA, isPresent() POSSUI VALOR FALSO
		assertThat( produto.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(produtoEntidade);
		
		//EXECUÇÃO
		List<ProdutoEntity> produto = this.produtoRepository.findAll();
		
		// VERIFICAR
		assertThat( produto.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(produtoEntidade);
		
		//EXECUÇÃO
		ProdutoEntity produtoSalvo = produtoRepository.save(produtoEntidade);
		
		//VALIDAÇÃO
		assertThat( produtoSalvo.getId() ).isNotNull();
		assertThat( produtoSalvo.getNome() ).isEqualTo( produtoEntidade.getNome() );
		assertThat( produtoSalvo.getDescricao() ).isEqualTo( produtoEntidade.getDescricao() );
		assertThat( produtoSalvo.getPreco() ).isEqualTo( produtoEntidade.getPreco() );
		
		
	}
	
	@Test
	void deleteById() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		ProdutoEntity produtoTemporario = testEntityManager.persist(produtoEntidade);
		
		//EXECUÇÃO
		produtoRepository.deleteById( produtoEntidade.getId() );
		
		// VERIFICAÇÃO 
		//BUSQUEI O USUARIO DELETADO E COMPAREI A RESPOSTA  
		
		Optional<ProdutoEntity> deletado = produtoRepository.findById( produtoTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	
	private ProdutoEntity createValidProduto() {
		
		// INSTANCIANDO O NOBO OBJETO DO TIPO ProdutoEntity
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		//COLOCANDO VALORES NOS ATRIBUTOS DE  UsuarioEntity
		produtoEntidade.setNome("Facinelli");
		produtoEntidade.setDescricao("Casaco");
		produtoEntidade.setPreco(170);
	  //produtoEntidade.setId(5); = NÃO INSERIR ID POIS O BANCO ESTA FAZENDO ISSO SOZINHO

		
		// RETORNANDO ESTE NOVO OBJETO CRIADO
		return produtoEntidade;
	}
}


