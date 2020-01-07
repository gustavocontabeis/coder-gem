package br.com.codersistemas;

import java.io.IOException;
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

import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.fe.NgComponent;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.libs.utils.mock.Genero;
import br.com.codersistemas.libs.utils.mock.Pessoa;

public class AppTest {
	
	private Pessoa rom;
	private Pessoa gi;
	private Pessoa gus;
	
	private Replacememnt replacement;

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
		
		replacement = Replacememnt.builder()
				.addClass(Pessoa.class)
				.build();
		
	}
	
	@Test
	public void gerarPojo(){
		PojoComponent component = new PojoComponent(rom);
		System.out.println(component.print());
	}

	@Test
	public void gerarRepository(){
		ResourceComponent component = new RespositoryComponent(replacement);
		System.out.println(component.print());
	}

	@Test
	public void gerarService(){}

	@Test
	public void gerarTSClass(){
		TSClass tsClass = new TSClass(rom); 
		System.err.println(tsClass.print());
	}

	@Test
	public void gerarRestController(){
		ResourceComponent component = new ControllerComponent(replacement);
		System.out.println(component.print());
	}

	@Test
	public void gerarNGService() throws Exception {
		NgService controller = new NgService(replacement);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponent() throws Exception {		
		NgComponent controller = new NgComponent(replacement);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(rom, replacement);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarFormularioPrime() throws IOException, URISyntaxException {
//		InputStream resourceAsStream = this.getClass().getResourceAsStream("teste.txt");
//		System.out.println(resourceAsStream);
//		
//		List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource("teste.txt").toURI()), Charset.defaultCharset());
//		for (String string : readAllLines) {
//			System.out.println(string);
//		}
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
