import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main extends Application {

	private StartMenu startMenu;
	private Pane pane;
	private Timeline timeLine;
	private Timeline insidTimeLine;
	private Timeline clockTimeLine;
	private Timeline cloudTimeLine;
	private Timeline purpelDaimondTimeline;
	private Timeline insidePurpleDaimondTimeLine;
	private Timeline yellowDaimondTimeline;
	private Timeline insideYellowDaimondTimeLine;
	private Timeline insideCloudTimeLine;
	private Timeline explosionTimeLine;
	private AnimationTimer animationTimer;
	private Circle myCircle;
	private Circle circle;
	private Hoop hoop;

	private double SCREEN_WIDTH;
	private double SCREEN_HIGHT;
	private final int myCircleAdvence = 5;
	private int advencedOnElusion = 0;
	private final int creationYBoundery = 600;
	private double MIN_RANGE_CREATION_Y = (-creationYBoundery) - advencedOnElusion; // -300
	private double MAX_RANGE_CREATION_Y = SCREEN_HIGHT + creationYBoundery - advencedOnElusion; // 720+(300) = 1020
	private MediaPlayer mediaPlayer;
	private MediaPlayer soundEffect;
	private ArrayList<MediaPlayer> soundEffectArray = new ArrayList<MediaPlayer>();
	private ArrayList<String> musicFileStringArray = new ArrayList<String>();

	private int timerSec = 0;
	private Text clockText = new Text();
	private Text scoreText = new Text();
	private Text shrinkText = new Text();
	private Text explosionAmountText = new Text();
	private Text playerNameText = new Text();
	private int gameSeconds = 0;
	private int sec = 0;
	private int tenSec = 0;
	private int minutes = 0;
	private int tenMinutes = 0;
	private int score = 0;
	private int shrinkTimer = 0;
	private final int minScoreForShrink = 10;
	private GameOverMenu gameOver;
	private PauseMenu pauseMenu;
	private ArrayList<Circle> circleArray = new ArrayList<Circle>();
	private ArrayList<Timeline> insideTimeLineArray = new ArrayList<Timeline>();
	private ArrayList<Cloud> cloudArray = new ArrayList<Cloud>();
	private ArrayList<Timeline> insideCloudTimeLineArray = new ArrayList<Timeline>();
	private ArrayList<Diamond> yellowDaimondArray = new ArrayList<Diamond>();
	private ArrayList<Diamond> purpleDaimondArray = new ArrayList<Diamond>();
	private ArrayList<Timeline> insideYellowDaimondArray = new ArrayList<Timeline>();
	private ArrayList<Timeline> insidePurpleDaimondArray = new ArrayList<Timeline>();
	private int explosionAmount = 0;

	private final String ExplosionSoundString = "CircleAttackFiles\\BOMB EXPLODING Sound effect Best Sound Effects (online-audio-converter.com).mp3";
	private final String collectDiamondSoundString = "CircleAttackFiles\\Collect Coin SOUND Effect (online-audio-converter.com).mp3";
	private final String gameOverSoundString = "CircleAttackFiles\\Denied - Sound Effect (HD) (online-audio-converter.com).mp3";
	private final String shrinkSoundString = "CircleAttackFiles\\Cartoon Shrink Sound Effect (online-audio-converter.com).mp3";


	private boolean isShrinkTextOnScreen = false;
	private boolean isExplosion = false;
	private boolean isPaused = false;
	private boolean isGameOver = false;
	private boolean isUpOnce = false;
	private boolean isDownOnce = false;
	private boolean isInUpElusion = false;
	private boolean isInDownElusion = false;

	private Text text = new Text();

	private final BooleanProperty upPressed = new SimpleBooleanProperty();
	private final BooleanProperty rightPressed = new SimpleBooleanProperty();
	private final BooleanProperty downPressed = new SimpleBooleanProperty();
	private final BooleanProperty leftPressed = new SimpleBooleanProperty();

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage gameStage) {
		startMenu = new StartMenu();
		SCREEN_WIDTH = startMenu.getMaxXBorder();
		SCREEN_HIGHT = startMenu.getMaxYBorder();
		pane = startMenu.getPane();
		myCircle = startMenu.getMyCircle().getCircle();
		startMenu.getStartGameButton().setOnAction(e -> {
			startMenu.setPlayerName(startMenu.getTextField().getText());
			if (startMenu.getPlayerName().equals("")) {
				startMenu.setPlayerName("Player");
			}
			comboNullDealer();
			startGame();
		});

	}

	public void startGame() {

		startMenu.getMediaPlayer().stop();
		getMedia();
		mediaSoundEffectCreation(ExplosionSoundString);
		mediaSoundEffectCreation(collectDiamondSoundString);
		mediaSoundEffectCreation(gameOverSoundString);
		mediaSoundEffectCreation(shrinkSoundString);
		getExplosionAmountText();
		getScoreText();
		getPlayerNameText();
		pane.getChildren().clear();
		myCircle.setRadius(30);
		myCircle.setCenterX(SCREEN_WIDTH * 0.1);
		myCircle.setCenterY(SCREEN_HIGHT / 2);
		pane.getChildren().addAll(myCircle);
		TimeLineEvents();
		keyBoardControl();

		text.setLayoutX(550);
		text.setLayoutY(100);
		pane.getChildren().addAll(clockText, scoreText, explosionAmountText, text, playerNameText);

	}

	public void keyBoardControl() {

		startMenu.getMenuScene().setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.UP) {
				upPressed.set(true);
			}
			if (e.getCode() == KeyCode.RIGHT) {
				rightPressed.set(true);
			}
			if (e.getCode() == KeyCode.DOWN) {
				downPressed.set(true);
			}
			if (e.getCode() == KeyCode.LEFT) {
				leftPressed.set(true);
			}
			if (e.getCode() == KeyCode.S) {
				shrinkStartLogic();
			}
			if (e.getCode() == KeyCode.E) {
				explosionCreation();
			}
			if (e.getCode() == KeyCode.ESCAPE || e.getCode() == KeyCode.P) {
				if (isPaused == false) {
					pauseLogic();
				} else if (isPaused == true) {
					playLogic();
				}
			}

		});
		startMenu.getMenuScene().setOnKeyReleased(e -> {
			if (e.getCode() == KeyCode.UP) {
				upPressed.set(false);
			}
			if (e.getCode() == KeyCode.RIGHT) {
				rightPressed.set(false);
			}
			if (e.getCode() == KeyCode.DOWN) {
				downPressed.set(false);
			}
			if (e.getCode() == KeyCode.LEFT) {
				leftPressed.set(false);
			}
		});

		animationTimer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {

//				text.setText("circleTimeLineArray size: " + insideTimeLineArray.size() + "\ncircle size: "
//						+ circleArray.size() + "\nyellowDaimondTimelineArray size: " + insideYellowDaimondArray.size()
//						+ "\nyellowDaimondArray size: " + yellowDaimondArray.size()
//						+ "\npurpleDaimondTimelineArray size: " + insidePurpleDaimondArray.size()
//						+ "\npurpleDaimondArray size: " + purpleDaimondArray.size() + "\nadvencedOnElusion: "
//						+ advencedOnElusion + "\ncloud size: " + cloudArray.size() + "\nMIN_RANGE_CREATION_Y: "
//						+ MIN_RANGE_CREATION_Y + "\nMAX_RANGE_CREATION_Y: " + MAX_RANGE_CREATION_Y + "\nCenterY: "
//						+ myCircle.getCenterY());

				// condition to move without the elusion
				if (upPressed.get() && (myCircle.getCenterY() - myCircle.getRadius() > 0) && isInUpElusion == false) {
					myCircle.setCenterY(myCircle.getCenterY() - myCircleAdvence);
				}
				if (rightPressed.get() && (myCircle.getCenterX() + myCircle.getRadius() < SCREEN_WIDTH)) {
					myCircle.setCenterX(myCircle.getCenterX() + myCircleAdvence);
				}
				if (downPressed.get() && myCircle.getCenterY() + myCircle.getRadius() < (SCREEN_HIGHT)
						&& isInDownElusion == false) {
					myCircle.setCenterY(myCircle.getCenterY() + myCircleAdvence);
				}
				if (leftPressed.get() && (myCircle.getCenterX() - myCircle.getRadius() > 0)) {
					myCircle.setCenterX(myCircle.getCenterX() - myCircleAdvence);
				}
				movingDownElusionHandle(); // include elusion logic
				movingUpElusionHandle(); // include elusion logic
			}
		};
		animationTimer.start();
	}

	public void movingDownElusionHandle() {
		if (myCircle.getCenterY() > SCREEN_HIGHT * 0.75 && advencedOnElusion <= creationYBoundery && isUpOnce == false
				&& downPressed.get() == true) {

			isInDownElusion = true;

			elusionLogicHandle(-myCircleAdvence);

			advencedOnElusion += myCircleAdvence;
			isUpOnce = true;
		} else {
			isInDownElusion = false;
		}

		if (isUpOnce == true && downPressed.get() == true) {
			isUpOnce = false;
		}
	}

	public void movingUpElusionHandle() {
		if (myCircle.getCenterY() < SCREEN_HIGHT * 0.3 && advencedOnElusion >= -creationYBoundery && isDownOnce == false
				&& upPressed.get() == true) {

			isInUpElusion = true;

			elusionLogicHandle(myCircleAdvence);

			advencedOnElusion -= myCircleAdvence;
			isDownOnce = true;
		} else {
			isInUpElusion = false;
		}

		if (isDownOnce == true && upPressed.get() == true) {
			isDownOnce = false;
		}
	}

	public void elusionLogicHandle(int myCircleAdvence/* positive for down, negative for up */) {

		for (int i = 0; i < circleArray.size(); i++) {
			circleArray.get(i).setCenterY(circleArray.get(i).getCenterY() + myCircleAdvence);
		}
		for (int i = 0; i < cloudArray.size(); i++) {
			cloudArray.get(i).getArc1().setCenterY(cloudArray.get(i).getArc1().getCenterY() + myCircleAdvence);
			cloudArray.get(i).getArc2().setCenterY(cloudArray.get(i).getArc2().getCenterY() + myCircleAdvence);
			cloudArray.get(i).getArc3().setCenterY(cloudArray.get(i).getArc3().getCenterY() + myCircleAdvence);
		}
		for (int i = 0; i < yellowDaimondArray.size(); i++) {
			yellowDaimondArray.get(i).getDaimond()
					.setY(yellowDaimondArray.get(i).getDaimond().getY() + myCircleAdvence);
		}
		for (int i = 0; i < purpleDaimondArray.size(); i++) {
			purpleDaimondArray.get(i).getDaimond()
					.setY(purpleDaimondArray.get(i).getDaimond().getY() + myCircleAdvence);
		}

		MIN_RANGE_CREATION_Y = (-creationYBoundery) - advencedOnElusion;
		MAX_RANGE_CREATION_Y = SCREEN_HIGHT + creationYBoundery - advencedOnElusion;
	}

	public void TimeLineEvents() {

		circleCreation(); // include circleMovement();
		yellowDaimondCreation(); // scoreLogic(diamond, insidePurpleDaimondTimeLine);
		purpleDaimondCreation(); // scoreLogic(diamond, insidePurpleDaimondTimeLine);
		cloudCreation(); // include cloudMovement(cloud, insideCloudTimeLine);
		clockCreationAndLogic(); // include getClockText();shrinkStopLogic();getShrinkText();

	}

	public void circleCreation() {

		timeLine = new Timeline(new KeyFrame(Duration.seconds(startMenu.getDifficultyComboSeconds()), e -> {
			circle = new Circle(30 + (int) (Math.random() * 100),
					Color.color(Math.random(), Math.random(), Math.random()));
			insidTimeLine = new Timeline();
			insideTimeLineArray.add(insidTimeLine); // adding the TimeLine to the array
			circle.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
			circle.setCenterX(SCREEN_WIDTH + circle.getRadius());
			circle.setCenterY(
					MIN_RANGE_CREATION_Y + (int) (Math.random() * (MAX_RANGE_CREATION_Y - MIN_RANGE_CREATION_Y)));
			// (int)(Math.random() * (max - min) + min)

			circleArray.add(circle);
			circleMovement(circle, insidTimeLine);// include looseLogic AND explosionLogic methods inside
			pane.getChildren().addAll(circle);
			allTextsToFront();
		}));
		timeLine.setCycleCount(Timeline.INDEFINITE);
		timeLine.play(); // Start animation
	}

	public void yellowDaimondCreation() {

		yellowDaimondTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
			Diamond diamond = new Diamond(Color.GOLD);
			insideYellowDaimondTimeLine = new Timeline();
			insideYellowDaimondArray.add(insideYellowDaimondTimeLine);
			diamond.getDaimond().setX(0 + (int) (Math.random() * SCREEN_WIDTH));
			diamond.getDaimond()
					.setY(MIN_RANGE_CREATION_Y + (int) (Math.random() * (MAX_RANGE_CREATION_Y - MIN_RANGE_CREATION_Y)));
			yellowDaimondArray.add(diamond);
			pane.getChildren().add(diamond.getDaimond());
			scoreLogic(diamond, insideYellowDaimondTimeLine);

			insideYellowDaimondTimeLine.setCycleCount(Timeline.INDEFINITE);
			insideYellowDaimondTimeLine.play();

		}));

		yellowDaimondTimeline.setCycleCount(Timeline.INDEFINITE);
		yellowDaimondTimeline.play(); // Start animation
	}

	public void purpleDaimondCreation() {

		purpelDaimondTimeline = new Timeline(new KeyFrame(Duration.seconds(40), e -> {
			Diamond diamond = new Diamond(Color.PURPLE);
			purpleDaimondArray.add(diamond);
			insidePurpleDaimondTimeLine = new Timeline();
			insidePurpleDaimondArray.add(insidePurpleDaimondTimeLine);
			diamond.getDaimond().setX(0 + (int) (Math.random() * SCREEN_WIDTH));
			diamond.getDaimond()
					.setY(MIN_RANGE_CREATION_Y + (int) (Math.random() * (MAX_RANGE_CREATION_Y - MIN_RANGE_CREATION_Y)));
			pane.getChildren().add(diamond.getDaimond());

			scoreLogic(diamond, insidePurpleDaimondTimeLine);

			insidePurpleDaimondTimeLine.setCycleCount(Timeline.INDEFINITE);
			insidePurpleDaimondTimeLine.play();
		}));
		purpelDaimondTimeline.setCycleCount(Timeline.INDEFINITE);
		purpelDaimondTimeline.play(); // Start animation
	}

	public void scoreLogic(Diamond diamond, Timeline insidDaimondTimeLine) {

		insidDaimondTimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(10), e1 -> {

			if (getDistanceFromDaimond(diamond, myCircle) <= myCircle.getRadius() + diamond.getDaimond().getWidth()) {

				soundEffectLogic(collectDiamondSoundString);
                   				
				if (diamond.getColor() == Color.GOLD) {
					score += 1;
					// daimond.getDaimond().setX(7000);

					insidDaimondTimeLine.stop();
					insidDaimondTimeLine.getKeyFrames().clear();
					insideYellowDaimondArray.remove(insidDaimondTimeLine);
					yellowDaimondArray.remove(diamond);

					pane.getChildren().remove(diamond.getDaimond());
					getScoreText();
				} else if (diamond.getColor() == Color.PURPLE) {
					explosionAmount += 1;
					score += 5;
					// daimond.getDaimond().setX(7000);

					insidDaimondTimeLine.stop();
					insidDaimondTimeLine.getKeyFrames().clear();
					insidePurpleDaimondArray.remove(insidDaimondTimeLine);
					purpleDaimondArray.remove(diamond);

					pane.getChildren().remove(diamond.getDaimond());
					getExplosionAmountText();
					getScoreText();
				}
			}
		}));
	}

	public void clockCreationAndLogic() {
		clockTimeLine = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			timerSec += 1;
			getClockText();
			shrinkStopLogic();
			getShrinkText();

		}));
		clockTimeLine.setCycleCount(Timeline.INDEFINITE);
		clockTimeLine.play(); // Start animation

	}

	public void cloudCreation() {
		cloudTimeLine = new Timeline(new KeyFrame(Duration.seconds(6), e -> {
			Cloud cloud = new Cloud();
			cloudArray.add(cloud);
			insideCloudTimeLine = new Timeline();
			pane.getChildren().addAll(cloud.getArc1(), cloud.getArc2(), cloud.getArc3());
			cloudMovement(cloud, insideCloudTimeLine);
		}));
		cloudTimeLine.setCycleCount(Timeline.INDEFINITE);
		cloudTimeLine.play(); // Start animation
	}

	public void explosionCreation() {

		if (explosionAmount > 0 && isExplosion == false) {

			explosionAmount = explosionAmount - 1;
			soundEffectLogic(ExplosionSoundString);
			getExplosionAmountText();
			hoop = new Hoop(startMenu.getMyCircle().getColor());
			pane.getChildren().add(hoop.getCircle());
			hoop.getCircle().setCenterX(myCircle.getCenterX());
			hoop.getCircle().setCenterY(myCircle.getCenterY());
			explosionLogic();
			isExplosion = true;
		}
	}

	public void explosionLogic() {
		explosionTimeLine = new Timeline(new KeyFrame(Duration.millis(10), e1 -> {
			hoop.getCircle().setRadius(hoop.getCircle().getRadius() + 2);
			for (int i = 0; i < circleArray.size(); i++) {
				if (getDistanceFromCircle(circleArray.get(i), hoop.getCircle()) <= circleArray.get(i).getRadius()
						+ hoop.getCircle().getRadius()) {

					pane.getChildren().remove(circleArray.get(i));
					insideTimeLineArray.get(i).stop();
					insideTimeLineArray.get(i).getKeyFrames().clear();
					insideTimeLineArray.remove(i);
					circleArray.remove(i);
					score += 1;
					getScoreText();
				}
			}

			if (hoop.getCircle().getRadius() >= 400) {
				pane.getChildren().remove(hoop.getCircle());
				explosionTimeLine.stop();
				explosionTimeLine.getKeyFrames().clear();
				isExplosion = false;
			}
		}));

		explosionTimeLine.setCycleCount(Timeline.INDEFINITE);
		explosionTimeLine.play(); // Start animation

	}

	public void circleMovement(Circle circle, Timeline insidTimeLine) {

		insidTimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(10), e1 -> {

			looseLogic(circle);
			circle.setCenterX(circle.getCenterX() - 1);
			if (circle.getCenterX() <= -circle.getRadius()) {
				pane.getChildren().remove(circle);
				insideTimeLineArray.remove(insidTimeLine);
				circleArray.remove(circle);
				insidTimeLine.stop();
				insidTimeLine.getKeyFrames().clear();
			}

			// explosionLogic(circle);

		}));
		insidTimeLine.setRate(0.1 + (int) (Math.random() * 3));
		insidTimeLine.setCycleCount(Timeline.INDEFINITE);
		insidTimeLine.play(); // Start animation

	}

	public void cloudMovement(Cloud cloud, Timeline insideCloudTimeLine) {
		insideCloudTimeLine.getKeyFrames().add(new KeyFrame(Duration.millis(10), e1 -> {
			cloud.getArc1().setCenterX(cloud.getArc1().getCenterX() - 1);
			cloud.getArc2().setCenterX(cloud.getArc2().getCenterX() - 1);
			cloud.getArc3().setCenterX(cloud.getArc3().getCenterX() - 1);
			if (cloud.getArc3().getCenterX() <= -cloud.getArc3().getRadiusX()) {
				pane.getChildren().removeAll(cloud.getArc1(), cloud.getArc2(), cloud.getArc3());
				insideCloudTimeLineArray.remove(insideCloudTimeLine);
				cloudArray.remove(cloud);
				insideCloudTimeLine.stop();
				insideCloudTimeLine.getKeyFrames().clear();
			}

		}));
		insideCloudTimeLineArray.add(insideCloudTimeLine); // adding the TimeLine to the array
		insideCloudTimeLine.setRate(0.6);
		insideCloudTimeLine.setCycleCount(Timeline.INDEFINITE);
		insideCloudTimeLine.play(); // Start animation
	}

	public void pauseLogic() {
		if (isPaused == false && isGameOver==false) {
			pauseMenu = new PauseMenu(startMenu.getPane(), startMenu.getPlayerName(), score, getSurvivalTime());
			timeLine.pause();
			clockTimeLine.pause();
			cloudTimeLine.pause();
			yellowDaimondTimeline.pause();
			purpelDaimondTimeline.pause();
			clockTimeLine.pause();
			for (int i = 0; i < insideTimeLineArray.size(); i++) {
				insideTimeLineArray.get(i).pause();
			}
			for (int i = 0; i < insideCloudTimeLineArray.size(); i++) {
				insideCloudTimeLineArray.get(i).pause();
			}
			for (int i = 0; i < insidePurpleDaimondArray.size(); i++) {
				insidePurpleDaimondArray.get(i).pause();
			}
			for (int i = 0; i < insideYellowDaimondArray.size(); i++) {
				insideYellowDaimondArray.get(i).pause();
			}

			exitLogic(pauseMenu);
			backToMenu(pauseMenu);
			continueLogic();
			isPaused = true;
		}
	}

	public void continueLogic() {
		pauseMenu.getLeftButton().setOnAction(e -> {
			playLogic();
		});
	}

	public void playLogic() {

		timeLine.play();
		clockTimeLine.play();
		cloudTimeLine.play();
		yellowDaimondTimeline.play();
		purpelDaimondTimeline.play();
		clockTimeLine.play();

		for (int i = 0; i < insideTimeLineArray.size(); i++) {
			insideTimeLineArray.get(i).play();
		}
		for (int i = 0; i < insideCloudTimeLineArray.size(); i++) {
			insideCloudTimeLineArray.get(i).play();
		}
		for (int i = 0; i < insidePurpleDaimondArray.size(); i++) {
			insidePurpleDaimondArray.get(i).play();
		}
		for (int i = 0; i < insideYellowDaimondArray.size(); i++) {
			insideYellowDaimondArray.get(i).play();
		}
		pane.getChildren().removeAll(pauseMenu.getText(), pauseMenu.getBottomBox());
		isPaused = false;

	}

	public void looseLogic(Circle circle) {

		if (getDistanceFromCircle(circle, myCircle) <= circle.getRadius() + myCircle.getRadius()) {

			soundEffectLogic(gameOverSoundString);

			purpelDaimondTimeline.stop();
			yellowDaimondTimeline.stop();
			clockTimeLine.stop();

			myCircle.setCenterY(-10000);

			gameOver = new GameOverMenu(startMenu.getPane(), startMenu.getPlayerName(), score, getSurvivalTime());
			isGameOver = true;
			exitLogic(gameOver);
			backToMenu(gameOver);
			playAgain(gameOver);
			animationTimer.stop();

		}

	}

	public void exitLogic(MidMenu midMenu) {
		midMenu.getExitButton().setOnAction(e -> {
			startMenu.getMenuStage().close();
			System.exit(0);
		});
	}

	public void backToMenu(MidMenu midMenu) {

		midMenu.getBackToMenuButton().setOnAction(e -> {

			clearKeyFrameFromTimeline(insideTimeLineArray);
			clearKeyFrameFromTimeline(insideCloudTimeLineArray);
			clearKeyFrameFromTimeline(insideYellowDaimondArray);
			clearKeyFrameFromTimeline(insidePurpleDaimondArray);

			timeLine.stop();
			timeLine.getKeyFrames().clear();

			clockTimeLine.stop();
			clockTimeLine.getKeyFrames().clear();

			cloudTimeLine.stop();
			cloudTimeLine.getKeyFrames().clear();

			yellowDaimondTimeline.stop();
			yellowDaimondTimeline.getKeyFrames().clear();

			purpelDaimondTimeline.stop();
			purpelDaimondTimeline.getKeyFrames().clear();

			animationTimer.stop();

			startMenu.getMenuStage().close();

			resetAllParameters();
			clearAllArrays();

			mediaPlayer.stop();
			start(startMenu.getMenuStage());
		});

	}

	public void playAgain(MidMenu midMenu) {
		midMenu.getLeftButton().setOnAction(e -> {

			clearKeyFrameFromTimeline(insideTimeLineArray);
			clearKeyFrameFromTimeline(insideCloudTimeLineArray);
			clearKeyFrameFromTimeline(insideYellowDaimondArray);
			clearKeyFrameFromTimeline(insidePurpleDaimondArray);

			timeLine.stop();
			timeLine.getKeyFrames().clear();

			clockTimeLine.stop();
			clockTimeLine.getKeyFrames().clear();

			cloudTimeLine.stop();
			cloudTimeLine.getKeyFrames().clear();

			yellowDaimondTimeline.stop();
			yellowDaimondTimeline.getKeyFrames().clear();

			purpelDaimondTimeline.stop();
			purpelDaimondTimeline.getKeyFrames().clear();

			mediaPlayer.stop();

			clearAllArrays();

			resetAllParameters();

			isGameOver = false;

			startGame();
		});
	}

	public void resetAllParameters() {
		sec = 0;
		tenSec = 0;
		minutes = 0;
		tenMinutes = 0;
		score = 0;
		explosionAmount = 0;
		advencedOnElusion = 0;
		MIN_RANGE_CREATION_Y = (-creationYBoundery) + advencedOnElusion;
		MAX_RANGE_CREATION_Y = SCREEN_HIGHT + creationYBoundery - advencedOnElusion;
		isShrinkTextOnScreen = false;
		isExplosion = false;
		isPaused = false;
		isGameOver = false;
		isUpOnce = false;
		isDownOnce = false;
		isInUpElusion = false;
		isInDownElusion = false;

	}

	public void clearAllArrays() {
		insideCloudTimeLineArray.clear();
		insideTimeLineArray.clear();
		circleArray.clear();
		cloudArray.clear();
		insidePurpleDaimondArray.clear();
		purpleDaimondArray.clear();
		insideYellowDaimondArray.clear();
		yellowDaimondArray.clear();
	}

	public double getDistanceFromCircle(Circle circle, Circle myCircle) {

		double disEnemyXfromLeft = circle.getCenterX();
		double disEnemyXfromRight = SCREEN_WIDTH - disEnemyXfromLeft;

		double disEnemyYfromUp = circle.getCenterY();
		double disMeXfromLeft = myCircle.getCenterX();
		double disMeXfromRight = SCREEN_WIDTH - disMeXfromLeft;

		double disMeYfromUp = myCircle.getCenterY();
		double distanceX = disMeXfromRight - disEnemyXfromRight;
		double distanceY = disEnemyYfromUp - disMeYfromUp;

		double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

		return distance;

	}

	public double getDistanceFromDaimond(Diamond diamond, Circle myCircle) {

		double disDaimondXfromLeft = diamond.getDaimond().getX();
		double disDaimondXfromRight = SCREEN_WIDTH - disDaimondXfromLeft;

		double disDaimondYfromUp = diamond.getDaimond().getY();
		double disMeXfromLeft = myCircle.getCenterX();
		double disMeXfromRight = SCREEN_WIDTH - disMeXfromLeft;

		double disMeYfromUp = myCircle.getCenterY();
		double distanceX = disMeXfromRight - disDaimondXfromRight;
		double distanceY = disDaimondYfromUp - disMeYfromUp;

		double distance = Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));

		return distance;

	}

	public void getMedia() {
		
		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());

		String musicFile = null;
		switch (startMenu.getModeCombo().getValue()) {

		case "Day":
			musicFile = "CircleAttackFiles\\202 - music 19.mp3";
			break;

		case "Night":
			musicFile = "CircleAttackFiles\\112 - music 12.mp3";
			break;

		case "Dust":
			musicFile = "CircleAttackFiles\\214 - music 31.mp3";
			break;
		default:
			musicFile = "CircleAttackFiles\\Mr.BlueSky (online-audio-converter.com).mp3";
		}

		File file = new File(musicFile);
		Media sound = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();

	}

	public void mediaSoundEffectCreation(String musicFile) {
		File file = new File(musicFile);
		Media sound = new Media(file.toURI().toString());
		soundEffect = new MediaPlayer(sound);
		musicFileStringArray.add(musicFile);
		soundEffectArray.add(soundEffect);
	}

	public void soundEffectLogic(String musicFile) {		
		int index = musicFileStringArray.indexOf(musicFile);				
		if(soundEffectArray.get(index).getStatus().toString().equals("PLAYING")){
			soundEffectArray.get(index).stop();
		}		
		soundEffectArray.get(index).play();
		soundEffectArray.get(index).setOnEndOfMedia(() -> {
			soundEffectArray.get(index).stop();
		});
	}

	public void getClockText() {

		gameSeconds += 1;
		sec += 1;

		if (sec == 10) {
			tenSec += 1;
			sec = 0;
		}
		if (tenSec == 6) {
			minutes += 1;
			tenSec = 0;
		}
		if (minutes == 10) {
			tenMinutes += 1;
			minutes = 0;
		}

		clockText.setText("SURVIVEL TIME: " + tenMinutes + "" + minutes + ":" + tenSec + "" + sec);
		clockText.setFont(Font.font(null, FontWeight.BOLD, 20));
		clockText.setLayoutX(SCREEN_WIDTH * 0.8);
		clockText.setLayoutY(SCREEN_HIGHT * 0.07);
		clockText.setFill(Color.RED);
		clockText.setStyle("-fx-font-weight: bold;");
		clockText.toFront();
	}

	public String getSurvivalTime() {
		return tenMinutes + "" + minutes + ":" + tenSec + "" + sec;

	}

	public void getScoreText() {
		scoreText.setText("SCORE: " + score);
		scoreText.setFont(Font.font(null, FontWeight.BOLD, 20));
		scoreText.setLayoutX(SCREEN_WIDTH * 0.02);
		scoreText.setLayoutY(SCREEN_HIGHT * 0.07);
		scoreText.setFill(Color.RED);
		scoreText.setStyle("-fx-font-weight: bold;");
		scoreText.toFront();
	}

	public void getExplosionAmountText() {
		explosionAmountText.setText("EXPLOSIONS: " + explosionAmount+"\n press (E)");
		explosionAmountText.setFont(Font.font(null, FontWeight.BOLD, 20));
		explosionAmountText.setLayoutX(SCREEN_WIDTH * 0.12);
		explosionAmountText.setLayoutY(SCREEN_HIGHT * 0.07);
		explosionAmountText.setFill(Color.PURPLE);
		explosionAmountText.toFront();
	}

	public void getPlayerNameText() {

		playerNameText.setText(startMenu.getPlayerName());
		playerNameText.setFont(Font.font(null, FontWeight.BOLD, 50));
		playerNameText.setLayoutX((SCREEN_WIDTH / 2) - ((playerNameText.getBoundsInLocal().getWidth()) / 2));
		playerNameText.setLayoutY(SCREEN_HIGHT * 0.1);
		playerNameText.setFill(startMenu.getMyCircle().getColor());
		playerNameText.toFront();

	}

	public void getShrinkText() {
		if (score >= minScoreForShrink && isShrinkTextOnScreen == false) {
			shrinkText.setText("BUY SHRINK (" + minScoreForShrink + ")\npress (S)");
			shrinkText.setFont(Font.font(null, FontWeight.BOLD, 20));
			shrinkText.setLayoutX(SCREEN_WIDTH * 0.25);
			shrinkText.setLayoutY(SCREEN_HIGHT * 0.07);
			shrinkText.setFill(Color.TOMATO);
			shrinkText.toFront();
			pane.getChildren().add(shrinkText);
			isShrinkTextOnScreen = true;
		}
		if (score < minScoreForShrink && isShrinkTextOnScreen == true) {

			pane.getChildren().remove(shrinkText);
			isShrinkTextOnScreen = false;
		}
	}

	public void shrinkStartLogic() {
		shrinkTimer = gameSeconds;
		if (score >= minScoreForShrink) {
			soundEffectLogic(shrinkSoundString);
			myCircle.setRadius(5);
			score = score - minScoreForShrink;
			getScoreText();
		}
	}

	public void shrinkStopLogic() {
		if (true && (gameSeconds - shrinkTimer == 30)) {
			myCircle.setRadius(30);
		}
	}

	public void clearKeyFrameFromTimeline(ArrayList<Timeline> timeLineArray) {
		for (int i = 0; i < timeLineArray.size(); i++) {
			timeLineArray.get(i).stop();
			timeLineArray.get(i).getKeyFrames().clear();
		}
	}

	public void allTextsToFront() {
		clockText.toFront();
		scoreText.toFront();
		explosionAmountText.toFront();
		text.toFront();
		playerNameText.toFront();
		shrinkText.toFront();

		if (isGameOver) {
			gameOver.getBottomBox().toFront();
			gameOver.getRecord().getTabelVbox().toFront();
			gameOver.getText().toFront();
		}
		if (isPaused) {
			pauseMenu.getBottomBox().toFront();
			gameOver.getText().toFront();
		}
	}

	public void comboNullDealer() {
		final int day = 0;
		final int medium = 1;
		if (startMenu.getModeCombo().getValue() == null) {
			startMenu.getModeCombo().getSelectionModel().select(day);
		}
		if (startMenu.getDifficultyCombo().getValue() == null) {
			startMenu.getDifficultyCombo().getSelectionModel().select(medium);
		}
	}

	public double getMaxRangCreationY() {
		return MAX_RANGE_CREATION_Y;
	}

	public double getMinRangCreationY() {
		return MIN_RANGE_CREATION_Y;
	}

}