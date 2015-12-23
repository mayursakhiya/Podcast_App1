
/*package e.aakriti.work.podcast_app;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import e.aakriti.work.common.RestApi;
import e.aakriti.work.common.SharedData;
import e.aakriti.work.common.Utility;

public class LoginActivity extends Activity {

	Button submitBtn;
	TextView forgotPwd, backTxt;
	ImageView fbLogin, gpLogin;
	Utility utility;
	String userName, passWord;
	EditText emailEt, passwordEt;
	int flag_Fp=0;
	SharedData shareData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		utility = new Utility(LoginActivity.this);
		submitBtn = (Button) findViewById(R.id.submitBtn);
		forgotPwd = (TextView) findViewById(R.id.forgetPwdTxt);
		backTxt = (TextView) findViewById(R.id.backTxt);
		fbLogin = (ImageView) findViewById(R.id.fbLoginBtn);
		gpLogin = (ImageView) findViewById(R.id.gpLoginBtn);
		emailEt = (EditText) findViewById(R.id.emailEt);
		passwordEt = (EditText) findViewById(R.id.passwordEt);

		shareData = new SharedData(LoginActivity.this);
		
		backTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, Landing_PagerActivity.class);
				intent.putExtra("from_Back", "1");
				startActivity(intent);
				finish();
			}
		});
		submitBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Toast.makeText(LoginActivity.this, "Submit - Coming
				// Soon...!", Toast.LENGTH_LONG).show();

				if(flag_Fp == 1)
				{
					// Forgot Password
					userName = emailEt.getText().toString();
					
					if (Utility.isNotNull(userName) && Utility.checkEmail(userName)) {
						new forgotPasswordWSCall(userName).execute();
						
					} else {
						
							Toast.makeText(LoginActivity.this, "Please enter Valid Email address", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					// Login
					userName = emailEt.getText().toString();
					passWord = passwordEt.getText().toString();
					if (Utility.isNotNull(userName) && Utility.isNotNull(passWord)) {
						new loginWSCall(userName, passWord).execute();
						
					} else {
						if(!Utility.isNotNull(userName))
						{
							Toast.makeText(LoginActivity.this, "Please enter your UserName or Email", Toast.LENGTH_LONG).show();
						}
						
						else if(!Utility.isNotNull(passWord))
						{
							Toast.makeText(LoginActivity.this, "Please provide your Password", Toast.LENGTH_LONG).show();
						}
					}
				}
				
				// }
			}
		});

		forgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoginActivity.this, "Forgot Password - Coming Soon...!", Toast.LENGTH_LONG).show();
				passwordEt.setVisibility(View.GONE);
				forgotPwd.setVisibility(View.GONE);
				flag_Fp = 1;
				emailEt.setHint("Enter email");
			}
		});

		fbLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "FB Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});

		gpLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "Google Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});
	}

	public class loginWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",user_name;
		private int errorCode = 0;

		loginWSCall(String user_email, String user_password) {
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(LoginActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing() && !isFinishing())
				mLoader.dismiss();

			if (errorCode == 0) {

			} else {

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
								String uri = RestApi.createURI(RestApi.Login_WS)+"/user_name/"+userName + "/password/" + passWord;
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(LoginActivity.this, "login"+result, Toast.LENGTH_LONG).show();
										
									}
								});
								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									errorCode = Integer.parseInt(objRes.optString("response"));

									if (errorCode == 1) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												emailEt.setText("");
												passwordEt.setText("");
												user_name = objRes.optString("username");
												shareData.putString("ListnerId", objRes.optString("id"));
												shareData.putString("ListnerName", user_name);
												shareData.putString("ListnerProfilePic", objRes.optString("profile_pic"));
												
												Intent intent = new Intent(LoginActivity.this,MainActivity.class);
												intent.putExtra("userName", user_name);
												startActivity(intent);
												finish();
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(LoginActivity.this, "Please check your Internet connection",
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
	
	public class forgotPasswordWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",user_name;
		private int errorCode = 0;

		forgotPasswordWSCall(String user_email) {
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(LoginActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();

			if (errorCode == 0) {

			} else {

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
								String uri = RestApi.createURI(RestApi.ForgotPassword_WS)+ "/email/" + userName;
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);
								runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(LoginActivity.this, "fp"+result, Toast.LENGTH_LONG).show();
										
									}
								});
								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									errorCode = Integer.parseInt(objRes.optString("response"));

									if (errorCode == 1) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												
												emailEt.setText("");
												passwordEt.setText("");
												user_name = objRes.optString("username");
												Intent intent = new Intent(LoginActivity.this,MainActivity.class);
												intent.putExtra("userName", user_name);
												startActivity(intent);
												
												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, ""+errorMessage, Toast.LENGTH_LONG)
												.show();
												
												passwordEt.setVisibility(View.VISIBLE);
												forgotPwd.setVisibility(View.VISIBLE);
												flag_Fp = 0;
												emailEt.setHint("Email/User Name");
												
												//finish();
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(LoginActivity.this, "Please check your Internet connection",
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
*/
package e.aakriti.work.podcast_app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import e.aakriti.work.common.SharedData;

public class LoginActivity extends Activity {
	

    
    //User data collected from login with social
    static String firstName = null;
    static String lastName = null;
    static String email = null;
    static Bitmap userPicture = null;

	/*Button submitBtn, 
	TextView forgotPwd, backTxt;
	ImageView fbLogin, gpLogin;
	Utility utility;
	String userName, passWord;
	EditText emailEt, passwordEt;*/
	Button connectWithPhoneButton,connectWithFacebookButton,connectWithGooglePlusButton,
	       connectWithLinkedInButton;
	int flag_Fp=0;
	SharedData shareData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

	/*	utility = new Utility(LoginActivity.this);
		submitBtn = (Button) findViewById(R.id.submitBtn);
		forgotPwd = (TextView) findViewById(R.id.forgetPwdTxt);
		backTxt = (TextView) findViewById(R.id.backTxt);
		fbLogin = (ImageView) findViewById(R.id.fbLoginBtn);
		gpLogin = (ImageView) findViewById(R.id.gpLoginBtn);
		emailEt = (EditText) findViewById(R.id.emailEt);
		passwordEt = (EditText) findViewById(R.id.passwordEt);*/
		connectWithPhoneButton =(Button)findViewById(R.id.phoneConnectButton);
		connectWithFacebookButton = (Button) findViewById(R.id.fbConnectButton);
		connectWithGooglePlusButton = (Button)findViewById(R.id.googlePlusConnectButton);
		connectWithLinkedInButton = (Button)findViewById(R.id.inConnectButton);
		
		
		

		shareData = new SharedData(LoginActivity.this);
		
	/*	backTxt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, Landing_PagerActivity.class);
				intent.putExtra("from_Back", "1");
				startActivity(intent);
				finish();
			}
		});               
	                         */
	//********** EventHandler for Connect with Phone ***********	
		connectWithPhoneButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
//**********EventHandler for Connect With Facebook**********
		
		connectWithFacebookButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//[FOR TEST-START]
				Intent intent = new Intent(LoginActivity.this,MainActivity.class);
				//intent.putExtra("userName", user_name);
				startActivity(intent);
				finish();
			    //[FOR TEST-END]	
				
				// TODO Auto-generated method stub
				// Toast.makeText(LoginActivity.this, "Submit - Coming
				// Soon...!", Toast.LENGTH_LONG).show();

/*		    	if(flag_Fp == 1)
				{
					// Forgot Password
					userName = emailEt.getText().toString();
					
					if (Utility.isNotNull(userName) && Utility.checkEmail(userName)) {
						new forgotPasswordWSCall(userName).execute();
						
					} else {
						
							Toast.makeText(LoginActivity.this, "Please enter Valid Email address", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					// Login
					userName = emailEt.getText().toString();
					passWord = passwordEt.getText().toString();
					if (Utility.isNotNull(userName) && Utility.isNotNull(passWord)) {
						new loginWSCall(userName, passWord).execute();
						
					} else {
						if(!Utility.isNotNull(userName))
						{
							Toast.makeText(LoginActivity.this, "Please enter your UserName or Email", Toast.LENGTH_LONG).show();
						}
						
						else if(!Utility.isNotNull(passWord))
						{
							Toast.makeText(LoginActivity.this, "Please provide your Password", Toast.LENGTH_LONG).show();
						}
					}
				}
				
				// }                                                            */                                                                      
			}
		});        

//**********EventHandler for Connect with Google Plus**********
		
		connectWithGooglePlusButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
//**********EventHandler for Connect With LinkedIn**********
		
		connectWithLinkedInButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});

// *************Due to change in customer requirement below section code is commit.***************
		
	/*	forgotPwd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(LoginActivity.this, "Forgot Password - Coming Soon...!", Toast.LENGTH_LONG).show();
				passwordEt.setVisibility(View.GONE);
				forgotPwd.setVisibility(View.GONE);
				flag_Fp = 1;
				emailEt.setHint("Enter email");
			}
		});

		fbLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "FB Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});

		gpLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(LoginActivity.this, "Google Login - Coming Soon...!", Toast.LENGTH_LONG).show();
			}
		});
	}                                                            */                                                                                     

/*	public class loginWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",user_name;
		private int errorCode = 0;

		loginWSCall(String user_email, String user_password) {
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(LoginActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing() && !isFinishing())
				mLoader.dismiss();

			if (errorCode == 0) {

			} else {

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
								String uri = RestApi.createURI(RestApi.Login_WS)+"/user_name/"+userName + "/password/" + passWord;
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);

								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									errorCode = Integer.parseInt(objRes.optString("response"));

									if (errorCode == 1) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												emailEt.setText("");
												passwordEt.setText("");
												user_name = objRes.optString("username");
												shareData.putString("ListnerId", objRes.optString("id"));
												shareData.putString("ListnerName", user_name);
												shareData.putString("ListnerProfilePic", objRes.optString("profile_pic"));
												
												Intent intent = new Intent(LoginActivity.this,MainActivity.class);
												intent.putExtra("userName", user_name);
												startActivity(intent);
												finish();
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(LoginActivity.this, "Please check your Internet connection",
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
	
	public class forgotPasswordWSCall extends AsyncTask<Void, Void, String> {

		private ProgressDialog mLoader;
		private String result = null, errorMessage = "",user_name;
		private int errorCode = 0;

		forgotPasswordWSCall(String user_email) {
		}

		@Override
		protected void onPreExecute() {
			mLoader = new ProgressDialog(LoginActivity.this);
			mLoader.setMessage("Loading");
			mLoader.setCancelable(false);
			mLoader.show();

			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			if (mLoader.isShowing())
				mLoader.dismiss();

			if (errorCode == 0) {

			} else {

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
								String uri = RestApi.createURI(RestApi.ForgotPassword_WS)+ "/email/" + userName;
								//String uri = "http://www.whooshkaa.com/index.php?r=api/LoginDevice&user_name="
									//	+ userName + "&password=" + passWord;
								result = RestApi.getDataFromURLWithoutParam(uri);

								if (Utility.isNotNull(result)) {
									final JSONObject objRes = new JSONObject(result);
									errorCode = Integer.parseInt(objRes.optString("response"));

									if (errorCode == 1) {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub
												
												emailEt.setText("");
												passwordEt.setText("");
												/*user_name = objRes.optString("username");
												Intent intent = new Intent(LoginActivity.this,MainActivity.class);
												intent.putExtra("userName", user_name);
												startActivity(intent);
												
												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, ""+errorMessage, Toast.LENGTH_LONG)
												.show();
												
												passwordEt.setVisibility(View.VISIBLE);
												forgotPwd.setVisibility(View.VISIBLE);
												flag_Fp = 0;
												emailEt.setHint("Email/User Name");
												
												//finish();
											}
										});
									} else {
										runOnUiThread(new Runnable() {

											@Override
											public void run() {
												// TODO Auto-generated method
												// stub

												errorMessage = objRes.optString("msg");
												Toast.makeText(LoginActivity.this, "" + errorMessage, Toast.LENGTH_LONG)
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
							Toast.makeText(LoginActivity.this, "Please check your Internet connection",
									Toast.LENGTH_LONG).show();
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;  
		}  */                                                                                                                                                
	} 
	


}
