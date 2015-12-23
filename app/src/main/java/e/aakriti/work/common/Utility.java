package e.aakriti.work.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Utility {

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]+");
    static Handler mHandler;
    private static final String TAG = "Utility";
    private Context context;
    
    
	public Utility(Context cnt){
		context=cnt;
	}
	
    public static Handler getHandler() {
        return mHandler;
    }

    public static void setHandler(Handler handler) {
        mHandler = handler;
    }

    public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) { 
	        //return ping("http://www.google.com");
	    	return true;
	    }else{
	        return false;
	    }
	}
	
	public boolean isNetworkAvailableSimple() {
	     ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	     NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	     return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	 }

	
	public static boolean ping(String u) { 
        try {
            URL url = new URL(u);
            HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
            urlc.setConnectTimeout(1000); // Time is in Milliseconds to wait for ping response
            urlc.connect();
            InputStreamReader in = new InputStreamReader((InputStream) urlc.getContent());
    	    BufferedReader buff = new BufferedReader(in);
    	    String line;
    	    StringBuffer text=new StringBuffer();
    	    do {
    	      line = buff.readLine();
    	      text.append(line + "\n");
    	    } while(line != null);
            
    	    if(text.toString().length()<400){
    	    	return false;
    	    }
            if (urlc.getResponseCode() == 200) {
                return true;
            }else{
                return false;
            }
        }catch (MalformedURLException e1){
            Log.e(TAG,"Erroe in URL to ping "+e1);
            return false;
        }catch (IOException e){
            Log.e(TAG,"Error in ping "+e);
            return false;
        }
        catch (Exception e){
            Log.e(TAG,"Error in ping "+e);
            return false;
        }
    }
	
    public static String getCurrentDateTime(int whatYouRequired) {
        // return the date
        if (whatYouRequired == 0) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            Date date = new Date();
            return dateFormat.format(date);
        }
        // return the time
        else if (whatYouRequired == 1) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
            Date date = new Date();
            return dateFormat.format(date);
        }
        else if(whatYouRequired == 2)
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            Date date = new Date();
            return dateFormat.format(date);
        }
        return null;
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isNull(String str) {
        return str == null || str.equalsIgnoreCase("null")
                || str.trim().length() == 0;
    }

    public static boolean isNotNull(String str) {
        return !(str == null ||
                str.equalsIgnoreCase("null") ||
                str.equalsIgnoreCase("") ||
                str.equalsIgnoreCase(null) ||
                str.trim().length() == 0);
    }

    public static boolean isValidDate(String date) {
        Pattern DATE_PATTERN = Pattern
                .compile("^([0-9]{4})*-([0-9]{2})*-([0-9]{2})$");
        return DATE_PATTERN.matcher(date).matches();
    }

    public static boolean checkEmail(String email_address) {
        return EMAIL_ADDRESS_PATTERN.matcher(email_address).matches();
    }

    /**
     * CONTACT US <br>
     * checkName() <br>
     * use for check name pattern..
     *
     * @param name - String
     * @return return <b>"true"</b> when is valid name. else return <b>"false"</b>;
     */
    public static boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    public static String convertDateTimeToIST(String originalDateTime) {
        String convertedDateTime = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = formatter.parse(originalDateTime);

            // To Convert TimeZone in Asia/Kolkata
            SimpleDateFormat sdfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone tzInIST = TimeZone.getTimeZone("America/New_York");
            sdfIST.setTimeZone(tzInIST);

            String sDateInIST = sdfIST.format(date); // Convert to String first
            convertedDateTime = sDateInIST;
            Date dateInIST = formatter.parse(sDateInIST);

            Log.e("Converted TimeZone", "" + tzInIST.getID() + " - " + tzInIST.getDisplayName());
            Log.e("Converted Date(Object)", "" + formatter.format(dateInIST));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedDateTime;
    }

    public static String convertDateTimeToDeviceTZ(String originalDateTime) {
        String convertedDateTime = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
            Date date = formatter.parse(originalDateTime);

            SimpleDateFormat sdfIST = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            TimeZone tzInIST = TimeZone.getDefault();
            sdfIST.setTimeZone(tzInIST);

            String sDateInIST = sdfIST.format(date); // Convert to String first
            convertedDateTime = sDateInIST;
            Date dateInIST = formatter.parse(sDateInIST);

            Log.e("Converted TimeZone", "" +" - "+tzInIST.getID() + " - " + tzInIST.getDisplayName());
            Log.e("Converted Date(Object)", "" + formatter.format(dateInIST));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertedDateTime;
    }
}
