package org.syndrome.compiler.input;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseFileContainer extends ArrayList<Query> {

	private Project project;

	public BaseFileContainer(Project project) {
		this.project = project;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1205441969804829605L;

	private String outputPath = null;

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public List<String> getPathList() {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < size(); i++) {
			List<String> matches = get(i).getMatches();
			for (int k = 0; k < matches.size(); k++) {
				if (list.contains(matches.get(k)))
					continue;
				list.add(matches.get(k));
			}
		}
		return list;
	}

	public String getOutputPath(Path cwd) {
		if (outputPath == null)
			outputPath = generateName();

		Path path = Paths.get(outputPath);

		if (!path.isAbsolute())
			path = Paths.get(cwd.toString(), outputPath);

		return (outputPath = path.toAbsolutePath().toString());
	}

	private String generateName() {
		return String.format("%s-%s.min.%s", project.getName(), project.getVersion(), getExtension());
	}

	protected abstract String getExtension();
}
