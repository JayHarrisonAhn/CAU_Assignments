package kr.ac.cau.mecs.lenerd.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * ImagePanel is JPanel variant for displaying BufferedImage
 * <p>
 * to maintain alpha channel in image, ImagePanel is transparent, by default.
 * 
 * @see JPanel
 * @see BufferedImage
 * @author LeNerd
 * @since 2018-05-23
 */
public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1241885397633460571L;

	BufferedImage image;

	public ImagePanel() {
		setOpaque(false);
	}

	/**
	 * set Image to draw
	 * 
	 * @param image
	 *            image to set.
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * get current Image
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage getImage() {
		return image;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		/*
		 * inform render to use bilinear interpolation
		 */
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		if (image != null) {
			/*
			 * if image is set, draw to fill.
			 * with aspect ratio
			 */
			float wr = (float)getWidth() / (float)image.getWidth();
			float hr = (float)getHeight() / (float)image.getHeight();
			
			float r = Math.min(wr, hr);

			int w = (int)(image.getWidth()*r);
			int h = (int)(image.getHeight()*r);
			g.drawImage(image, (getWidth()-w)/2,(getHeight()-h)/2,w,h, this);
		}
	}
}
