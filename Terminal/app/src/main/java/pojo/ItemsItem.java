package pojo;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class ItemsItem{

	@JsonField(name ="owner")
	private Owner owner;

	@JsonField(name ="score")
	private int score;

	@JsonField(name ="is_accepted")
	private boolean isAccepted;

	@JsonField(name ="last_activity_date")
	private int lastActivityDate;

	@JsonField(name ="creation_date")
	private int creationDate;

	@JsonField(name ="answer_id")
	private int answerId;

	@JsonField(name ="question_id")
	private int questionId;

	@JsonField(name ="last_edit_date")
	private int lastEditDate;

	public void setOwner(Owner owner){
		this.owner = owner;
	}

	public Owner getOwner(){
		return owner;
	}

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return score;
	}

	public void setIsAccepted(boolean isAccepted){
		this.isAccepted = isAccepted;
	}

	public boolean isIsAccepted(){
		return isAccepted;
	}

	public void setLastActivityDate(int lastActivityDate){
		this.lastActivityDate = lastActivityDate;
	}

	public int getLastActivityDate(){
		return lastActivityDate;
	}

	public void setCreationDate(int creationDate){
		this.creationDate = creationDate;
	}

	public int getCreationDate(){
		return creationDate;
	}

	public void setAnswerId(int answerId){
		this.answerId = answerId;
	}

	public int getAnswerId(){
		return answerId;
	}

	public void setQuestionId(int questionId){
		this.questionId = questionId;
	}

	public int getQuestionId(){
		return questionId;
	}

	public void setLastEditDate(int lastEditDate){
		this.lastEditDate = lastEditDate;
	}

	public int getLastEditDate(){
		return lastEditDate;
	}

	@Override
 	public String toString(){
		return 
			"ItemsItem{" + 
			"owner = '" + owner + '\'' + 
			",score = '" + score + '\'' + 
			",is_accepted = '" + isAccepted + '\'' + 
			",last_activity_date = '" + lastActivityDate + '\'' + 
			",creation_date = '" + creationDate + '\'' + 
			",answer_id = '" + answerId + '\'' + 
			",question_id = '" + questionId + '\'' + 
			",last_edit_date = '" + lastEditDate + '\'' + 
			"}";
		}
}