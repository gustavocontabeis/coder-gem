package br.com.codersistemas.gem.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import br.com.codersistemas.gem.util.TypeScriptUtils;

public class TypeScriptUtilsTest {

	@Test
	public void testToTypeScript() {
		
		assertTrue("boolean".equals(TypeScriptUtils.toTypeScript(Boolean.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Double.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(Enum.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Float.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Integer.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Long.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Number.class)));
		assertTrue("number".equals(TypeScriptUtils.toTypeScript(Short.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(String.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(Date.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(ArrayList.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(Enumeration.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(HashMap.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(HashSet.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(List.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(Set.class)));
		assertTrue("any[]".equals(TypeScriptUtils.toTypeScript(Map.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(java.time.LocalDate.class)));
		assertTrue("string".equals(TypeScriptUtils.toTypeScript(java.time.LocalDateTime.class)));
		
	}

}
