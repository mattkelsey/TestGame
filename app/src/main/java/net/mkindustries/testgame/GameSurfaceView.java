package net.mkindustries.testgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Matt on 3/4/2015.
 */
public class GameSurfaceView extends Activity implements View.OnTouchListener {
    SurfaceClass surfaceView;
    float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        surfaceView = new SurfaceClass(this);
        surfaceView.setOnTouchListener(this);
        x = 0;
        y = 0;
        setContentView(surfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.pause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.resume();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        return false;
    }

    public class SurfaceClass extends SurfaceView implements Runnable {

        SurfaceHolder holder;
        Thread thread = null;
        boolean isRunning = false;

        public SurfaceClass(Context context) {
            super(context);
            holder = getHolder();
        }

        public void pause() {
            isRunning = false;
            while(true){
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            thread = null;
        }

        public void resume() {
            isRunning = true;
            thread = new Thread(this);
            thread.start();
        }

        //@Override
        public void run() {

            while(isRunning){
                if(!holder.getSurface().isValid())
                    continue;

                Canvas canvas = holder.lockCanvas();
                canvas.drawRGB(0, 255, 100);
                if (x != 0 && y != 0) {
                    Bitmap player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
                    canvas.drawBitmap(player, x, y, null);
                }
                holder.unlockCanvasAndPost(canvas);
            }

        }
    }

}
