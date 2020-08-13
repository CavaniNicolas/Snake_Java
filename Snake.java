import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Snake {

	private ArrayList<BodyCell> body = new ArrayList<BodyCell>();
	private int length; // Taille du serpent

	private boolean grow = false; // Estce que le serpent doit grossir ou non

	private BufferedImage headIm = null;
	private BufferedImage bodyIm = null;
	private BufferedImage tailIm = null;

	public Snake() {

	}

	public void initSnake(int gameSize) {
		int middle = gameSize / 2;
		
		if (headIm != null && bodyIm != null) {
			body.add(new BodyCell(middle, middle, Dir.Left, headIm));
			body.add(new BodyCell(middle+1, middle, Dir.Left, bodyIm));
			body.add(new BodyCell(middle+2, middle, Dir.Left, tailIm));
		} else {
			// Sans les images
			body.add(new BodyCell(middle, middle, Dir.Left, Color.green));
			body.add(new BodyCell(middle+1, middle, Dir.Left));
			body.add(new BodyCell(middle+2, middle, Dir.Left));
		}
		this.length = 3;
	}


	/**Dessine le serpent */
	public void drawSnake(Graphics g) {
		for (int i=0; i<body.size(); i++) {
			body.get(i).drawBodyCell(g);
		}
	}


	/**Actualise les images (par rapport aux directions) du corps du serpent
	 * Le dernier element doit etre la queue
	 */
	public void updateBodyImages() {
		body.get(1).setDir(body.get(0).getDir());
		body.get(1).setImage(this.bodyIm);

		body.get(body.size()-1).setImage(this.tailIm);
	}


	public void moveSnake(Dir currentDir) {

		if (this.grow == false) {
			// Si on ne mange pas de pomme, on grandit de la tete et on retrecit de la queue (donc on avance)
			body.remove(body.size()-1);
		} else {
			this.length ++;
		}
		
		// Si on mange une pomme on grandit juste de la tete
		int x = body.get(0).getX();
		int y = body.get(0).getY();
		
		if (currentDir == Dir.Up) y--;
		if (currentDir == Dir.Down) y++;
		if (currentDir == Dir.Left) x--;
		if (currentDir == Dir.Right) x++;
		
		body.add(0, new BodyCell(x, y, currentDir, Color.green, this.headIm));
		
		// Il ne faut grossir qu'une fois par pommes mangees !
		this.grow = false;

		updateBodyImages();
	}



	public BodyCell getHead() {
		return this.body.get(0);
	}

	public int getLength() {
		return this.length;
	}

	public ArrayList<BodyCell> getBody() {
		return body;
	}

	public BufferedImage getHeadIm() {
		return headIm;
	}

	public void setHeadIm(BufferedImage headIm) {
		this.headIm = headIm;
	}

	public BufferedImage getBodyIm() {
		return bodyIm;
	}

	public void setBodyIm(BufferedImage bodyIm) {
		this.bodyIm = bodyIm;
	}

	public BufferedImage getTailIm() {
		return bodyIm;
	}

	public void setTailIm(BufferedImage tailIm) {
		this.tailIm = tailIm;
	}

	public boolean willGrow() {
		return grow;
	}

	public void setGrow(boolean grow) {
		this.grow = grow;
	}

}