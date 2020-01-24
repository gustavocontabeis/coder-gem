package br.com.codersistemas;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.codersistemas.codergemapi.domain.Pessoa;
import br.com.codersistemas.gem.components.IComponent;
import br.com.codersistemas.gem.components.Replacememnt;
import br.com.codersistemas.gem.components.ResourceComponent;
import br.com.codersistemas.gem.components.TSClass;
import br.com.codersistemas.gem.components.be.ControllerComponent;
import br.com.codersistemas.gem.components.be.HQLComponent;
import br.com.codersistemas.gem.components.be.PojoComponent;
import br.com.codersistemas.gem.components.be.RespositoryComponent;
import br.com.codersistemas.gem.components.fe.NgComponent;
import br.com.codersistemas.gem.components.fe.NgComponentHtml;
import br.com.codersistemas.gem.components.fe.NgDialogHtml;
import br.com.codersistemas.gem.components.fe.NgFormularioHtml;
import br.com.codersistemas.gem.components.fe.NgService;
import br.com.codersistemas.gem.components.fe.NgTabelaHtml;
import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.AtributoDTO;
import br.com.codersistemas.libs.dto.ColumnDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.JPAUtil;
import br.com.codersistemas.libs.utils.ReflectionUtils;
import br.com.codersistemas.libs.utils.StringUtil;

public class AppTest2 {

	private Class classe = null;

	private Replacememnt r;

	private AplicacaoDTO appDTO;
	private EntidadeDTO entidadeDTO;
	private String json;

	private Object obj;

	@Before
	public void antes() throws Exception {

		//classe = Pessoa.class;
		classe = br.gov.caixa.pedes.sistemas.siarr.domain.Termo.class;

		r = Replacememnt.builder().addClass(classe).build();

		gerarAplicacaoDTO();

		obj = ReflectionUtils.newInstance(classe);

	}

	public void gerarAplicacaoDTO() throws Exception {

		appDTO = gerarAplicacaoDTO("minha-app", classe);

		List<EntidadeDTO> entidades = appDTO.getEntidades();
		for (EntidadeDTO entidade : entidades) {
			entidade.setAplicacao(null);
			List<AtributoDTO> atributos = entidade.getAtributos();
			for (AtributoDTO atributo : atributos) {
				atributo.setEntidade(null);
			}
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		json = gson.toJson(appDTO);

		appDTO = gerarAplicacaoDTO("minha-app", classe);
		for (EntidadeDTO a : appDTO.getEntidades()) {
			entidadeDTO = a;
		}

	}

	@Test
	public void gerarJson() throws Exception {
		System.out.println(json);
	}

	@Test
	public void gerarPojo() {
		PojoComponent component = new PojoComponent(obj);
		System.out.println(component.print());
	}

	@Test
	public void gerarSQLInserts() {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO public." + entidadeDTO.getTabela() + " (");

		String x = "";
		for (AtributoDTO atributo : entidadeDTO.getAtributos()) {
			if (atributo.isCollection())
				continue;
			x += atributo.getColuna() + ", ";
		}

		x = StringUtil.removeEnd(x, ", ") + ") values (";
		sb.append(x);

		x = "";
		for (AtributoDTO atributo : entidadeDTO.getAtributos()) {
			if (atributo.isCollection())
				continue;

			switch (atributo.getTipo()) {
			case "BOOLEAN":
				x += "FALSE, ";
				break;
			case "INTEGER":
				x += "1, ";
				break;
			case "LONG":
				x += "2, ";
				break;
			case "FLOAT":
				x += "3.5, ";
				break;
			case "DOUBLE":
				x += "4.5, ";
				break;
			case "DATE":
				x += "'2000-12-31', ";
				break;
			case "STRING":
				x += "'OK', ";
				break;
			}
		}

		x = StringUtil.removeEnd(x, ", ");
		x += ");";
		sb.append(x);

		System.out.println(sb);

	}

	@Test
	public void gerarRepository() {
		ResourceComponent component = new RespositoryComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarRepositoryTest() {

	}

	@Test
	public void gerarService() {
	}

	@Test
	public void gerarServiceTest() {

		StringBuilder sb = new StringBuilder();

		sb.append("import static org.junit.Assert.*;\r\n");
		sb.append("import static org.mockito.Mockito.*;\r\n");
		sb.append("import java.time.LocalDateTime;\r\n");
		sb.append("import org.junit.Test;\r\n");
		sb.append("import org.junit.runner.RunWith;\r\n");
		sb.append("import org.mockito.InjectMocks;\r\n");
		sb.append("import org.mockito.Mock;\r\n");
		sb.append("import org.springframework.test.context.junit4.SpringRunner;\r\n");
		sb.append("\r\n");
		sb.append("@RunWith(SpringRunner.class)\r\n");
		sb.append("public class ImovelHistoricoServiceTest {\r\n");
		sb.append("\r\n");
		sb.append("	@InjectMocks\r\n");
		sb.append("	private ImovelHistoricoService service;\r\n");
		sb.append("	\r\n");

		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if (atributo.isFk()) {
				sb.append("	@Mock\r\n");
				sb.append("	private " + atributo.getNome() + " " + atributo.getNomeInstancia() + ";\r\n");
			}
		}
		
		Method[] methods = ReflectionUtils.getMethods(classe);
		for (Method method : methods) {
			 if (Modifier.isPublic(method.getModifiers())) {
				 
					sb.append("	@Test\r\n");
					sb.append("	public void when_" + method.getName() + "_when_success() {\r\n");
					sb.append("		//when(authenticationFacade.getUsuarioLogado()).thenReturn(Usuario.builder().build());\r\n");
					sb.append("		//service." + method.getName() + "();\r\n");
					sb.append("	}\r\n");
			 }
		}

		sb.append("}\r\n");
		String str = sb.toString().replace("ImovelHistorico", entidadeDTO.getNome());
		System.out.println(str);

	}

	@Test
	public void gerarSpecification() { 
		gerarSpecification(entidadeDTO);
	}

	@Test
	public void gerarRestController() {
		ResourceComponent component = new ControllerComponent(r);
		System.out.println(component.print());
	}

	@Test
	public void gerarRestControllerTestes() {
		// gerar URL
		// Gerar body post
	}

	@Test
	public void gerarTSClass() {

		System.out.println(StringUtil.toUnderlineCase(entidadeDTO.getNome()).replace("_", "-") + ".model.ts");

		TSClass tsClass = new TSClass(obj);

		System.out.println(tsClass.print());
	}

	@Test
	public void gerarNGService() throws Exception {
		NgService controller = new NgService(r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarNGComponent() throws Exception {
		NgComponent controller = new NgComponent(appDTO, entidadeDTO, r);
		System.out.println(controller.print());
	}

	@Test
	public void gerarFormulario() throws Exception {
		NgComponentHtml ngHtmlCrud = new NgComponentHtml(obj, r);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarTabela() throws Exception {
		IComponent ngHtmlCrud = new NgTabelaHtml(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarCampos() throws Exception {
		IComponent ngHtmlCrud = new NgFormularioHtml(entidadeDTO);
		System.out.println(ngHtmlCrud.print());
	}

	@Test
	public void gerarDialog() throws Exception {
		NgDialogHtml obj = new NgDialogHtml(this.obj, r);
		obj.setHeaderText("OK");
		obj.setExibirDialog("exibirDialog");
		System.out.println(obj.print());
	}

	/**
	 * https://www.baeldung.com/java-with-jsoup
	 */
	@Test
	public void alterarFormulario() throws Exception {

		List<String> readAllLines = Files.readAllLines(
				Paths.get(this.getClass().getResource("post.component.html").toURI()), Charset.defaultCharset());
		StringBuilder sb = new StringBuilder();
		readAllLines.stream().forEach(s -> sb.append(s + "\n"));
		String text = sb.toString();

		Document document = Jsoup.parse(text);
		Elements titulo = document.select(".titulo");
		text = text.replace(titulo.outerHtml(), "[" + titulo.outerHtml() + "]");
		System.out.println(text);
	}

	@Test
	public void gerarHQL() throws Exception {

		String str = "Contrato contato = new Contrato();\n";
		str += "Empreendimento empreendimento = contato.empreendimento\n";
		str += "Unidade unidade = empreendimento.unidade\n";
		str += "\n";
		str += "unidade.cidade\n";
		str += "contato.diasAtraso;";

		IComponent component = new HQLComponent(str);
		System.out.println(component.print());
		System.out.println("-----------------------------");

		// String s = "Empreendimento empreendimento = contato.empreendimento";
		// String s = "Empreendimento empreendimento = contato.empreendimento";
		// System.out.println(s.matches("\\w*.? \\w*.? = \\w*.?\\.\\w*.?"));

	}
	
	@Test
	public void limparSQL() throws Exception {
		String sb = 
				"select\n" +
				"	itempagope0_.co_item_pago_periodo as co_item_1_29_0_,\n" +
				"	periodo12_.co_periodo as co_perio1_28_1_,\n" +
				"	vigenciaad13_.co_vigencia as co_vigen1_31_2_,\n" +
				"	termo14_.co_termo as co_termo1_30_3_,\n" +
				"	itempagar15_.co_item_pagar as co_item_1_27_4_,\n" +
				"	contrato16_.co_contrato as co_contr1_22_5_,\n" +
				"	imovel17_.co_imovel as co_imove1_20_6_,\n" +
				"	empreendim18_.co_empreendimento as co_empre1_19_7_,\n" +
				"	unidade19_.co_unidade as co_unida2_7_8_,\n" +
				"	composicao20_.co_composicao_identificador as co_compo1_10_9_,\n" +
				"	tipounidad21_.co_tipo_unidade as co_tipo_1_9_10_,\n" +
				"	uf22_.co_uf as co_uf1_8_11_,\n" +
				"	unidade23_.co_unidade as co_unida2_7_12_,\n" +
				"	tipounidad24_.co_tipo_unidade as co_tipo_1_9_13_,\n" +
				"	uf25_.co_uf as co_uf1_8_14_,\n" +
				"	itempagope0_.dt_pagamento as dt_pagam2_29_0_,\n" +
				"	itempagope0_.co_item_pagar as co_item_5_29_0_,\n" +
				"	itempagope0_.co_periodo as co_perio6_29_0_,\n" +
				"	itempagope0_.ic_pagto_adm as ic_pagto3_29_0_,\n" +
				"	itempagope0_.ic_tipo_item_pago as ic_tipo_4_29_0_,\n" +
				"	itempagope0_.co_vigencia as co_vigen7_29_0_,\n" +
				"	periodo12_.dt_final_periodo as dt_final2_28_1_,\n" +
				"	periodo12_.dt_inicio_periodo as dt_inici3_28_1_,\n" +
				"	periodo12_.no_periodo as no_perio4_28_1_,\n" +
				"	vigenciaad13_.dt_fim as dt_fim2_31_2_,\n" +
				"	vigenciaad13_.dt_inicio as dt_inici3_31_2_,\n" +
				"	vigenciaad13_.co_empreendimento as co_empre5_31_2_,\n" +
				"	vigenciaad13_.ic_atual as ic_atual4_31_2_,\n" +
				"	vigenciaad13_.co_termo as co_termo6_31_2_,\n" +
				"	vigenciaad13_.co_unidade_administradora as co_unida7_31_2_,\n" +
				"	termo14_.nu_ano as nu_ano2_30_3_,\n" +
				"	termo14_.ic_glosa_fim_termo as ic_glosa3_30_3_,\n" +
				"	termo14_.ic_glosa_pagamento_realizado as ic_glosa4_30_3_,\n" +
				"	termo14_.ic_pagamento_imediato as ic_pagam5_30_3_,\n" +
				"	termo14_.ic_pagamento_previsto as ic_pagam6_30_3_,\n" +
				"	termo14_.qt_dias_ate_glosa_definitiva as qt_dias_7_30_3_,\n" +
				"	termo14_.qt_dias_ate_pagamento_cancelado as qt_dias_8_30_3_,\n" +
				"	termo14_.tp_pagamento as tp_pagam9_30_3_,\n" +
				"	termo14_.ic_termo as ic_term10_30_3_,\n" +
				"	termo14_.vl_pagamento as vl_paga11_30_3_,\n" +
				"	itempagar15_.co_contrato as co_cont11_27_4_,\n" +
				"	itempagar15_.dt_final_pagamento_previsto as dt_final2_27_4_,\n" +
				"	itempagar15_.dt_glosa_final as dt_glosa3_27_4_,\n" +
				"	itempagar15_.dt_glosa_inicial as dt_glosa4_27_4_,\n" +
				"	itempagar15_.dt_inicial_pagamento_previsto as dt_inici5_27_4_,\n" +
				"	itempagar15_.dt_retido_final as dt_retid6_27_4_,\n" +
				"	itempagar15_.dt_retido_inicial as dt_retid7_27_4_,\n" +
				"	itempagar15_.ic_glosa as ic_glosa8_27_4_,\n" +
				"	itempagar15_.ic_retido as ic_retid9_27_4_,\n" +
				"	itempagar15_.ic_pagto_adm as ic_pagt10_27_4_,\n" +
				"	contrato16_.co_arquivo_sifob as co_arqu30_22_5_,\n" +
				"	contrato16_.co_centralizadora as co_centr2_22_5_,\n" +
				"	contrato16_.dt_assinatura_contrato as dt_assin3_22_5_,\n" +
				"	contrato16_.dt_termino_contrato as dt_termi4_22_5_,\n" +
				"	contrato16_.dt_termino_pagamento_administradora as dt_termi5_22_5_,\n" +
				"	contrato16_.dt_ultma_prestacao as dt_ultma6_22_5_,\n" +
				"	contrato16_.qt_dias_atraso as qt_dias_7_22_5_,\n" +
				"	contrato16_.dv_contrato as dv_contr8_22_5_,\n" +
				"	contrato16_.co_empreendimento as co_empr31_22_5_,\n" +
				"	contrato16_.co_imovel as co_imov32_22_5_,\n" +
				"	contrato16_.ic_mip as ic_mip9_22_5_,\n" +
				"	contrato16_.nu_imovel_anterior as nu_imov10_22_5_,\n" +
				"	contrato16_.nu_prestacao as nu_pres11_22_5_,\n" +
				"	contrato16_.nu_unidade_operacional as nu_unid12_22_5_,\n" +
				"	contrato16_.pz_remanescente as pz_rema13_22_5_,\n" +
				"	contrato16_.qt_coobrigados as qt_coob14_22_5_,\n" +
				"	contrato16_.qt_prestacao_ataso as qt_pres15_22_5_,\n" +
				"	contrato16_.pz_amortizacao as pz_amor16_22_5_,\n" +
				"	contrato16_.ic_situacao_contrato as ic_situ17_22_5_,\n" +
				"	contrato16_.ic_situacao_inadimplencia as ic_situ18_22_5_,\n" +
				"	contrato16_.ic_situacao_pagamento_administradora as ic_situ19_22_5_,\n" +
				"	contrato16_.tx_arrendamento as tx_arre20_22_5_,\n" +
				"	contrato16_.ic_transferido_arrendatario as ic_tran21_22_5_,\n" +
				"	contrato16_.vr_arrendamento as vr_arre22_22_5_,\n" +
				"	contrato16_.vr_diferenca_prestacao as vr_dife23_22_5_,\n" +
				"	contrato16_.vr_divida_vencida as vr_divi24_22_5_,\n" +
				"	contrato16_.vr_encargo as vr_enca25_22_5_,\n" +
				"	contrato16_.vr_encargo_atraso as vr_enca26_22_5_,\n" +
				"	contrato16_.vr_moratorio_total as vr_mora27_22_5_,\n" +
				"	contrato16_.qt_prestacao as qt_pres28_22_5_,\n" +
				"	contrato16_.vr_seguro as vr_segu29_22_5_,\n" +
				"	imovel17_.no_bairro as no_bairr2_20_6_,\n" +
				"	imovel17_.ds_cep as ds_cep3_20_6_,\n" +
				"	imovel17_.no_cidade as no_cidad4_20_6_,\n" +
				"	imovel17_.de_complemento_logradouro as de_compl5_20_6_,\n" +
				"	imovel17_.qt_dias_ociosidade as qt_dias_6_20_6_,\n" +
				"	imovel17_.co_empreendimento as co_empr12_20_6_,\n" +
				"	imovel17_.no_logradouro as no_logra7_20_6_,\n" +
				"	imovel17_.nu_imovel as nu_imove8_20_6_,\n" +
				"	imovel17_.ic_situacao_imovel as ic_situa9_20_6_,\n" +
				"	imovel17_.ic_situacao_ocupacao as ic_situ10_20_6_,\n" +
				"	imovel17_.sg_uf as sg_uf11_20_6_,\n" +
				"	empreendim18_.no_empreendimento as no_empre2_19_7_,\n" +
				"	empreendim18_.co_unidade as co_unida3_19_7_,\n" +
				"	empreendim18_.co_vigencia as co_vigen4_19_7_,\n" +
				"	unidade19_.ic_ativo as ic_ativo3_7_8_,\n" +
				"	unidade19_.ds_bairro as ds_bairr4_7_8_,\n" +
				"	unidade19_.no_cidade as no_cidad5_7_8_,\n" +
				"	unidade19_.cod_unidade as cod_unid6_7_8_,\n" +
				"	unidade19_.co_composicao_identificador as co_comp14_7_8_,\n" +
				"	unidade19_.ds_email as ds_email7_7_8_,\n" +
				"	unidade19_.ds_endereco as ds_ender8_7_8_,\n" +
				"	unidade19_.ic_exclo as ic_exclo9_7_8_,\n" +
				"	unidade19_.ds_identificador_unidade as ds_iden10_7_8_,\n" +
				"	unidade19_.no_unidade as no_unid11_7_8_,\n" +
				"	unidade19_.sg_unidade as sg_unid12_7_8_,\n" +
				"	unidade19_.ds_telefone as ds_tele13_7_8_,\n" +
				"	unidade19_.co_tipo_unidade as co_tipo15_7_8_,\n" +
				"	unidade19_.co_uf as co_uf16_7_8_,\n" +
				"	unidade19_.co_unidade_vinculada as co_unid17_7_8_,\n" +
				"	unidade19_.ic_tipo as ic_tipo1_7_8_,\n" +
				"	composicao20_.de_composicao_identificador as de_compo2_10_9_,\n" +
				"	composicao20_.sg_camposicao_identificador as sg_campo3_10_9_,\n" +
				"	tipounidad21_.de_descricao as de_descr2_9_10_,\n" +
				"	tipounidad21_.sg_tipo_unidade as sg_tipo_3_9_10_,\n" +
				"	uf22_.no_uf as no_uf2_8_11_,\n" +
				"	uf22_.sg_uf as sg_uf3_8_11_,\n" +
				"	unidade23_.ic_ativo as ic_ativo3_7_12_,\n" +
				"	unidade23_.ds_bairro as ds_bairr4_7_12_,\n" +
				"	unidade23_.no_cidade as no_cidad5_7_12_,\n" +
				"	unidade23_.cod_unidade as cod_unid6_7_12_,\n" +
				"	unidade23_.co_composicao_identificador as co_comp14_7_12_,\n" +
				"	unidade23_.ds_email as ds_email7_7_12_,\n" +
				"	unidade23_.ds_endereco as ds_ender8_7_12_,\n" +
				"	unidade23_.ic_exclo as ic_exclo9_7_12_,\n" +
				"	unidade23_.ds_identificador_unidade as ds_iden10_7_12_,\n" +
				"	unidade23_.no_unidade as no_unid11_7_12_,\n" +
				"	unidade23_.sg_unidade as sg_unid12_7_12_,\n" +
				"	unidade23_.ds_telefone as ds_tele13_7_12_,\n" +
				"	unidade23_.co_tipo_unidade as co_tipo15_7_12_,\n" +
				"	unidade23_.co_uf as co_uf16_7_12_,\n" +
				"	unidade23_.co_unidade_vinculada as co_unid17_7_12_,\n" +
				"	unidade23_.ic_tipo as ic_tipo1_7_12_,\n" +
				"	tipounidad24_.de_descricao as de_descr2_9_13_,\n" +
				"	tipounidad24_.sg_tipo_unidade as sg_tipo_3_9_13_,\n" +
				"	uf25_.no_uf as no_uf2_8_14_,\n" +
				"	uf25_.sg_uf as sg_uf3_8_14_\n" +
				"from\n" +
				"	arrsm001.arrtb030_item_pago_periodo itempagope0_\n" +
				"inner join arrsm001.arrtb029_periodo periodo1_ on\n" +
				"	itempagope0_.co_periodo = periodo1_.co_periodo\n" +
				"inner join arrsm001.arrtb032_vigencia_administradora_empreendimento vigenciaad2_ on\n" +
				"	itempagope0_.co_vigencia = vigenciaad2_.co_vigencia\n" +
				"inner join arrsm001.arrtb031_termo termo3_ on\n" +
				"	vigenciaad2_.co_termo = termo3_.co_termo\n" +
				"inner join arrsm001.arrtb028_item_pagar itempagar4_ on\n" +
				"	itempagope0_.co_item_pagar = itempagar4_.co_item_pagar\n" +
				"inner join arrsm001.arrtb023_sifob_contrato contrato5_ on\n" +
				"	itempagar4_.co_contrato = contrato5_.co_contrato\n" +
				"inner join arrsm001.arrtb021_sifob_imovel imovel6_ on\n" +
				"	contrato5_.co_imovel = imovel6_.co_imovel\n" +
				"inner join arrsm001.arrtb020_empreendimento empreendim7_ on\n" +
				"	contrato5_.co_empreendimento = empreendim7_.co_empreendimento\n" +
				"inner join arrsm001.arrtb008_unidade unidade8_ on\n" +
				"	empreendim7_.co_unidade = unidade8_.co_unidade\n" +
				"inner join arrsm001.arrtb008_unidade unidade9_ on\n" +
				"	unidade8_.co_unidade_vinculada = unidade9_.co_unidade\n" +
				"inner join arrsm001.arrtb008_unidade unidade10_ on\n" +
				"	unidade9_.co_unidade_vinculada = unidade10_.co_unidade\n" +
				"inner join arrsm001.arrtb008_unidade unidade11_ on\n" +
				"	unidade10_.co_unidade_vinculada = unidade11_.co_unidade\n" +
				"inner join arrsm001.arrtb029_periodo periodo12_ on\n" +
				"	itempagope0_.co_periodo = periodo12_.co_periodo\n" +
				"inner join arrsm001.arrtb032_vigencia_administradora_empreendimento vigenciaad13_ on\n" +
				"	itempagope0_.co_vigencia = vigenciaad13_.co_vigencia\n" +
				"inner join arrsm001.arrtb031_termo termo14_ on\n" +
				"	vigenciaad13_.co_termo = termo14_.co_termo\n" +
				"inner join arrsm001.arrtb028_item_pagar itempagar15_ on\n" +
				"	itempagope0_.co_item_pagar = itempagar15_.co_item_pagar\n" +
				"inner join arrsm001.arrtb023_sifob_contrato contrato16_ on\n" +
				"	itempagar15_.co_contrato = contrato16_.co_contrato\n" +
				"inner join arrsm001.arrtb021_sifob_imovel imovel17_ on\n" +
				"	contrato16_.co_imovel = imovel17_.co_imovel\n" +
				"inner join arrsm001.arrtb020_empreendimento empreendim18_ on\n" +
				"	contrato16_.co_empreendimento = empreendim18_.co_empreendimento\n" +
				"inner join arrsm001.arrtb008_unidade unidade19_ on\n" +
				"	empreendim18_.co_unidade = unidade19_.co_unidade\n" +
				"inner join arrsm001.arrtb011_composicao_identificador composicao20_ on\n" +
				"	unidade19_.co_composicao_identificador = composicao20_.co_composicao_identificador\n" +
				"inner join arrsm001.arrtb010_tipo_unidade tipounidad21_ on\n" +
				"	unidade19_.co_tipo_unidade = tipounidad21_.co_tipo_unidade\n" +
				"inner join arrsm001.arrtb009_uf uf22_ on\n" +
				"	unidade19_.co_uf = uf22_.co_uf\n" +
				"inner join arrsm001.arrtb008_unidade unidade23_ on\n" +
				"	unidade19_.co_unidade_vinculada = unidade23_.co_unidade\n" +
				"inner join arrsm001.arrtb010_tipo_unidade tipounidad24_ on\n" +
				"	unidade23_.co_tipo_unidade = tipounidad24_.co_tipo_unidade\n" +
				"inner join arrsm001.arrtb009_uf uf25_ on\n" +
				"	unidade23_.co_uf = uf25_.co_uf\n" +
				"where\n" +
				"	termo3_.co_termo = 7\n" +
				"	and itempagar4_.ic_pagto_adm =?\n" +
				"	and itempagope0_.ic_tipo_item_pago =?\n" +
				"order by\n" +
				"	itempagope0_.co_item_pago_periodo asc\n" +
				"limit ?";
		

	}
	

	private void gerarSpecification(EntidadeDTO entidadeDTO) {

		System.out.println("");
		List<AtributoDTO> atributos = entidadeDTO.getAtributos();
		for (AtributoDTO atributo : atributos) {
			if (atributo.isFk()) {
				System.out.println("Join<Object, Object> join" + StringUtil.capitalize(atributo.getNome())
						+ " = root.join(\"" + atributo.getNome() + "\");");
			}
		}

		System.out.println("");
		System.out.println("List<Predicate> predicates = new ArrayList<>();");

		for (AtributoDTO atributo : atributos) {
			if (!atributo.isFk()) {
				System.out.println("");
				System.out.println("if(filter.get" + StringUtil.capitalize(atributo.getNome()) + "() != null)){");
				System.out.println("   predicates.add(cb.equal(" + atributo.getEntidade().getNomeInstancia() + ".get(\""
						+ atributo.getNome() + "\"), filter.get" + StringUtil.capitalize(atributo.getNome()) + "()));");
				System.out.println("}");
			}
		}

	}

	private AplicacaoDTO gerarAplicacaoDTO(String nomeAplicacao, Class<?>... classes) {

		AplicacaoDTO aplicacao = new AplicacaoDTO();
		aplicacao.setNome(nomeAplicacao);
		aplicacao.setEntidades(new ArrayList<EntidadeDTO>());

		for (Class<?> classe : classes) {

			EntidadeDTO entidade = new EntidadeDTO();
			entidade.setAtributos(new ArrayList<>());
			entidade.setNome(classe.getSimpleName());
			entidade.setNomeClasse(classe.getName());
			entidade.setNomeInstancia(StringUtil.uncapitalize(classe.getSimpleName()));
			entidade.setRotulo(StringUtil.label(classe.getSimpleName()));
			entidade.setTabela(StringUtil.toUnderlineCase(classe.getSimpleName()).toLowerCase());
			entidade.setAplicacao(aplicacao);
			entidade.setRestURI("/" + StringUtil.uncaplitalizePlural(entidade.getTabela().replace("_", "-")));
			aplicacao.getEntidades().add(entidade);

			Field[] fields = ReflectionUtils.getFields(classe);
			for (Field field : fields) {
				AtributoDTO atributo = new AtributoDTO();
				if (field.getType().isEnum()) {
					List<String> enumValues = new ArrayList<>();
					Class enummClass = field.getType();
					Object[] enumConstants = enummClass.getEnumConstants();
					for (Object object : enumConstants) {
						Enum e = (Enum) object;
						enumValues.add(e.name());
					}
					atributo.setEnum(true);
					atributo.setEnumaracao(enumValues.toArray(new String[enumValues.size()]));
				}

				atributo.setTipoClasse(field.getType().getName());
				atributo.setNome(field.getName());
				atributo.setNomeInstancia(StringUtil.uncapitalize(field.getName()));
				atributo.setNomeLista(StringUtil.uncaplitalizePlural(field.getName()));
				atributo.setRotulo(StringUtil.label(field.getName()));
				atributo.setCollection(field.getType().isArray() || field.getType() == List.class
						|| field.getType() == Set.class || field.getType() == Map.class);
				atributo.setFk((!atributo.getTipoClasse().startsWith("java.") && !atributo.isEnum())
						|| atributo.isCollection());
				ColumnDTO columnDTO = JPAUtil.getDto(classe, field);

				if (StringUtil.isNotBlank(columnDTO.getName())) {
					atributo.setColuna(columnDTO.getName());
				} else {
					atributo.setColuna(field.getName());
				}

				atributo.setPk(columnDTO.isPk());

				if (atributo.isPk() && !atributo.getColuna().startsWith("id_")) {
					atributo.setColuna("id_" + atributo.getColuna());
				}

				atributo.setObrigatorio(!columnDTO.isNullable());
				atributo.setPrecisao(columnDTO.getPrecision());
				atributo.setTamanho(columnDTO.getLength());
				atributo.setTipo(field.getType().getSimpleName().toUpperCase());
				atributo.setEntidade(entidade);

				Method getter = ReflectionUtils.getGetter(classe, field);
				if (getter != null) {
					Class tipoGenericoRetorno = ReflectionUtils.getTipoGenericoRetorno(getter);
					if (tipoGenericoRetorno != null) {
						atributo.setTipoClasseGenerica(tipoGenericoRetorno.getName());
						atributo.setTipoClasseGenericaNome(tipoGenericoRetorno.getSimpleName());
					}
				}

				if (atributo.isCollection()) {
					atributo.setColuna(null);
					atributo.setObrigatorio(false);
				}

				entidade.getAtributos().add(atributo);
			}
		}

		return aplicacao;

	}

}
