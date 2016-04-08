package org.syndrome.compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.syndrome.compiler.exception.InvalidConfigDefinationException;
import org.syndrome.compiler.exception.RootNotFound;
import org.syndrome.compiler.input.ConfigParser;
import org.syndrome.compiler.input.Projects;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App {

	private final static String CONFIG_FILE = "syndrome.xml";
	private final static String SEPARATOR = File.separator;

	public static void main(String args[]) throws ParserConfigurationException, InvalidConfigDefinationException,
			SAXException, IOException, RootNotFound {
		System.out.println("Compiler v1.0.1");
		
		Path projectPath = Paths.get(args.length < 1 ? new File(".").getCanonicalPath() : args[0]).toAbsolutePath();

		System.out.println(String.format("Path '%s' selected as default path", projectPath));
		
		File file = new File(projectPath.toString());
		if (!file.exists())
			exit(String.format("Project path %s not found", file.getAbsolutePath()));

		String xmlFileName = String.format("%s%s%s", projectPath, SEPARATOR, CONFIG_FILE);
		file = new File(xmlFileName);
		if (!file.exists())
			exit(String.format("'%s' config file cannot found in path '%s' ", CONFIG_FILE, projectPath));

		System.out.println(String.format("Reading project file '%s'", xmlFileName));
		DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Projects projects = ConfigParser.parseFromFile(projectPath, dBuilder.parse(xmlFileName));

		for (int i = 0; i < projects.size(); i++)
			Exporter.export(projectPath, projects.get(i));
	}

	protected static void exit(String message) {
		System.err.println(message);
		System.exit(0);
	}

}
