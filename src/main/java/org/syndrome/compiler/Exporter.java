package org.syndrome.compiler;

import java.io.IOException;
import java.nio.file.Path;

import org.syndrome.compiler.input.Project;
import org.syndrome.compiler.minifier.CSSMinifier;
import org.syndrome.compiler.minifier.JSMinifier;

public class Exporter {
	public static void export(Path cwd, Project project) throws IOException {
		System.out.println("-------");
		System.out.println(String.format("Project '%s' is compiling", project.getName()));

		JSMinifier.minify(cwd, project);
		CSSMinifier.minify(cwd, project);
		System.out.println(String.format("Compile of the project '%s' completed", project.getName()));
		System.out.println("-------");
	}
}
