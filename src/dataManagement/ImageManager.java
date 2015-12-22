package dataManagement;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.imageio.ImageIO;

import entity.AbstractEntity;

public class ImageManager {

	private final HashMap<String, BufferedImage> data;
	private HashMap<String, String> relativNames;

	private final String datadir = "data";
	private final String[] datatypes = { ".png" };
	private final int m_resColor = (98 << 24) + (255 << 8);// 50 transparenzy,
															// but light green

	private BufferedImage imgNotFound;

	public ImageManager() {
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
			relativNames.put(imgname, relativNames.get("NotFound.png"));
			System.out.println("Image not found. Please make sure, that you dont use any Path Information! Image Name was " + imgname);
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

	public BufferedImage getImage(String imgname, int owner) {
		String path = relativNames.get(imgname);
		String colpath = path + "c" + owner;
		BufferedImage img2 = data.get(colpath);

		// System.out.println(owner+" "+colpath);
		if (img2 == null) {
			BufferedImage img = getImage(imgname);//
			if (img == imgNotFound)
				return img;
			try {
				img2 = deepCopy(img);
			} catch(UnsupportedOperationException e){
				return img;
			}
			int color = AbstractEntity.getOwnerColor(owner).getRGB();
			for (int i = 0; i < img2.getHeight(); i++) {
				for (int j = 0; j < img2.getWidth(); j++) {
					if (img.getRGB(j, i) == m_resColor)
						img2.setRGB(j, i, color);// color in teamcolor
				}
			}

			data.put(colpath, img2);

		}
		return img2;
	}

	public BufferedImage getImageAura(String imgname, int owner) {
		String path = relativNames.get(imgname);
		String colpath = path + "aura" + owner;
		BufferedImage img2 = data.get(colpath);

		if (img2 == null) {
			BufferedImage img = getImage(imgname);//
			if (img == imgNotFound)
				return img;
			int color = AbstractEntity.getOwnerColor(owner).getRGB();

			img2 = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			for (int i = 1; i < img2.getHeight() - 1; i++) {
				for (int j = 1; j < img2.getWidth() - 1; j++) {
					int alpha = (img.getRGB(j, i) >> 24) & 0xff;
					if (alpha == 0) {
						img2.setRGB(j, i, 0);
						continue;
					}
					img2.setRGB(j + 1, i + 1, color);
					img2.setRGB(j - 1, i - 1, color);
					img2.setRGB(j + 1, i - 1, color);
					img2.setRGB(j - 1, i + 1, color);

					img2.setRGB(j + 1, i, color);
					img2.setRGB(j, i + 1, color);
					img2.setRGB(j - 1, i, color);
					img2.setRGB(j, i - 1, color);

				}
			}
			for (int i = 0; i < img2.getHeight(); i++) {
				for (int j = 0; j < img2.getWidth(); j++) {
					int alpha = (img.getRGB(j, i) >> 24) & 0xff;
					if (alpha <= 5)// do a bit tollerance
						continue;
					img2.setRGB(j, i, 0);
				}
			}

			data.put(colpath, img2);
		}
		return img2;
	}

	/**
	 * Makes a deep copy of a BufferedImage (return type is
	 * BufferedImage.TYPE_4BYTE_ABGR)
	 * 
	 * @param bi
	 * @return
	 */
	static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, true, null);
	}

	public ArrayList<String> getImages(String inDir) {
		ArrayList<String> imgs = new ArrayList<String>();
		for (HashMap.Entry<String, String> entry : relativNames.entrySet()) {
			if (entry.getValue().contains(inDir)) {
				imgs.add(entry.getKey());
			}
		}
		return imgs;
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
					img = ImageManager.getScaledImage(toscale, width, height);
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

	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
		// int imageWidth = image.getWidth();
		// int imageHeight = image.getHeight();
		//
		// double scaleX = (double) width / imageWidth;
		// double scaleY = (double) height / imageHeight;
		// AffineTransform scaleTransform =
		// AffineTransform.getScaleInstance(scaleX, scaleY);
		// AffineTransformOp bilinearScaleOp = new
		// AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		//
		// return bilinearScaleOp.filter(image, new BufferedImage(width, height,
		// image.getType()));

		BufferedImage target = new BufferedImage(width, height, image.getType());
		java.awt.Graphics gr = target.getGraphics();
		gr.drawImage(image.getScaledInstance(target.getWidth(), target.getHeight(), BufferedImage.SCALE_FAST), 0, 0, null); // converting
																															// Image
																															// ->
																															// BufferedImage
		return target;
	}

	public String getNameFromImage(BufferedImage img) {
		if (img == null) {
			return "null";
		}
		for (int i = 0; i < data.values().size(); i++) {
			if (img.equals(data.values().toArray()[i])) {
				return (String) data.keySet().toArray()[i];
			}
		}
		return "null";
	}

	public void addImage(String key, BufferedImage value) {
		relativNames.put(key, key);
		if (data.containsKey(key)) {
			data.replace(key, value);
		} else {
			data.put(key, value);
		}

	}

}
