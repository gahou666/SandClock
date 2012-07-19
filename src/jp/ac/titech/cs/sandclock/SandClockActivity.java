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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SandClockActivity extends Activity implements SensorEventListener{
	static class Point{
		int x;
		int y;
		Point(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
    /** Called when the activity is first created. */
	private final String TAG = this.getClass().getSimpleName();
	private SensorManager mSensorManager;
	private Screen mTextView;
	double gravx, gravy;
	Point CLOCKBASE = new Point(3,23);
	Point CLOCKEND = new Point(577,895);
	int EXIT_WIDTH = 3;
	Point CLOCKCENTER = new Point((CLOCKBASE.x+CLOCKEND.x)/2,(CLOCKBASE.y+CLOCKEND.y)/2);
	final double GRAVITY = 9.80619920;
	//default is 3 minutes
	double tt = 0;
	double maxt = 9000;	//50 per second , 3000 per minute
	double Uamount = Math.sqrt(0.3);
	double Lamount = 1-Math.sqrt(0.7);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        setContentView(l);
        l.addView(new Screen(this));
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
    		gravx = (double)(event.values[0]);
    		gravy = (double)(event.values[1]);
    		
    		Log.i(TAG, "x"+String.valueOf(gravx));
    		Log.i(TAG, "y"+String.valueOf(gravy));
    		
    	}
    }
    
    public class Screen extends View {
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
    		c.drawText("testString"+String.valueOf(tt), CLOCKCENTER.x+50, CLOCKCENTER.y, p);
    		
    		//orientation is fixed.
    		/*
    		Resources res = getResources();
    		Configuration cfg = res.getConfiguration();
    		if(cfg.orientation == Configuration.ORIENTATION_PORTRAIT){
    			if(gravx<0) tt+=1.0;
    			else tt-=1.0;
    		}
    		*/
    		tt-=(gravx/GRAVITY);
    		
    		p.setStrokeWidth(3);
    		Path path = new Path();
    		path.reset();
    		path.moveTo(CLOCKBASE.x, CLOCKBASE.y);
    		path.lineTo(CLOCKEND.x, CLOCKBASE.y);
    		path.lineTo(CLOCKCENTER.x+EXIT_WIDTH, CLOCKCENTER.y);
    		path.lineTo(CLOCKEND.x, CLOCKEND.y);
    		path.lineTo(CLOCKBASE.x, CLOCKEND.y);
    		path.lineTo(CLOCKCENTER.x-EXIT_WIDTH, CLOCKCENTER.y);
    		path.lineTo(CLOCKBASE.x, CLOCKBASE.y);
    		c.drawPath(path, p);
    		path.reset();
    		
    		//draw sand
    		p.setStrokeWidth(1);
    		p.setColor(Color.YELLOW);
    		p.setStyle(Paint.Style.FILL_AND_STROKE);
    		
    		if(tt<0) tt=0;
    		if(tt>maxt) tt=maxt;
    		
    		float rate = (float)(Math.sqrt(1-(float)(tt/maxt))*Uamount);
    		float drate = (float)((1-Math.sqrt(1-(float)(tt/maxt)))*Lamount);
    		float rate2 = (float)(Math.sqrt(1-(float)((maxt-tt)/maxt))*Uamount);
    		float drate2 = (float)((1-Math.sqrt(1-(float)((maxt-tt)/maxt)))*Lamount);
    		Point BIAS = new Point(CLOCKEND.x-CLOCKCENTER.x-EXIT_WIDTH, CLOCKBASE.y-CLOCKCENTER.y);
    		/*
    		Log.i(TAG, "t    : "+String.valueOf(tt));
    		Log.i(TAG, "maxt : "+String.valueOf(maxt));
    		Log.i(TAG, "rate : "+String.valueOf(rate));
    		*/
    		//portrait
    		if(gravx<0){
        		if(maxt>tt){
            		//draw upper sand
            		path.reset();
            		path.moveTo(CLOCKCENTER.x+EXIT_WIDTH, CLOCKCENTER.y);
            		path.lineTo(CLOCKCENTER.x+EXIT_WIDTH+BIAS.x*rate, CLOCKCENTER.y+BIAS.y*rate);
            		path.lineTo(CLOCKCENTER.x-EXIT_WIDTH-BIAS.x*rate, CLOCKCENTER.y+BIAS.y*rate);
            		path.lineTo(CLOCKCENTER.x-EXIT_WIDTH, CLOCKCENTER.y);
            		c.drawPath(path,p);
            		path.reset();
            		//draw lower sand
            		path.reset();
            		path.moveTo(CLOCKBASE.x, CLOCKEND.y);
            		path.lineTo(CLOCKBASE.x+BIAS.x*drate, CLOCKEND.y+BIAS.y*drate);
            		path.lineTo(CLOCKEND.x-BIAS.x*drate, CLOCKEND.y+BIAS.y*drate);
            		path.lineTo(CLOCKEND.x, CLOCKEND.y);
            		c.drawPath(path,p);
            		path.reset();
        		}else{
        			//draw lower sand only
            		path.reset();
            		path.moveTo(CLOCKBASE.x, CLOCKEND.y);
            		path.lineTo((float)(CLOCKBASE.x+BIAS.x*0.3), (float)(CLOCKEND.y+BIAS.y*0.3));
            		path.lineTo((float)(CLOCKEND.x-BIAS.x*0.3), (float)(CLOCKEND.y+BIAS.y*0.3));
            		path.lineTo(CLOCKEND.x, CLOCKEND.y);
            		c.drawPath(path,p);    			
            		path.reset();
        		}
        		//portrait_reverse
    		}else{
        		if(0<tt){
            		//draw upper sand
            		path.reset();
            		path.moveTo(CLOCKCENTER.x+EXIT_WIDTH, CLOCKCENTER.y);
            		path.lineTo(CLOCKCENTER.x+EXIT_WIDTH-BIAS.x*rate2, CLOCKCENTER.y-BIAS.y*rate2);
            		path.lineTo(CLOCKCENTER.x-EXIT_WIDTH+BIAS.x*rate2, CLOCKCENTER.y-BIAS.y*rate2);
            		path.lineTo(CLOCKCENTER.x-EXIT_WIDTH, CLOCKCENTER.y);
            		c.drawPath(path,p);
            		path.reset();
            		//draw lower sand
            		path.reset();
            		path.moveTo(CLOCKEND.x, CLOCKBASE.y);
            		path.lineTo(CLOCKEND.x-BIAS.x*drate2, CLOCKBASE.y-BIAS.y*drate2);
            		path.lineTo(CLOCKBASE.x+BIAS.x*drate2, CLOCKBASE.y-BIAS.y*drate2);
            		path.lineTo(CLOCKBASE.x, CLOCKBASE.y);
            		c.drawPath(path,p);
            		path.reset();
        		}else{
        			//draw lower sand only
            		path.reset();
            		path.moveTo(CLOCKEND.x, CLOCKBASE.y);
            		path.lineTo((float)(CLOCKEND.x-BIAS.x*0.3), (float)(CLOCKBASE.y-BIAS.y*0.3));
            		path.lineTo((float)(CLOCKBASE.x+BIAS.x*0.3), (float)(CLOCKBASE.y-BIAS.y*0.3));
            		path.lineTo(CLOCKBASE.x, CLOCKBASE.y);
            		c.drawPath(path,p);    			
            		path.reset();
        		}
    		}
    		//Timer
    		try {
    			TimeUnit.MILLISECONDS.sleep(10);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    				
    		invalidate();
    	}
    }
    
	public boolean onCreateOptionsMenu(Menu menu){
		boolean ret = super.onCreateOptionsMenu(menu);
		menu.add(0, Menu.FIRST, Menu.NONE, "2minutes");
		menu.add(0, Menu.FIRST+1, Menu.NONE, "3minutes");
		menu.add(0, Menu.FIRST+2, Menu.NONE, "5minutes");
		return ret;
	}
	
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case Menu.FIRST:
			tt = 0;
			maxt = 6000;
			Uamount = Math.sqrt(0.2);
			Lamount = 1-Math.sqrt(0.8);
			break;
		case Menu.FIRST+1:
			tt = 0;
			maxt = 9000;
			Uamount = Math.sqrt(0.3);
			Lamount = 1-Math.sqrt(0.7);
			break;
		case Menu.FIRST+2:
			tt = 0;
			maxt = 15000;
			Uamount = Math.sqrt(0.5);
			Lamount = 1-Math.sqrt(0.5);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}