package br.com.codersistemas;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.libs.utils.mock.Genero;
import br.com.codersistemas.libs.utils.mock.Pessoa;

public class AppTest {
	
	private Pessoa rom;
	private Pessoa gi;
	private Pessoa gus;

	@Before
	public void antes(){
		System.out.println("====================================================================");
		
		rom = new Pessoa();
		rom.setAltura(1.7F);
		rom.setAtivo(true);
		rom.setFilhos(new ArrayList<Pessoa>());
		rom.setGenero(Genero.FEMININO);
		rom.setId(1L);
		rom.setMae(null);
		rom.setNome("Rom");
		rom.setSalario(1000.0);
		
		gi = new Pessoa();
		gi.setAltura(1.7F);
		gi.setAtivo(true);
		gi.setFilhos(new ArrayList<Pessoa>());
		gi.setGenero(Genero.MASCULINO);
		gi.setId(2L);
		gi.setNome("Gi");
		gi.setSalario(1000.0);
		
		gus = new Pessoa();
		gus.setAltura(1.7F);
		gus.setAtivo(true);
		gus.setFilhos(new ArrayList<Pessoa>());
		gus.setGenero(Genero.MASCULINO);
		gus.setId(2L);
		gus.setNome("Gus");
		gus.setSalario(1000.0);
		
		rom.getFilhos().add(gus);
		gus.setMae(rom);
		gi.setMae(rom);

	}
	
	@Test
	public void gerarPojo(){}

	@Test
	public void gerarRepository(){}

	@Test
	public void gerarService(){}

	@Test
	public void gerarTSClass(){
		TSClass tsClass = new TSClass(rom); 
	}

	@Test
	public void gerarRestController(){}

	@Test
	public void gerarFormulario() throws Exception {
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
		for (String string : readAllLines) {
			String x = string.replace("Post", "Pessoa");
			x = string.replace("post", "pessoa");
			System.out.println(x);
		}
	}

	@Test
	public void gerarComponent() throws Exception {
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.component.ts").toURI()), Charset.defaultCharset());
		for (String string : readAllLines) {
			String x = string.replace("Post", "Pessoa");
			x = string.replace("post", "pessoa");
			System.out.println(x);
		}
	}

	@Test
	public void gerarAngularService() throws Exception {
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.service.ts").toURI()), Charset.defaultCharset());
		for (String string : readAllLines) {
			String x = string.replace("Post", "Pessoa");
			x = string.replace("post", "pessoa");
			System.out.println(x);
		}
	}

	@Test
	public void gerarFormularioPrime() throws IOException, URISyntaxException {
		InputStream resourceAsStream = this.getClass().getResourceAsStream("teste.txt");
		System.out.println(resourceAsStream);
		
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("teste.txt").toURI()), Charset.defaultCharset());
		for (String string : readAllLines) {
			System.out.println(string);
		}
	}
	
	/**
	 * https://www.baeldung.com/java-with-jsoup
	 */
	@Test
	public void alterarFormulario() throws Exception {
		
		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
		StringBuilder sb = new StringBuilder();
		readAllLines.stream().forEach(s->sb.append(s+"\n"));
		String text = sb.toString();
		
		Document document = Jsoup.parse(text);
		Elements titulo = document.select(".titulo");
		text = text.replace(titulo.outerHtml(), "["+titulo.outerHtml()+"]");
		System.out.println(text);
	}
	
	@Test
	public void alterarFormularioXXX() throws Exception {
		
		
	}
	
	
	


}
