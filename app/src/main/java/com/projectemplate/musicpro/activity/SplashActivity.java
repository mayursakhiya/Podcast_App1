package com.projectemplate.musicpro.activity;

import java.util.List;

import com.projectemplate.musicpro.config.GlobalValue;
import com.projectemplate.musicpro.modelmanager.ModelManager;
import com.projectemplate.musicpro.modelmanager.ModelManagerListener;
import com.projectemplate.musicpro.object.CategoryMusic;
import com.projectemplate.musicpro.util.LanguageUtil;
import com.projectemplate.musicpro.util.MySharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import e.aakriti.work.podcast_app.R;

public class SplashActivity extends FragmentActivity {
	private ModelManager modelManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		LanguageUtil.setLocale(new MySharedPreferences(this).getLanguage(),
				this);

		GlobalValue.constructor(this);
		modelManager = new ModelManager(this);
		modelManager.getBaseUrl(new ModelManagerListener() {
			@Override
			public void onSuccess(Object object) {
				getListMusicType();
			}

			@Override
			public void onError() {
				getListMusicType();
			}
		});
	}

	private void getListMusicType() {
		modelManager.getListMusicTypes(new ModelManagerListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object object) {
				GlobalValue.listCategoryMusics
						.addAll((List<CategoryMusic>) object);
				startMainActivity();
			}

			@Override
			public void onError() {
				Toast.makeText(
						SplashActivity.this,
						"There is an error with the internet connection. Music data cannot be loaded.",
						Toast.LENGTH_LONG).show();
				startMainActivity();
			}
		});
	}

	private void startMainActivity() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(getBaseContext(), SongListActivity.class));
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_left);
				finish();
			}
		}, 2000);
	}

	@Override
	public void onBackPressed() {
	}
}
