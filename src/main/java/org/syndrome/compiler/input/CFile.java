package org.syndrome.compiler.input;

import java.io.File;

import org.syndrome.compiler.exception.RootNotFound;

public class CFile extends Query {

	public CFile(File file) throws RootNotFound {
		super(file.getParentFile(), file.getName());
		setRecursive(false);
	}

}
