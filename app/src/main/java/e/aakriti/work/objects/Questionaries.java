package e.aakriti.work.objects;

import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;

public class Questionaries {
	
	public static final String ID = "id";
	public static final String Question = "question";
    public static final String Answers = "answers";
    public static final String SelectedAnswer = "";
    
    private String id;
    private String selected_answer;
    
	public String getSelected_answer() {
		return selected_answer;
	}

	public void setSelected_answer(String selected_answer) {
		this.selected_answer = selected_answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	private String question;
	private String answers;
	
	public Questionaries(JSONObject obj) {
		// TODO Auto-generated constructor stub
		
		setAnswers(obj.optString(Answers));
		setId(obj.optString(ID));
		setQuestion(obj.optString(Question));
		
	}
	
	
}
