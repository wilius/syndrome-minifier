package org.syndrome.compiler.input;

import java.io.File;
import java.nio.file.Path;

import org.syndrome.compiler.exception.InvalidConfigDefinationException;
import org.syndrome.compiler.exception.RootNotFound;
import org.syndrome.compiler.util.UrlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigParser {

	private Projects projects;
	private Document document;
	private Path cwd;

	private ConfigParser(Path cwd, Document doc) throws InvalidConfigDefinationException, RootNotFound {
		projects = new Projects();
		document = doc;
		this.cwd = cwd;
		parseProjects();
	}

	public static Projects parseFromFile(Path cwd, Document doc) throws InvalidConfigDefinationException, RootNotFound {
		return new ConfigParser(cwd, doc).getResult();
	}

	private Projects getResult() {
		return projects;
	}

	private void parseProjects() throws InvalidConfigDefinationException, RootNotFound {
		String nodeName = document.getDocumentElement().getNodeName();
		if (!"projects".equalsIgnoreCase(nodeName))
			throw new InvalidConfigDefinationException(
					String.format("projects tag must be root element of the document but found %s", nodeName));
		NodeList childs = document.getDocumentElement().getChildNodes();
		Node item;
		for (int i = 0; i < childs.getLength(); i++) {
			item = childs.item(i);
			if (item instanceof Element)
				projects.add(parseProject(item));
		}
	}

	private Project parseProject(Node item) throws InvalidConfigDefinationException, RootNotFound {
		String nodeName = item.getNodeName();
		if (!"project".equalsIgnoreCase(nodeName))
			throw new InvalidConfigDefinationException(
					String.format("All of the children of projects tag have to project tag but found %s", nodeName));

		NodeList childs = item.getChildNodes();
		Node node;
		Project project = new Project();
		for (int i = 0; i < childs.getLength(); i++) {
			node = childs.item(i);
			switch (node.getNodeName().toLowerCase()) {
			case "name":
				project.setName(node.getTextContent());
				break;
			case "version":
				project.setVersion(node.getTextContent());
				break;
			case "build":
				try {
					project.setBuildNumber(Integer.parseInt(node.getTextContent()));
				} catch (Exception e) {
					System.err.println(String.format("Build number '%s' is invalid and reseted by the system",
							node.getTextContent()));
				}
				break;
			case "script":
				project.setScript((Script) parseFileContainer(node, new Script(project)));
				break;
			case "style":
				project.setStyle((Style) parseFileContainer(node, new Style(project)));
				break;
			}
		}

		return project;

	}

	private BaseFileContainer parseFileContainer(Node item, BaseFileContainer fileContainer)
			throws InvalidConfigDefinationException, RootNotFound {
		NodeList children = item.getChildNodes();
		Node child;
		for (int i = 0; i < children.getLength(); i++) {
			child = children.item(i);
			if (child instanceof Element)
				fileContainer.add(parseQuery(child));
		}

		return fileContainer;
	}

	private Query parseQuery(Node item) throws InvalidConfigDefinationException, RootNotFound {
		String nodeName = item.getNodeName();
		String url = cwd.toString();
		
		if ("file".equalsIgnoreCase(nodeName)) 
			return new CFile(new File(url + File.separator + UrlUtil.trim(item.getTextContent())));

		if (!"query".equalsIgnoreCase(nodeName))
			throw new InvalidConfigDefinationException(String.format("Invalid tag for filecontainer '%s'", nodeName));

		Node matchNode = item.getAttributes().getNamedItem("match");
		if (matchNode == null)
			throw new InvalidConfigDefinationException("Match attribute of query tag not found");

		Node rootNode = item.getAttributes().getNamedItem("root");
		if (rootNode != null)
			url += File.separator + UrlUtil.trim(rootNode.getTextContent());

		return new Query(new File(url), matchNode.getTextContent());
	}
}
