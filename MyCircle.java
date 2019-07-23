import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MyCircle extends Shape {

	private double radius;
    private Circle circle = new Circle();
	
	
    
	public MyCircle() {
		super(Color.BLUE);
		setColor(Color.BLUE);
		setCircleFill();
	}


	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}


	public Circle getCircle() {
		return circle;
	}


	public void setCircle(Circle circle) {
		this.circle = circle;
	}

	public void setCircleFill() {
		circle.setFill(getColor());
	}
	
}
