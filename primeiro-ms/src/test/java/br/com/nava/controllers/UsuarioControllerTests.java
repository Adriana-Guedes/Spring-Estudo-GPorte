package br.com.nava.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.nava.dtos.UsuarioDTO;
import br.com.nava.entities.UsuarioEntity;
import br.com.nava.services.UsuarioService;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc  //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
 public class UsuarioControllerTests {

	
	ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
	
 
	 @Autowired
	 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
	 
	 @Autowired
	 private UsuarioService usuarioService;
	 
	 
	 @Test
		void getAllTest() throws Exception {
			
			// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/usuarios")
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			
			UsuarioDTO[] lista = mapper.readValue(responseStr, UsuarioDTO[].class);
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(lista).isNotEmpty();
		}
	 
	 
	 
	 @Test
		void getOneTests() throws Exception {
		 
		// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/usuarios/7") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
			
			UsuarioDTO usuario = mapper.readValue(responseStr, UsuarioDTO.class);
			
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(usuario.getId() ).isEqualTo(7) ;  // OU O ID 
			assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
		}
	 
	 
	 
	 @Test
		void saveTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO  ProfessorDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNome("Amanda");
			usuario.setEmail("amanda@rmail.com");
		
			
			// PARA ENVIAR A REQUISIÇÃO
			ResultActions response = mockMvc.perform(
					post("/usuarios")
					.content( mapper.writeValueAsString(usuario) )
					.contentType("application/json")
				);
		

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			UsuarioDTO usuariorSalvo = mapper.readValue(responseStr, UsuarioDTO.class);
			
			// VERIFICAR SE FOI SALVO CORRETAMENTE
			assertThat ( usuariorSalvo.getId() ).isPositive();
			assertThat( usuariorSalvo.getNome()).isEqualTo( usuario.getNome() );
			assertThat( usuariorSalvo.getEmail()).isEqualTo( usuario.getEmail() );
			
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
			
		}

	 @Test
		void updateTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO USUARIODTO PARA ENVIAREMOS JUNTO COM A REQUISIÇÃO
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNome("Amanda");
			usuario.setEmail("amanda@rmail.com");
			
			// PARA ENVIAR A REQUISIÇÃO 
					ResultActions response = mockMvc.perform(
					patch("/usuarios/7")
					.content( mapper.writeValueAsString(usuario) )
					.contentType("application/json")
				);

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			UsuarioDTO usuarioSalvo = mapper.readValue(responseStr, UsuarioDTO.class);
			
			// VERIFICA SE FOI SALVO CORRETAMENTO
			assertThat ( usuarioSalvo.getId() ).isPositive();
			assertThat( usuarioSalvo.getNome() ).isEqualTo( usuario.getNome() );
			assertThat( usuarioSalvo.getEmail() ).isEqualTo( usuarioSalvo.getEmail() );
		
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
		}
		
	 
	 @Test
		void deleteTest() throws Exception {
		 
		 
		 UsuarioEntity obj = this.createValidUsuario();
		 UsuarioDTO dto = this.usuarioService.save(obj);

		 	// PARA ENVIAR A REQUISIÇÃO
			ResultActions response = mockMvc.perform(
					delete("/usuarios/12")
					.contentType("application/" + dto.getId()));
			// PEGANDO O RESULTADO VIA  mvcResult
			MvcResult result = response.andReturn();		
			assertThat(result.getResponse().getStatus()).isEqualTo(200);
		}
	 
	 
	 //METODO CRIAÇÃO DE OBJETO
	 private UsuarioEntity createValidUsuario() {
		 
		// INSTANCIANDO O NOVO OBJETO DO TIPO ProfessorEntity 
		 UsuarioEntity usuarioEntidade = new UsuarioEntity();
		 
		 usuarioEntidade.setNome("Amanda");
		 usuarioEntidade.setEmail("amanda@rmail.com");
		 
		// RETORNANDO ESTE NOVO OBJETO CRIADO
		 return usuarioEntidade;
	 }

	}

