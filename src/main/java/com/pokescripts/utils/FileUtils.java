package com.pokescripts.utils;

import java.io.*;

public class FileUtils {

    public static String readFileFromFileSystem (String path) throws Exception {
        File file = new File(path);
		System.out.println("Reading file: "+file.getAbsolutePath());
		return getStringFromInputStream(new FileInputStream(file));
    }

	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is, "UTF8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

    public static void writeStringToFile(String content, String filePath){
		BufferedWriter writer = null;
		try {
			File file = new File(filePath);
			System.out.println("Writing content to file: "+file.getAbsolutePath());
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Close the writer regardless of what happens...
				writer.close();
			} catch (Exception e) {
			}
		}
	}

}
