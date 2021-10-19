package br.com.codersistemas.libs.utils.mock.dto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class Resource extends Component {

	public Resource() {
		super();
		content = read(getResourceName());
	}

	protected abstract String getResourceName();

	@Override
	protected String print() {
		return content;
	}

	private String read(String resource) {
		StringBuilder sb = new StringBuilder();
		try {
			List<String> readAllLines = Files.readAllLines(Paths.get(this.getClass().getResource(getResourceName()).toURI()), Charset.defaultCharset());
			for (String line : readAllLines) 
				sb.append(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
