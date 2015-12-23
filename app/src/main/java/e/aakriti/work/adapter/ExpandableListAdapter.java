package e.aakriti.work.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import e.aakriti.work.common.Utility;
import e.aakriti.work.objects.Questionaries;
import e.aakriti.work.podcast_app.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	ArrayList<Questionaries> allQues;

	public ExpandableListAdapter(Context context, List<String> listDataHeader,
			HashMap<String, List<String>> listChildData, ArrayList<Questionaries> allQues) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
		this.allQues = allQues;

		/*
		 * for(int i=0;i<_listDataChild.size();i++) {
		 * 
		 * }
		 */
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {

		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_item, null);
		}

		CheckedTextView txtListChild = (CheckedTextView) convertView.findViewById(R.id.lblListItem);

		txtListChild.setText(childText);
		try {
			if (Utility.isNotNull(allQues.get(groupPosition).getSelected_answer()) && allQues.get(groupPosition).getSelected_answer().equalsIgnoreCase(childText)) {
				txtListChild.setChecked(true);
			}

			else {
				txtListChild.setChecked(false);
			}
		} catch (Exception e) {

		}
		txtListChild.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (((CheckedTextView) v).isChecked()) {
					((CheckedTextView) v).setChecked(false);
					allQues.get(groupPosition).setSelected_answer("");
				} else {
					((CheckedTextView) v).setChecked(true);
					allQues.get(groupPosition).setSelected_answer(childText);
				}
				
			}
		});

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_group, null);
		}

		TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
