import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Record {

	private ArrayList<Person> personList = new ArrayList<Person>();
	private Text recordText = new Text();
	private File file = new File("CircleAttackFiles\\Highest Record.txt");
	private PrintWriter write = null;
	private TableViewSample recordTable = new TableViewSample();
	private final int TOP_SCORERS = 20;

	public Record() {
		super();
		loadScore();
	}

	public void setNewRank(String name, int score, String survivalTime) {

		if (file.length() > 0 && personList.get(personList.size() - 1).getScore() >= score && personList.size() >= 20) {
			setTable();
		} else {

			Person person = new Person(name, score, survivalTime);
			personList.add(person);
			Collections.sort(personList);
			saveScore();
			setTable();
			write.close();
		}
	}

	public PrintWriter getPrintWriter() {
		return write;

	}

	public ArrayList<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<Person> personList) {
		this.personList = personList;
	}

	public void loadScore() {
		Scanner read = null;
		try {
			read = new Scanner(file);
		} catch (FileNotFoundException e) {
			try {
				if (file.createNewFile())
					System.out.println("new file has created named: " + file.getName());
				else
					System.out.println("Not succesful file creation 1");
			} catch (IOException e1) {
				System.out.println("Not succesful file creation 2");
				e1.printStackTrace();
			}

		}

		while (file.length() > 0 && read.hasNext()) {
			Person person = new Person(read.next(), read.nextInt(), read.next());
			personList.add(person);
		}
	}

	public void saveScore() {

		try {
			write = new PrintWriter(file);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		write.println(toStringForFile());

	}

	public void setText(double layOutX, double layOutY) {

		recordText.setText(toString());
		recordText.setCache(true);
		recordText.setFill(Color.BLACK);
		recordText.setFont(Font.font(null, FontWeight.BOLD, 20));
		recordText.setLayoutX(layOutX);
		recordText.setLayoutY(layOutY);
		recordText.toFront();
	}

	public Text getText() {

		return recordText;

	}

	public String toStringForFile() {
		String string = "";

		if (personList.size() >= TOP_SCORERS) {

			for (int i = 0; i < TOP_SCORERS; i++) {
				string += personList.get(i).getName() + " " + personList.get(i).getScore() + " "
						+ personList.get(i).getSurvivelTime() + "\n";
			}

		} else {

			for (int i = 0; i < personList.size(); i++) {
				string += personList.get(i).getName() + " " + personList.get(i).getScore() + " "
						+ personList.get(i).getSurvivelTime() + "\n";
			}
		}
		return string;

	}

	public void setTable() {

		if (personList.size() >= TOP_SCORERS) {
			for (int i = 0; i < TOP_SCORERS; i++) {
				recordTable.setRowToTable("" + (i + 1), personList.get(i).getName(), personList.get(i).getScore(),
						personList.get(i).getSurvivelTime());
			}

		} else {
			for (int i = 0; i < personList.size(); i++) {
				recordTable.setRowToTable("" + (i + 1), personList.get(i).getName(), personList.get(i).getScore(),
						personList.get(i).getSurvivelTime());
			}
		}
		recordTable.setDataToTabel();
	}

	public TableView<?> getTable() {
		return recordTable.getTable();
	}

	public VBox getTabelVbox() {
		return recordTable.getVBox();

	}

}
