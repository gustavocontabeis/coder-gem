package br.com.codersistemas;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.codersistemas.codergemapi.domain.Aplicacao;
import br.com.codersistemas.codergemapi.domain.Atributo;
import br.com.codersistemas.codergemapi.domain.Entidade;
import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.fe.NgComponent;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgDialogHtml;
import br.com.codersistemas.gem.components.fe.NgFormularioHtml;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.libs.utils.MockUtils;
import br.com.codersistemas.libs.utils.mock.Genero;
import br.com.codersistemas.libs.utils.mock.Pessoa;

public class AppTest2 {
	
	private Pessoa rom;
	private Pessoa gi;
	private Pessoa gus;
	
	private Replacememnt r;
	private Aplicacao app;

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
		
		app = new Aplicacao();
		app.setId(1L);
		app.setNome("Aplicação");
		
		app.setEntidades(new ArrayList<>());
		app.getEntidades().add(new Entidade());
		app.getEntidades().iterator().next().setAtributos(new ArrayList<Atributo>());
		app.getEntidades().iterator().next().getAtributos().add(new Atributo());
		
		//app = (Aplicacao) ReflectionUtils.createObjectWithValues();
		
		r = Replacememnt.builder()
				.addClass(Aplicacao.class)
				.build();
		
	}
	
	@Test
	public void gerarJson() throws Exception {
		Gson gson = new GsonBuilder().create();
		Aplicacao app = (Aplicacao) MockUtils.create(this.app);
		System.out.println(gson.toJson(app));
		//System.out.println(component.print());
	}

	@Test
	public void gerarPojo(){
		PojoComponent component = new PojoComponent(rom);
		System.out.println(component.print());
	}

	@Test
	public void gerarSQLInserts(){
	}

	@Test
	public void gerarRepository(){
		ResourceComponent component = new RespositoryComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarService(){}

	@Test
	public void gerarRestController(){
		ResourceComponent component = new ControllerComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarRestControllerTestes(){
		//gerar URL
		//Gerar body post
	}

	@Test
	public void gerarTSClass(){
		TSClass tsClass = new TSClass(rom); 
		System.err.println(tsClass.print());
	}
	
	@Test
	public void gerarNGService() throws Exception {
		NgService controller = new NgService(r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponent() throws Exception {		
		NgComponent controller = new NgComponent(r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(rom, r);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarCampos() throws Exception {
		NgFormularioHtml ngHtmlCrud = new NgFormularioHtml(rom, r);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarDialog() throws Exception {
		NgDialogHtml obj = new NgDialogHtml(rom, r);
		obj.setHeaderText("OK");
		obj.setExibirDialog("exibirDialog");
		System.out.println(obj.print());
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
