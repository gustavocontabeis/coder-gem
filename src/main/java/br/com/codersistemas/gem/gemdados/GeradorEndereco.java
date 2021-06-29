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
		return "";
	}
	public String gerarCep() {
		return "";
	}
	public String gerarCidade() {
		return "";
	}
	public String gerarEstado() {
		return "";
	}
	public String gerarUF() {
		return "";
	}
}
