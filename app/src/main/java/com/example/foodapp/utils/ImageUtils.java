package com.example.foodapp.utils;

import android.graphics.*;

public class ImageUtils {
    public static Bitmap getCircularBitmap(Bitmap srcBitmap) {
        int size = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        Bitmap output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(srcBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);

        return output;
    }
}
