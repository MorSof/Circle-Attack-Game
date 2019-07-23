import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StartMenu {

	private Stage menuStage = new Stage();
	private Pane pane = new Pane();
	private Scene menuScene = new Scene(pane);
	private MyCircle myCircle = new MyCircle();
	private Sphere sphere = new Sphere();
	private VBox rightBox = new VBox();
	private HBox bottomBox = new HBox();
	private HBox topBox = new HBox();
	private ComboBox<String> comboColor = new ComboBox<String>();
	private ComboBox<String> comboMode = new ComboBox<String>();
	private ComboBox<String> comboDifficulty = new ComboBox<String>();
	private Button startGameButton = new Button("Start Game !");
	private Button exitButton = new Button("Exit Game");
	private Label nameOfGame = new Label("CIRCLE ATTACK");
	private Record record = new Record();
	private TextField textField = new TextField("Player");
	private String playerName;
	private PhongMaterial material = new PhongMaterial();
	
	private MediaPlayer mediaPlayer;
	private String musicFile="CircleAttackFiles\\[MapleStory BGM] Yo Taipei (online-audio-converter.com).mp3";

	
	
	private Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
	
	private double maxXBorder = primaryScreenBounds.getMaxX();
	private double maxYBorder = primaryScreenBounds.getMaxY();

	public StartMenu() {		
		getMedia();
		stageSize();
		setPane();
		setModeCombo();
		setDifficultyCombo();
		exitButtonLogic();
	}

	public void stageSize() {

		menuStage.setAlwaysOnTop(true);
		menuStage.setFullScreen(true);
		menuStage.setScene(menuScene);
		menuStage.show();
		pane.setOnMouseClicked(e -> {
			System.out.println("x:" + e.getSceneX());
			System.out.println("y:" + e.getSceneY());

		});

	}

	public void setSphere() {
		material.setDiffuseColor(Color.BLACK);
		material.setSpecularColor(Color.WHITE);
		sphere.setMaterial(material);
		sphere.setRadius(120);
		sphere.setTranslateX(620);
		sphere.setTranslateY(350);
	}

	public Sphere getSphere() {
		return sphere;
	}

	public void setMyCircle() {
		myCircle.getCircle().setRadius(120);
		myCircle.getCircle().setStroke(Color.BLACK);
		myCircle.getCircle().setCenterX(maxXBorder / 2);
		myCircle.getCircle().setCenterY(maxYBorder / 2);
	}

	public String getPlayerName() {
		return playerName;

	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public TextField getTextField() {
		return textField;

	}

	public void setPane() {

		// pane.setBackground(
		// new Background(new BackgroundFill(Color.rgb(0, 191, 255), CornerRadii.EMPTY,
		// Insets.EMPTY)));

		record.setTable();
		record.setText(50/* layOutX */, 300/* layOutY */);
		record.getTabelVbox().setLayoutX(20);
		record.getTabelVbox().setLayoutY((maxYBorder / 4));// 180

		setMyCircle();
		// setSphere();
		setBottomBox();
		setTopBox();
		setTextField();

		pane.getChildren().addAll(myCircle.getCircle(), rightBox, bottomBox, topBox, record.getTabelVbox(),
				textField/* ,sphere */, comboColor, comboMode, comboDifficulty);

	}

	public void setTextField() {

		final int TEXT_FIELD_LENGTH = 350;
		final int TEXT_FIELD_HIGHT = 10;

		textField.textProperty().addListener((observable, old_value, new_value) -> {
			if (new_value.contains(" ") || new_value.length()>=10) {
				// prevents spacing or more than 10 letters name
				textField.setText(old_value);
			}
		});
		textField.setPrefWidth(TEXT_FIELD_LENGTH);
		textField.setPrefHeight(TEXT_FIELD_HIGHT);
		textField.setLayoutX((maxXBorder / 2) - TEXT_FIELD_LENGTH / 2);
		textField.setLayoutY(maxYBorder * 0.8);// 530
		textField.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				playerName = textField.getText();
				if (playerName.equals("") || playerName == null) {
					setPlayerName("Player");
				}
			}
		});

	}

	public void setColorCombo() {

		comboColor.setStyle("-fx-font-weight: bold;");
		comboColor.setLayoutX(maxXBorder * 0.65);// 880
		comboColor.setLayoutY(320);
		comboColor.setPadding(new Insets(10, 15, 10, 15));
		comboColor.getItems().addAll("Black", "Blue", "Yellow", "Red", "Pink", "Green");
		comboColor.setPromptText("Color");
		comboColor.setOnAction(e -> {
			setColorComboLogic();
		});

	}

	public void setModeCombo() {

		comboMode.setStyle("-fx-font-weight: bold;");
		comboMode.setLayoutX((maxXBorder * 0.65) + 120);// 990
		comboMode.setLayoutY(320);
		comboMode.setPadding(new Insets(10, 15, 10, 15));
		comboMode.getItems().addAll("Day", "Night", "Dust");
		comboMode.setPromptText("Mode");
		comboMode.setOnAction(e -> {
			setModeComboLogic();
		});
	}

	public void setDifficultyCombo() {

		comboDifficulty.setStyle("-fx-font-weight: bold;");
		comboDifficulty.setLayoutX((maxXBorder * 0.65) + 240);
		comboDifficulty.setLayoutY(320);
		comboDifficulty.setPadding(new Insets(10, 15, 10, 15));
		comboDifficulty.getItems().addAll("Easy", "Medium", "Hard", "Realy?");
		comboDifficulty.setPromptText("Difficulty");

	}

	public void setModeComboLogic() {
		switch (comboMode.getValue()) {
		case "Day":
			pane.setBackground(
					new Background(new BackgroundFill(Color.rgb(0, 191, 255), CornerRadii.EMPTY, Insets.EMPTY)));
			break;
		case "Night":
			pane.setBackground(
					new Background(new BackgroundFill(Color.rgb(25, 25, 112), CornerRadii.EMPTY, Insets.EMPTY)));
			break;
		case "Dust":
			pane.setBackground(
					new Background(new BackgroundFill(Color.rgb(244, 164, 96), CornerRadii.EMPTY, Insets.EMPTY)));
			break;
		}
	}

	public ComboBox<String> getModeCombo() {
		return comboMode;

	}

	public double getDifficultyComboSeconds() {
		double secondsDifficulty = 4;

		switch (comboDifficulty.getValue()) {
		case "Easy":
			secondsDifficulty = 2;
			break;
		case "Medium":
			secondsDifficulty = 1.5;
			break;
		case "Hard":
			secondsDifficulty = 1;
			break;
		case "Realy?":
			secondsDifficulty = 0.5;
			break;
		default:
			secondsDifficulty = 1;
		}

		return secondsDifficulty;
	}

	public ComboBox<String> getDifficultyCombo() {
		return comboDifficulty;

	}

	public void setColorComboLogic() {

		switch (comboColor.getValue()) {

		case "Black":

			myCircle.getCircle().setFill(Color.BLACK);
			myCircle.setColor(Color.BLACK);
			break;

		case "Blue":

			myCircle.getCircle().setFill(Color.BLUE);
			myCircle.setColor(Color.BLUE);
			break;

		case "Yellow":

			myCircle.getCircle().setFill(Color.YELLOW);
			myCircle.setColor(Color.YELLOW);
			break;

		case "Red":

			myCircle.getCircle().setFill(Color.RED);
			myCircle.setColor(Color.RED);
			break;

		case "Pink":

			myCircle.getCircle().setFill(Color.PINK);
			myCircle.setColor(Color.PINK);
			break;

		case "Green":

			myCircle.getCircle().setFill(Color.GREEN);
			myCircle.setColor(Color.GREEN);
			break;

		}
	}

	public void setTopBox() {

		topBox.getChildren().addAll(nameOfGame);

		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		nameOfGame.setEffect(ds);
		nameOfGame.setCache(true);
		nameOfGame.setTextFill(Color.BLUE);
		nameOfGame.setFont(Font.font(null, FontWeight.BOLD, 100));
        nameOfGame.setPrefWidth(maxXBorder);
        nameOfGame.setAlignment(Pos.CENTER);
		topBox.setPrefWidth(nameOfGame.getPrefWidth());
		
		topBox.setLayoutX((maxXBorder / 2) -topBox.getPrefWidth() / 2);// 250
		topBox.setLayoutY(maxYBorder * 0.);

	}

	public void exitButtonLogic() {

		exitButton.setOnAction(e -> {
			System.exit(0);
		});

	}

	public void setExitButton(Button exitButton) {

		exitButton.setTextFill(Color.BLUE);
		exitButton.setFont(Font.font(null, FontWeight.BOLD, 15));
		// exitButton.setPadding(new Insets(20, 20, 20, 20));
		exitButton.setPrefWidth(120);
		exitButton.setPrefHeight(40);

		this.exitButton = exitButton;
	}

	public void setStartGameButton(Button startGameButton) {

		startGameButton.setTextFill(Color.BLUE);
		startGameButton.setFont(Font.font(null, FontWeight.BOLD, 15));
		// startGameButton.setPadding(new Insets(20, 20, 20, 20));
		startGameButton.setPrefWidth(120);
		startGameButton.setPrefHeight(40);

		this.startGameButton = startGameButton;
	}

	public void setBottomBox() {

		setStartGameButton(startGameButton);
		setExitButton(exitButton);

		final double BOTTOM_BOX_SPACING_SIZE = 50;
		final double BOTTOM_BOX_WIDTH_SIZE = startGameButton.getPrefWidth() + BOTTOM_BOX_SPACING_SIZE
				+ exitButton.getPrefWidth();
		final double BOTTOM_BOX_HIGHT_SIZE = 200;

		bottomBox.getChildren().addAll(startGameButton, exitButton);
		bottomBox.setPrefWidth(BOTTOM_BOX_WIDTH_SIZE);
		bottomBox.setPrefHeight(BOTTOM_BOX_HIGHT_SIZE);
		bottomBox.setLayoutX((maxXBorder / 2) - BOTTOM_BOX_WIDTH_SIZE / 2);
		bottomBox.setLayoutY(maxYBorder * 0.9);
		bottomBox.setSpacing(BOTTOM_BOX_SPACING_SIZE);
		setColorCombo();

	}
	public void getMedia() {

		File file = new File(musicFile);
		Media sound = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

	}

	public MyCircle getMyCircle() {
		return myCircle;

	}

	public Stage getMenuStage() {
		return menuStage;
	}

	public void setMenuStage(Stage menuStage) {
		this.menuStage = menuStage;
	}

	public Pane getPane() {
		return pane;
	}

	public void setPane(BorderPane pane) {
		this.pane = pane;
	}

	public Scene getMenuScene() {
		return menuScene;
	}

	public void setMenuScene(Scene menuScene) {
		this.menuScene = menuScene;
	}

	public Button getStartGameButton() {
		return startGameButton;
	}

	public Button getExitButton() {
		return exitButton;
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

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	
	
}
