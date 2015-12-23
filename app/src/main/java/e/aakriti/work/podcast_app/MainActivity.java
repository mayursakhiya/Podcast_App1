package e.aakriti.work.podcast_app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import e.aakriti.work.adapter.CategoriesListAdapter;
import e.aakriti.work.adapter.Explore_Episodesadapter;
import e.aakriti.work.adapter.Explore_showsAdapter;
import e.aakriti.work.adapter.FeaturedSliderPagerAdapter;
import e.aakriti.work.adapter.PopularShowsAdapter;
import e.aakriti.work.adapter.RecentShowsListAdapter;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;
import e.aakriti.work.common.WrappingGridView;
import e.aakriti.work.common.WrappingListView;
import e.aakriti.work.common.getAllCategoriesWS;
import e.aakriti.work.common.getAllExploreEpisodesWS;
import e.aakriti.work.common.getAllExploreShowsWS;
import e.aakriti.work.common.getAllFeaturedWS;
import e.aakriti.work.common.getAllPopularWS;
import e.aakriti.work.common.getAllRecentShowsWS;
import e.aakriti.work.nav_drawer_library.NavDrawerItem;
import e.aakriti.work.nav_drawer_library.NavDrawerListAdapter;
import e.aakriti.work.objects.Categories;
import e.aakriti.work.objects.DownloadShows;
import e.aakriti.work.objects.Explore_episodes;
import e.aakriti.work.objects.Explore_shows;
import e.aakriti.work.objects.FeaturedShows;
import e.aakriti.work.objects.FollowingShows;
import e.aakriti.work.objects.PopularShows;
import e.aakriti.work.objects.RecentShows;

public class MainActivity extends FragmentActivity {

	// create object of FragmentPagerAdapter
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static List<Categories> allCategories;
	public static ArrayList<FeaturedShows> featured;
	public static ArrayList<PopularShows> popular;
	public static ArrayList<RecentShows> recent;
	public static ArrayList<DownloadShows> downloaded_shows;
	public static ArrayList<FollowingShows> following_shows;
	public static ArrayList<Explore_episodes> explore_episodes;
	public static ArrayList<Explore_shows> explore_shows;

	LinearLayout linearDrawer;
	ListView mDrawerList;
	NavDrawerListAdapter nav_adapter;
	ArrayList<NavDrawerItem> navDrawerItems;
	String[] mDrawerTitles;
	private DrawerLayout mDrawerLayout;
	public TypedArray mDrawerItemImages;
	ActionBarDrawerToggle mDrawerToggle;
	ImageView menuImg;

	static CategoriesListAdapter listAdapter;
	String listner_id = "";
	SharedData sharedData;
	Utility utility;
	// viewpager to display pages
	public static ViewPager mViewPager;
	static Context context;
	int firstFlag = 0;
	static FeaturedSliderPagerAdapter adapter;
	static PopularShowsAdapter popularadapter;
	static RecentShowsListAdapter recentshowsAdapter;
	static Explore_Episodesadapter episodesadapter;
	static Explore_showsAdapter showsadapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		// Create the adapter that will return a fragment for each of the five
		// primary sections of the app.

		menuImg = (ImageView) findViewById(R.id.menuImg);
		linearDrawer = (LinearLayout) findViewById(R.id.linear_drawer);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		mDrawerTitles = getResources().getStringArray(R.array.drawer_menu_array);
		mDrawerItemImages = getResources().obtainTypedArray(R.array.drawer_menu_icon_array);

		utility = new Utility(MainActivity.this);
		sharedData = new SharedData(MainActivity.this);
		listner_id = sharedData.getString("ListnerId", "");
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(1);
		mViewPager.setSelected(true);

		allCategories = new ArrayList<Categories>();
		featured = new ArrayList<FeaturedShows>();
		popular = new ArrayList<PopularShows>();
		recent = new ArrayList<RecentShows>();
		explore_episodes = new ArrayList<Explore_episodes>();
		explore_shows = new ArrayList<Explore_shows>();
		downloaded_shows = new ArrayList<DownloadShows>();
		following_shows = new ArrayList<FollowingShows>();

		context = MainActivity.this;

		adapter = new FeaturedSliderPagerAdapter(context);
		popularadapter = new PopularShowsAdapter(context);
		recentshowsAdapter = new RecentShowsListAdapter(context);
		episodesadapter = new Explore_Episodesadapter(context);
		showsadapter = new Explore_showsAdapter(context);

		navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[0], mDrawerItemImages.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[1], mDrawerItemImages.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[2], mDrawerItemImages.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[3], mDrawerItemImages.getResourceId(3, -1)));
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[4], mDrawerItemImages.getResourceId(4, -1)));
        navDrawerItems.add(new NavDrawerItem(mDrawerTitles[5], mDrawerItemImages.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem("Log Out", mDrawerItemImages.getResourceId(5, -1)));

        menuImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mDrawerLayout.isDrawerOpen(Gravity.START))
                    mDrawerLayout.closeDrawer(Gravity.START);
                else
                    mDrawerLayout.openDrawer(Gravity.START);
			}
		});
        mDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mDrawerLayout, R.drawable.menu, R.string.abc_capital_off, R.string.abc_capital_on) {
            public void onDrawerClosed(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                try {
                    View focus = MainActivity.this.getCurrentFocus();
                    if (focus != null)
                        inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                } catch ( Exception e) {
                    //e.printStackTrace();
                }
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                try {
                    View focus = MainActivity.this.getCurrentFocus();
                    if (focus != null)
                        inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), 0);
                } catch ( Exception e) {
                    //e.printStackTrace();
                }
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }*/

        nav_adapter = new NavDrawerListAdapter(MainActivity.this, navDrawerItems);
        mDrawerList.setAdapter(nav_adapter);
        mDrawerList.setOnItemClickListener(new HandleNavigationDrawerItemClick());
        supportInvalidateOptionsMenu();

        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				//home
				case 0:
					
					break;
				//who to follow
				case 1:
					
					break;
					//playlist
				case 2:
					
					break;
					//favourites
				case 3:
					
					break;
					//settings
				case 4:
					
					Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
					startActivity(intent);
					//finish();
					
					break;
					//feedback
				case 5:
					
					break;	
					//logout
				case 6:
					
					sharedData.clearAll();
				    intent = new Intent(MainActivity.this, LoginActivity.class);
					intent.putExtra("from_Back", "0");
					startActivity(intent);
					finish();
					
					break;
				
				default:
					break;
				}
			}
		});
        

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mViewPager.setOffscreenPageLimit(1);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				switch (arg0) {
				case 0:
					// Categories
					Log.e("all cat", "" + allCategories.size() + CategoriesFragment.list.getChildCount());
					if (allCategories.size() == 0 && mViewPager.getCurrentItem() == 0) {
						new getAllCategoriesWS(CategoriesFragment.list, context, listAdapter).execute();
					} else {
						listAdapter = new CategoriesListAdapter(context, (ArrayList<Categories>) allCategories);

						// setting list adapter
						CategoriesFragment.list.setAdapter(listAdapter);

						listAdapter.notifyDataSetChanged();
					}
					CategoriesFragment.list.invalidate();
					break;
				case 1:
					// Discover
					if (featured.size() == 0 && mViewPager.getCurrentItem() == 1) {
						new getAllFeaturedWS(DiscoverFragment.viewPager, context, adapter).execute();
					} else {
						adapter = new FeaturedSliderPagerAdapter(context);
						DiscoverFragment.viewPager.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}

					if (popular.size() == 0 && mViewPager.getCurrentItem() == 1) {
						new getAllPopularWS(DiscoverFragment.gridViewq, context, popularadapter).execute();
					} else {
						popularadapter = new PopularShowsAdapter(context);
						DiscoverFragment.gridViewq.setAdapter(popularadapter);
						popularadapter.notifyDataSetChanged();
					}

					DiscoverFragment.gridViewq.setColumnWidth(100);
					DiscoverFragment.gridViewq.setNumColumns(2);

					if (recent.size() == 0 && mViewPager.getCurrentItem() == 1) {
						new getAllRecentShowsWS(DiscoverFragment.listView, context, recentshowsAdapter).execute();

					} else {
						recentshowsAdapter = new RecentShowsListAdapter(context);
						DiscoverFragment.listView.setAdapter(recentshowsAdapter);
						recentshowsAdapter.notifyDataSetChanged();
					}

					DiscoverFragment.gridViewq.invalidate();
					DiscoverFragment.listView.invalidate();
					DiscoverFragment.viewPager.invalidate();
					Log.e("list count==", "" + DiscoverFragment.listView.getChildCount());
					break;
				case 2:
					// Explore
					if (explore_episodes.size() == 0 && mViewPager.getCurrentItem() == 2) {
						new getAllExploreEpisodesWS(ExploreFragment.gridView_episodes, context,
								episodesadapter).execute();
					} else {
						ExploreFragment.gridView_episodes.setAdapter(episodesadapter);
						episodesadapter.notifyDataSetChanged();
					}

					ExploreFragment.gridView_episodes.setColumnWidth(100);
					ExploreFragment.gridView_episodes.setNumColumns(2);

					if (explore_shows.size() == 0 && mViewPager.getCurrentItem() == 2) {
						new getAllExploreShowsWS(ExploreFragment.gridView_shows, context, showsadapter)
								.execute();
					} else {
						ExploreFragment.gridView_shows.setAdapter(showsadapter);
						showsadapter.notifyDataSetChanged();
					}

					ExploreFragment.gridView_shows.setColumnWidth(100);
					ExploreFragment.gridView_shows.setNumColumns(2);

					ExploreFragment.toggleButton.setChecked(true);
					ExploreFragment.gridView_episodes.setVisibility(View.VISIBLE);
					ExploreFragment.gridView_shows.setVisibility(View.GONE);
					ExploreFragment.gridView_episodes.bringToFront();
					ExploreFragment.gridView_episodes.invalidate();
					ExploreFragment.gridView_shows.invalidate();

					break;
				case 3:
					// My Shows
					if (recent.size() == 0) {
						new getAllRecentShowsWS(My_ShowsFragment.latest, context, recentshowsAdapter).execute();

					} else {
						recentshowsAdapter = new RecentShowsListAdapter(context);
						My_ShowsFragment.latest.setAdapter(recentshowsAdapter);
						recentshowsAdapter.notifyDataSetChanged();
					}

					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 1:
					if (firstFlag == 0) {

						firstFlag = 1;
						if (featured.size() == 0) {
							new getAllFeaturedWS(DiscoverFragment.viewPager, context, adapter).execute();
						} else {
							adapter = new FeaturedSliderPagerAdapter(context);
							DiscoverFragment.viewPager.setAdapter(adapter);
							adapter.notifyDataSetChanged();
						}

						if (popular.size() == 0) {
							new getAllPopularWS(DiscoverFragment.gridViewq, context, popularadapter).execute();
						} else {
							popularadapter = new PopularShowsAdapter(context);
							DiscoverFragment.gridViewq.setAdapter(popularadapter);
							popularadapter.notifyDataSetChanged();
						}

						DiscoverFragment.gridViewq.setColumnWidth(100);
						DiscoverFragment.gridViewq.setNumColumns(2);

						if (recent.size() == 0) {
							new getAllRecentShowsWS(DiscoverFragment.listView, context, recentshowsAdapter).execute();

						} else {
							recentshowsAdapter = new RecentShowsListAdapter(context);
							DiscoverFragment.listView.setAdapter(recentshowsAdapter);
							recentshowsAdapter.notifyDataSetChanged();
						}

						DiscoverFragment.gridViewq.requestLayout();
						DiscoverFragment.listView.requestLayout();
						Log.e("list count==", "" + DiscoverFragment.listView.getChildCount());

					}
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * A FragmentPagerAdapter that returns a fragment corresponding to one of
	 * the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			Bundle args = new Bundle();

			switch (position) {
			case 0:
				fragment = new CategoriesFragment();
				args.putInt(CategoriesFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				break;
			case 1:
				fragment = new DiscoverFragment();
				args.putInt(DiscoverFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				break;
			case 2:
				fragment = new ExploreFragment();
				args.putInt(ExploreFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				break;
			case 3:
				fragment = new My_ShowsFragment();
				args.putInt(My_ShowsFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				break;

			}

			return fragment;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "Categories";
			case 1:
				return "Discover";
			case 2:
				return "Explore";
			case 3:
				return "My Sows";
			}
			return null;
		}
	}

	public static class CategoriesFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		static View view;
		static ListView list;

		public CategoriesFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			view = inflater.inflate(R.layout.categories_list, container, false);
			list = (ListView) view.findViewById(R.id.catListing);

			list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					Toast.makeText(context, allCategories.get(position).getFull_name()+" .", Toast.LENGTH_LONG).show();
				}
			});
			return view;
		}
	}

	public static class DiscoverFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		static View view;
		static ViewPager viewPager;
		static WrappingGridView gridViewq;
		static WrappingListView listView;
		static ImageView round1, round2;
		static RelativeLayout roundRel;

		public DiscoverFragment() {
		}

		@SuppressWarnings("deprecation")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			view = inflater.inflate(R.layout.discover, container, false);
			viewPager = (ViewPager) view.findViewById(R.id.viewPager);
			gridViewq = (WrappingGridView) view.findViewById(R.id.gridView);
			listView = (WrappingListView) view.findViewById(R.id.listView);

			round1 = (ImageView) view.findViewById(R.id.round1);
			round2 = (ImageView) view.findViewById(R.id.round2);
			roundRel = (RelativeLayout) view.findViewById(R.id.roundRel);

			round1.setImageResource(R.drawable.active_round);
			round2.setImageResource(R.drawable.inactive_round);
			
			viewPager.setOnPageChangeListener(new OnPageChangeListener() {

				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					if (viewPager.getCurrentItem() == 0) {
						round1.setImageResource(R.drawable.active_round);
						round2.setImageResource(R.drawable.inactive_round);
					} else {
						round1.setImageResource(R.drawable.inactive_round);
						round2.setImageResource(R.drawable.active_round);
					}
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});

			roundRel.bringToFront();
			round1.setVisibility(View.VISIBLE);
			round2.setVisibility(View.VISIBLE);


			Log.e("list count==", "" + listView.getChildCount());

			return view;
		}
	}

	public static class ExploreFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";
		static View view;
		static ToggleButton toggleButton;
		static GridView gridView_episodes, gridView_shows;

		public ExploreFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			view = inflater.inflate(R.layout.explore, container, false);
			toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
			gridView_episodes = (GridView) view.findViewById(R.id.gridView_episodes);
			gridView_shows = (GridView) view.findViewById(R.id.gridView_shows);

			

			toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if (isChecked) {
						toggleButton.setText("IsChecked == Episodes");
						gridView_episodes.setVisibility(View.VISIBLE);
						gridView_shows.setVisibility(View.GONE);
						gridView_episodes.bringToFront();
						// Toast.makeText(context, "IsChecked == Episodes",
						// Toast.LENGTH_LONG).show();
					} else {
						toggleButton.setText("IsChecked == Shows");
						gridView_episodes.setVisibility(View.GONE);
						gridView_shows.setVisibility(View.VISIBLE);
						gridView_shows.bringToFront();
						// Toast.makeText(context, "IsChecked == Shows",
						// Toast.LENGTH_LONG).show();
					}
				}
			});
			return view;
		}
	}

	public static class My_ShowsFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		static ListView latest, downloads, following;
		static Button btn_latest, btn_dwnlds, btn_follwing;
		static RecentShowsListAdapter recent_adapter;
		static RelativeLayout no_records_rel;
		static TextView no_records_txt;

		public My_ShowsFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			View view = inflater.inflate(R.layout.my_shows, container, false);
			latest = (ListView) view.findViewById(R.id.latest_shows);
			downloads = (ListView) view.findViewById(R.id.downloads);
			following = (ListView) view.findViewById(R.id.followings);
			btn_dwnlds = (Button) view.findViewById(R.id.dwnlds_btn);
			btn_follwing = (Button) view.findViewById(R.id.following_btn);
			btn_latest = (Button) view.findViewById(R.id.latest_btn);
			no_records_rel = (RelativeLayout) view.findViewById(R.id.no_records_rel);
			no_records_txt = (TextView) view.findViewById(R.id.no_records_txt);

			recent_adapter = new RecentShowsListAdapter(context);

			
			btn_dwnlds.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					latest.setVisibility(View.GONE);
					following.setVisibility(View.GONE);
					if(downloaded_shows.size()==0)
					{
						no_records_rel.setVisibility(View.VISIBLE);
						downloads.setVisibility(View.GONE);
						no_records_rel.bringToFront();
						no_records_txt.setText("No Downloads yet.");
					}
					else
					{
						downloads.setVisibility(View.VISIBLE);
						downloads.bringToFront();
					}
					
				}
			});

			btn_latest.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					latest.setVisibility(View.VISIBLE);
					downloads.setVisibility(View.GONE);
					following.setVisibility(View.GONE);
					no_records_rel.setVisibility(View.GONE);
					latest.bringToFront();
				}
			});

			btn_follwing.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					latest.setVisibility(View.GONE);
					downloads.setVisibility(View.GONE);
					if(following_shows.size()==0)
					{
						no_records_rel.setVisibility(View.VISIBLE);
						following.setVisibility(View.GONE);
						no_records_rel.bringToFront();
						no_records_txt.setText("No Following Shows yet.");
					}
					else
					{
						following.setVisibility(View.VISIBLE);
						following.bringToFront();
					}
				}
			});

			return view;
		}
	}
	
	//Handles the left drawer item
	private class HandleNavigationDrawerItemClick implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			leftNavigationDrawerItemSelection(position);
		}
	}
	private void leftNavigationDrawerItemSelection(int position){
		
		switch(position){
		case 0: //Home
			mDrawerLayout.closeDrawers();
			mViewPager.setCurrentItem(1);
			break;
		case 1:  //Who to Follow
			//
			break;
		case 2:  //PlayList
			//
			break;
		case 3:  //Favourites
			//
			break;
		case 4:  //Settings
			//
			break;
		case 5: // Item click for feedback. This will open install email client  
			Intent mailIntent=new Intent(Intent.ACTION_SEND);
            mailIntent.setData(Uri.parse("mailto"));
            String[] to= {"amarjite446@gmail.com"};
            mailIntent.putExtra(Intent.EXTRA_EMAIL, to);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "User feedback");
            //mailIntent.putExtra(Intent.EXTRA_TEXT, "hi");
            mailIntent.setType("message/rfc822");
            startActivity(Intent.createChooser(mailIntent, "send mail")); 
			break;
		case 6:  //logout will go to login activity. 
			
			AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this);
			logoutAlert.setTitle("Are you sure you want to log out of your account?");
			logoutAlert.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
					logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(logoutIntent);
					finish();			
				}
			});
			logoutAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			AlertDialog alert = logoutAlert.create();
			alert.show();				
			break;
		}
		
	}

}
