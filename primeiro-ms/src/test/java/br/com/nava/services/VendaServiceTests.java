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

import br.com.nava.dtos.VendaDTO;
import br.com.nava.entities.VendaEntity;
import br.com.nava.repositories.VendaRepository;





@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES

public class VendaServiceTests {

		
	@Autowired
	private VendaService vendaService;
	
	@MockBean //SERVE PARA SINALIZAR QUE IREMOS MOCKAR(SIMULAR) A CAMADA REPOSITORY
	private VendaRepository  vendaRepository;
	
	
	
	@Test
	void getAllTest() {
			List<VendaEntity> listaMockada = new ArrayList<VendaEntity>();

			VendaEntity vendaEntidade = createValidVenda();
		
		listaMockada.add(vendaEntidade);
		
		// QUANDO O REPOSITORY FOR ACIONADO o repository for ACIONADO , RETORNARÁ A Mockada
		when( vendaRepository.findAll() ).thenReturn( listaMockada );
		
		List<VendaDTO> retorno = vendaService.getAll();
		// VALIDAR A RESPOSTA
		isVendaValid(retorno.get(0), listaMockada.get(0));
				
	}
	
	
	
	
	// QUANDO O OBJETO É ACHADO NO BANCO DE DADOS
	@Test
	void getOneWhenFoundObjectTest() {
		
		VendaEntity vendaEntidade = createValidVenda();
		
		Optional<VendaEntity> optional = Optional.of(vendaEntidade);
		
		when ( vendaRepository.findById(1) ).thenReturn( optional );
		
		// EXECUÇÃO
		VendaDTO obj = vendaService.getOne(1);
		
		// VALIDAR A RESPOSTA 
		isVendaValid(obj, vendaEntidade);
	}
	
	// quando o objeto NÃO é  achado no banco de dados
	@Test
	void getOneWhenNotFoundObjectTest() {
		
		// Optional.empty() -> simulando o caso de NÃO achar o registro no banco de dados
		Optional<VendaEntity> optional = Optional.empty();
		
		when ( vendaRepository.findById(1) ).thenReturn( optional );
		
		// execução
		VendaDTO obj = vendaService.getOne(1);
		
		// objeto com valores "padrão"
		VendaEntity vendaEntidade = new VendaEntity();
		
		
		// validar a resposta
		isVendaValid(obj, vendaEntidade);
	}
	
	@Test
	void saveTest() {
		
		// 1-) Cenário
		//objeto com dados válidos de um professor
		VendaEntity vendaEntidade = createValidVenda();
		
		// quando o professorRepository.save for acionado, retornaremos um objeto de professor com dados válidos
		when( vendaRepository.save(vendaEntidade) ).thenReturn(vendaEntidade);
		
		VendaDTO vendasalvo = vendaService.save(vendaEntidade);
		
		// validar a resposta
		isVendaValid(vendasalvo, vendaEntidade);
		
	}
	
	@Test
	void updateWhenFoundObj() {
		
		//Cenário
		
		VendaEntity vendaEntidade = createValidVenda();
		Optional<VendaEntity> optional = Optional.of(vendaEntidade);
		
		//mocks
		when (vendaRepository.findById( vendaEntidade.getId() ) ).thenReturn(optional);
		when ( vendaRepository.save(vendaEntidade) ).thenReturn(vendaEntidade);
		
		// execução
		VendaDTO vendaAlterado = vendaService.
					update(vendaEntidade.getId(), vendaEntidade);
		
		// validar a resposta
		isVendaValid(vendaAlterado, vendaEntidade);
	}
	
	@Test
	void updateWhenNotFoundObj() {
				
		// Cenário
		// Optional.empty() por conta que não achou o objeto a ser alterado
		Optional<VendaEntity> optional = Optional.empty();
		
		VendaEntity vendaEntidade = createValidVenda();
		
		// mocks
		when ( vendaRepository.findById(1) ).thenReturn(optional);
		
		// execução
		// estamos passando como argumento o professorEntidade pois 
		// em suposição ele não estará no banco de dadaos neste cenário
		VendaDTO vendaAlterado = vendaService.
							update(1, vendaEntidade );
		
		// validar a resposta
		isVendaValid(vendaAlterado, new VendaEntity() );
	}
	
	@Test
	void deleteTest() {
		
		// execução
		// assertDoesNotThrow espera uma lambda (método sem nome) e verifica se a lambda executa sem erro
		assertDoesNotThrow( () -> vendaService.delete(1) );
		
		// verifico se o vendaRepository.deleteById foi executado somente uma vez 
		verify( vendaRepository, times(1) ).deleteById(1);
	}
	
	
	//METODO PARA AJUDAR NA VALIDAÇÃO
	
	private void isVendaValid( VendaDTO obj, VendaEntity vendaEntidade ) {
		
		assertThat( obj.getValorTotal()).isEqualTo( vendaEntidade.getValorTotal() );
		assertThat( obj.getId()).isEqualTo( vendaEntidade.getId() );
		
	}
			
	
	//METODO PARA CRIAÇÃO DE OBJETO
	private VendaEntity createValidVenda() {
		
		// instanciando o novo objeto do tipo VendaEntity
		VendaEntity vendaEntidade = new VendaEntity();
		
		// colocando valores nos atributos de VendaEntity
		vendaEntidade.setValorTotal(Float.valueOf(200));	
		vendaEntidade.setId(1);
		
		// retornando este novo objeto criado
		return vendaEntidade;
	}
	
	
	

}
