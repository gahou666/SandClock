package jp.ac.titech.cs.sandclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SandClockActivity extends Activity {
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		FrameLayout fl = new FrameLayout(this);
		setContentView(fl);
		
		TextView tv = new TextView(this);
    	//setContentView(tv);
    	//fl.addView(tv);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        //setContentView(l);
        fl.addView(l);
        l.addView(new Screen(this));
        
        /*
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(new ImageResizeView(this, R.drawable.t1));
        */
    }
}