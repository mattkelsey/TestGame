package net.mkindustries.testgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

/**
 * Created by Matt on 3/4/2015.
 */
public class GameClass extends View {

    Bitmap player;

    public GameClass(Context context) {
        super(context);
        player = BitmapFactory.decodeResource(getResources(), R.drawable.player);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(player, (canvas.getWidth()/2), 0, null);
    }

}
