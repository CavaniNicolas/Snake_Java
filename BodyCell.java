import java.awt.geom.AffineTransform;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class BodyCell {
	
	private int x;
	private int y;
	private Dir dir;
	private Color color = Color.gray;
	private Image image = null;

	public BodyCell(int x, int y, Dir dir, Color color, Image image) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.color = color;
		this.image = image;
	}

	public BodyCell(int x, int y, Dir dir, Color color) {
		this(x, y, dir, color, null);
	}

	public BodyCell(int x, int y, Dir dir, Image image) {
		this(x, y, dir, Color.gray, image);
	}

	public BodyCell(int x, int y, Dir dir) {
		this(x, y, dir, Color.gray, null);
	}

	public void drawBodyCell(Graphics g) {
		int marge = Platform.marge;
		int size = Platform.boxSize;

		if (this.image != null) {

			// Pour faire pivoter les images en fonction de la direction de cette partie du coprs du serpent
			// Merci a https://stackoverflow.com/questions/8639567/java-rotating-images
			Graphics2D g2d = (Graphics2D)g;
			//Make a backup so that we can reset our graphics object after using it.
			AffineTransform backup = g2d.getTransform();
			//rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
			//is the angle to rotate the image. If you want to rotate around the center of an image,
			//use the image's center x and y coordinates for rx and ry.
			AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians(this.dir.getAngle()), marge + this.x*size + size/2, marge + this.y*size + size/2);
			//Set our Graphics2D object to the transform
			g2d.setTransform(a);
			//Draw our image like normal
			g2d.drawImage(this.image, marge + x * size, marge + y * size, size, size, null);
			//Reset our graphics object so we can draw with it again.
			g2d.setTransform(backup);

		} else {
			g.setColor(this.color);
			g.fillOval(marge + x * size, marge + y * size, size, size);
		}
	}


	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Dir getDir() {
		return dir;
	}

	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}