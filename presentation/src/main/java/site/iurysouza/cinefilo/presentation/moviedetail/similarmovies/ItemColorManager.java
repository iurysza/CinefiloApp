package site.iurysouza.cinefilo.presentation.moviedetail.similarmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.widget.FrameLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 03/02/2017.
 */

public class ItemColorManager {

  private Context context;
  private String posterPath;
  private FrameLayout overlay;

  @Builder
  public ItemColorManager(
      Context context,
      String posterPath,
      FrameLayout overlay) {
    this.context = context;
    this.posterPath = posterPath;
    this.overlay = overlay;
  }

  void loadBitmapAndCreateColorPallete() {


    SimpleTarget backDropTarget = new SimpleTarget<Bitmap>() {
      @Override
      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        createPalleteFrom(resource);
      }
    };

    Glide
        .with(context)
        .load(ImageUtils.getPosterUrl(posterPath))
        .asBitmap()
        .placeholder(R.drawable.placeholder)
        .into(backDropTarget);


  }

  private void createPalleteFrom(Bitmap bitmap) {
    if (bitmap != null && !bitmap.isRecycled()) {
      Palette.from(bitmap).generate(palette -> {
        int defaultColor = 0x000000;
        int dominantColor = palette.getDominantColor(defaultColor);
        int lightColor = palette.getDominantSwatch().getTitleTextColor();

        GradientDrawable gradientDrawable =
            new GradientDrawable(
                GradientDrawable.Orientation.BOTTOM_TOP,
                new int[] {dominantColor,lightColor});
        gradientDrawable.setCornerRadius(0f);
        overlay.setBackground(gradientDrawable);
      });
    }
  }
}