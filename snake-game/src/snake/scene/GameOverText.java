package snake.scene;

import static snake.util.Constants.GAME_OVER_COLOR;
import static snake.util.Constants.GAME_OVER_LOCATION;
import static snake.util.Constants.GAME_OVER_TEXT;

import java.awt.Point;

import snake.graphics.Text;

public class GameOverText extends Text {

	public GameOverText(int score) {
		super(GAME_OVER_COLOR, String.format(GAME_OVER_TEXT, score), new Point(GAME_OVER_LOCATION));
	}
}
