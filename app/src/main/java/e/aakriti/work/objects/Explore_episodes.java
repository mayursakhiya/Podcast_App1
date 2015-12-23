package e.aakriti.work.objects;

import org.json.JSONObject;

public class Explore_episodes {

	public static final String ID = "id";
	public static final String PNAME = "pname";
	public static final String NUMBER = "number";
	public static final String NAME = "name";
	public static final String SNAME = "sname";
	public static final String PODCASTER_ID = "podcast_id";
	public static final String DESCRIPTION = "desc";
	public static final String NAME_VI = "name_vi";
	public static final String LYRIC = "lyric";
	public static final String LYRICS_VI = "lyrics_vi";
	public static final String LINK = "link";
	public static final String SINGER_NAME = "singerName";
	public static final String SINGER_ID = "singerId";
	public static final String LISTEN = "listen";
	public static final String ALBUM_ID = "albumId";
	public static final String AUTHOR_ID = "authorId";
	public static final String CAT_ID = "categoryId";
	public static final String CREATED_DATE = "createDate";
	public static final String DOWNLOAD = "download";
	public static final String HOT = "hot";
	public static final String NEW = "new";
	public static final String STATUS = "status";
	public static final String LINK_APP = "link_app";
	public static final String ORDER_NO = "order_number";
	public static final String IMAGE = "image";
	public static final String CNAME = "cname";
	public static final String DURATION = "duration";
	
	private String id;
	private String pname;
	private String number;
	private String name;
	private String sname;
	private String podcast_id;
	private String desc;
	private String name_vi;
	private String lyric;
	private String lyrics_vi;
	private String link;
	
	
	public Explore_episodes(JSONObject obj) {
		// TODO Auto-generated constructor stub
		setAlbumId(obj.optString(ALBUM_ID));
		setAuthorId(obj.optString(AUTHOR_ID));
		setCategoryId(obj.optString(CAT_ID));
		setCname(obj.optString(CNAME));
		setCreateDate(obj.optString(CREATED_DATE));
		setDesc(obj.optString(DESCRIPTION));
		setDownload(obj.optString(DOWNLOAD));
		setDuration(obj.optString(DURATION));
		setHot(obj.optString(HOT));
		setId(obj.optString(ID));
		setImage(obj.optString(IMAGE));
		setLink(obj.optString(LINK));
		setLink_app(obj.optString(LINK_APP));
		setListen(obj.optString(LISTEN));
		setLyric(obj.optString(LYRIC));
		setLyrics_vi(obj.optString(LYRICS_VI));
		setName(obj.optString(NAME));
		setName_vi(obj.optString(NAME_VI));
		setNew1(obj.optString(NEW));
		setNumber(obj.optString(NUMBER));
		setOrder_number(obj.optString(ORDER_NO));
		setPname(obj.optString(PNAME));
		setPodcast_id(obj.optString(PODCASTER_ID));
		setSingerId(obj.optString(SINGER_ID));
		setSingerName(obj.optString(SINGER_NAME));
		setSname(obj.optString(SNAME));
		setStatus(obj.optString(STATUS));
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getPodcast_id() {
		return podcast_id;
	}
	public void setPodcast_id(String podcast_id) {
		this.podcast_id = podcast_id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getName_vi() {
		return name_vi;
	}
	public void setName_vi(String name_vi) {
		this.name_vi = name_vi;
	}
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
	}
	public String getLyrics_vi() {
		return lyrics_vi;
	}
	public void setLyrics_vi(String lyrics_vi) {
		this.lyrics_vi = lyrics_vi;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public String getSingerId() {
		return singerId;
	}
	public void setSingerId(String singerId) {
		this.singerId = singerId;
	}
	public String getListen() {
		return listen;
	}
	public void setListen(String listen) {
		this.listen = listen;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getDownload() {
		return download;
	}
	public void setDownload(String download) {
		this.download = download;
	}
	public String getHot() {
		return hot;
	}
	public void setHot(String hot) {
		this.hot = hot;
	}
	public String getNew1() {
		return new1;
	}
	public void setNew1(String new1) {
		this.new1 = new1;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLink_app() {
		return link_app;
	}
	public void setLink_app(String link_app) {
		this.link_app = link_app;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	private String singerName;
	private String singerId;
	private String listen;
	private String albumId;
	private String authorId;
	private String categoryId;
	private String createDate;
	private String download;
	private String hot;
	private String new1;
	private String status;
	private String link_app;
	private String order_number;
	private String image;
	private String cname;
	private String duration;
	
	}
