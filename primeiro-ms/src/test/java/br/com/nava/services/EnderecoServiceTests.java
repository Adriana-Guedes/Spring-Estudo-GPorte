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

import br.com.nava.dtos.EnderecoDTO;
import br.com.nava.entities.EnderecoEntity;
import br.com.nava.repositories.EnderecoRepository;




	@ExtendWith(SpringExtension.class)
	@SpringBootTest
	@AutoConfigureMockMvc
	public class EnderecoServiceTests {


		
		@Autowired
		private EnderecoService enderecoService;
		
		@MockBean //SERVE PARA SINALIZAR QUE IREMOS MOCKAR(SIMULAR) A CAMADA REPOSITORY
		private EnderecoRepository enderecoRepository;
		
		
		
		@Test
		void getAllTest() {
			
			// vamos criar uma lista de entidade de endereco com o objeto
			// de retornar a mesma quando o enderecoRepository.findAll() 
			// for acionado
			
			List<EnderecoEntity> listaMockada = new ArrayList<EnderecoEntity>();
	
			EnderecoEntity enderecoEntidade = createValidEndereco();
			
			listaMockada.add(enderecoEntidade);
			
			// quando o repository for acionado, retorno a lista Mockada
			when( enderecoRepository.findAll() ).thenReturn( listaMockada );
			
			List<EnderecoDTO> retorno = enderecoService.getAll();
;				
			// validar a resposta
			isEnderecoValid(retorno.get(0), listaMockada.get(0));
					
		}
		
		// quando o objeto é achado no banco de dados
		@Test
		void getOneWhenFoundObjectTest() {
			
			EnderecoEntity professorEntidade = createValidEndereco();
			
			Optional<EnderecoEntity> optional = Optional.of(professorEntidade);
			
			when ( enderecoRepository.findById(1) ).thenReturn( optional );
			
			// execução
			EnderecoDTO obj = enderecoService.getOne(1);
			
			//validação
			
//			assertThat( obj.getCep() ).isEqualTo( professorEntidade.getCep() );
//			assertThat( obj.getNome() ).isEqualTo( professorEntidade.getNome() );
//			assertThat( obj.getNumero() ).isEqualTo( professorEntidade.getNumero() );
//			assertThat( obj.getRua() ).isEqualTo( professorEntidade.getRua() );
//			assertThat( obj.getId() ).isEqualTo( professorEntidade.getId() );
			
			// validar a resposta
			isEnderecoValid(obj, professorEntidade);
		}
		
		// quando o objeto NÃO é  achado no banco de dados
		@Test
		void getOneWhenNotFoundObjectTest() {
			
			// Optional.empty() -> simulando o caso de NÃO achar o registro no banco de dados
			Optional<EnderecoEntity> optional = Optional.empty();
			
			when ( enderecoRepository.findById(1) ).thenReturn( optional );
			
			// execução
			EnderecoDTO obj = enderecoService.getOne(1);
			
			// objeto com valores "padrão"
			EnderecoEntity enderecoEntidade = new EnderecoEntity();
			
			//validação
			
//			assertThat( obj.getCep() ).isEqualTo( professorEntidade.getCep() );
//			assertThat( obj.getNome() ).isEqualTo( professorEntidade.getNome() );
//			assertThat( obj.getNumero() ).isEqualTo( professorEntidade.getNumero() );
//			assertThat( obj.getRua() ).isEqualTo( professorEntidade.getRua() );
//			assertThat( obj.getId() ).isEqualTo( professorEntidade.getId() );
			
			// validar a resposta
			isEnderecoValid(obj, enderecoEntidade);
		}
		
		@Test
		void saveTest() {
			
			// CENÁRIO
			//OBJETO COM DADOS VALIDOS DE UM ENDEREÇO 
			EnderecoEntity enderecoEntidade = createValidEndereco();
			
			// QUANDO O ENDERECOREPOSITORY.save FOR ACIONADO, RETORNAREMOS UM OBJETO DE ENDERECO COM DADDOS VÁLIDOS
			when( enderecoRepository.save(enderecoEntidade) ).thenReturn(enderecoEntidade);
			
			EnderecoDTO enderecoSalvo = enderecoService.save(enderecoEntidade);
			
			// VALIDAR A RESPOSTA
			isEnderecoValid(enderecoSalvo, enderecoEntidade);
			
		}
		
		@Test
		void updateWhenFoundObj() {
			
			//CENÁRIO
			
			EnderecoEntity enderecoEntidade = createValidEndereco();
			Optional<EnderecoEntity> optional = Optional.of(enderecoEntidade);
			
			//mocks
			when (enderecoRepository.findById( enderecoEntidade.getId() ) ).thenReturn(optional);
			when ( enderecoRepository.save(enderecoEntidade) ).thenReturn(enderecoEntidade);
			
			// EXECUÇÃO
			EnderecoDTO enderecoAlterado = enderecoService.
						update(enderecoEntidade.getId(), enderecoEntidade);
			
			// VALIDAR A RESPOSTA
			isEnderecoValid(enderecoAlterado, enderecoEntidade);
		}
		
		@Test
		void updateWhenNotFoundObj() {
					
			// CENÁRIO
			// Optional.empty() POR CONTA QUE NÃO ACHOU O OBJETO E SER ALTERADO
			Optional<EnderecoEntity> optional = Optional.empty();
			
			EnderecoEntity enderecoEntidade = createValidEndereco();
			
			// mocks
			when ( enderecoRepository.findById(1) ).thenReturn(optional);
			
			// EXECUÇÃO
			// ESTAMOS PASSANDO COMO ARGUMENTO O ENDERECOENTIDADE POIS 
			// EM SUPOSIÇÃO ELE NÃO ESTARA NO BANCO DE DADOS NESTE CENÁRIO 
			EnderecoDTO enderecoAlterado = enderecoService.
								update(1, enderecoEntidade );
			
			// VALIDAR A RESPOSTA
			isEnderecoValid(enderecoAlterado, new EnderecoEntity() );
		}
		
		@Test
		void deleteTest() {
			
			// execução
			// assertDoesNotThrow espera uma lambda (método sem nome) e verifica se a lambda executa sem erro
			assertDoesNotThrow( () -> enderecoService.delete(1) );
			
			// verifico se o professorRepository.deleteById foi executado somente uma vez 
			verify( enderecoRepository, times(1) ).deleteById(2);
		}
		
		
		
		
		
		//METODO DE VALIDAÇÃO DE OBJETO, SE O FOI CRIADO
		private void isEnderecoValid( EnderecoDTO obj, EnderecoEntity enderecoEntidade ) {
			
			assertThat( obj.getId() ).isNotNull();
			assertThat( obj.getRua() ).isEqualTo( enderecoEntidade.getRua() );
			assertThat( obj.getNumero() ).isEqualTo( enderecoEntidade.getNumero() );
			assertThat( obj.getCep() ).isEqualTo( enderecoEntidade.getCep() );
			assertThat( obj.getCidade() ).isEqualTo( enderecoEntidade.getCidade() );
		
		}
		
		
		//METODO DE CRIAÇÃO DE OBJETO
		private EnderecoEntity createValidEndereco() {
			
			// INSTANCIANDO O NOVO OBJETO DO TIPO EnderecorEntity
			EnderecoEntity enderecoEntidade = new EnderecoEntity();
			
			// COLOCANDO VALORES NOS ATRIBUTOS DE EnderecorEntity
			
			
			enderecoEntidade.setRua("Avenida do Teste 5");
			enderecoEntidade.setNumero(44);
			enderecoEntidade.setCep("0360230000");
			enderecoEntidade.setCidade("São Paulo");
			enderecoEntidade.setId(1);
			// RETORNANDO ESTE NOVO OBJETO CRIADO
			return enderecoEntidade;
		}
		
		
		
	}

