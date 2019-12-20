package br.com.codersistemas.gem.components;

import java.lang.reflect.Field;

import br.com.codersistemas.gem.util.TypeScriptUtils;
import br.com.codersistemas.libs.utils.ReflectionUtils;

public class TSClass extends Component {

	public TSClass(Object obj) {
		System.out.println("export class " + obj.getClass().getSimpleName() + " {");
		Field[] fields = ReflectionUtils.getFields(obj);
		for (Field field : fields) {
			System.out.println("\t"+field.getName() + ": " + TypeScriptUtils.toTypeScript(field.getType()) + ";");
		}
		System.out.println("}");
	}

	@Override
	public String print() {
		return null;
	}

}

