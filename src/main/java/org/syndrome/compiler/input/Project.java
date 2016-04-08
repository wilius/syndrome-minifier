package org.syndrome.compiler.input;

import java.util.Date;

public class Project extends Base {

	private String name;
	private Script script;
	private Style style;
	private String version;
	private int buildNumber = 0;
	private Date buildDate;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the buildNumber
	 */
	public int getBuildNumber() {
		return buildNumber;
	}

	/**
	 * @return the buildDate
	 */
	public Date getBuildDate() {
		return buildDate;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * @param script
	 *            the script to set
	 */
	void setScript(Script script) {
		this.script = script;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	void setStyle(Style style) {
		this.style = style;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	void setVersion(String version) {
		this.version = version.matches("^(v|V)") ? version : "v" + version;
	}

	/**
	 * @param buildNumber
	 *            the buildNumber to set
	 */
	void setBuildNumber(int buildNumber) {
		this.buildNumber = buildNumber;
	}

	public boolean haveScript() {
		return script != null;
	}

	public boolean haveStyle() {
		return style != null;
	}

	public int getIncreasedBuildNumber() {
		buildNumber++;
		return buildNumber;
	}

	/**
	 * @return the script
	 */
	public Script getScript() {
		return script;
	}

	/**
	 * @return the style
	 */
	public Style getStyle() {
		return style;
	}
}
