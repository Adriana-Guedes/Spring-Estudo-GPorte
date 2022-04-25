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

import br.com.nava.entities.UsuarioEntity;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UsuarioRepositoryTests {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@Test
	void findByIdWhenFoundTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// VAI PERSISTIR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		// AO FINAL DO TESTES, ESTA ENTIDADE SERÁ DELETADA
		testEntityManager.persist(usuarioEntidade);
		
		// BUSCAR A ENTIDADE NO BANCO DE DADOS PARA TESTAR O  findById
		
		// EXECUÇÃO DO findById
		Optional<UsuarioEntity> usuario = usuarioRepository.findById( usuarioEntidade.getId() );
		
		// VALIDANDO A RESPOSTA SE O OBJETO ENCONTRAQDO NÃO É NULO 
		assertThat( usuario ).isNotNull();
	}
	
	@Test
	void findByIdWhenNotFoundTest() {
		
		// BUSCAR UMA ENTIDADE NA QUAL NÃO EXISTE NO BANCO DE DADOS
		Optional<UsuarioEntity> usuario = usuarioRepository.findById(1);
		
		// TEMOS QUE VERIFICAR SE O OPCIONAL NÃO POSSUI VALORES, OU SEJA, isPresent() POSSUI VALOR FALSO
		assertThat( usuario.isPresent() ).isFalse();		
	}
	
	@Test
	void findAllTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(usuarioEntidade);
		
		//EXECUÇÃO
		List<UsuarioEntity> usuario = this.usuarioRepository.findAll();
		
		// VERIFICAR
		assertThat( usuario.size() ).isEqualTo(1);
	}
	
	@Test
	void saveTest() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		testEntityManager.persist(usuarioEntidade);
		
		//EXECUÇÃO
		UsuarioEntity usuarioSalvo = usuarioRepository.save(usuarioEntidade);
		
		//VALIDAÇÃO
		
		assertThat( usuarioSalvo.getId() ).isNotNull();
		assertThat( usuarioSalvo.getNome() ).isEqualTo( usuarioEntidade.getNome() );
		assertThat( usuarioSalvo.getEmail() ).isEqualTo( usuarioEntidade.getEmail() );
		
	}
	
	@Test
	void deleteById() {
		
		UsuarioEntity usuarioEntidade = createValidUsuario();
		
		// SALVANDO TEMPORARIAMENTE  O USUAARIO NO BANDO DE DADOS 
		UsuarioEntity usuarioTemporario = testEntityManager.persist(usuarioEntidade);
		
		//EXECUÇÃO
		usuarioRepository.deleteById( usuarioEntidade.getId() );
		
		// VERIFICAÇÃO 
		//BUSQUEI O USUARIO DELETADO E COMPAREI A RESPOSTA  
		
		Optional<UsuarioEntity> deletado = usuarioRepository.findById( usuarioTemporario.getId() );
		
		assertThat( deletado.isPresent() ).isFalse();
	}
	
	
	private UsuarioEntity createValidUsuario() {
		
		// INSTANCIANDO O NOBO OBJETO DO TIPO UsuarioEntity
		UsuarioEntity usuarioEntidade = new UsuarioEntity();
		
		//COLOCANDO VALORES NOS ATRIBUTOS DE  UsuarioEntity
		usuarioEntidade.setNome("Adriana");
		usuarioEntidade.setEmail("adriana@email.com");
		//usuarioEntidade.setId(1); = NÃO INSERIR ID POIS O BANCO ESTA FAZENDO ISSO SOZINHO

		
		// RETORNANDO ESTE NOVO OBJETO CRIADO
		return usuarioEntidade;
	}
}



