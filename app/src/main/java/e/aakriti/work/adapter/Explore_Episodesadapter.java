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
import e.aakriti.work.objects.Explore_episodes;
import e.aakriti.work.objects.PopularShows;
import e.aakriti.work.podcast_app.MainActivity;
import e.aakriti.work.podcast_app.R;

public class Explore_Episodesadapter extends BaseAdapter {

    private Context context;
    private final List<Explore_episodes> gridValues;
    ViewHolder holder;

    //Constructor to initialize values
    public Explore_Episodesadapter(Context context) {

        this.context        = context;
        this.gridValues     = MainActivity.explore_episodes;
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

            convertView = inflater.inflate( R.layout.popular_grid_item , null);
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

        holder.title.setText(gridValues.get(position).getName());
        holder.textcontent.setText(gridValues.get(position).getCname());
       // holder.textcontent1.setText("Ep#"+gridValues.get(position).getNo_of_episode());
        
        ImageLoader loader = new ImageLoader(context);
        loader.DisplayImage(gridValues.get(position).getImage(), holder.image);
        return convertView;
    }
    
    
    class ViewHolder {
    	
    	ImageView image;
    	TextView title,textcontent,textcontent1;
    	
       
    }
}
