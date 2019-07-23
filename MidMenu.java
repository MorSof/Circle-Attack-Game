import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class MidMenu {

	private Text text = new Text();
	private Button backToMenuButton = new Button("Back to Menu");
	private Button leftButton = new Button();
	private Button exitButton = new Button("Exit Game");
	private HBox bottomBox = new HBox();
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
	private double maxXBorder = primaryScreenBounds.getMaxX();
	private double maxYBorder = primaryScreenBounds.getMaxY();
    private final double BUTTON_WIDTH=120;
    private final double BUTTON_HIGHT=40;
    private final double BUTTON_TEXT_SIZE=15;


	
	public MidMenu(Pane pane, String name, int Score, String survivalTime) {
		setBackToMenuButton();
		setLeftButton();
		setExitButton();
		setPane(pane);
	}

	public void setPane(Pane pane) {
		setBottomBox();
		pane.getChildren().addAll(bottomBox, text);
	}

	public void setLeftButton() {

		leftButton.setTextFill(Color.GREEN);
		leftButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		leftButton.setPrefWidth(BUTTON_WIDTH);
		leftButton.setPrefHeight(BUTTON_HIGHT);
		leftButton.toFront();
	}

	public void setBackToMenuButton() {

		backToMenuButton.setTextFill(Color.GREEN);
		backToMenuButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		backToMenuButton.setPrefWidth(BUTTON_WIDTH);
		backToMenuButton.setPrefHeight(BUTTON_HIGHT);
		backToMenuButton.toFront();

	}

	public void setExitButton() {

		exitButton.setTextFill(Color.GREEN);
		exitButton.setFont(Font.font(null, FontWeight.BOLD, BUTTON_TEXT_SIZE));
		exitButton.setPrefWidth(BUTTON_WIDTH);
		exitButton.setPrefHeight(BUTTON_HIGHT);
		exitButton.toFront();

	}

	public void setBottomBox() {

		final double BOTTOM_BOX_SPACING_SIZE = 50;
		final double BOTTOM_BOX_HIGHT_SIZE = 200;
		final double BOTTOM_BOX_WIDTH_SIZE = leftButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE
				+ backToMenuButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE + exitButton.getPrefWidth();

		bottomBox.getChildren().addAll(leftButton, backToMenuButton , exitButton);
		bottomBox.setPrefWidth(BOTTOM_BOX_WIDTH_SIZE);
		bottomBox.setPrefHeight(BOTTOM_BOX_HIGHT_SIZE);
		bottomBox.setLayoutX((maxXBorder / 2) - BOTTOM_BOX_WIDTH_SIZE / 2);
		bottomBox.setLayoutY(maxYBorder * 0.9);
		bottomBox.setSpacing(BOTTOM_BOX_SPACING_SIZE);

	}

	public Text getText() {
		return text;
	}

	public void setText() {

		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		text.setEffect(ds);
		text.setCache(true);
		text.setFont(Font.font(null, FontWeight.BOLD, 100));
		text.setFill(Color.DARKGREEN);
		text.setLayoutX((maxXBorder / 2) - (text.getBoundsInLocal().getWidth() / 2));
		text.setLayoutY(maxYBorder * 0.25);
		text.toFront();
	}

	public Button getBackToMenuButton() {
		return backToMenuButton;
	}

	public Button getExitButton() {
		return exitButton;
	}

	public Button getLeftButton() {
		return leftButton;
	}

	public HBox getBottomBox() {
		return bottomBox;
	}

	public double getMaxXBorder() {
		return maxXBorder;
	}

	public void setMaxXBorder(double maxXBorder) {
		this.maxXBorder = maxXBorder;
	}

	public double getMaxYBorder() {
		return maxYBorder;
	}

	public void setMaxYBorder(double maxYBorder) {
		this.maxYBorder = maxYBorder;
	}

}
