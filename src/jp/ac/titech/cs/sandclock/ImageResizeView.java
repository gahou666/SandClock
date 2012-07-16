package jp.ac.titech.cs.sandclock;

import android.content.Context;
import android.graphics.*;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class ImageResizeView extends View{
    
    private Bitmap bmp;
    private int dp_w;
    private int dp_h;
    private int drow_h;
    private int drow_s;
     
    public ImageResizeView(Context context, int resource_name){
        super(context);
        // リソースからbitmapを作成
        bmp = BitmapFactory.decodeResource(context.getResources(), resource_name);
        // WindowManager取得
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        // Displayインスタンス生成
        Display dp = wm.getDefaultDisplay();
        // ディスプレイサイズ取得
        dp_w = dp.getWidth();
        dp_h = dp.getHeight();
        // リサイズ画像の高さ
        drow_h = (dp_w / 2) * 3;
        // 描画始点の高さ
        drow_s = (dp_h - drow_h) / 2;
    }
 
    @Override
    protected void onDraw(Canvas canvas){
        // 背景色
        canvas.drawColor(Color.BLACK);
        // イメージ画像リサイズ
        bmp = Bitmap.createScaledBitmap(bmp, dp_w, drow_h , true); 
        // 描画
        canvas.drawBitmap(bmp, 0, drow_s, null);
    }
}
