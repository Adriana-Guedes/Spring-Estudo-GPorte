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

import br.com.nava.dtos.UsuarioDTO;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioServiceTests {

	
	
	

	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean //SERVE PARA SINALIZAR QUE IREMOS MOCKAR(SIMULAR) A CAMADA REPOSITORY
	private UsuarioRepository usuarioRepository;
	
	
	
	@Test
	void getAllTest() {
	
		List<UsuarioEntity> listaMockada = new ArrayList<UsuarioEntity>();
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		listaMockada.add(usuarioEntidade);

		// quando o repository for acionado, retorno a lista Mockada
		when( usuarioRepository.findAll() ).thenReturn( listaMockada );
		
		List<UsuarioDTO> retorno = usuarioService.getAll();
	
		
		// validar a resposta
		isUsuarioValid(retorno.get(0), listaMockada.get(0));
				
	}
	
	// quando o objeto é achado no banco de dados
	@Test
	void getOneWhenFoundObjectTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		Optional<UsuarioEntity> optional = Optional.of(usuarioEntidade);
		
		when ( usuarioRepository.findById(1) ).thenReturn( optional );
		
		// execução
		UsuarioDTO obj = usuarioService.getOne(1);
	
		// validar a resposta
		isUsuarioValid(obj, usuarioEntidade);
	}
	
	// quando o objeto NÃO é  achado no banco de dados
	@Test
	void getOneWhenNotFoundObjectTest() {
		
		// Optional.empty() -> simulando o caso de NÃO achar o registro no banco de dados
		Optional<UsuarioEntity> optional = Optional.empty();
		
		when ( usuarioRepository.findById(1) ).thenReturn( optional );
		
		// execução
		UsuarioDTO obj = usuarioService.getOne(1);
		
		// objeto com valores "padrão"
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
				// validar a resposta
		isUsuarioValid(obj, usuarioEntidade);
	}
	
	@Test
	void saveTest() {
		
		//  CENÁRIO
		//OBJETO COM DADOS VÁLIDOS DE UM PROFESSOR
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// QUANDO O URUARIOREPOSITORY.save FOR ACIONADO, RETORNAREMOS UM OBJETO DE USUARIO COM DADOS VÁLIDOS
		when( usuarioRepository.save(usuarioEntidade) ).thenReturn(usuarioEntidade);
		
		UsuarioDTO usuarioSalvo = usuarioService.save(usuarioEntidade);
		
		// VALIDAR A RESPOSTA
		isUsuarioValid(usuarioSalvo, usuarioEntidade);
		
	}
	
	@Test
	void updateWhenFoundObj() {
		
		//CENÁRIO
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		Optional<UsuarioEntity> optional = Optional.of(usuarioEntidade);
		
		//mocks
		when (usuarioRepository.findById( usuarioEntidade.getId() ) ).thenReturn(optional);
		when ( usuarioRepository.save(usuarioEntidade) ).thenReturn(usuarioEntidade);
		
		// EXECUÇÃO
		UsuarioDTO usuarioAlterado = usuarioService.
					update(usuarioEntidade.getId(), usuarioEntidade);
		
		// VALIDAR A RESPOSTA
		isUsuarioValid(usuarioAlterado, usuarioEntidade);
	}
	
	@Test
	void updateWhenNotFoundObj() {
				
		// CENARIO
		// Optional.empty() por conta que não achou o objeto a ser alterado
		Optional<UsuarioEntity> optional = Optional.empty();
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// mocks
		when ( usuarioRepository.findById(1) ).thenReturn(optional);
		
		// execução
		// estamos passando como argumento o professorEntidade pois 
		// em suposição ele não estará no banco de dadaos neste cenário
		UsuarioDTO usuarioAlterado = usuarioService.
							update(1, usuarioEntidade );
		
		// validar a resposta
		isUsuarioValid(usuarioAlterado, new UsuarioEntity() );
	}
	
	@Test
	void deleteTest() {
		
		// execução
		// assertDoesNotThrow espera uma lambda (método sem nome) e verifica se a lambda executa sem erro
		assertDoesNotThrow( () -> usuarioService.delete(1) );
		
		// verifico se o professorRepository.deleteById foi executado somente uma vez 
		verify( usuarioRepository, times(1) ).deleteById(1);
	}
	
	
	//METODOS
	
	
	private void isUsuarioValid( UsuarioDTO obj, UsuarioEntity usuarioEntidade ) {
		
		
		
		assertThat( obj.getNome() ).isEqualTo( usuarioEntidade.getNome() );
		assertThat( obj.getEmail() ).isEqualTo( usuarioEntidade.getEmail() );
		assertThat( obj.getId() ).isEqualTo( usuarioEntidade.getId());
			}
	
	private UsuarioEntity createValidUsuario() {
		
		// INSTANCIANDO O NOVO OBJETO DO TIPO USUARIO  UsuarioEntity
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
		
		// colocando valores nos atributos de ProfessorEntity
		usuarioEntidade.setNome("Adriana");
		usuarioEntidade.setEmail("adriana@email.com");
		usuarioEntidade.setId(1);
		
		// retornando este novo objeto criado
		return usuarioEntidade;
	}
	
	
	
}

