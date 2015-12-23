package e.aakriti.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import e.aakriti.work.objects.Categories;
import e.aakriti.work.podcast_app.R;

import java.util.ArrayList;

public class CategoriesListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Categories> allCategories = new ArrayList<Categories>();

    public CategoriesListAdapter(Context context, ArrayList<Categories> allCategories) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.allCategories = allCategories;
    }

    @Override
    public int getCount() {
        return allCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return allCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.category_item, parent, false);
            h = new ViewHolder();

            h.tvCategoryName = (TextView) convertView.findViewById(R.id.lblListItem);

            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }


        h.tvCategoryName.setText(allCategories.get(position).getFull_name());
       
        return convertView;
    }

    class ViewHolder {
        public TextView tvCategoryName;
    }
}

