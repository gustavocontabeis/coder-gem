package br.com.codersistemas.gem.gemdados;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * https://www.4devs.com.br/gerador_de_pessoas
 * @author gustavo
 *
 */
public class GeradorPessoaFisica {
	
	private String[] nomesMasculino = {
		"Leonardo", "Gael", "Renato", 
		"Victor", "Iago", "Nelson Bruno Enzo", "Diogo Lucas", "Kauê", "Ryan", 
		"Mário", "Murilo", "Victor", "Heitor", "Fernando", "Thomas", "Benício", "Thiago", 
		"Marcos", "Renato", "Gabriel", "Benício", "Vicente", 
	};
	
	private String[] nomesFeminino = {
		"Aurora", "Eliane", "Vera", "Cláudia", "Raquel", "Rosângela", "Isis Sophia Josefa", "Caroline", "Luana", "Isabelle", "Antônia Rafaela", 
		"Rayssa", "Jéssica", "Aline", "Andrea", "Alice", "Natália", "Nair", "Vanessa", "Nicole", "Evelyn"
	};
	
	private String[] sobrenomes = {"Lorenzo Ryan Silveira", "Oliver Pereira",
		"Nascimento", "Ferreira", "da Mata", "Duarte", "Alves", "Nunes",
		"Ferreira", "Dias", "Ferreira", "Gonçalves", "Galvão", "Drumond",
		"Gonçalves", "Nascimento", "Martins", "Hadassa da Luz", "Bernardes", 
		"Silveira", "da Conceição", "Lima", "Cardoso", "Araújo", "da Silva",
	};
	
	private String[] dominios = {
		"gmail.com", "hotmail.com", "outlook.com", "terra.com.br", "codersistemas.com.br"
	};
		
	private Genero genero;
	private String nomeUtilizado;
	private String nomeMasculino;
	private String nomeFeminino;
	private String sobrenome;
	private Random random;

	private Date nascimento;
	
	public GeradorPessoaFisica() {
		random = new Random();
		genero = random.nextInt(100) % 2 == 0 ? Genero.MASCULINO : Genero.FEMNINO;
		nomeMasculino = nomesMasculino[random.nextInt(nomesMasculino.length)];
		nomeFeminino = nomesFeminino[random.nextInt(nomesFeminino.length)];
		sobrenome = sobrenomes[random.nextInt(sobrenomes.length)];
	}
	
	public String gerarNome(Genero genero) {
		
		Random random = new Random();
		
		if(genero == null) {
			this.genero = random.nextInt(100) % 2 == 0 ? Genero.MASCULINO : Genero.FEMNINO;
		}else {
			this.genero = genero;
		}
		
		StringBuilder sb = new StringBuilder();
		if(this.genero == Genero.MASCULINO) {
			nomeUtilizado = nomesMasculino[random.nextInt(nomesMasculino.length)]; 
		} else if(this.genero == Genero.FEMNINO) {
			nomeUtilizado = nomesFeminino[random.nextInt(nomesFeminino.length)]; 
		}
		
		sb.append(nomeUtilizado);
		sb.append(" ");
		sb.append(sobrenome);
		
		return sb.toString();
	}
	
	public String gerarNomeMae() {
		return gerarNome(Genero.FEMNINO);
	}
	
	public String gerarNomePai() {
		return gerarNome(Genero.MASCULINO);
	}
	
	public String gerarCPF(boolean mascara) {
		GeraCpfCnpj gerador = new GeraCpfCnpj();
		return gerador.cpf(mascara);
	}
	
	public Date gerarDataDeNascimento(int idadeDe, int idadeAte) {
		Calendar c = Calendar.getInstance();
		int y = c.get(Calendar.YEAR);
		c.set(Calendar.DAY_OF_MONTH, random.nextInt(31));
		c.set(Calendar.MONTH, random.nextInt(12));
		c.set(Calendar.YEAR, (y - idadeAte) + random.nextInt(idadeAte));
		c.setLenient(true);
		nascimento = c.getTime();
		return nascimento;
	}
		
	public Integer getIdadeAtual() {
		if(nascimento != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(nascimento);
			int anoNascimento = c.get(Calendar.YEAR);
			int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
			return anoAtual - anoNascimento;
		}
		return null;
	}
	
	public String gerarEmail() {
		return nomeUtilizado.toLowerCase()
				.replace(" ", "")
				.replaceAll("[àáâã]", "a")
				.replaceAll("[èéêẽ]", "e")
				.replaceAll("[ìí]", "i")
				.replace("[óôõ]", "o")
				.replace("[úû]", "")
				.replace("ç", "c")
				.toLowerCase() + "@" + dominios[random.nextInt(dominios.length)];
	}
	
	public String gerarCelular() {
		return "";
	}
	
	public String gerarLogin() {
		return "";
	}
	
	public String gerarSenha() {
		return "";
	}

}
