
public class Person implements Comparable<Person> {

	private String name;
	private int score;
	private String survivelTime;

	public Person(String name, int score, String survivelTime) {
		super();
		this.name = name;
		this.score = score;
		this.survivelTime = survivelTime;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getSurvivelTime() {
		return survivelTime;
	}

	public void setSurvivelTime(String survivelTime) {
		this.survivelTime = survivelTime;
	}

	@Override
	public int compareTo(Person person) {
		if (getScore() < person.getScore())
			return 1;
		else if (getScore() > person.getScore())
			return -1;
		else {
			if (getSurvivelTime().compareTo(person.getSurvivelTime()) < 0)
				return 1;
			else if (getSurvivelTime().compareTo(person.getSurvivelTime()) > 0)
				return -1;
		}
		return 0;
	}

}
