package e.aakriti.work.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.imageloader.ImageLoader;
import e.aakriti.work.objects.Categories;
import e.aakriti.work.podcast_app.R;

public class CategoriesGridAdapter extends BaseAdapter {

    private Context context;
    private final List<Categories> gridValues;
    ViewHolder holder;

    //Constructor to initialize values
    public CategoriesGridAdapter(Context context, List<Categories> allCategories) {

        this.context        = context;
        this.gridValues     = allCategories;
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return gridValues.size();
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
        	
        	LayoutInflater inflater = (LayoutInflater) context
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate( R.layout.grid_item , null);
            holder = new ViewHolder();
            
            // set value into textview

            holder.tvCategoryName = (TextView) convertView
                    .findViewById(R.id.textView1);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.chkBox);
            holder.rel = (RelativeLayout) convertView.findViewById(R.id.rel1);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
        	holder = (ViewHolder) convertView.getTag();
        }

        holder.tvCategoryName.setText(gridValues.get(position).getFull_name());
        
        holder.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checkbox.performClick();
            }
        });
        
        holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				Toast.makeText(context, ""+gridValues.get(position).getFull_name(), Toast.LENGTH_LONG).show();
			}
		});
        
        ImageLoader image = new ImageLoader(context);
        
        image.DisplayImage(gridValues.get(position).getImage(), holder.imageView);
        return convertView;
    }
    
    
    class ViewHolder {
        public TextView tvCategoryName;
        public CheckBox checkbox;
        public RelativeLayout rel;
        public ImageView imageView;
    }
}
