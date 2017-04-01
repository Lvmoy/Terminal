package pojo;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Owner{

	@JsonField(name ="profile_image")
	private String profileImage;

	@JsonField(name ="user_type")
	private String userType;

	@JsonField(name ="user_id")
	private int userId;

	@JsonField(name ="link")
	private String link;

	@JsonField(name ="reputation")
	private int reputation;

	@JsonField(name ="display_name")
	private String displayName;

	@JsonField(name ="accept_rate")
	private int acceptRate;

	public void setProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public String getProfileImage(){
		return profileImage;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

	public String getUserType(){
		return userType;
	}

	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getUserId(){
		return userId;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setReputation(int reputation){
		this.reputation = reputation;
	}

	public int getReputation(){
		return reputation;
	}

	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}

	public String getDisplayName(){
		return displayName;
	}

	public void setAcceptRate(int acceptRate){
		this.acceptRate = acceptRate;
	}

	public int getAcceptRate(){
		return acceptRate;
	}

	@Override
 	public String toString(){
		return 
			"Owner{" + 
			"profile_image = '" + profileImage + '\'' + 
			",user_type = '" + userType + '\'' + 
			",user_id = '" + userId + '\'' + 
			",link = '" + link + '\'' + 
			",reputation = '" + reputation + '\'' + 
			",display_name = '" + displayName + '\'' + 
			",accept_rate = '" + acceptRate + '\'' + 
			"}";
		}
}