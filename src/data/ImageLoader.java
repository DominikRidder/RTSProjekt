package data;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;

public class ImageLoader {

	private HashMap<String, BufferedImage> data;
	private HashMap<String, String> relativNames;

	private String datadir = "data";
	private String[] datatypes = { ".png" };
	
	private BufferedImage imgNotFound;

	
	
	public ImageLoader() {
		data = new HashMap<String, BufferedImage>();
		
		loadRelativNames();
		
		try {
			imgNotFound = ImageIO.read(new File(relativNames.get("NotFound.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(String imgname) {
		String path = relativNames.get(imgname);

		if (path == null) { // Image Name not found in datadir
			System.out
					.println("Image not found. Please make sure, that you dont use any Path Information!");
			data.put(path, imgNotFound);
			return imgNotFound;
		}

		BufferedImage img = data.get(path);

		if (img == null) { // Image not loaded until now.
			try {
				img = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (img == null) { // Loading failed
				System.out.println("Loading the Image failed!");
				img = imgNotFound;
			} else {
				data.put(path, img); // Add Image to data HashMap
			}
		}

		return img;
	}

	public BufferedImage getImage(String imgname, int width, int height) {
		StringBuilder keybuild = new StringBuilder(imgname); // attemp to make
																// it fast
		keybuild.append(";");
		keybuild.append(width);
		keybuild.append(";");
		keybuild.append(height);

		String key = keybuild.toString();
		BufferedImage img = data.get(key);

		if (img == null) {
			BufferedImage toscale = getImage(imgname);

			if (toscale == null) {
				data.put(key, imgNotFound);
				return imgNotFound;
			}

			if (toscale.getWidth() != width || toscale.getHeight() != height) {
				try {
					img = ImageLoader.getScaledImage(toscale, width, height);
				} catch (IOException e) {
					e.printStackTrace();

					img = imgNotFound;
				}

				data.put(key, img);

			} else {
				img = toscale;
			}
		}

		return img;
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

	public static BufferedImage getScaledImage(BufferedImage image, int width,
			int height) throws IOException {
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(
				scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(
				scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		return bilinearScaleOp.filter(image, new BufferedImage(width, height,
				image.getType()));
	}

}
