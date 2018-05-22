package me.andrewpeng.cadence.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;

import me.andrewpeng.cadence.core.Renderer;

/**
 * Created by isaacleung on 2018-05-05.
 */

public class HyperlinkText {
    public String text;
    public String URL;
    public static int x;
    public static int y;
    public int x1,x2,y1,y2;
    public static int textSize;
    public static int color;
    public static int alpha;
    public static SpannableString test;

    public HyperlinkText(String text, String URL, int x, int y, int textSize, int color, int alpha) {
        this.text = text;
        this.URL = URL;
        this.x = x;
        this.y = y;
        this.textSize = textSize;
        this.color = color;
        this.alpha = alpha;

        test = new SpannableString(text);
        test.setSpan(new URLSpan(URL),12,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


    }

    public static void render(Canvas graphics, Paint paint) {
        int oldAlpha = paint.getAlpha();
        paint.setAlpha(alpha);
        Renderer.centerText(test + "", graphics, x, y, paint, textSize, color, 255);
        paint.setAlpha(oldAlpha);
    }
}
