package e.aakriti.work.podcast_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import e.aakriti.work.adapter.CategoriesGridAdapter;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;
import e.aakriti.work.objects.Categories;

public class CategoriesActivity extends Activity{
	
	static CategoriesGridAdapter listAdapter;
    String listner_id="";
    SharedData sharedData;
    Utility utility;
    static List<Categories> allCategories;
    GridView gridView;
    Button doneBtn;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categories);
		
		utility = new Utility(CategoriesActivity.this);
		sharedData = new SharedData(CategoriesActivity.this);
		listner_id = sharedData.getString("ListnerId", "");
		gridView = (GridView) findViewById(R.id.gridView);
		doneBtn = (Button) findViewById(R.id.doneBtn);
		
        new getAllCategoriesWS().execute();
        
        doneBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
        
	}
	
	
	public class getAllCategoriesWS extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",response = "";
		private int errorCode = 0;
		
		public getAllCategoriesWS() {
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(CategoriesActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();
			listAdapter = new CategoriesGridAdapter(CategoriesActivity.this, allCategories);
			
	        // setting list adapter
	        gridView.setAdapter(listAdapter);
	        gridView.setColumnWidth(300);
            gridView.setNumColumns(2);
	        
			listAdapter.notifyDataSetChanged();
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
								String uri = RestApi.createURI(RestApi.GetAllCategories_WS);
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);

								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									response = objRes.optString("response");
									
									if (!response.equalsIgnoreCase("")) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												
												try {
													JSONArray jsonArray = new JSONArray(response);
													allCategories = new ArrayList<Categories>(jsonArray.length());
													for (int i =0 ;i<jsonArray.length();i++)
													{
														JSONObject obj = jsonArray.getJSONObject(i);
														Categories cat = new Categories(obj);
														allCategories.add(cat);
													}
													Log.e("allQue", ""+allCategories.size());
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(CategoriesActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Toast.makeText(CategoriesActivity.this, "Please check your Internet connection",
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
	
	

}
