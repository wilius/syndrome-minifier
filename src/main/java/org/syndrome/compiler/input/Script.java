package org.syndrome.compiler.input;

public class Script extends BaseFileContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8332985888099301842L;

	public Script(Project project) {
		super(project);
	}

	@Override
	protected String getExtension() {
		return "js";
	}

}
