package br.com.nava.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import br.com.nava.entities.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
	
	private int id;
	@NotEmpty(message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
	@NotNull (message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
	@Length(min = 3 , max = 80, message = "O numero de caracteres deve ser entre 3 e 80") //TAMANHO DOS CARACTERIES
	@Pattern( regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*$", message = "É valido apenas caracteres")
	private String nome;
	private String descricao;
	private float preco;
	
	
	
	public ProdutoEntity toEntity() {
		
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, ProdutoEntity.class);

  }

}