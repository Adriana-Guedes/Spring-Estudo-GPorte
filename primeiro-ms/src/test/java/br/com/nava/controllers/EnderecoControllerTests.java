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

import br.com.nava.dtos.EnderecoDTO;
import br.com.nava.entities.EnderecoEntity;
import br.com.nava.services.EnderecoService;

		
		
				@ExtendWith(SpringExtension.class)
				@SpringBootTest
				@AutoConfigureMockMvc    //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
				 public class EnderecoControllerTests {
					
					ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
					
				
				 @Autowired
				 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
				 
				 @Autowired
				 private EnderecoService enderecoService;
				 
				 
				 @Test
					void getAllTest() throws Exception {
						
						// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
						ResultActions response = mockMvc.perform(
								get("/enderecos").contentType("application/json")
														);
						// PEGANDO O RESULTADO 
						MvcResult result = response.andReturn();
						// PEGANDO O RESULTADO NO FORMATO DE STRING
						String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
						
									
						EnderecoDTO[] lista = mapper.readValue(responseStr, EnderecoDTO[].class);
						// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
						assertThat(lista).isNotEmpty();
					}
				 
				 
				 
				 @Test
					void getOneTests() throws Exception {
					 
					// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
						ResultActions response = mockMvc.perform(
															get("/enderecos/6") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
															.contentType("application/json")
														);
						// PEGANDO O RESULTADO 
						MvcResult result = response.andReturn();
						// PEGANDO O RESULTADO NO FORMATO DE STRING
						String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
						
						System.out.println(responseStr);
						
						ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
						
						EnderecoDTO endereco = mapper.readValue(responseStr, EnderecoDTO.class);
						
						// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
						assertThat(endereco.getId() ).isEqualTo(6) ;  // OU O ID 
						assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
					}
				 
				 
				 
				 
				 
				 @Test
					void saveTest() throws Exception {
						
						ObjectMapper mapper = new ObjectMapper();
				
						// CRIAMOS UM OBJETO DO TIPO  EnderecoDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
						EnderecoDTO endereco = new EnderecoDTO();
						endereco.setRua("Avenida do Teste 5");
						endereco.setNumero(44);
						endereco.setCep("0360230000");
						endereco.setCidade("São Paulo");
						endereco.setEstado("SP");
						
						// PARA ENVIAR A REQUISIÇÃO
						ResultActions response = mockMvc.perform(
								post("/enderecos")
								.content( mapper.writeValueAsString(endereco) )
								.contentType("application/json")
							);
						
						// PEGANDO O RESULTADO VIA MvcResult
						MvcResult result = response.andReturn();
						// PEGANDO O RESULTADO NO FORMATO DE STRING
						String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
						
						System.out.println(responseStr);
						
						EnderecoDTO enderecoSalvo = mapper.readValue(responseStr, EnderecoDTO.class);
						
						// VERIFICA SE FOI SALVO CORRETAMENTE
						
						assertThat ( enderecoSalvo.getId()).isPositive();//
						assertThat ( enderecoSalvo.getRua()).isEqualTo(endereco.getRua());
						assertThat( enderecoSalvo.getNumero()).isEqualTo( endereco.getNumero());
						assertThat( enderecoSalvo.getCep()).isEqualTo( endereco.getCep());
						assertThat( enderecoSalvo.getCidade()).isEqualTo( endereco.getCidade());
						assertThat( enderecoSalvo.getEstado()).isEqualTo( endereco.getEstado());
						
						assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
						
					}
			
				 @Test
					void updateTest() throws Exception {
						
						ObjectMapper mapper = new ObjectMapper();
						
						// CRIAMOS UM OBJTO DO TIPO  EnderecoDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃOo
						EnderecoDTO endereco = new EnderecoDTO();
						endereco.setRua("Avenida do Teste");
						endereco.setNumero(32);
						endereco.setCep("023230000");
						endereco.setCidade("São Paulo");
						endereco.setEstado("SP");
						
						// PARA ENVIAR A REQUISIÇÃO 
						ResultActions response = mockMvc.perform(
								patch("/enderecos/6")
								.content( mapper.writeValueAsString(endereco) )
								.contentType("application/json")
							);

						// PEGANDO O RESULTADO VIA MvcResult
						MvcResult result = response.andReturn();
						// PEGANDO O RESULTADO NO FORMATO DE STRING
						String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
						
						System.out.println(responseStr);
						
						EnderecoDTO enderecoSalvo = mapper.readValue(responseStr, EnderecoDTO.class);
						
						// VERIFICAR SE FOI SALVO CORRETAMENTE
						assertThat ( enderecoSalvo.getId() ).isPositive();
						assertThat ( enderecoSalvo.getRua()).isEqualTo(endereco.getRua());
						assertThat( enderecoSalvo.getNumero()).isEqualTo( endereco.getNumero() );
						assertThat( enderecoSalvo.getCep() ).isEqualTo( endereco.getCep() );
						assertThat( enderecoSalvo.getCidade() ).isEqualTo( endereco.getCidade() );
						assertThat( enderecoSalvo.getEstado() ).isEqualTo( endereco.getEstado() );
						
						assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
					}
					
				 
				 @Test
					void deleteTest() throws Exception {
					 
					 	EnderecoEntity obj = this.createValidEndereco();		
						EnderecoDTO dto = this.enderecoService.save(obj);

						// PARA ENVIAR A REQUISIÇÃO
						ResultActions response = mockMvc.perform(
								delete("/enderecos/" + dto.getId())
								.contentType("application/json"));
						// PEGANDO O RESULTADO VIA  mvcResult
						MvcResult result = response.andReturn();		
						assertThat(result.getResponse().getStatus()).isEqualTo(200);
					}

				 //METODO CRIAÇÃO E OBJETO 
				 private EnderecoEntity createValidEndereco() {
					 
					// INSTANCIANDO O NOVO OBJETO DO TIPO ProfessorEntity 
					 EnderecoEntity enderecoEntidade = new EnderecoEntity();
					 
					 enderecoEntidade.setRua("Avenida do Teste 5");
					 enderecoEntidade.setNumero(44);
					 enderecoEntidade.setCep("0360230000");
					 enderecoEntidade.setCidade("São Paulo");
					 enderecoEntidade.setEstado("SP");
					 
					// RETORNANDO ESTE NOVO OBJETO CRIADO
					 return enderecoEntidade;
				 }
				 
				}
	

