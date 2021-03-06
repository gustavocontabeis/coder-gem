package br.com.codersistemas.gem.gemdados;

import java.util.Random;

public class GeradorEndereco {
	
	public GeradorEndereco() {
		super();
		random = new Random();
	}
	
	private String[] tipoDeLogradouros = {
			"Rua",
			"Rua",
			"Rua",
			"Rua",
			"Avenida",
			"Avenida",
			"Avenida",
			"Praça",
			"Travessa"
	};
	
	private String[] cidades = {
			"Porto Alegre",
			"Guaíba",
			"Cachoeirinha",
			"Lajeado",
			"Bom Retiro do Sul",
			"Relvado",
	};
	
	private String[] logradouros = {
			"Antônio Carneiro Pinto", 
			"Fernando Osório", 
			"Mexiana", 
			"São Judas Tadeu", 
			"Três Mil e Quatro", 
			"Domingos Fernandes de Souza", 
			"Coronel Vicente", 
			"Cãncio Gomes", 
			"Humaitá", 
			"Tito Lívio Zambecari", 
			"Doutor Idelfonso Pinto", 
			"Rimolo Biagio", 
			"Coronel Hélio Bezerra", 
			"Cezar Di Giorgio", 
			"Alba Carvalho Degrazia", 
			"José Inácio", 
			"Imperatriz Leopoldina", 
			"Gervásio da Rosa", 
			"Aquiles Porto Alegre", 
			"São Jorge", 
			"Leão XIII", 
			"Adroaldo Novo Correa", 
			"Alameda Alípio César", 
			"Imperador Hiroito", 
			"Beco Vinte e Quatro", 
			"Lasar Segall", 
			"José Conde", 
			"Raul Bopp", 
			"Acesso A - Três", 
			"Doutor Dário Rodrigues da Silva", 
			"Professor Duplan", 
			"Comendador Tavares", 
			"Quatro Mil Quinhentos e Sessenta e Um", 
			"Engenheiro Alberto Henrique Kruse", 
			"Jacicoema", 
			"david Weinstein", 
			"Felipe Corrêa da Silva", 
			"Mil Trezentos e Sete", 
			"Vicente Monteggia", 
			"Erasto Roxo de Araújo Corrêa", 
			"Otávio Faria", 
			"José Carlos Dias de Oliveira", 
			"Waldemar Canterji", 
			"Maria Elaine Wotter", 
			"Quarenta e Sete", 
			"da Cultura"
		};

	private String[] complemento = {
			"",
			"apartamento nr %",
			"apto %",
			"fundos",
			"% andar"
	};
	
	private Random random;
	
	public String gerarTipoLogradouro() {
		return tipoDeLogradouros[random.nextInt(tipoDeLogradouros.length)];
	}
	
	public String gerarLogradouro(boolean comTipoLogradouro) {
		return ( comTipoLogradouro ? gerarTipoLogradouro() + " " : "" ) + logradouros[random.nextInt(logradouros.length)];
	}
	
	public String gerarNumero() {
		return String.valueOf(random.nextInt(3000));
	}
	
	public String gerarComplemento() {
		return complemento[random.nextInt(complemento.length)].replace("%", random.nextInt(12)+"");
	}
	
	public String gerarCep() {
		return "91"+String.valueOf((random.nextLong()*123456)).substring(0, 3)+"-"+String.valueOf((random.nextLong()*123456)).substring(0, 3);
	}
	
	public String gerarCidade() {
		return cidades[random.nextInt(cidades.length)];
	}
	
	public String gerarEstado() {
		return "Rio Grande do Sul";
	}
	
	public String gerarUF() {
		return "RS";
	}
}
