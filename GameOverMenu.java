
import javafx.scene.layout.Pane;

public class GameOverMenu extends MidMenu {

	private Record record = new Record();

	public GameOverMenu(Pane pane, String name, int Score, String survivalTime) {
		super(pane, name, Score, survivalTime);
		setNames();
		setText();
		setTabelPosition();
		setRecord(name, Score, survivalTime);
		pane.getChildren().addAll(record.getTabelVbox());
	}

	public void setNames() {
		getText().setText("GAME OVER");
		getLeftButton().setText("Play Again");
	}

	public void setRecord(String name, int score, String survivalTime) {
		record.setNewRank(name, score, survivalTime);
	}

	public Record getRecord() {
		return record;

	}

	public void setTabelPosition() {

		record.getTabelVbox().setLayoutX(10);
		record.getTabelVbox().setLayoutY(150);

	}
}
