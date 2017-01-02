/**
 * 
 */
package com.jsn.filesystem;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.io.FileUtils;

/**
 * @author Surendranath Reddy
 *
 */
public class LogMonitor {

	private static String directoryPath = "/home/abzooba/work/workspace/equianpnc/logs-copy";
	private static File logDirectory = new File(directoryPath);
	private static final long MAX_LOG_FOLDER_SIZE = 1000000000; //1GB

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File[] logFiles = logDirectory.listFiles();

		Arrays.sort(logFiles, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
			}
		});

		System.out.println("Number of Files : " + logFiles.length);

		long initFolderSize = FileUtils.sizeOfDirectory(logDirectory);

		System.out.println("Log folder size : " + initFolderSize + " bytes");

		deleteFiles(logFiles);

		System.out.println("Number of Files final : " + logDirectory.listFiles().length);
		System.out.println("Log folder size : " + FileUtils.sizeOfDirectory(logDirectory) + " bytes");
	}

	private static void deleteFiles(File[] logFiles) {
		for (int i = 0; i < logFiles.length; ++i) {
			if (checkDirSize()) {
				System.out.println("Folder size lower limit reached. Stopping deleting!!!");
				break;
			}
			File logFile = logFiles[i];
			boolean isDeleted = logFile.delete();
			System.out.println("Name : " + logFile.getName() + "\tSize : " + logFile.length() + " bytes" + ((isDeleted) ? " is Deleted" : " is Not Deleted"));

		}
	}

	private static boolean checkDirSize() {
		if (FileUtils.sizeOfDirectory(logDirectory) < MAX_LOG_FOLDER_SIZE) {
			return true;
		} else {
			return false;
		}
	}

}
