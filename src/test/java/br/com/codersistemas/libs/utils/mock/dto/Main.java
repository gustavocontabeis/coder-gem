package br.com.codersistemas.libs.utils.mock.dto;

import br.com.codersistemas.libs.dto.AplicacaoDTO;
import br.com.codersistemas.libs.dto.EntidadeDTO;

public class Main {
	public static void main(String[] args) {
		
		AplicacaoDTO aplicacaoDTO = new AplicacaoDTO("", br.com.codersistemas.codergemapi.domain.Pessoa.class);
		EntidadeDTO entidade = aplicacaoDTO.getEntidades().iterator().next();
		
//		Component entity = new BEEntity(classe);
//		System.out.println(entity.print());
		
		Component respository = new BERepository(entidade);
		System.out.println(respository.print());
		
//		Component service = new BEService(classe);
//		System.out.println(respository.print());
		

	}
}
