package br.com.nava.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;



@ExtendWith(SpringExtension.class)  //PRA DIZER QUE ESSA É A CLASSE DE TESTE
public class CalculadoraUtilTests {

	
	
	
	// INSTACIAR A CLASSE DESEJADA
	
	private CalculadoraUtil calculadoraUtil = new CalculadoraUtil();
	
	
	
	@Test  ///NOTAÇÃO DE TESTE
	void somarTest() {  //CHAMADA O METODO DA CLASSE + "TEST" PARA VERIFICAR SE A RESPOSTA DO METODO É ESPERADA
	
		int atual = calculadoraUtil.somar(5, 9);
		 assertEquals(14, atual); //RESULTADO ESPERADO DO METODO
		
	}
	
}