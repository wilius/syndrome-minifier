package org.syndrome.compiler.input;

public class Style extends BaseFileContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8859533261644914658L;

	public Style(Project project) {
		super(project);
	}

	@Override
	protected String getExtension() {
		return "css";
	}

}
