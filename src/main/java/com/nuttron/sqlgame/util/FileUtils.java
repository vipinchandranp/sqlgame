package com.nuttron.sqlgame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public static String getFileContent(String filename) {
		File fileToRead = new File(filename);
		StringBuilder sb = new StringBuilder();
		try (FileReader fileStream = new FileReader(fileToRead);
				BufferedReader bufferedReader = new BufferedReader(fileStream)) {
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
}
