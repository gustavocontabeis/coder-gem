package br.com.codersistemas.gem.gemdados;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.codersistemas.libs.utils.DateUtils;

public class GeradorPessoaFisicaTest {

	private GeradorPessoaFisica pf;
	private int arrayLength;

	@Before
	public void testBefore() {
		arrayLength = 100;
	}

	@Test
	public void testGerarTudo() {
		for (int i = 0; i < arrayLength; i++) {
			pf = new GeradorPessoaFisica();
			GeradorEndereco ge = new GeradorEndereco();
			System.out.println("-------------------------------------------");
			System.out.println("M:                  " + pf.gerarNome(Genero.MASCULINO));
			System.out.println("F:                  " + pf.gerarNome(Genero.FEMNINO));
			System.out.println("?:                  " + pf.gerarNome(null));
			System.out.println("MAE:                " + pf.gerarNomeMae());
			System.out.println("PAI:                " + pf.gerarNomePai());
			System.out.println("MAIL:               " + pf.gerarEmail());
			System.out.println("CPF:                " + pf.gerarCPF(true));
			System.out.println("CPF:                " + pf.gerarCPF(false));
			System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(0, 70), "dd/MM/yyyy"));
			System.out.println("Idade atual:        " + pf.getIdadeAtual());
			System.out.println("Tipo de Logradouro: " + ge.gerarTipoLogradouro());
			System.out.println("Logradouro:         " + ge.gerarLogradouro(true));
			System.out.println("Logradouro:         " + ge.gerarLogradouro(false));
			System.out.println("Número:             " + ge.gerarNumero());
			System.out.println("Complemento:        " + ge.gerarComplemento());
			System.out.println("CEP:                " + ge.gerarCep());
			System.out.println("Cidade:             " + ge.gerarCidade());
			System.out.println("Estado:             " + ge.gerarEstado());
			System.out.println("UF:                 " + ge.gerarUF());
		}
	}
	
	@Test
	public void testGerarNome() {
		for (int i = 0; i < 10; i++) {
			pf = new GeradorPessoaFisica();
			System.out.println("-------------------------------------------");
			System.out.println("M:                  " + pf.gerarNome(Genero.MASCULINO));
			System.out.println("F:                  " + pf.gerarNome(Genero.FEMNINO));
			System.out.println("?:                  " + pf.gerarNome(null));
		}
	}

	@Test
	public void testGerarNomeMae() {
		pf = new GeradorPessoaFisica();
		System.out.println("-------------------------------------------");
		System.out.println("MAE:                " + pf.gerarNomeMae());
	}

	@Test
	public void testGerarNomePai() {
		pf = new GeradorPessoaFisica();
		System.out.println("-------------------------------------------");
		System.out.println("PAI:                " + pf.gerarNomePai());
	}

	@Test
	public void testGerarCPF() {
		pf = new GeradorPessoaFisica();
		GeradorEndereco ge = new GeradorEndereco();
		System.out.println("-------------------------------------------");
		System.out.println("CPF:                " + pf.gerarCPF(true));
		System.out.println("CPF:                " + pf.gerarCPF(false));
	}

	@Test
	public void testGerarDataDeNascimento() {
		pf = new GeradorPessoaFisica();
		System.out.println("-------------------------------------------");
		GeradorEndereco ge = new GeradorEndereco();
		System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(10, 20), "dd/MM/yyyy"));
		System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(0, 70), "dd/MM/yyyy"));
		System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(0, 18), "dd/MM/yyyy"));
		System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(60, 70), "dd/MM/yyyy"));
		System.out.println("Nascimento:         " + DateUtils.formatarData(pf.gerarDataDeNascimento(43, 43), "dd/MM/yyyy"));
	}

	@Test
	public void testGerarEmail() {
		pf = new GeradorPessoaFisica();
		System.out.println("-------------------------------------------");
		pf.gerarNome(null);
		System.out.println("MAIL:               " + pf.gerarEmail());
	}

	@Test
	public void testGerarCelular() {
		pf = new GeradorPessoaFisica();
		System.out.println("-------------------------------------------");
		System.out.println("CELULAR:                  " + pf.gerarCelular());
	}

}
