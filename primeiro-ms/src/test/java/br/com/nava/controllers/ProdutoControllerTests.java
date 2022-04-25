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

import br.com.nava.dtos.ProdutoDTO;




@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoControllerTests {
	
	ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
	
	
	 @Autowired
	 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
	 
	 
	 @Test
		void getAllTest() throws Exception {
			
			// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/produtos")
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			
			ProdutoDTO[] lista = mapper.readValue(responseStr, ProdutoDTO[].class);
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(lista).isNotEmpty();
		}
	 
	 
	 
	 @Test
		void getOneTests() throws Exception {
		 
		// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/produtos/1") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
			
			ProdutoDTO produto = mapper.readValue(responseStr, ProdutoDTO.class);
			
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(produto.getId() ).isEqualTo(1) ;  // OU O ID 
			assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
		}
	 
	 
	 
	 @Test
		void saveTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO  ProfessorDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
			ProdutoDTO produto = new ProdutoDTO();
			produto.setNome("Arroz");
			produto.setDescricao("Arroz integral");
			produto.setPreco(30);
		
			
			// PARA ENVIAR A REQUISIÇÃO
			ResultActions response = mockMvc.perform(
					post("/produtos")
					.content( mapper.writeValueAsString(produto) )
					.contentType("application/json")
				);
		

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			ProdutoDTO produtoSalvo = mapper.readValue(responseStr, ProdutoDTO.class);
			
			// VERIFICAR SE FOI SALVO CORRETAMENTE
			assertThat ( produtoSalvo.getId() ).isPositive();
			assertThat( produtoSalvo.getNome()).isEqualTo( produto.getNome() );
			assertThat( produtoSalvo.getDescricao()).isEqualTo( produto.getDescricao() );
			assertThat(produtoSalvo.getPreco()).isEqualTo(produto.getPreco());
			
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
			
		}

	 @Test
		void updateTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO USUARIODTO PARA ENVIAREMOS JUNTO COM A REQUISIÇÃO
			ProdutoDTO produto = new ProdutoDTO();
			produto.setNome("Feijao");
			produto.setDescricao("Feijão Preto");
			produto.setPreco(30);
			
			// PARA ENVIAR A REQUISIÇÃO 
					ResultActions response = mockMvc.perform(
					patch("/produtos/1")
					.content( mapper.writeValueAsString(produto) )
					.contentType("application/json")
				);

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			ProdutoDTO produtoSalvo = mapper.readValue(responseStr, ProdutoDTO.class);
			
			// VERIFICA SE FOI SALVO CORRETAMENTO
			assertThat ( produtoSalvo.getId() ).isPositive();
			assertThat( produtoSalvo.getNome()).isEqualTo( produto.getNome() );
			assertThat( produtoSalvo.getDescricao()).isEqualTo( produto.getDescricao() );
			assertThat(produtoSalvo.getPreco()).isEqualTo(produto.getPreco());
		
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
		}
		
	 
	 @Test
		void deleteTest() throws Exception {

			// para enviar a requisição
			ResultActions response = mockMvc.perform(
					delete("/produtos/1")
					.contentType("application/json"));
			// pegando o resultado via mvcResult
			MvcResult result = response.andReturn();		
			assertThat(result.getResponse().getStatus()).isEqualTo(200);
		}

	}

