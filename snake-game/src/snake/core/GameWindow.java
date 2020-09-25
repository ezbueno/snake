package snake.core;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import snake.graphics.Renderer;
import snake.scene.Snake;
import snake.util.Constants;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements KeyListener {
	private Renderer renderer;
	private Snake snake;
	private Image buffer;
	private Graphics gImage;
	private Rectangle drawingArea;
	private long lastKeyboardEventTime;

	public GameWindow(Snake snake) {
		this.snake = snake;
		setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		setResizable(false);
		setTitle(Constants.WINDOW_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		addKeyListener(this);
		setVisible(true);

		buffer = createImage(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		gImage = buffer.getGraphics();
		renderer = new Renderer(gImage);
				
		addWindowListener(getWindowAdapter());

		URL url = getClass().getResource("/snake/snake-icon.png");
		Image imageIcon = Toolkit.getDefaultToolkit().getImage(url);
		this.setIconImage(imageIcon);
		
		defineDrawingArea();
	}

	public Renderer getRenderer() {
		return renderer;
	}

	@Override
	public void paint(Graphics gScreen) {
		if (gImage == null || renderer == null) {
			return;
		}

		renderer.render();
		gScreen.drawImage(buffer, 0, 0, null);
	}
	
	private void defineDrawingArea() {
		int upperY = (int) (Constants.WINDOW_HEIGHT - getContentPane().getSize().getHeight());
		drawingArea = new Rectangle(0, upperY, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT - upperY);
	}
	
	public Rectangle getDrawingArea() {
		return drawingArea;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		long now = System.currentTimeMillis();
		
		if (now - lastKeyboardEventTime < 40) {
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			snake.up();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			snake.down();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			snake.left();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			snake.right();
		} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		lastKeyboardEventTime = now; 
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	// Método que impede a Janela do JFrame ser minimizada
	private WindowAdapter getWindowAdapter() {
		return new WindowAdapter() {
			@Override
			public void windowIconified(WindowEvent we) {
				setState(JFrame.NORMAL);
				JOptionPane.showMessageDialog(null, "Não é possível minimizar!");
			}
		};
	}
}
