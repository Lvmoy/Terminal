package pojo;

import java.util.List;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class Response{

	@JsonField(name ="quota_max")
	private int quotaMax;

	@JsonField(name ="quota_remaining")
	private int quotaRemaining;

	@JsonField(name ="has_more")
	private boolean hasMore;

	@JsonField(name ="items")
	private List<ItemsItem> items;

	public void setQuotaMax(int quotaMax){
		this.quotaMax = quotaMax;
	}

	public int getQuotaMax(){
		return quotaMax;
	}

	public void setQuotaRemaining(int quotaRemaining){
		this.quotaRemaining = quotaRemaining;
	}

	public int getQuotaRemaining(){
		return quotaRemaining;
	}

	public void setHasMore(boolean hasMore){
		this.hasMore = hasMore;
	}

	public boolean isHasMore(){
		return hasMore;
	}

	public void setItems(List<ItemsItem> items){
		this.items = items;
	}

	public List<ItemsItem> getItems(){
		return items;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"quota_max = '" + quotaMax + '\'' + 
			",quota_remaining = '" + quotaRemaining + '\'' + 
			",has_more = '" + hasMore + '\'' + 
			",items = '" + items + '\'' + 
			"}";
		}
}