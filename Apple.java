
import java.awt.Color;
import java.awt.Image;

public class Apple extends Fruit {

	/**Constructeur : Coordonnees + couleur unie */
	public Apple(int x, int y, Color color) {
		super(x, y, color);
	}

	/**Constructeur : Coordonnees + image, avec une couleur par default rouge pour la pomme */
	public Apple(int x, int y, Image image) {
		super(x, y, Color.red, image);
	}
}