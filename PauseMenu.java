import javafx.scene.layout.Pane;

public class PauseMenu extends MidMenu {

	public PauseMenu(Pane pane, String playerName, int score, String survivalTime) {
		super(pane, playerName, score, survivalTime);
		getText().setText("GAME PAUSED");
		setText();
		getLeftButton().setText("Continue");
		
	}

}
