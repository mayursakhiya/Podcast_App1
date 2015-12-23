package e.aakriti.work.common;

import java.net.ConnectException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;
import e.aakriti.work.adapter.CategoriesListAdapter;
import e.aakriti.work.adapter.Explore_Episodesadapter;
import e.aakriti.work.adapter.FeaturedSliderPagerAdapter;
import e.aakriti.work.adapter.RecentShowsListAdapter;
import e.aakriti.work.objects.Categories;
import e.aakriti.work.objects.Explore_episodes;
import e.aakriti.work.objects.FeaturedShows;
import e.aakriti.work.objects.RecentShows;
import e.aakriti.work.podcast_app.MainActivity;

public class getAllExploreEpisodesWS extends AsyncTask<Void, Void, String> {

	private ProgressDialog mLoader;
	private String result = null, errorMessage = "",response = "";
	private int errorCode = 0;
	GridView list;
	Context context;
	//ArrayList<Categories> allCategories;
	Explore_Episodesadapter adapter;
	Utility utility;
	
	public getAllExploreEpisodesWS(GridView gridView_episodes,Context c,Explore_Episodesadapter episodesadapter) {
		// TODO Auto-generated constructor stub
		this.list = gridView_episodes;
		this.context = c;
		//this.allCategories = (ArrayList<Categories>) MainActivity.allCategories;
		this.adapter = episodesadapter;
		utility = new Utility(context);
	}
	
	

	@Override
	protected void onPreExecute() {
		mLoader = new ProgressDialog(context);
		mLoader.setMessage("Loading");
		mLoader.setCancelable(false);
		mLoader.show();

		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		if (mLoader.isShowing())
			mLoader.dismiss();
		
		adapter = new Explore_Episodesadapter(context);
		list.setAdapter(adapter);
        
		super.onPostExecute(result);
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			if (utility.isNetworkAvailable()) {
				Thread th = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							String uri = RestApi.createURI(RestApi.GetAllExplore_episodes_WS)+"/type/episode";
							//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
								//	+ userName + "&password=" + passWord;
							result = RestApi.getDataFromURLWithoutParam(uri);

							if (Utility.isNotNull(result)) {
								final JSONObject objRes = new JSONObject(result);
								response = objRes.optString("data");
								
								if (!response.equalsIgnoreCase("")) {
									((Activity) context).runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method
											// stub
											
											try {
												JSONArray jsonArray = new JSONArray(response);
												MainActivity.explore_episodes = new ArrayList<Explore_episodes>(jsonArray.length());
												for (int i =0 ;i<jsonArray.length();i++)
												{
													JSONObject obj = jsonArray.getJSONObject(i);
													Explore_episodes episode = new Explore_episodes(obj);
													MainActivity.explore_episodes.add(episode);
												}
												Log.e("episode", ""+MainActivity.explore_episodes.size());
											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										}
									});
								} else {
									((Activity) context).runOnUiThread(new Runnable() {

										@Override
										public void run() {
											// TODO Auto-generated method
											// stub

											errorMessage = objRes.optString("msg");
											Toast.makeText(context, "" + errorMessage, Toast.LENGTH_LONG)
													.show();
										}
									});
								}
							}
						} catch (ConnectException e) {
							Log.e("", "" + e.toString());
						} catch (Exception e) {
							Log.e("", "" + e.toString());
						}
					}
				});
				th.start();
				th.join();
			} else {
				((Activity) context).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(context, "Please check your Internet connection",
								Toast.LENGTH_LONG).show();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
