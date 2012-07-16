package jp.ac.titech.cs.sandclock;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SandClockActivity extends Activity implements SensorEventListener{
    /** Called when the activity is first created. */
	private final String TAG = this.getClass().getSimpleName();
	int tt = 0;
	private SensorManager mSensorManager;
	private Screen mTextView;
	double grav;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		FrameLayout fl = new FrameLayout(this);
		setContentView(fl);
        fl.addView(new Screen(this));
        
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	
    	List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_GRAVITY);
    	if(sensors.size()>0){
    		Sensor s = sensors.get(0);
    		mSensorManager.registerListener((SensorEventListener) this, s, SensorManager.SENSOR_DELAY_NORMAL);
    	}
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
    	
    }
    
    @Override
    public void onSensorChanged(SensorEvent event){
    	if(event.sensor.getType() == Sensor.TYPE_GRAVITY){
    		grav = (double)(event.values[0]);
    		
    		Log.i(TAG, String.valueOf(grav));
    	}
    }
    
    public class Screen extends View {
    	int tt=0;
    	public Screen(Context context){
    		super(context);
    	}
    	
    	@Override
    	protected void onDraw(Canvas c){
    		super.onDraw(c);
    		Paint p = new Paint();
    		//draw glass
    		p.setARGB(255, 255, 255, 255);
    		p.setStyle(Paint.Style.STROKE);
    		c.drawText("testString"+String.valueOf(tt), 100, 150, p);
    		
    		//orientation
    		Resources res = getResources();
    		Configuration cfg = res.getConfiguration();
    		if(cfg.orientation == Configuration.ORIENTATION_PORTRAIT){
    			if(grav<0) tt++;
    			else tt--;
    		}
    		
    		p.setStrokeWidth(3);
    		Path path = new Path();
    		path.reset();
    		path.moveTo(287, 459);
    		path.lineTo(3, 23);
    		path.lineTo(577, 23);
    		path.lineTo(293, 459);
    		path.lineTo(577, 895);
    		path.lineTo(3, 895);
    		path.lineTo(287, 459);
    		c.drawPath(path, p);
    		
    		//draw sand
    		p.setColor(Color.YELLOW);
    		p.setStyle(Paint.Style.FILL_AND_STROKE);
    		float maxt = 120;
    		float t = maxt-tt;
    		float rate = (float)Math.sqrt(t/maxt);
    		if(tt>120) rate = 0;

    		path.reset();
    		PointF base1 = new PointF(287,459);
    		path.moveTo(base1.x, base1.y);
    		path.lineTo(base1.x-284*rate*(float)0.6, base1.y-436*rate*(float)0.6);
    		path.lineTo(base1.x+6+284*rate*(float)0.6, base1.y-436*rate*(float)0.6);
    		path.lineTo(base1.x+6, base1.y);
    		c.drawPath(path,p);
    		
    		path.reset();
    		PointF base2 = new PointF(3,895);
    		path.moveTo(base2.x, base2.y);
    		path.lineTo(base2.x+284*(1-rate)*(float)0.6, base2.y-436*(1-rate)*(float)0.6);
    		path.lineTo(base2.x+574-284*(1-rate)*(float)0.6, base2.y-436*(1-rate)*(float)0.6);
    		path.lineTo(base2.x+574, base2.y);
    		c.drawPath(path,p);

    		//Timer
    		try {
    			TimeUnit.SECONDS.sleep(1);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    				
    		invalidate();
    		
    		//How do i get the current orientation - stack overflow
    	}
    }
}