package e.aakriti.work.podcast_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryEpisodeActivity extends Activity{
	
	int cat_id;
	String title;
	GridView category_episodes;
	View header;
	TextView title_Txt;
	ImageView menuImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.category_episode);
		
		header = (View) findViewById(R.id.layout_header);
		title_Txt = (TextView) header.findViewById(R.id.titleTxt);
		menuImg = (ImageView) header.findViewById(R.id.menuImg);
		
		menuImg.setImageResource(R.drawable.search);
		
	}
	

}
