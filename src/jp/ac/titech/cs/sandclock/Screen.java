package jp.ac.titech.cs.sandclock;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.View;

public class Screen extends View {
	public Screen(Context context){
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas c){
		super.onDraw(c);
		Paint p = new Paint();
		Bitmap img;
		
		Resources res = this.getContext().getResources();
		img = BitmapFactory.decodeResource(res, R.drawable.t1);
		
		c.drawBitmap(img,0,0,p);
		
	}
}
