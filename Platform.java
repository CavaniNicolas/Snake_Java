import java.awt.Color;
import java.awt.Image;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Platform extends JPanel {
	private static final long serialVersionUID = 2L;
	
	private boolean isGraphicInitDone = false;

	public static int boxSize; // Valeur graphique, longueur d'un cote d'une case du plateau
	public static int gameSize; // Nombre de cases par cote sur le plateau (valeur reelle)

	public static int marge; // Marge graphique entre le plateau et le bord de la fenetre
	private int width; // largeur du JPanel
	private int height; // hauteur du JPanel

	private Snake snake = new Snake(); // Le serpent
	private ArrayList<Fruit> apples = new ArrayList<Fruit>(); // La liste des fruits
	private BufferedImage appleImage = null; // Image de la pomme

	private int nbApples = 3; // Nombre de pommes max a afficher a l'ecran

	private boolean allowInput;
	private Dir currentDir = Dir.Left;

	private int speed = 150;

	public Platform(int newGameSize) {
		gameSize = newGameSize;
	}

	public Platform() {
		this(12);
	}

	/**Fonction d'affichage principale */
	public void paintComponent(Graphics g) {
		initGraphicFields();

		g.setColor(Color.yellow);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.black);
		drawGrid(g);

		g.setColor(Color.red);
		g.drawRect(marge, marge, this.width, this.height);

		displayText(g);

		drawFruits(g);
		snake.drawSnake(g);
	}

	public void displayText(Graphics g) {
		g.setFont(new Font("Tahoma", Font.BOLD, 24));
		g.setColor(Color.blue);

		// Les points
		g.drawString(String.valueOf(snake.getLength()), marge/2, 3*marge/4);

		// La defaite
		if (SnakeGame.playing == false) {
			g.drawString("Perdu", 2*marge, 3*marge/4);
		}
	}


	/**Dessine une grille sur le plateau pour visualiser chaque case */
	public void drawGrid(Graphics g) {
		for (int i=0; i<gameSize; i++) {
			for (int j=0; j<gameSize; j++) {
				g.drawRect(marge + i * boxSize, marge + j * boxSize, boxSize, boxSize);
			}
		}
	}

	/**Dessine les fruits */
	public void drawFruits(Graphics g) {
		for (int i=0; i<apples.size(); i++) {
			apples.get(i).drawFruit(g);
		}
	}

	/**Initialise les valeurs de marge width et height (du JPanel) lors du premier appel de paintComponent() */
	public void initGraphicFields() {
		// On fait les calculs des dimensions graphiques que 1 seule fois
		if (!isGraphicInitDone) {
			// On garde le plus petit des deux
			if (this.getWidth() < this.getHeight()) {
				this.width = this.getWidth();
				this.height = this.width;
			} else {
				this.width = this.getHeight();
				this.height = this.width;
			}
			// marge de 6.6% de l'ecran
			marge = this.width / 15;
			this.width = this.width - 2 * marge;
			this.height = this.height - 2 * marge;

			// Taille de chaque carre sur le plateau
			boxSize = this.width / gameSize;

			// Pour que le plateau fasse exactement la taille des tous les carres quon peut y afficher
			this.width = boxSize * gameSize;
			this.height = this.width;

			//System.out.println(marge + " " + width + " " + height);
			this.isGraphicInitDone = true;
		}
	}

	/**Creer les pommes pour remplir la ArrayList jusqu'au nbApples */
	public void createApples() {

		int i=0;
		BodyCell bodyCell;
		Fruit fruit;

		int x;
		int y;

		boolean isPlaced;
		
		while (apples.size() < nbApples) {
			isPlaced = true;

			x = (int)(Math.random() * (double)gameSize);
			y = (int)(Math.random() * (double)gameSize);

			// On verifie si on ne place pas la nouvelle pomme sur le serpent
			while (isPlaced == true && i<snake.getLength()) {
				bodyCell = snake.getBody().get(i);
				if (x == bodyCell.getX() || y == bodyCell.getY()) {
					isPlaced = false;
				}
				i++;
			}

			// On verifie si on ne place pas la nouvelle pomme sur une autre pomme
			i = 0;
			while (isPlaced == true && i<apples.size()) {
				fruit = apples.get(i);
				if (x == fruit.getX() || y == fruit.getY()) {
					isPlaced = false;
				}
				i++;
			}

			if (isPlaced) {
				apples.add(new Apple(x, y, appleImage));
				//apples.add(new Apple(x, y, Color.red));
			}
		}
	}

	/**Charge toutes les images une fois dans la memoire */
	public void loadAllImages() {
		try {
			appleImage = ImageIO.read(new File("assets/apple.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			snake.setHeadIm(ImageIO.read(new File("assets/snakeHead.png")));
			snake.setBodyIm(ImageIO.read(new File("assets/snakeStraightBody.png")));
			snake.setTailIm(ImageIO.read(new File("assets/snakeTail.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void moveSnake() {
		snake.moveSnake(this.currentDir);
	}

	
	public void checkCollisions() {
		int i = 0;
		BodyCell snakeHead = snake.getHead();
		
		BodyCell bodyCell;
		Fruit f;
		// Check les collision avec les pommes
		for (i=0; i<apples.size(); i++) {
			f = apples.get(i);
			if (snakeHead.getX() == f.getX() && snakeHead.getY() == f.getY()) {
				snake.setGrow(true);
				apples.remove(i);
			}
		}

		// Check les collisions avec le serpent
		i = 1;
		while (i < snake.getBody().size()) {
			bodyCell = snake.getBody().get(i);
			if (snakeHead.getX() == bodyCell.getX() && snakeHead.getY() == bodyCell.getY()) {
				SnakeGame.playing = false;
			}
			i++;
		}

		// Check les collisions avec les murs
		if (snakeHead.getX() < 0 || snakeHead.getX() > gameSize-1
		|| snakeHead.getY() < 0 || snakeHead.getY() > gameSize-1) {
			SnakeGame.playing = false;
		}
	}

	public void updateSpeed() {
		if (snake.getLength() > 10) {
			this.speed = 130;
		}
		if (snake.getLength() > 20) {
			this.speed = 110;
		}
	}

	public int getSpeed() {
		return this.speed;
	}

	public boolean isInputAllowed() {
		return this.allowInput;
	}

	public void setAllowInput(boolean bool) {
		this.allowInput = bool;
	}

	public Snake getSnake() {
		return this.snake;
	}

	public Dir getCurrentDir() {
		return currentDir;
	}

	public void setCurrentDir(Dir currentDir) {
		this.currentDir = currentDir;
	}

}