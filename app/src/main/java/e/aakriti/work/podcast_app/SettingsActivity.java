package e.aakriti.work.podcast_app;

import java.net.ConnectException;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;

public class SettingsActivity extends Activity{
	
	String title,cat_id,cat_name,user_id;
	View header;
	TextView title_Txt;
	ImageView menuImg,searchImg;
	String listner_id = "";
	SharedData sharedData;
	Utility utility;
	EditText oldpswdEt,newpswdEt,cnfrmpswdEt;
	Button saveBtn;
	String old_pswd,new_pswd,confirm_pswd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.settings);
		
		utility = new Utility(SettingsActivity.this);
		sharedData = new SharedData(SettingsActivity.this);
		listner_id = sharedData.getString("ListnerId", "");
		
		header = (View) findViewById(R.id.layout_header);
		title_Txt = (TextView) header.findViewById(R.id.titleTxt);
		menuImg = (ImageView) header.findViewById(R.id.menuImg);
		searchImg = (ImageView) header.findViewById(R.id.searchImg);
		oldpswdEt = (EditText) findViewById(R.id.oldpswdEt);
		newpswdEt = (EditText) findViewById(R.id.newpswdEt);
		cnfrmpswdEt = (EditText) findViewById(R.id.cnfrmpswdEt);
		saveBtn = (Button) findViewById(R.id.doneBtn);
		
		searchImg.setVisibility(View.INVISIBLE);
		menuImg.setImageResource(R.drawable.back);
		title_Txt.setText("Change Password");
		//title_Txt.setGravity(Gravity.LEFT);
		
		menuImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				old_pswd = oldpswdEt.getText().toString();
				new_pswd = newpswdEt.getText().toString();
				confirm_pswd = cnfrmpswdEt.getText().toString();
				
				if(Utility.isNotNull(old_pswd) && Utility.isNotNull(new_pswd) && Utility.isNotNull(confirm_pswd))
				{
					if(new_pswd.equals(confirm_pswd))
					{
						new changePasswordWSCall().execute();
					}
					else
					{
						Toast.makeText(SettingsActivity.this, "New Password and Confirm Password does not match.!", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(SettingsActivity.this, "Please provide all details", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	public void onBackPressed() 
	{
		finish();
	};
	
	public class changePasswordWSCall extends AsyncTask<Void, Void, String> {

		
		private String result = null, errorMessage = "",response = "";
		private int errorCode = 0;
		ProgressDialog mLoader;
		
		public changePasswordWSCall() {
			
		}
		

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(SettingsActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();
	        
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
								String uri = RestApi.createURI(RestApi.ChangePassword+"/oldpass/"+old_pswd+"/newpass/"+new_pswd+"/list_id/"+listner_id);
								result = RestApi.getDataFromURLWithoutParam(uri);
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(SettingsActivity.this, ""+result, Toast.LENGTH_LONG).show();
									}
								});
								Log.e("result ==", result);
								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									response = objRes.optString("response");
									Log.e("result", response);
									if (!response.equalsIgnoreCase("")) {
										
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(SettingsActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(SettingsActivity.this, "Please check your Internet connection",
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
