package e.aakriti.work.objects;

import org.json.JSONObject;

public class FollowingShows {
	
	public static final String ID = "id";
	public static final String CATEGORYID = "category_id";
    public static final String TITLE = "title";
    public static final String SUB_TITLE = "sub_title";
    public static final String META_TITLE = "meta_title";
	public static final String META_DESC = "meta_description";
	public static final String META_KEYWORD = "meta_keyword";
	public static final String DESCRIPTION = "description";
    public static final String TAG = "tag";
    public static final String RSS = "rss";
    public static final String FEATURED = "featured";
	public static final String COVER_PAGE = "cover_page";
	public static final String SHOW_IMAGE = "show_img";
	public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String STATUS = "status";
    public static final String ROLEID = "role_id";
	public static final String DEL_FLAG = "del_flag";
	public static final String ALERT = "alert";
    public static final String PODCASTER_ID = "podcaster_id";
    public static final String NO_OF_EPISODES = "no_of_episode";
    
	private String id;
	private String category_id;
	private String title;
	private String sub_title;
	private String meta_title;
	private String meta_description;
	private String meta_keyword;
	private String description;
	private String tag;
	private String featured;
	private String cover_page;
	private String show_img;
	private String created_at;
	private String updated_at;
	private String status;
	private String role_id;
	private String del_flag;
	private String alert;
	private String podcaster_id;
	private String no_of_episode;
	
	
	public FollowingShows(JSONObject obj) {
		
		setAlert(obj.optString(ALERT));
		setCategory_id(obj.optString(CATEGORYID));
		setCover_page(obj.optString(COVER_PAGE));
		setCreated_at(obj.optString(CREATED_AT));
		setUpdated_at(obj.optString(UPDATED_AT));
		setDel_flag(obj.optString(DEL_FLAG));
		setDescription(obj.optString(DESCRIPTION));
		setFeatured(obj.optString(FEATURED));
		setId(obj.optString(ID));
		setMeta_description(obj.optString(META_DESC));
		setMeta_keyword(obj.optString(META_KEYWORD));
		setMeta_title(obj.optString(META_TITLE));
		setNo_of_episode(obj.optString(NO_OF_EPISODES));
		setPodcaster_id(obj.optString(PODCASTER_ID));
		setRole_id(obj.optString(ROLEID));
		setShow_img(obj.optString(SHOW_IMAGE));
		setStatus(obj.optString(STATUS));
		setSub_title(obj.optString(SUB_TITLE));
		setTag(obj.optString(TAG));
		setTitle(obj.optString(TITLE));
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getMeta_title() {
		return meta_title;
	}
	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}
	public String getMeta_description() {
		return meta_description;
	}
	public void setMeta_description(String meta_description) {
		this.meta_description = meta_description;
	}
	public String getMeta_keyword() {
		return meta_keyword;
	}
	public void setMeta_keyword(String meta_keyword) {
		this.meta_keyword = meta_keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getFeatured() {
		return featured;
	}
	public void setFeatured(String featured) {
		this.featured = featured;
	}
	public String getCover_page() {
		return cover_page;
	}
	public void setCover_page(String cover_page) {
		this.cover_page = cover_page;
	}
	public String getShow_img() {
		return show_img;
	}
	public void setShow_img(String show_img) {
		this.show_img = show_img;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public String getPodcaster_id() {
		return podcaster_id;
	}
	public void setPodcaster_id(String podcaster_id) {
		this.podcaster_id = podcaster_id;
	}
	public String getNo_of_episode() {
		return no_of_episode;
	}
	public void setNo_of_episode(String no_of_episode) {
		this.no_of_episode = no_of_episode;
	}
	
	
}
