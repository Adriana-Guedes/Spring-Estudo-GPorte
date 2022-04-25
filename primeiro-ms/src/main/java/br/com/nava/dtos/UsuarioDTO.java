package br.com.nava.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import br.com.nava.entities.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	

	private int id;
	@NotEmpty(message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
	@NotNull (message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
	@Length(min = 3 , max = 80, message = "O numero de caracteres deve ser entre 3 e 80") //TAMANHO DOS CARACTERIES
	@Pattern( regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]*$", message = "É valido apenas caracteres")
	private String nome;
	private String email;
	
	
	
	public UsuarioEntity toEntity() {
		
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, UsuarioEntity.class);

  }
}