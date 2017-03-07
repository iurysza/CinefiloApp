package site.iurysouza.cinefilo.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.res.ResourcesCompat;

/**
 * Created by Iury Souza on 20/12/2016.
 */

public class ImageUtils {
  public static String getPosterUrl(String url) {
    return Constants.MOVIE_DB_API.BASE_IMAGE_URL + url;
  }

  public static String getBackDropUrl(String url) {
    return Constants.MOVIE_DB_API.BASE_IMAGE_URL + url;
  }

  private static Drawable changeDrawableColor(Context context, @DrawableRes int drawableId,
      int color) {
    Drawable drawable = context.getResources().getDrawable(drawableId);
    drawable.setColorFilter(new
        PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
    return drawable;
  }

  public static Drawable changeIconColor(Context context, @DrawableRes int iconRes, @ColorRes int colorRes) {
    @ColorRes int color =
        ResourcesCompat.getColor(context.getResources(), colorRes, null);
    return changeDrawableColor(context, iconRes, color);
  }

}
