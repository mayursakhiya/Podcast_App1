package com.projectemplate.musicpro.activity;

import java.util.ArrayList;
import java.util.List;

import com.projectemplate.musicpro.config.GlobalValue;
import com.projectemplate.musicpro.database.DatabaseUtility;
import com.projectemplate.musicpro.fragment.AboutFragment;
import com.projectemplate.musicpro.fragment.CategoryMusicFragment;
import com.projectemplate.musicpro.fragment.ListSongsFragment;
import com.projectemplate.musicpro.fragment.PlayerFragment;
import com.projectemplate.musicpro.fragment.PlaylistFragment;
import com.projectemplate.musicpro.fragment.SearchFragment;
import com.projectemplate.musicpro.fragment.SettingsFragment;
import com.projectemplate.musicpro.modelmanager.ModelManager;
import com.projectemplate.musicpro.modelmanager.ModelManagerListener;
import com.projectemplate.musicpro.object.CategoryMusic;
import com.projectemplate.musicpro.object.Playlist;
import com.projectemplate.musicpro.object.Song;
import com.projectemplate.musicpro.service.MusicService;
import com.projectemplate.musicpro.service.MusicService.ServiceBinder;
import com.projectemplate.musicpro.service.PlayerListener;
import com.projectemplate.musicpro.slidingmenu.SlidingMenu;
import com.projectemplate.musicpro.util.LanguageUtil;
import com.projectemplate.musicpro.util.Logger;
import com.projectemplate.musicpro.util.MySharedPreferences;
import com.projectemplate.musicpro.widget.AutoBgButton;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.objects.PopularShows;
import e.aakriti.work.podcast_app.R;

public class SongListActivity extends AppCompatActivity implements OnClickListener {
	public static final int TOP_CHART = 0;
	public static final int NOMINATIONS = 1;
	public static final int CATEGORY_MUSIC = 2;
	public static final int PLAYLIST = 3;
	public static final int SEARCH = 4;
	public static final int GOOD_APP = 5;
	public static final int ABOUT = 6;
	public static final int EXIT_APP = 7;

	public static final int LIST_SONG_FRAGMENT = 0;
	public static final int CATEGORY_MUSIC_FRAGMENT = 1;
	public static final int PLAYLIST_FRAGMENT = 2;
	public static final int SEARCH_FRAGMENT = 3;
	public static final int SETTING_FRAGMENT = 4;
	public static final int PLAYER_FRAGMENT = 5;
	public static final int ABOUT_FRAGMENT = 6;

	public static final int FROM_LIST_SONG = 0;
	public static final int FROM_NOTICATION = 1;
	public static final int FROM_SEARCH = 2;
	public static final int FROM_OTHER = 3;

	public static final int NOTIFICATION_ID = 231109;

	private FragmentManager fm;
	private Fragment[] arrayFragments;
	public SlidingMenu menu;
	public ModelManager modelManager;

	private AutoBgButton btnPreviousFooter, btnPlayFooter, btnNextFooter;
	// private ImageView imgSongFooter;
	private View layoutPlayerFooter;
	private TextView lblSongNameFooter, lblArtistFooter;

	private TextView lblTopChart, lblNominations, lblCategoryMusic, lblPlaylist, lblSearch, lblGoodApp, lblAbout,
			lblExitApp;

	// private LinearLayout llAdview;
	// private InterstitialAd interstitialAd;
	// private AdView adView;
	// AdRequest interstitialRequest;
	// AdRequest adRequest;

	// private static final String BANNER_AD_ID =
	// "ca-app-pub-9687197024195006/5976545579";
	// private static final String INTERSTITIAL_AD_ID =
	// "ca-app-pub-4954528972146554/1415422024";
	private static final String BANNER_AD_ID = "YOUR_BANNER_AD_ID";
	private static final String INTERSTITIAL_AD_ID = "YOUR_INTERSTITIAL_ID";

	private boolean doubleBackToExitPressedOnce;

	public int currentFragment;
	public int currentMusicType;
	public int toMusicPlayer;
	public Playlist currentPlaylist;

	public String nextPageNomination;
	public String nextPageTopWeek;

	public List<Song> listNominations;
	public List<Song> listTopWeek;

	public DatabaseUtility databaseUtility;

	public MusicService mService;
	private Intent intentService;
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			ServiceBinder binder = (ServiceBinder) service;
			mService = binder.getService();
			if (GlobalValue.listSongPlay != null) {

				mService.setListSongs(GlobalValue.listSongPlay);
			}
			mService.setListener(new PlayerListener() {
				@Override
				public void onSeekChanged(String lengthTime, String currentTime, int progress) {
					((PlayerFragment) arrayFragments[PLAYER_FRAGMENT]).seekChanged(lengthTime, currentTime, progress);
				}

				@Override
				public void onChangeSong(int indexSong) {
					((PlayerFragment) arrayFragments[PLAYER_FRAGMENT]).changeSong(indexSong);
					if (GlobalValue.getCurrentSong() != null) {
						lblSongNameFooter.setText(GlobalValue.getCurrentSong().getName());
						lblArtistFooter.setText(GlobalValue.getCurrentSong().getArtist());
					}
				}
			});
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	private PopularShows selected_song_vo;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initList();
		databaseUtility = new DatabaseUtility(this);
		setContentView(R.layout.activity_main);
		LanguageUtil.setLocale(new MySharedPreferences(this).getLanguage(), this);
		if (getIntent() != null) {
			selected_song_vo = (PopularShows) getIntent().getSerializableExtra("selected_song");
		}

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

		initService();
		initMenu();
		initUI();
		initControl();
		initFragment();
		try {
			getIntent().getExtras().get("notification");
			toMusicPlayer = SongListActivity.FROM_NOTICATION;
			showFragment(PLAYER_FRAGMENT);
		} catch (Exception e) {
			setSelect(TOP_CHART);
		}

	}

	private void getListMusicType() {
		modelManager.getListMusicTypes(new ModelManagerListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(Object object) {
				GlobalValue.listCategoryMusics.addAll((List<CategoryMusic>) object);

			}

			@Override
			public void onError() {
				Toast.makeText(SongListActivity.this,
						"There is an error with the internet connection. Music data cannot be loaded.",
						Toast.LENGTH_LONG).show();

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			onBackPressed();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			unbindService(mConnection);
		} catch (Exception e) {
			e.printStackTrace();
			cancelNotification();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
		setVisibilityFooter();
	}

	public void setVisibilityFooter() {
		try {
			if (mService.isPause() || mService.isPlay()) {
				layoutPlayerFooter.setVisibility(View.VISIBLE);
			} else {
				layoutPlayerFooter.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			layoutPlayerFooter.setVisibility(View.GONE);
		}
	}

	private void initService() {
		intentService = new Intent(this, MusicService.class);
		startService(intentService);
		bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
	}

	private void initUI() {
		btnPreviousFooter = (AutoBgButton) findViewById(R.id.btnPreviousFooter);
		btnPlayFooter = (AutoBgButton) findViewById(R.id.btnPlayFooter);
		btnNextFooter = (AutoBgButton) findViewById(R.id.btnNextFooter);
		// imgSongFooter = (ImageView) findViewById(R.id.imgSongFooter);
		layoutPlayerFooter = (LinearLayout) findViewById(R.id.layoutPlayerFooter);
		lblSongNameFooter = (TextView) findViewById(R.id.lblSongNameFooter);
		lblArtistFooter = (TextView) findViewById(R.id.lblArtistFooter);
		lblTopChart = (TextView) menu.findViewById(R.id.lblTopChart);
		lblNominations = (TextView) menu.findViewById(R.id.lblNominations);
		lblCategoryMusic = (TextView) menu.findViewById(R.id.lblCategoryMusic);
		lblPlaylist = (TextView) menu.findViewById(R.id.lblPlaylist);
		lblSearch = (TextView) menu.findViewById(R.id.lblSearch);
		lblGoodApp = (TextView) menu.findViewById(R.id.lblGoodApp);
		lblAbout = (TextView) menu.findViewById(R.id.lblAbout);
		lblExitApp = (TextView) menu.findViewById(R.id.lblExitApp);

		// initBannerAd();
		// initInterstitialAd();

		if (GlobalValue.getCurrentSong() != null) {
			lblSongNameFooter.setText(GlobalValue.getCurrentSong().getName());
			lblArtistFooter.setText(GlobalValue.getCurrentSong().getArtist());
		}

	}

	private void initControl() {
		btnPreviousFooter.setOnClickListener(this);
		btnPlayFooter.setOnClickListener(this);
		btnNextFooter.setOnClickListener(this);
		layoutPlayerFooter.setOnClickListener(this);
		lblTopChart.setOnClickListener(this);
		lblNominations.setOnClickListener(this);
		lblCategoryMusic.setOnClickListener(this);
		lblPlaylist.setOnClickListener(this);
		lblSearch.setOnClickListener(this);
		lblGoodApp.setOnClickListener(this);
		lblAbout.setOnClickListener(this);
		lblExitApp.setOnClickListener(this);
		lblSongNameFooter.setSelected(true);
		lblArtistFooter.setSelected(true);
	}

	// private void initBannerAd() {
	// llAdview = (LinearLayout) findViewById(R.id.adView);
	// adView = new AdView(this);
	// adView.setAdSize(AdSize.BANNER);
	// adView.setAdUnitId(BANNER_AD_ID);
	// llAdview.addView(adView);
	//
	// adRequest = new AdRequest.Builder()
	// // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	// // .addTestDevice("AABF8B935F163F0E1AE8E244871E883A")
	// .build();
	//
	// adView.loadAd(adRequest);
	// }

	// private void initInterstitialAd() {
	// interstitialAd = new InterstitialAd(this);
	// interstitialAd.setAdUnitId(INTERSTITIAL_AD_ID);
	// interstitialRequest = new AdRequest.Builder()
	// // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	// // .addTestDevice("AABF8B935F163F0E1AE8E244871E883A")
	// .build();
	// interstitialAd.loadAd(interstitialRequest);
	//
	// interstitialAd.setAdListener(new AdListener() {
	// @Override
	// public void onAdLoaded() {
	//
	// displayInterstitialAd();
	// }
	//
	// @Override
	// public void onAdFailedToLoad(int errorCode) {
	// }
	// });
	//
	// }

	// public void displayInterstitialAd() {
	// if (interstitialAd.isLoaded()) {
	// interstitialAd.show();
	// } else {
	// interstitialAd.loadAd(interstitialRequest);
	// Log.d("INTERSTITIAL_AD", "Interstitial ad was not ready to be shown.");
	// }
	// }

	private void initFragment() {
		fm = getSupportFragmentManager();
		arrayFragments = new Fragment[7];
		
	
		arrayFragments = new Fragment[7];
		arrayFragments[LIST_SONG_FRAGMENT] = fm.findFragmentById(R.id.fragmentListSongs);
		arrayFragments[CATEGORY_MUSIC_FRAGMENT] = fm.findFragmentById(R.id.fragmentCategoryMusic);
		arrayFragments[PLAYLIST_FRAGMENT] = fm.findFragmentById(R.id.fragmentPlaylist);
		arrayFragments[SEARCH_FRAGMENT] = fm.findFragmentById(R.id.fragmentSearch);
		arrayFragments[SETTING_FRAGMENT] = fm.findFragmentById(R.id.fragmentSetting);
		arrayFragments[PLAYER_FRAGMENT] = fm.findFragmentById(R.id.fragmentPlayer);
		arrayFragments[ABOUT_FRAGMENT] = fm.findFragmentById(R.id.fragmentAbout);

		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.commit();
	}

	private void showFragment(int fragmentIndex) {
		currentFragment = fragmentIndex;
		FragmentTransaction transaction = fm.beginTransaction();
		for (Fragment fragment : arrayFragments) {
			transaction.hide(fragment);
		}
		transaction.show(arrayFragments[fragmentIndex]);
		transaction.commit();
		Logger.e(fragmentIndex);
	}

	private void initList() {
		listNominations = new ArrayList<Song>();
		listTopWeek = new ArrayList<Song>();
	}

	private void initMenu() {
		menu = new SlidingMenu(this);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.layout_menu);

		// lsvMenu = (NoScrollListView)
		// menu.getMenu().findViewById(R.id.lsvMenu);
		// lsvMenu.setAdapter(musicTypeAdapter);
		// lsvMenu.setOnItemClickListener(new OnItemClickListener() {
		// @Override
		// public void onItemClick(AdapterView<?> adapter, View view, int
		// position, long id) {
		// musicTypeAdapter.setIndex(position);
		// setSelect(MUSIC_TYPE);
		// }
		// });
		initMenuControl();
	}

	private void initMenuControl() {
		// layoutSelectNominations = (View)
		// menu.findViewById(R.id.layoutSelectNominations);
		// layoutSelectTopWeek = (View)
		// menu.findViewById(R.id.layoutSelectTopWeek);
		// layoutSelectGoodApp = (View)
		// menu.findViewById(R.id.layoutSelectGoodApp);
		//
		// findViewById(R.id.layoutNominations).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// setSelect(NOMINATIONS);
		// }
		// });
		// findViewById(R.id.layoutTopWeek).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// setSelect(TOP_CHART);
		// }
		// });
		// findViewById(R.id.layoutGoodApp).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // setSelect(GOOD_APP);
		// Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri
		// .parse("https://play.google.com/store/apps/collection/topselling_new_free"));
		// startActivity(browserIntent);
		// overridePendingTransition(R.anim.slide_in_left,
		// R.anim.slide_out_left);
		// }
		// });
		//
		// if (GlobalValue.listCategoryMusics.size() == 0) {
		// modelManager.getListMusicTypes(new ModelManagerListener() {
		// @SuppressWarnings("unchecked")
		// @Override
		// public void onSuccess(Object object) {
		// GlobalValue.listCategoryMusics.addAll((List<CategoryMusic>) object);
		// musicTypeAdapter.notifyDataSetChanged();
		// }
		//
		// @Override
		// public void onError() {
		// }
		// });
		// }
	}

	public void gotoFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();
		currentFragment = fragment;
	}

	public void backFragment(int fragment) {
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
		transaction.show(arrayFragments[fragment]);
		transaction.hide(arrayFragments[currentFragment]);
		transaction.commit();
		currentFragment = fragment;
	}

	private void setSelect(int select) {
		GlobalValue.currentMenu = select;
		switch (select) {
		case TOP_CHART:
			lblTopChart.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblNominations.setBackgroundColor(Color.TRANSPARENT);
			lblCategoryMusic.setBackgroundColor(Color.TRANSPARENT);
			lblPlaylist.setBackgroundColor(Color.TRANSPARENT);
			lblSearch.setBackgroundColor(Color.TRANSPARENT);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundColor(Color.TRANSPARENT);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(LIST_SONG_FRAGMENT);
			break;

		case NOMINATIONS:
			lblTopChart.setBackgroundColor(Color.TRANSPARENT);
			lblNominations.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblCategoryMusic.setBackgroundColor(Color.TRANSPARENT);
			lblPlaylist.setBackgroundColor(Color.TRANSPARENT);
			lblSearch.setBackgroundColor(Color.TRANSPARENT);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundColor(Color.TRANSPARENT);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(LIST_SONG_FRAGMENT);
			break;

		case CATEGORY_MUSIC:
			lblTopChart.setBackgroundColor(Color.TRANSPARENT);
			lblNominations.setBackgroundColor(Color.TRANSPARENT);
			lblCategoryMusic.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblPlaylist.setBackgroundColor(Color.TRANSPARENT);
			lblSearch.setBackgroundColor(Color.TRANSPARENT);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundColor(Color.TRANSPARENT);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(CATEGORY_MUSIC_FRAGMENT);
			break;

		case PLAYLIST:
			lblTopChart.setBackgroundColor(Color.TRANSPARENT);
			lblNominations.setBackgroundColor(Color.TRANSPARENT);
			lblCategoryMusic.setBackgroundColor(Color.TRANSPARENT);
			lblPlaylist.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblSearch.setBackgroundColor(Color.TRANSPARENT);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundColor(Color.TRANSPARENT);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(PLAYLIST_FRAGMENT);
			break;

		case SEARCH:
			lblTopChart.setBackgroundColor(Color.TRANSPARENT);
			lblNominations.setBackgroundColor(Color.TRANSPARENT);
			lblCategoryMusic.setBackgroundColor(Color.TRANSPARENT);
			lblPlaylist.setBackgroundColor(Color.TRANSPARENT);
			lblSearch.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundColor(Color.TRANSPARENT);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(SEARCH_FRAGMENT);
			break;

		case ABOUT:
			lblTopChart.setBackgroundColor(Color.TRANSPARENT);
			lblNominations.setBackgroundColor(Color.TRANSPARENT);
			lblCategoryMusic.setBackgroundColor(Color.TRANSPARENT);
			lblPlaylist.setBackgroundColor(Color.TRANSPARENT);
			lblSearch.setBackgroundColor(Color.TRANSPARENT);
			lblGoodApp.setBackgroundColor(Color.TRANSPARENT);
			lblAbout.setBackgroundResource(R.drawable.bg_item_menu_select);
			lblExitApp.setBackgroundColor(Color.TRANSPARENT);
			showFragment(ABOUT_FRAGMENT);
			break;

		case GOOD_APP:
		case EXIT_APP:
			return;
		}
		menu.showContent();
	}

	public void setButtonPlay() {
		if (mService.isPause()) {
			btnPlayFooter.setBackgroundResource(R.drawable.bg_btn_play_small);
		} else {
			btnPlayFooter.setBackgroundResource(R.drawable.bg_btn_pause_small);
		}
		((PlayerFragment) arrayFragments[PLAYER_FRAGMENT]).setButtonPlay();
	}

	public void cancelNotification() {
		NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nMgr.cancel(NOTIFICATION_ID);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnPreviousFooter:
			onClickPreviousFooter();
			break;

		case R.id.btnPlayFooter:
			onClickPlayFooter();
			break;

		case R.id.btnNextFooter:
			onClickNextFooter();
			break;

		case R.id.layoutPlayerFooter:
			onClickPlayerFooter();
			break;

		case R.id.lblTopChart:
			onClickTopChart();
			break;

		case R.id.lblNominations:
			onClickNominations();
			break;

		case R.id.lblCategoryMusic:
			onClickCategoryMusic();
			break;

		case R.id.lblPlaylist:
			onClickPlaylist();
			break;

		case R.id.lblSearch:
			onClickSearch();
			break;

		case R.id.lblGoodApp:
			onClickGoodApp();
			break;

		case R.id.lblAbout:
			onClickAbout();
			break;

		case R.id.lblExitApp:
			// displayInterstitialAd();
			onClickExitApp();
			break;
		}
	}

	private void onClickPreviousFooter() {
		mService.backSongByOnClick();
	}

	private void onClickPlayFooter() {
		mService.playOrPauseMusic();
		setButtonPlay();
	}

	private void onClickNextFooter() {
		mService.nextSongByOnClick();
	}

	private void onClickPlayerFooter() {
		toMusicPlayer = FROM_OTHER;
		gotoFragment(PLAYER_FRAGMENT);
	}

	private void onClickTopChart() {
		setSelect(TOP_CHART);
	}

	private void onClickNominations() {
		setSelect(NOMINATIONS);
	}

	private void onClickCategoryMusic() {
		setSelect(CATEGORY_MUSIC);
	}

	private void onClickPlaylist() {
		setSelect(PLAYLIST);
	}

	private void onClickSearch() {
		setSelect(SEARCH);
	}

	private void onClickGoodApp() {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/collection/topselling_new_free"));
		startActivity(browserIntent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
	}

	private void onClickAbout() {
		setSelect(ABOUT);
	}

	private void onClickExitApp() {
		showQuitDialog();
	}

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing()) {
			menu.showContent();
		} else {
			switch (currentFragment) {
			case PLAYER_FRAGMENT:
				if (toMusicPlayer == FROM_SEARCH) {
					backFragment(SEARCH_FRAGMENT);
				} else {
					backFragment(LIST_SONG_FRAGMENT);
				}
				break;

			case LIST_SONG_FRAGMENT:
				if (GlobalValue.currentMenu == CATEGORY_MUSIC) {
					backFragment(CATEGORY_MUSIC_FRAGMENT);
				} else if (GlobalValue.currentMenu == PLAYLIST) {
					backFragment(PLAYLIST_FRAGMENT);
				} else {
					// quitApp();
					super.onBackPressed();
				}
				break;

			default:
				// quitApp();
				super.onBackPressed();
				break;
			}
		}
	}

	private void quitApp() {
		// displayInterstitialAd();
		if (doubleBackToExitPressedOnce) {
			finish();
			stopService(intentService);
			cancelNotification();
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
			return;
		}

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, R.string.doubleBackToExit, Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);
	}

	private void showQuitDialog() {
		new AlertDialog.Builder(this).setTitle(R.string.app_name).setMessage(R.string.msgQuitApp)
				.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						stopService(intentService);
						cancelNotification();
						finish();
					}
				}).setNegativeButton(android.R.string.cancel, null).create().show();
	}
}
