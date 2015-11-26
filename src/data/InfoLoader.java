package data;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class InfoLoader {
	private final HashMap<String, HashMap<String, String>> data;
	private HashMap<String, String> relativNames;

	private final String datadir = "data";
	private final String[] datatypes = { ".inf" };

	public InfoLoader() {
		data = new HashMap<String, HashMap<String, String>>();

		loadRelativNames();
	}

	public HashMap<String, String> getInfo(String imgname) {
		String path = relativNames.get(imgname);

		if (path == null) { // Info Name not found in datadir
			return null;
		}

		HashMap<String, String> info = data.get(path);

		if (info == null) { // Info not loaded until now.
			info = parseInfo(path);

			if (info == null) { // Loading failed
				System.out.println("Loading the Info failed!");
				info = null;
			}
			data.put(path, info); // Add Info to data HashMap
		}

		return info;
	}

	private HashMap<String, String> parseInfo(String path) {
		HashMap<String, String> info = new HashMap<String, String>();
		File f = new File(path);
		
		try (BufferedReader br = new BufferedReader(new FileReader(f))){
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.length() > 0 && Character.isAlphabetic(line.charAt(0))) {
					String parts[] = line.split("=", 2);
					info.put(parts[0], parts[1]);
				} else if (line.replace("\n", "").replace(" ", "").length() == 0) {
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return info;
	}

	private void loadRelativNames() {
		relativNames = new HashMap<String, String>();
		Stack<File> dirs = new Stack<File>();
		File nextfile = null;

		dirs.add(new File(datadir));

		while (!dirs.empty()) {
			nextfile = dirs.pop();
			for (File file : nextfile.listFiles()) {
				if (file.isDirectory()) { // Adding dirs to search
					if (!file.getName().endsWith(".git")) {
						dirs.push(file);
					}
				} else { // is File
					String filename = file.getName();

					for (String typ : datatypes) { // File is an image?

						if (filename.endsWith(typ)) {
							relativNames.put(filename, file.getAbsolutePath()); // remember
																				// File
																				// name
							break;
						}
					}
				}
			}
		}
	}
}
