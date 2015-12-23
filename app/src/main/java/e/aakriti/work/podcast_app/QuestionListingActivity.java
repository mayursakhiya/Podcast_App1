package e.aakriti.work.podcast_app;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;
import e.aakriti.work.adapter.ExpandableListAdapter;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;
import e.aakriti.work.objects.Questionaries;

public class QuestionListingActivity extends Activity{
	
	ExpandableListView question_list;
	Button skip_btn,ok_btn;
	Utility utility;
	ArrayList<Questionaries> allQues;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<List<String>> listDataChilds;
    String listner_id="";
    SharedData sharedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_list);
		
		utility = new Utility(QuestionListingActivity.this);
		sharedData = new SharedData(QuestionListingActivity.this);
		listner_id = sharedData.getString("ListnerId", "");
		
		question_list = (ExpandableListView) findViewById(R.id.question_listView);
		skip_btn = (Button) findViewById(R.id.skip_btn);
		ok_btn = (Button) findViewById(R.id.ok_btn);
		new getAllQuestionsWSCall().execute();
		
		question_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
		{
		    public boolean onGroupClick(ExpandableListView arg0, View itemView, int itemPosition, long itemId)
		    {
		    	question_list.expandGroup(itemPosition);
		        return true;
		    }
		});
		
		ok_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				for (int i = 0; i < allQues.size(); i++) {
					Log.e("==", ""+allQues.get(i).getSelected_answer());
					//if(Utility.isNotNull(allQues.get(i).getSelected_answer()))
					//{
						new submitQuestionAnswersWSCall(i,allQues.get(i).getId(),allQues.get(i).getSelected_answer()).execute();
						
					//}
				}
			}
		});
		
		skip_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(QuestionListingActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
	}
	
	
	public class submitQuestionAnswersWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",response = "";
		private int errorCode = 0,i;
		String id,answer;

		public submitQuestionAnswersWSCall(int i, String id,String answer) {
			// TODO Auto-generated constructor stub
			this.id = id;
			this.answer = answer;
			this.i = i;
		}
		
		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(QuestionListingActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();
			
			if(i == (allQues.size()-1))
			{
				Intent intent = new Intent(QuestionListingActivity.this,CategoriesActivity.class);
				startActivity(intent);
				finish();
				
			}
			
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
								if(Utility.isNotNull(answer))
								{
									String uri = RestApi.createURI(RestApi.SubmitQueAnswers_WS)+"/que_id/"+id + "/list_id/" + listner_id+"/ans/"+answer;
									//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
										//	+ userName + "&password=" + passWord;
									
									result = RestApi.getDataFromURLWithoutParam(uri);
									Log.e("result", result);
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
							Toast.makeText(QuestionListingActivity.this, "Please check your Internet connection",
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
	

	public class getAllQuestionsWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",response = "";
		private int errorCode = 0;

		
		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(QuestionListingActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();

			// preparing list data
	        prepareListData();
	 
	        listAdapter = new ExpandableListAdapter(QuestionListingActivity.this, listDataHeader, listDataChild,allQues);
	 
	        // setting list adapter
	        question_list.setAdapter(listAdapter);
	        
	        for(int i=0;i<listAdapter.getGroupCount();i++)
	        {
	        question_list.expandGroup(i);
	        }
	        
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
								String uri = RestApi.createURI(RestApi.QuestionsList_WS);
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
													allQues = new ArrayList<Questionaries>(jsonArray.length());
													for (int i =0 ;i<jsonArray.length();i++)
													{
														JSONObject obj = jsonArray.getJSONObject(i);
														Questionaries que = new Questionaries(obj);
														allQues.add(que);
													}
													Log.e("allQue", ""+allQues.size());
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
												Toast.makeText(QuestionListingActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(QuestionListingActivity.this, "Please check your Internet connection",
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
	
	private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChilds = new ArrayList<List<String>>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        
        List<String> subChilds;
        for(int i = 0;i<allQues.size();i++)
        {
        	listDataHeader.add(allQues.get(i).getQuestion());
        	String[] separated = allQues.get(i).getAnswers().split(",");
        	subChilds = new ArrayList<String>();
        	for(int j=0;j<separated.length;j++)
        	{
        		subChilds.add(separated[j]);
        	}
        	listDataChilds.add(i, subChilds);
        	
        }
        
        for(int i=0;i<listDataChilds.size();i++)
        {
        	listDataChild.put(listDataHeader.get(i), listDataChilds.get(i));
        }
        
    }
}