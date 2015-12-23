package e.aakriti.work.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import e.aakriti.work.imageloader.ImageLoader;
import e.aakriti.work.podcast_app.MainActivity;
import e.aakriti.work.podcast_app.R;

public class FeaturedSliderPagerAdapter extends PagerAdapter {

	Context context;

	public FeaturedSliderPagerAdapter(Context context) {
		// super();
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public int getCount() {
		// Return total pages, here one for each data item
		return 2;
	}

	// Create the given page (indicated by position)
	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View page;

		Log.e("position == ", "" + position);

		page = inflater.inflate(R.layout.featured_pager_item, null);
		ImageView image = (ImageView) page.findViewById(R.id.imageView1);
		TextView title = (TextView) page.findViewById(R.id.textTitle);
		TextView textcontent = (TextView) page.findViewById(R.id.textContent);
		TextView textcontent1 = (TextView) page.findViewById(R.id.textContent1);
		
		// ().setBackgroundResource(urls.get(position));
		// Add the page to the front of the queue
		ImageLoader loader = new ImageLoader(context);
		loader.DisplayImage(MainActivity.featured.get(position).getShow_img(), image);
		title.setText(MainActivity.featured.get(position).getTitle());
		textcontent.setText(MainActivity.featured.get(position).getCat_name());
		textcontent1.setText("Ep#"+MainActivity.featured.get(position).getNo_of_episode());
		((ViewPager) container).addView(page, 0);
		return page;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// See if object from instantiateItem is related to the given view
		// required by API
		return arg0 == (View) arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
		object = null;
	}
}