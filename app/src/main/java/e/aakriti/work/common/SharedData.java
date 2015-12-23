package e.aakriti.work.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedData {
	private Context context;
	private String preferenceName = "aakriti";

	public SharedData(Context cnt) {
		context = cnt;
	}

	public void clearAll(){
		  SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		  SharedPreferences.Editor spe = sp.edit();
		  spe.clear();
		  spe.commit();
		 }
	
	public void putString(String key, String value){
		SharedPreferences sp = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

		SharedPreferences.Editor spe = sp.edit();
		spe.putString(key, value);
		spe.commit();
	}

	public void putBoolean(String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putBoolean(key, value);
		spe.commit();
	}

	public void putInt(String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor spe = sp.edit();
		spe.putInt(key, value);
		spe.commit();
	}

	public String getString(String key, String defaultValue) {
		String value = "";
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		value = sp.getString(key, defaultValue);
		return value;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		boolean value = false;
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		value = sp.getBoolean(key, defaultValue);
		return value;
	}

	public int getInt(String key, int defaultValue) {
		int value = -1;
		SharedPreferences sp = context.getSharedPreferences(preferenceName,
				Context.MODE_PRIVATE);
		value = sp.getInt(key, defaultValue);
		return value;
	}
	
}
