package br.com.codersistemas.gem.components.be;

import br.com.codersistemas.gem.components.IComponent;

public class HQLComponent implements IComponent {
	
	private static final String INNER_JOIN = "\\w*.? \\w*.? = \\w*.?\\.\\w*.?";
	private static final String FROM = "\\w*.? \\w*.? = new \\w*.?\\(\\);";
	
	private String code; 

	public HQLComponent(String code) {
		super();
		this.code = code;
	}

	@Override
	public String print() {
		
		StringBuilder sb = new StringBuilder();
		
    	String[] splitN = code.split("\n");
    	
    	String from = "";
    	
    	for (String line : splitN) {
    		line = normalizeLine(line);
        	if(line.matches(FROM)){
        		String[] split = line.split("\\s");
        		from = split[1];
				sb.append(String.format("from %s %s\n", split[0], split[1]));
        	} else if(line.matches(INNER_JOIN)){
        		String[] split = line.split("\\s");
				sb.append(String.format("inner join %s.%s %s\n", from, split[1], split[1]));
        	}
		}
		return sb.toString();
	}

	private String normalizeLine(String line) {
		line = line.replaceAll("=", " = ");
		line = line.replaceAll("\\.", " . ");
		while(line.contains("  "))
			line = line.replace("  ", " ");
		while(line.contains(" . "))
			line = line.replace(" . ", ".");
		line = line.trim();
		return line;
	}

}
