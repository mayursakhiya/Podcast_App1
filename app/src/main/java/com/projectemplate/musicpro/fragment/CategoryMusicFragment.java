package com.projectemplate.musicpro.fragment;

import com.projectemplate.musicpro.BaseFragment;
import com.projectemplate.musicpro.activity.SongListActivity;
import com.projectemplate.musicpro.adapter.CategoryMusicAdapter;
import com.projectemplate.musicpro.config.GlobalValue;
import com.projectemplate.musicpro.slidingmenu.SlidingMenu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import e.aakriti.work.podcast_app.R;

public class CategoryMusicFragment extends BaseFragment {
	private GridView grvCategoryMusic;
	private CategoryMusicAdapter categoryMusicAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_category_music, container, false);
//		initUIBase(view);
//		setButtonMenu(view);
		return view;
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			getMainActivity().menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			getMainActivity().setVisibilityFooter();
			categoryMusicAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void initUIBase(View view) {
		super.initUIBase(view);
//		setHeaderTitle(R.string.categoryMusic);
		grvCategoryMusic = (GridView) view.findViewById(R.id.grvCategoryMusic);
		if (GlobalValue.listCategoryMusics!=null&&GlobalValue.listCategoryMusics.size()>0) {
			
		categoryMusicAdapter = new CategoryMusicAdapter(getActivity(), GlobalValue.listCategoryMusics);
		grvCategoryMusic.setAdapter(categoryMusicAdapter);
		}
		grvCategoryMusic.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View v, int position, long l) {
				getMainActivity().currentMusicType = position;
				getMainActivity().gotoFragment(SongListActivity.LIST_SONG_FRAGMENT);
			}
		});
	}
}
