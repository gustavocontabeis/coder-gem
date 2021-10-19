package br.com.codersistemas.libs.utils.mock.dto;

import br.com.codersistemas.libs.dto.EntidadeDTO;
import br.com.codersistemas.libs.utils.StringUtil;

public class BERepository extends Resource {
	
	private EntidadeDTO entidade;

	public BERepository(EntidadeDTO entidade) {
		this.entidade = entidade;
//		replace("Contratos", StringUtil.caplitalizePlural(entidade.getNomeClasse()));
//		replace("contratos", StringUtil.uncaplitalizePlural(entidade.getNomeClasse()));
//		replace("Contrato", StringUtil.caplitalizeSingular(entidade.getNomeClasse()));
//		replace("contrato", StringUtil.uncaplitalizeSingular(entidade.getNomeClasse()));
	}

	@Override
	protected String getResourceName() {
		return "BERepository.txt";
	}

}
