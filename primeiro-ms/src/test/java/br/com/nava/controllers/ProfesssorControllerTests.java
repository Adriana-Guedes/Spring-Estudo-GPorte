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

import br.com.nava.dtos.ProfessorDTO;
	
	
			@ExtendWith(SpringExtension.class)
			@SpringBootTest
			@AutoConfigureMockMvc  //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
			public class ProfesssorControllerTests {
				
				ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
				
				
			 @Autowired
			 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
			 
			 
			 @Test
				void getAllTest() throws Exception {
					
					// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
					ResultActions response = mockMvc.perform(
														get("/professores")
														.contentType("application/json")
													);
					// PEGANDO O RESULTADO 
					MvcResult result = response.andReturn();
					// PEGANDO O RESULTADO NO FORMATO DE STRING
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
								
					ProfessorDTO[] lista = mapper.readValue(responseStr, ProfessorDTO[].class);
					// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
					assertThat(lista).isNotEmpty();
				}
			 
			 
			 
			 @Test
				void getOneTests() throws Exception {
				 
				// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
					ResultActions response = mockMvc.perform(
														get("/professores/1") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
														.contentType("application/json")
													);
					// PEGANDO O RESULTADO 
					MvcResult result = response.andReturn();
					// PEGANDO O RESULTADO NO FORMATO DE STRING
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
					System.out.println(responseStr);
					
					ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
					
					ProfessorDTO professor = mapper.readValue(responseStr, ProfessorDTO.class);
					
					// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
					assertThat(professor.getId() ).isEqualTo(1) ;  // OU O ID 
					assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
				}
			 
			 
			 
			 @Test
				void saveTest() throws Exception {
					
					ObjectMapper mapper = new ObjectMapper();
					
					// CRIAMOS UM OBJETO DO TIPO  ProfessorDTO para enviarmos junto com a requisição
					ProfessorDTO professor = new ProfessorDTO();
					professor.setCep("04567895");
					professor.setNome("Professor Teste");
					professor.setNumero(3);
					professor.setRua("Rua de Teste");
					
					// para enviar a requisição
					ResultActions response = mockMvc.perform(
							post("/professores")
							.content( mapper.writeValueAsString(professor) )
							.contentType("application/json")
						);
				

					// pegando o resultado via MvcResult
					MvcResult result = response.andReturn();
					// pegando o resultado no formato de String
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
					System.out.println(responseStr);
					
					ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
					
					// verificar se foi salvo corretamente
					assertThat ( professorSalvo.getId() ).isPositive();
					assertThat( professorSalvo.getCep() ).isEqualTo( professor.getCep() );
					assertThat( professorSalvo.getNome() ).isEqualTo( professor.getNome() );
					assertThat( professorSalvo.getNumero() ).isEqualTo( professor.getNumero() );
					assertThat( professorSalvo.getRua() ).isEqualTo( professor.getRua() );
					
					assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
					
				}
		
			 @Test
				void updateTest() throws Exception {
					
					ObjectMapper mapper = new ObjectMapper();
					
					// criamos um objeto do tipo ProfessorDTO para enviarmos junto com a requisição
					ProfessorDTO professor = new ProfessorDTO();
					professor.setCep("04567895");
					professor.setNome("Professor Teste");
					professor.setNumero(3);
					professor.setRua("Rua de Teste");
					
					// para enviar a requisição
					ResultActions response = mockMvc.perform(
							patch("/professores/1")
							.content( mapper.writeValueAsString(professor) )
							.contentType("application/json")
						);

					// pegando o resultado via MvcResult
					MvcResult result = response.andReturn();
					// pegando o resultado no formato de String
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
					System.out.println(responseStr);
					
					ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
					
					// verificar se foi salvo corretamente
					assertThat ( professorSalvo.getId() ).isPositive();
					assertThat( professorSalvo.getCep() ).isEqualTo( professor.getCep() );
					assertThat( professorSalvo.getNome() ).isEqualTo( professor.getNome() );
					assertThat( professorSalvo.getNumero() ).isEqualTo( professor.getNumero() );
					assertThat( professorSalvo.getRua() ).isEqualTo( professor.getRua() );
					
					assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
				}
				
			 
			 @Test
				void deleteTest() throws Exception {

					// para enviar a requisição
					ResultActions response = mockMvc.perform(
							delete("/professores/2")
							.contentType("application/json"));
					// pegando o resultado via mvcResult
					MvcResult result = response.andReturn();		
					assertThat(result.getResponse().getStatus()).isEqualTo(200);
				}

			}