
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class Cloud extends Shape {

	private Main main = new Main();
	private Arc arc1 = new Arc();
	private Arc arc2 = new Arc();
	private Arc arc3 = new Arc();
	private int arc1X = 1280;
	private double arc1y = main.getMinRangCreationY()
			+ (int) (Math.random() * (main.getMaxRangCreationY() - main.getMinRangCreationY()));

	public Cloud() {
		super(Color.WHITE);
		setArc1(arc1);
		setArc2(arc2);
		setArc3(arc3);
	}

	public Arc getArc1() {
		return arc1;
	}

	public void setArc1(Arc arc1) {

		setGeneralArc(arc1, 0);
		this.arc1 = arc1;
	}

	public Arc getArc2() {
		return arc2;
	}

	public void setArc2(Arc arc2) {
		setGeneralArc(arc2, 50);
		this.arc2 = arc2;
	}

	public Arc getArc3() {
		return arc3;
	}

	public void setArc3(Arc arc3) {
		setGeneralArc(arc3, 100);
		this.arc3 = arc3;
	}

	public void setGeneralArc(Arc arc, double Xoffset) {

		arc.setCenterX(arc1X + Xoffset);
		arc.setCenterY(arc1y);
		arc.setStartAngle(0);
		arc.setRadiusX(50);
		arc.setRadiusY(50);
		arc.setFill(getColor());
		arc.setStroke(Color.BLACK);
		arc.setType(ArcType.ROUND);
		arc.setLength(180);
	}

}
