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
        // ���\�[�X����bitmap���쐬
        bmp = BitmapFactory.decodeResource(context.getResources(), resource_name);
        // WindowManager�擾
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        // Display�C���X�^���X����
        Display dp = wm.getDefaultDisplay();
        // �f�B�X�v���C�T�C�Y�擾
        dp_w = dp.getWidth();
        dp_h = dp.getHeight();
        // ���T�C�Y�摜�̍���
        drow_h = (dp_w / 2) * 3;
        // �`��n�_�̍���
        drow_s = (dp_h - drow_h) / 2;
    }
 
    @Override
    protected void onDraw(Canvas canvas){
        // �w�i�F
        canvas.drawColor(Color.BLACK);
        // �C���[�W�摜���T�C�Y
        bmp = Bitmap.createScaledBitmap(bmp, dp_w, drow_h , true); 
        // �`��
        canvas.drawBitmap(bmp, 0, drow_s, null);
    }
}
