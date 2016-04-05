package net.mkindustries.testgame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
    boolean joyReset = true;
    Canvas canvas;

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

    int random(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                joyReset = false;
                break;

            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                joyReset = false;
                break;

            case MotionEvent.ACTION_UP:
                joyReset = true;
                break;
        }
//        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
//            x = 0;
//            y = 0;
//        }


        return true;
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

        int playerX = 1230;
        int playerY = 770;

        public void up() {
            playerY -= 15;

        }

        public void left() {
            playerX -= 15;

        }

        public void right() {
            playerX += 15;

        }

        public void down() {
            playerY += 15;

        }

        public void changeTile() {
            if(playerY < 0){
                drawTile(0); //TOP
            }
            else if(playerX > canvas.getWidth()) {
                drawTile(1); //RIGHT
            }
            else if(playerY > canvas.getHeight()) {
                drawTile(2); //BOTTOM
            }
            else if(playerX < 0){
                drawTile(3); //LEFT
            }
            else{
                //AWKWARD
            }
        }

        public void drawTile(int side) {
            int randomR = random(0,255);
            int randomG = random(0,255);
            int randomB = random(0,255);
            if (side == 0) {
                playerY += canvas.getHeight();
                //canvas.drawRGB(randomR, randomG, randomB);
            }
            else if (side == 1){
                playerX -= canvas.getWidth();
                //canvas.drawRGB(randomR, randomG, randomB);
            }
            else if (side == 2){
                playerY -= canvas.getHeight();
                //canvas.drawRGB(randomR, randomG, randomB);
            }
            else if (side == 3){
                playerX += canvas.getWidth();
                //canvas.drawRGB(randomR, randomG, randomB);
            }
            else {
                playerX = canvas.getWidth()/2;
                playerY = canvas.getHeight()/2;
                canvas.drawRGB(0,100,0);
            }
        }
        //@Override
        public void run() {

            while(isRunning){
                if(!holder.getSurface().isValid())
                    continue;

                canvas = holder.lockCanvas();
                canvas.drawRGB(0, 100, 0);
                int width = canvas.getWidth();
                int height = canvas.getHeight();
                Paint joy = new Paint();
                joy.setARGB(100,255,0,255);
                Paint joyOuter = new Paint();
                joyOuter.setARGB(200,100,100,100);
                float joysize = width/5;
                Bitmap playerRaw = BitmapFactory.decodeResource(getResources(), R.drawable.player);
                Bitmap player = Bitmap.createScaledBitmap(playerRaw, canvas.getWidth()/5, canvas.getWidth()/5, false);
                canvas.drawBitmap(player, playerX, playerY, null);
                canvas.drawCircle(joysize-joysize/2,(height-joysize)+joysize/2, joysize/2, joyOuter);

                if (playerY < 0 || playerX < 0 || playerY > canvas.getHeight() || playerX > canvas.getWidth())
                {
                    changeTile();

                }



                if (x != 0 && y != 0 && joyReset == false/* && x < joysize && y > height-joysize*/) {


                    /*if(y < (height-joysize)+joysize/2) {
                        up();
                        if(x > joysize-joysize/2) {
                            right();
                        }
                        else if(x < joysize-joysize/2) {
                            left();
                        }
                    }
                    else if(y > (height-joysize)+joysize/2) {
                        down();
                        if(x > joysize-joysize/2) {
                            right();
                        }
                        else if(x < joysize-joysize/2) {
                            left();
                        }
                    }*/

                    if(y < (height-joysize)+joysize/3 && x > joysize/3 && x < joysize-joysize/3) {
                        up();
                    }
                    else if (y > height-joysize/3 && x > joysize/3 && x < joysize-joysize/3) {
                        down();
                    }
                    else if (x > 2*joysize/3 && y > height-(2*joysize/3) && y < height-joysize/3) {
                        right();
                    }
                    else if (x < joysize/3 && y > height-(2*joysize/3) && y < height-joysize/3) {
                        left();
                    }
                    else if (y < (height-joysize)+joysize/3 && x > 2*joysize/3) {
                        up();
                        right();
                    }
                    else if (y < (height-joysize)+joysize/3 && x < joysize/3) {
                        up();
                        left();
                    }
                    else if (y > height-joysize/3 && x < joysize/3) {
                        down();
                        left();
                    }
                    else if (y > height-joysize/3 && x > joysize/3) {
                        down();
                        right();
                    }
                    canvas.drawCircle(x, y, canvas.getWidth()/20, joy);

                }
                else
                {
                    canvas.drawCircle(joysize-joysize/2,(height-joysize)+joysize/2, canvas.getWidth()/20, joy);
                }

                holder.unlockCanvasAndPost(canvas);
            }

        }


    }

}
