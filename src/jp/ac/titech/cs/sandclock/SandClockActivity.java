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
        fl.addView(new Screen(this));
    }
}