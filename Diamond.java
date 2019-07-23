
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Diamond extends Shape {

	private Rectangle daimond = new Rectangle(10, 10);

	public Diamond(Color color) {
		super(color);
		createDaimond();
	}

	public void createDaimond() {

		daimond.setFill(getColor());
		
		daimond.setRotate(45);
	}

	public Rectangle getDaimond() {
		return daimond;		
	}
	
}
