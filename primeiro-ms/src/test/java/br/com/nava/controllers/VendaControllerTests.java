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
import br.com.nava.dtos.VendaDTO;
import br.com.nava.entities.VendaEntity;
import br.com.nava.services.VendaService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc  //PÁRA DEIXAR AS CONFGIRUAÇÕES PADROES
 public class VendaControllerTests {

	
	ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
	
	
	 @Autowired
	 private MockMvc mockMvc; // INSTANCIAR O MOCKMVC PARA SIMULARAÇÕES
	 
	 @Autowired
	 private VendaService vendaService;
	 
	 @Test
	 void getAllTest() throws Exception {
			
			// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/vendas")
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);			
			VendaDTO[] lista = mapper.readValue(responseStr, VendaDTO[].class);
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(lista).isNotEmpty();
		}
	 
	 
	 
	 @Test
		void getOneTests() throws Exception {
		 
		// ARMAZENA O OBJETO QUE FARA O TEST COLHER O RESULTADO
			ResultActions response = mockMvc.perform(
												get("/vendas/1") //NECESSÁRIO INFORMAR O ID ESPECIFICO QUE VC DESEJA TRAZER 
												.contentType("application/json")
											);
			// PEGANDO O RESULTADO 
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			ObjectMapper mapper = new ObjectMapper(); // CONVERSOR DE STRING PARA ARRAY
			
			VendaDTO venda = mapper.readValue(responseStr, VendaDTO.class);
			
			// VERFICANDO SE A LISTA DE RETORNO NÃO É VAZIA
			assertThat(venda.getId() ).isEqualTo(1) ;  // OU O ID 
			assertThat(result.getResponse().getStatus()).isEqualTo(200); //OU O STATUS DA REQUISIÇÃO
		}
	 
	 
	 
	 @Test
		void saveTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO  ProfessorDTO PARA ENVIARMOS JUNTO COM A REQUISIÇÃO
			VendaDTO venda = new VendaDTO();
			venda.setValorTotal(Float.valueOf(20));	
			
			System.out.println(mapper.writeValueAsString(venda));
		
					
			// PARA ENVIAR A REQUISIÇÃO
			ResultActions response = mockMvc.perform(
					post("/vendas")
					.content( mapper.writeValueAsString(venda) )//CONVERSÃO PARA JSON
					.contentType("application/json")
				);
		

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			VendaDTO vendaSalvo = mapper.readValue(responseStr, VendaDTO.class);
			
			// VERIFICAR SE FOI SALVO CORRETAMENTE
			assertThat ( vendaSalvo.getId() ).isPositive();
			assertThat( vendaSalvo.getValorTotal()).isEqualTo( venda.getValorTotal() );
						
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
			
		}

	 @Test
		void updateTest() throws Exception {
			
			ObjectMapper mapper = new ObjectMapper();
			
			// CRIAMOS UM OBJETO DO TIPO USUARIODTO PARA ENVIAREMOS JUNTO COM A REQUISIÇÃO
			VendaDTO venda = new VendaDTO();
			venda.setValorTotal(Float.valueOf(30));	
			
			// PARA ENVIAR A REQUISIÇÃO 
					ResultActions response = mockMvc.perform(
					patch("/vendas/1")
					.content( mapper.writeValueAsString(venda) )
					.contentType("application/json")
				);

			// PEGANDO O RESULTADO VIA MvcResult
			MvcResult result = response.andReturn();
			// PEGANDO O RESULTADO NO FORMATO DE STRING
			String responseStr = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
			
			System.out.println(responseStr);
			
			VendaDTO vendaSalvo = mapper.readValue(responseStr, VendaDTO.class);
			
			// VERIFICA SE FOI SALVO CORRETAMENTO
			assertThat ( vendaSalvo.getId() ).isPositive();
			assertThat( vendaSalvo.getValorTotal() ).isEqualTo( venda.getValorTotal() );
					
			assertThat( result.getResponse().getStatus() ).isEqualTo( 200 );
		}
		
	 
	 @Test
		void deleteTest() throws Exception {

		 
		 VendaEntity obj = this.createValidVenda();
		 VendaDTO dto = this.vendaService.save(obj);
		 
			// PARA ENVIAR A REQUISIÇÃO 
			ResultActions response = mockMvc.perform(
					delete("/vendas/" + dto.getId())
					.contentType("application/json"));
			// PEGANDO O RESULTADO VIA mvcResult
			MvcResult result = response.andReturn();		
			assertThat(result.getResponse().getStatus()).isEqualTo(200);
		}
	 
	 
	 private VendaEntity createValidVenda() {
		 
		 VendaEntity vendaEntity = new VendaEntity();
		
		 vendaEntity.setValorTotal(Float.valueOf(20));	
		 
		 return vendaEntity;
	 }

}



