package br.com.codersistemas.gem.components;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import br.com.codersistemas.libs.utils.mock.Pessoa;

public class ReplacememntTest {

	@Test
	public void testTche() {
		Replacememnt build = Replacememnt.builder().addClass(Pessoa.class).build();
		Map<String, String> replaces = build.getReplaces();
		for(Entry<String, String> entry : replaces.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(" - ");
			System.out.print(entry.getValue());
			System.out.println("");
		}
	}

}
