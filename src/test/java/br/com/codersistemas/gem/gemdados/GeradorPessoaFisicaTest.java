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
	public void testGerarNome() {
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
		}
	}

	@Test
	public void testGerarNomeMae() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarNomePai() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarCPF() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarDataDeNascimento() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarCelular() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testGerarSenha() {
		fail("Not yet implemented");
	}

}
