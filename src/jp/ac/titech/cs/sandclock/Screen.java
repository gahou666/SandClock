package jp.ac.titech.cs.sandclock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.View;

public class Screen extends View {
	public Screen(Context context){
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas c){
		super.onDraw(c);
		Paint p = new Paint();
		
		p.setARGB(255, 255, 255, 255);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(3);
		float[] line = {
				287, 459,
				3, 23,
				3, 23,
				577, 23,
				577, 23,
				293, 459,
				293, 459,
				577, 895,
				577, 895,
				3, 895,
				3, 895,
				287, 459};
		c.drawLines(line, p);
		
		p.setColor(Color.YELLOW);
		p.setStyle(Paint.Style.FILL_AND_STROKE);
		Path path = new Path();
		float maxt = 3000;
		float t = 0;
		float rate = (float)Math.sqrt(t/maxt);

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
		/*
		Bitmap img;
		
		Resources res = this.getContext().getResources();
		img = BitmapFactory.decodeResource(res, R.drawable.t1);
		
		c.drawBitmap(img,0,0,p);
		*/
		
	}
}
