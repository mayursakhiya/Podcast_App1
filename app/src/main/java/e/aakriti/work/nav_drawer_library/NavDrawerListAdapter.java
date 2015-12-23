package e.aakriti.work.nav_drawer_library;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import e.aakriti.work.podcast_app.R;

@SuppressLint("InflateParams")
public class NavDrawerListAdapter extends BaseAdapter {
	private ArrayList<NavDrawerItem> navDrawerItems;
	private LayoutInflater mInflater;
	private int selectedItem;
	private Context mContext;

	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
		mInflater = LayoutInflater.from(context);
		this.navDrawerItems = navDrawerItems;
		mContext = context;
	}

	public int getCount() {
		return navDrawerItems.size();
	}

	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}
	
	public void selectItem(int selectedItem){
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtTitle.setText(navDrawerItems.get(position).getTitle());
        holder.imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		return convertView;
	}
	
	class ViewHolder
	{
		TextView txtTitle;
		ImageView imgIcon;
	}
}