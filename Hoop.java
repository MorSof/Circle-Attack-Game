import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Hoop extends Shape {

	private Circle circle;

	public Hoop(Color color) {
		super(Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0.5));
		circle=new Circle();
		setHoop();
	}
	
	public void setHoop() {
		
		circle.setFill(getColor());
		circle.setStroke(Color.PURPLE);

	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}

}
