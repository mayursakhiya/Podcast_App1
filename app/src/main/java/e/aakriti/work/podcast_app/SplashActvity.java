package e.aakriti.work.podcast_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;

public class SplashActvity extends Activity implements OnChronometerTickListener{
	
	Chronometer chr;
	int TimeOut = 3;
	int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		chr = (Chronometer) findViewById(R.id.chr);
		chr.setOnChronometerTickListener(this);
		chr.start();
	}
	public void onChronometerTick(Chronometer arg0) {
		// TODO Auto-generated method stub
		count ++;
		if(count >= TimeOut)
		{
			chr.stop();
			moveToNextScreen();
		}
	}
	public void moveToNextScreen()
	{
		Intent intent = new Intent(SplashActvity.this,LoginActivity.class);
		startActivity(intent);
		//overridePendingTransition(R.anim.slide_in_right,
			//	R.anim.slide_out_right);
		finish();
	}

}
