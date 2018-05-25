package br.com.test.ilegra.schedule.service;

import java.io.File;
import java.io.FileFilter;

import br.com.test.ilegra.constants.Constants;

public class FilesProcessService {

	private static File files[];

	/**
	 * Start the process of .dat files
	 */
	public static void doProcess() {
		readFiles();

		if (files != null && files.length > 0) {
			processFiles();
		}
	}

	/**
	 * read available files in the directory
	 */
	private static void readFiles() {
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(Constants.FILE_EXTENSION);
			}
		};
		
		File dir = new File(Constants.MAIN_PATH + Constants.PATH_IN);
		files = dir.listFiles(filter);
	}

	/**
	 * 	process content of files
	 */
	private static void processFiles() {
		for (File file : files) {
			
		}
	}

}
