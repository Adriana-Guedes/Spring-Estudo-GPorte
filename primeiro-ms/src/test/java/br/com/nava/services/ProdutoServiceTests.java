package br.com.nava.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.nava.dtos.ProdutoDTO;
import br.com.nava.entities.ProdutoEntity;
import br.com.nava.repositories.ProdutoRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc  //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
public class ProdutoServiceTests {
	
	
	

	
	@Autowired
	private ProdutoService produtoService;
	
	@MockBean //SERVE PARA SINALIZAR QUE IREMOS MOCKAR(SIMULAR) A CAMADA REPOSITORY
	private ProdutoRepository  produtoRepository;
	
	
	
	@Test
	void getAllTest() {
			List<ProdutoEntity> listaMockada = new ArrayList<ProdutoEntity>();

			ProdutoEntity produtoEntidade = createValidProduto();
		
		listaMockada.add(produtoEntidade);
		
		// quando o repository for acionado, retorno a lista Mockada
		when( produtoRepository.findAll() ).thenReturn( listaMockada );
		
		List<ProdutoDTO> retorno = produtoService.getAll();
		// validar a resposta
		isProdutoValid(retorno.get(0), listaMockada.get(0));
				
	}
	
	// quando o objeto é achado no banco de dados
	@Test
	void getOneWhenFoundObjectTest() {
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		Optional<ProdutoEntity> optional = Optional.of(produtoEntidade);
		
		when ( produtoRepository.findById(1) ).thenReturn( optional );
		
		// EXECUÇÃO
		ProdutoDTO obj = produtoService.getOne(1);
		
		// VALIDAR A RESPOSTA 
		isProdutoValid(obj, produtoEntidade);
	}
	
	// quando o objeto NÃO é  achado no banco de dados
	@Test
	void getOneWhenNotFoundObjectTest() {
		
		// Optional.empty() -> simulando o caso de NÃO achar o registro no banco de dados
		Optional<ProdutoEntity> optional = Optional.empty();
		
		when ( produtoRepository.findById(1) ).thenReturn( optional );
		
		// execução
		ProdutoDTO obj = produtoService.getOne(1);
		
		// objeto com valores "padrão"
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		
		// validar a resposta
		isProdutoValid(obj, produtoEntidade);
	}
	
	@Test
	void saveTest() {
		
		// 1-) Cenário
		//objeto com dados válidos de um professor
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// quando o professorRepository.save for acionado, retornaremos um objeto de professor com dados válidos
		when( produtoRepository.save(produtoEntidade) ).thenReturn(produtoEntidade);
		
		ProdutoDTO produtoSalvo = produtoService.save(produtoEntidade);
		
		// validar a resposta
		isProdutoValid(produtoSalvo, produtoEntidade);
		
	}
	
	@Test
	void updateWhenFoundObj() {
		
		//Cenário
		
		ProdutoEntity produtoEntidade = createValidProduto();
		Optional<ProdutoEntity> optional = Optional.of(produtoEntidade);
		
		//mocks
		when (produtoRepository.findById( produtoEntidade.getId() ) ).thenReturn(optional);
		when ( produtoRepository.save(produtoEntidade) ).thenReturn(produtoEntidade);
		
		// execução
		ProdutoDTO produtoAlterado = produtoService.
					update(produtoEntidade.getId(), produtoEntidade);
		
		// validar a resposta
		isProdutoValid(produtoAlterado, produtoEntidade);
	}
	
	@Test
	void updateWhenNotFoundObj() {
				
		// Cenário
		// Optional.empty() por conta que não achou o objeto a ser alterado
		Optional<ProdutoEntity> optional = Optional.empty();
		
		ProdutoEntity produtoEntidade = createValidProduto();
		
		// mocks
		when ( produtoRepository.findById(1) ).thenReturn(optional);
		
		// execução
		// estamos passando como argumento o professorEntidade pois 
		// em suposição ele não estará no banco de dadaos neste cenário
		ProdutoDTO produtoAlterado = produtoService.
							update(1, produtoEntidade );
		
		// validar a resposta
		isProdutoValid(produtoAlterado, new ProdutoEntity() );
	}
	
	@Test
	void deleteTest() {
		
		// execução
		// assertDoesNotThrow espera uma lambda (método sem nome) e verifica se a lambda executa sem erro
		assertDoesNotThrow( () -> produtoService.delete(1) );
		
		// verifico se o professorRepository.deleteById foi executado somente uma vez 
		verify( produtoRepository, times(1) ).deleteById(1);
	}
	
	
	//METODO PARA AJUDAR NA VALIDAÇÃO
	
	private void isProdutoValid( ProdutoDTO obj, ProdutoEntity produtoEntidade ) {
		
		assertThat( obj.getNome() ).isEqualTo( produtoEntidade.getNome() );
		assertThat( obj.getDescricao() ).isEqualTo( produtoEntidade.getDescricao() );
		assertThat( obj.getPreco() ).isEqualTo( produtoEntidade.getPreco() );
		assertThat( obj.getId() ).isEqualTo( produtoEntidade.getId() );
		
	}
			
	
	//METODO PARA CRIAÇÃO DE OBJETO
	private ProdutoEntity createValidProduto() {
		
		// instanciando o novo objeto do tipo ProdutoEntity
		ProdutoEntity produtoEntidade = new ProdutoEntity();
		
		// colocando valores nos atributos de ProfessorEntity
		produtoEntidade.setNome("Facinelli");
		produtoEntidade.setDescricao("Casaco");
		produtoEntidade.setPreco(170);
		produtoEntidade.setId(5);
		
		// retornando este novo objeto criado
		return produtoEntidade;
	}
	
	
	
}