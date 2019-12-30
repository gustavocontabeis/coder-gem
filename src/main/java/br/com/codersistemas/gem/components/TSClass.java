package br.com.codersistemas.gem.components;

import java.lang.reflect.Field;

import br.com.codersistemas.gem.util.TypeScriptUtils;
import br.com.codersistemas.libs.utils.ReflectionUtils;

public class TSClass extends Component {
	
	StringBuilder sb = new StringBuilder();

	public TSClass(Object obj) {
		super(null);
		sb.append("export class " + obj.getClass().getSimpleName() + " {\n");
		Field[] fields = ReflectionUtils.getFields(obj);
		for (Field field : fields) {
			sb.append("\t"+field.getName() + ": " + TypeScriptUtils.toTypeScript(field.getType()) + ";\n");
		}
		sb.append("}\n");
	}

	@Override
	public String print() {
		return sb.toString();
	}

	@Override
	protected String getResourceName() {
		// TODO Auto-generated method stub
		return null;
	}

}

