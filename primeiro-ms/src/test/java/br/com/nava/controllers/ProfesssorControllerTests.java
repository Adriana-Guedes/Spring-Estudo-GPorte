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
import br.com.nava.entities.ProfessorEntity;
import br.com.nava.services.ProfessorService;
	
	
			@ExtendWith(SpringExtension.class)
			@SpringBootTest
			@AutoConfigureMockMvc  //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
			public class ProfesssorControllerTests {
				
				ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
				
				
			 @Autowired
			 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
			 
			 @Autowired
			 private ProfessorService professorService;
			 
			 
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
														get("/professores/3") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
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
					assertThat(professor.getId() ).isEqualTo(3) ;  // OU O ID 
					assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
				}
			 
			 
			 
			 @Test
				void saveTest() throws Exception {
					
					ObjectMapper mapper = new ObjectMapper();
					
					// CRIAMOS UM OBJETO DO TIPO  PROFESSORDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
					ProfessorDTO professor = new ProfessorDTO();
					professor.setCep("04567895");
					professor.setNome("Professor Teste");
					professor.setNumero(3);
					professor.setRua("Rua de Teste");
					
					// PARA ENVIAR A REQUISIÇÃO
					ResultActions response = mockMvc.perform(
							post("/professores")
							.content( mapper.writeValueAsString(professor) )
							.contentType("application/json")
						);
				

					// PEGANDO O RESULTADO VIA MVCRESULT
					MvcResult result = response.andReturn();
					// PEGANDO O RESULTADO NO FORMATO DE STRING
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
					System.out.println(responseStr);
					
					ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
					
					// VERIFICAR SE FOI SALVO CORRETAMENTE
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
					
					// CRIAMOS UM OBJETO DO TIPO PROFESSORDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
					ProfessorDTO professor = new ProfessorDTO();
					professor.setCep("04567895");
					professor.setNome("Professor Teste");
					professor.setNumero(3);
					professor.setRua("Rua de Teste");
				
					
					// PARA ENVIAR A REQUISIÇÃO
					ResultActions response = mockMvc.perform(
							patch("/professores/3")
							.content( mapper.writeValueAsString(professor) )
							.contentType("application/json")
						);

					// PEGANDO O RESULTADO VIA  MvcResult
					MvcResult result = response.andReturn();
					// PEGANDO O RESULTADO NO FORMATO DE STRING
					String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
					
					System.out.println(responseStr);
					
					ProfessorDTO professorSalvo = mapper.readValue(responseStr, ProfessorDTO.class);
					
					// VERIFICAR SE FOI SALVO CORRETAMENTE
					assertThat ( professorSalvo.getId() ).isPositive();
					assertThat( professorSalvo.getCep() ).isEqualTo( professor.getCep() );
					assertThat( professorSalvo.getNome() ).isEqualTo( professor.getNome() );
					assertThat( professorSalvo.getNumero() ).isEqualTo( professor.getNumero() );
					assertThat( professorSalvo.getRua() ).isEqualTo( professor.getRua() );
					
					assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
				}
			
			 
			 @Test
				void deleteTest() throws Exception {
					
				// PRIMEIRO VAMOS INSERIR UM REGISTRO
					ProfessorEntity obj = this.createValidProfessor();		
					ProfessorDTO dto = this.professorService.save(obj);
							
					// PARA ENVIAR A REQUISIÇÃO
					ResultActions response = mockMvc.perform(
							delete("/professores/" + dto.getId())				
							.contentType("application/json")
						);

					// PEGANDO O RESULTADO VIA MVCRESULT
					MvcResult result = response.andReturn();
					
					assertThat( result.getResponse().getStatus()).isEqualTo( 200 );
				}
				
			 
			 //METODO CRIAÇÃO E OBJETO
				private ProfessorEntity createValidProfessor() {
					
					// INSTANCIANDO O NOVO OBJETO DO TIPO ProfessorEntity
					ProfessorEntity professorEntidade = new ProfessorEntity();
					
					// COLOCANDO VALORES NOofessorEntity
					professorEntidade.setCep("04567895");  
					professorEntidade.setNome("Professor Teste");
					professorEntidade.setNumero(3);
					professorEntidade.setRua("Rua de Teste");		
					
					// RETORNANDO ESTE NOVO OBJETO CRIADO
					return professorEntidade;
				}
				
			}