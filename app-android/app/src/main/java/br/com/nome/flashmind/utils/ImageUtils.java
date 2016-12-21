package br.com.nome.flashmind.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Alessandro Pryds on 20/12/2016.
 */

public class ImageUtils {
    public static void loadImageFromUrl(Context context, ImageView imageView, String url, boolean animate) {
        if (!animate) {
            Glide.with(context).load(url).dontAnimate().into(imageView);
            return;
        }
        Glide.with(context).load(url).into(imageView);
    }
}
