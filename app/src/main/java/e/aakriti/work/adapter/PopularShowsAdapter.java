package e.aakriti.work.adapter;

import java.util.List;

import com.projectemplate.musicpro.activity.SongListActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import e.aakriti.work.imageloader.ImageLoader;
import e.aakriti.work.objects.PopularShows;
import e.aakriti.work.podcast_app.MainActivity;
import e.aakriti.work.podcast_app.R;

public class PopularShowsAdapter extends BaseAdapter {

	private Context context;
	private final List<PopularShows> gridValues;
	ViewHolder holder;

	// Constructor to initialize values
	public PopularShowsAdapter(Context context) {

		this.context = context;
		this.gridValues = MainActivity.popular;
	}

	@Override
	public int getCount() {

		// Number of times getView method call depends upon gridValues.length
		return 4;
	}

	@Override
	public Object getItem(int position) {

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	// Number of times getView method call depends upon gridValues.length

	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			convertView = inflater.inflate(R.layout.popular_grid_item, null);
			holder = new ViewHolder();

			// set value into textview

			holder.image = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.title = (TextView) convertView.findViewById(R.id.textTitle);
			holder.textcontent = (TextView) convertView.findViewById(R.id.textContent);
			holder.textcontent1 = (TextView) convertView.findViewById(R.id.textContent1);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.title.setText(gridValues.get(position).getTitle());
		holder.textcontent.setText(gridValues.get(position).getCat_name());
		holder.textcontent1.setText("Ep#" + gridValues.get(position).getNo_of_episode());

		ImageLoader loader = new ImageLoader(context);
		loader.DisplayImage(gridValues.get(position).getShow_img(), holder.image);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(context, SongListActivity.class);
				i.putExtra("selected_song", gridValues.get(position));
				context.startActivity(i);

			}
		});
		return convertView;
	}

	class ViewHolder {

		ImageView image;
		TextView title, textcontent, textcontent1;

	}
}
