
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TableViewSample {

	private TableView<Person> table = new TableView<Person>();
	private final Label label = new Label("Rank Table");
	private TableColumn<Person, String> placeCol = new TableColumn<Person, String>("Place");
	private TableColumn<Person, String> nameCol = new TableColumn<Person, String>("Name");
	private TableColumn<Person, String> scoreCol = new TableColumn<Person, String>("Score");
	private TableColumn<Person, String> timeCol = new TableColumn<Person, String>("Survivel Time");
	private VBox vBox = new VBox();
	private ObservableList<Person> data = FXCollections.observableArrayList();

	public TableViewSample() {

		setTabel();
		setVBox();
		label.setFont(new Font("Arial", 20));
	}

	public VBox getVBox() {
		return vBox;
	}

	public void setVBox() {
		vBox.getChildren().addAll(label, table);
		// vBox.setPadding(new Insets(20,20,20,20));
	}

	public TableView<Person> getTable() {
		return table;
	}

	@SuppressWarnings("unchecked")
	public void setTabel() {

		table.setEditable(true);

		placeCol.setCellValueFactory(new PropertyValueFactory<Person, String>("place"));

		nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		nameCol.setMinWidth(70);

		scoreCol.setCellValueFactory(new PropertyValueFactory<Person, String>("score"));
		scoreCol.setMinWidth(60);

		timeCol.setCellValueFactory(new PropertyValueFactory<Person, String>("survivelTime"));
		timeCol.setMinWidth(80);

		table.getColumns().addAll(placeCol, nameCol, scoreCol, timeCol);

	}

	public void setRowToTable(String place, String name, int score, String survivelTime) {
		data.add(new Person(place, name, score, survivelTime));
	}

	public void setDataToTabel() {
		table.setItems(data);

	}

	public static class Person {

		private SimpleStringProperty place;
		private SimpleStringProperty name;
		private SimpleIntegerProperty score;
		private SimpleStringProperty survivelTime;

		private Person(String place, String name, int score, String survivelTime) {

			this.place = new SimpleStringProperty(place);
			this.name = new SimpleStringProperty(name);
			this.score = new SimpleIntegerProperty(score);
			this.survivelTime = new SimpleStringProperty(survivelTime);

		}

		public String getPlace() {
			return place.get();
		}

		public void setPlace(String place) {
			this.place.set(place);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String name) {
			this.name.set(name);
		}

		public int getScore() {
			return score.get();
		}

		public void setScore(int score) {
			this.score.set(score);
		}

		public String getSurvivelTime() {
			return survivelTime.get();
		}

		public void setSurvivelTime(String time) {
			survivelTime.set(time);
		}
	}

}
