package org.syndrome.compiler.minifier;

import java.nio.file.Path;
import java.util.List;

import org.syndrome.compiler.input.Project;

import com.google.javascript.jscomp.AbstractCommandLineRunner;
import com.google.javascript.jscomp.CommandLineRunner;

public class JSMinifier extends CommandLineRunner {

	private JSMinifier(String[] args) {
		super(args);
	}

	protected int compile() {
		int result = 0;
		int runs = 1;
		try {
			for (int i = 0; i < runs && result == 0; i++) {
				result = doRun();
			}
		} catch (AbstractCommandLineRunner.FlagUsageException e) {
			System.err.println(e.getMessage());
			result = -1;
		} catch (Throwable t) {
			t.printStackTrace();
			result = -2;
		}
		return result;
	}

	public static boolean minify(Path cwd, Project project) {
		List<String> arguments = project.getScript().getPathList();
		if (arguments.size() == 0)
			return false;

		// StringBuilder builder = new StringBuilder();
		System.out.println(String.format("Javascript file list as below for project %s", project.getName()));
		for (int i = 0, size = arguments.size(); i < size; i++) {
			String file = arguments.get(i * 2);
			arguments.add(i * 2, "--js");
			System.out.println(file);
		}

		String path = project.getScript().getOutputPath(cwd);
		arguments.add(0, "--js_output_file=" + path);
		arguments.add("--language_out=ES5");

		System.out.println(String.format("\nOutput file for project %s is '%s'", project.getName(), path));
		System.out.println("JS compiling is in progress");
		System.out.println("Wait for while compiling your scripts");
		boolean res = new JSMinifier(arguments.toArray(new String[arguments.size()])).compile() >= 0;
		System.out.println(String.format("Compile of javascripts of the project %s completed", project.getName()));
		return res;
	}

}
