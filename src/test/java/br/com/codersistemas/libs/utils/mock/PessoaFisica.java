package br.com.codersistemas.libs.utils.mock;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PessoaFisica implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(generator="seq_pessoa", strategy=GenerationType.SEQUENCE) @SequenceGenerator(name="seq_pessoa") @Column(name="id_pessoa", nullable=false) 
	private Long id;

	@Column(name="nome", length=120, nullable=false)
	private String nome;

	@Enumerated(EnumType.STRING) @Column(length=60, nullable=false)
	private Genero genero;

	@Column(name="altura", precision=10, scale=3, nullable=false)
	private Float altura;

	@Column(name="salario", precision=10, scale=2, nullable=false)
	private Double salario;

	@Column(name="ativo", length=1, nullable=false)
	private Boolean ativo;

	@Temporal(TemporalType.DATE) 
	@Column(name="data_de_nacimento", nullable=false)
	private Date dataDeNacimento;

	@ManyToOne @JoinColumn(name="id_pessoa", nullable=false)
	private PessoaFisica mae;

	@OneToMany(mappedBy="mae")
	private List<PessoaFisica> filhos;

}
//Ajuste os tamanhos dos campos.

