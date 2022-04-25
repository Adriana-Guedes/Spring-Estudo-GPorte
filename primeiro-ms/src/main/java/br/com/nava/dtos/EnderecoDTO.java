package br.com.nava.dtos;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;
import br.com.nava.entities.EnderecoEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
	

		
		private int id;
		
		@NotEmpty(message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
		@NotNull (message ="Preenchimento Obrigatório") //NÃO PODE VIR VAZIO CASO ACONTEÇA A MENSAGEM É EXIBIDA
		@Length(min = 3 , max = 80, message = "O numero de caracteres deve ser entre 3 e 80") //TAMANHO DOS CARACTERIES
		@Pattern( regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ 0-9 ]*$", message = "É valido apenas caracteres")
		private String rua;
		private int numero;
		private String cep;
		private String cidade;
		private String estado;

		
		public EnderecoEntity toEntity() {
			
			ModelMapper mapper = new ModelMapper();
			
			return mapper.map(this, EnderecoEntity.class);
		}
	}
