
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

public class Fruit {
	
	private int x;
	private int y;
	private Color color;
	private Image image;

	public Fruit(int x, int y, Color color, Image image) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.image = image;
	}

	public Fruit(int x, int y, Color color) {
		this(x, y, color, null);
	}

	public Fruit(int x, int y, Image image) {
		this(x, y, Color.red, image);
	}

	public Fruit(int x, int y) {
		this(x, y, Color.red, null);
	}

	public void drawFruit(Graphics g) {
		int marge = Platform.marge;
		int size = Platform.boxSize;

		if (this.image != null) {
			g.drawImage(this.image, marge + x * size, marge + y * size, size, size, null);
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