package br.com.codersistemas.libs.utils.mock;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.codersistemas.libs.annotations.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Pessoa {
	
	@Id
	private Long id;
	
	@Column
	private String nome;
	
	@Label(name="Gênero")
	private Genero genero;
	
	@Label(name="Altura")
	private Float altura;
	
	@Label(name="Salário")
	private Double salario;
	
	private Boolean ativo;
	
	private Date dataDeNacimento;
	
	private Pessoa mae;
	private List<Pessoa>filhos;
	
}
