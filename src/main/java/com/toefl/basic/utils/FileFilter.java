package com.toefl.basic.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		File file = new File(name);
		String fileName = file.getName();
		return fileName.indexOf(type) != -1;
	}

	private String type;

	public FileFilter(String type) {
		this.type = type;
	}
}
