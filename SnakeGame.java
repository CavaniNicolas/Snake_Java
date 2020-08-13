import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SnakeGame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;

	private Platform platform = new Platform();
	public static boolean playing = true;

	public SnakeGame() {
		this.setTitle("Snake Game");
		this.setSize(750, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false); // mais l'affichage fonctionne quand meme et s'adapte si on redimmensionne :P

		this.setContentPane(platform);
		this.addKeyListener(this);

		this.setVisible(true);

		initGame();

		repaint();

		startGame();
	}

	public void initGame() {
		platform.loadAllImages();

		platform.getSnake().initSnake(Platform.gameSize);
		platform.createApples();
	}

	public void startGame() {
		while (playing) {
			platform.createApples();
			repaint();

			platform.updateSpeed();
			sleep(platform.getSpeed());
			// sleep(150);

			platform.moveSnake();
			platform.checkCollisions();
			platform.setAllowInput(true);
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyChar();
		// System.out.print("Code clavier "+ code + "\n ");

		if (platform.isInputAllowed()) {

			if (code == 122 && platform.getCurrentDir() != Dir.Down) { // Si on veut aller Up mais pas si on va deja Down
				platform.setCurrentDir(Dir.Up);
				platform.setAllowInput(false);
			}
			if (code == 115 && platform.getCurrentDir() != Dir.Up) { // Si on veut aller Down mais pas si on va deja Up
				platform.setCurrentDir(Dir.Down);
				platform.setAllowInput(false);
			}
			if (code == 113 && platform.getCurrentDir() != Dir.Right) { // Si on veut aller Left mais pas si on va deja Right
				platform.setCurrentDir(Dir.Left);
				platform.setAllowInput(false);
			}
			if (code == 100 && platform.getCurrentDir() != Dir.Left) { // Si on veut aller Right mais pas si on va deja Left
				platform.setCurrentDir(Dir.Right);
				platform.setAllowInput(false);
			}

		}
	}
	
	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}

	/**Delay */
	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}
}