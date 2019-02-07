public class QuizClass {
	String choice1 = "";
	String choice2 = "";
	String choice3 = "";
	String choice4 = "";
	String choice5 = "";
	String answer = "";
	String question = "";
	int i = 0;
	
	// CONSTRUCTOR
	public QuizClass(int i, String question, String choice1, String choice2, String choice3, String choice4, String choice5, String answer) {
		this.choice1 = choice1; // THE CHOICE 1 FROM THE FILE IS SAVED IN THE CLASS VARIABLE CHOICE 1
		this.choice2 = choice2;
		this.choice3 = choice3;
		this.choice4 = choice4;
		this.choice5 = choice5;
		this.answer = answer; // THE ANSWER FROM THE FILE IS SAVED IN THE CLASS VARIABLE ANSWER
		this.question = question;
		this.i = i; // KEEPS TRACK OF QUESTION NUMBER
	}
	
	// METHOD TO GET CHOICE 1 VALUE
	public String getChoice1() {
		return choice1;
	}
	
	// METHOD TO GET CHOICE 2 VALUE 
	public String getChoice2() {
		return choice2;
	}
	
	// METHOD TO GET CHOICE 3 VALUE
	public String getChoice3() {
		return choice3;
	}
	
	// METHOD TO GET CHOICE 4 VALUE
	public String getChoice4() {
		return choice4;
	}
	
	// METHOD TO GET CHOICE 5 VALUE
	public String getChoice5() {
		return choice5;
	}
	
	// METHOD TO GET QUESTION 
	public String getQuestion() {
		return question;
	}
	
	// METHOD TO GET ANSWER
	public String getAnswer() {
		return answer;
	}
}