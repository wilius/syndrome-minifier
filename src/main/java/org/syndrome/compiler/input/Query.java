package org.syndrome.compiler.input;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.syndrome.compiler.exception.RootNotFound;

public class Query {

	private File root;
	private String match;
	private boolean isRecursive = true;

	public Query(File root, String match) throws RootNotFound {
		if (!root.exists())
			throw new RootNotFound(String.format("Root '%s' not found in project path", root.getAbsolutePath()));
		
		if (root.isFile())
			root = root.getParentFile();

		this.root = root;
		this.match = match;
		parseSpecialQueries();
	}

	private void parseSpecialQueries() {
		if (match.startsWith("*"))
			match = ".+" + Pattern.quote(match.substring(1));
	}

	public List<String> getMatches() {
		List<String> result = new ArrayList<String>();
		fillList(root, result);
		return result;
	}

	private void fillList(File root, List<String> result) {
		File[] files = root.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				if (files[i].getName().matches(match))
					result.add(files[i].getAbsolutePath());
			} else if (isRecursive) {
				fillList(files[i], result);
			}
		}
	}

	/**
	 * @param isRecursive
	 *            the isRecursive to set
	 */
	void setRecursive(boolean isRecursive) {
		this.isRecursive = isRecursive;
	}
}
