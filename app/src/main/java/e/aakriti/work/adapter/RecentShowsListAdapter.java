package e.aakriti.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import e.aakriti.work.imageloader.ImageLoader;
import e.aakriti.work.objects.Categories;
import e.aakriti.work.objects.RecentShows;
import e.aakriti.work.podcast_app.MainActivity;
import e.aakriti.work.podcast_app.R;

import java.util.ArrayList;

public class RecentShowsListAdapter extends BaseAdapter {
	
    private LayoutInflater mInflater;
    private ArrayList<RecentShows> allRecentshows = new ArrayList<RecentShows>();
    Context context;

    public RecentShowsListAdapter(Context context) {
    	this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        allRecentshows = MainActivity.recent;
    }

    @Override
    public int getCount() {
        return allRecentshows.size();
    }

    @Override
    public Object getItem(int position) {
        return allRecentshows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.recent_show_item, parent, false);
            h = new ViewHolder();

            h.tvCategoryName = (TextView) convertView.findViewById(R.id.category);
            h.title = (TextView) convertView.findViewById(R.id.title);
            h.title1 = (TextView) convertView.findViewById(R.id.title1);
            h.imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }


        h.title.setText(allRecentshows.get(position).getName());
        h.title1.setText(allRecentshows.get(position).getSname());
        h.tvCategoryName.setText(allRecentshows.get(position).getDesc());
        
        ImageLoader loader = new ImageLoader(context);
        loader.DisplayImage(allRecentshows.get(position).getImage(), h.imageView);
       
        return convertView;
    }

    class ViewHolder {
        public TextView tvCategoryName,title,title1;
        ImageView imageView;
    }
}

