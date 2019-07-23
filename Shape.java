import javafx.scene.paint.Color;

public abstract class Shape {

	private Color color;

	public Shape(Color color) {
		setColor(color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

	
	
}
