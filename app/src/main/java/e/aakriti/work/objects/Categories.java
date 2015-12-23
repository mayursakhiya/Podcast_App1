package e.aakriti.work.objects;

import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;

public class Categories {
	
	public static final String ID = "id";
	public static final String PARENT_ID = "parent_id";
    public static final String NAME = "name";
    public static final String FULLNAME = "full_name";
    public static final String CAT_KEYWORD = "category_keyword";
	public static final String IMAGE = "image";
    
	private String id;
	private String parent_id;
	private String name;
	private String full_name;
	private String category_keyword;
	private String image;
	private Boolean isSelected;
	
	
	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Categories(JSONObject obj) {
		
		// TODO Auto-generated constructor stub
		setFull_name(obj.optString(FULLNAME));
		setCategory_keyword(obj.optString(CAT_KEYWORD));
		setId(obj.optString(ID));
		setImage(obj.optString(IMAGE));
		setName(obj.optString(NAME));
		setParent_id(obj.optString(PARENT_ID));
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getCategory_keyword() {
		return category_keyword;
	}
	public void setCategory_keyword(String category_keyword) {
		this.category_keyword = category_keyword;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
}
