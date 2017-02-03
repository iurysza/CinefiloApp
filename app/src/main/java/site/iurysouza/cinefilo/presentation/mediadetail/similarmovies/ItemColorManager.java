package site.iurysouza.cinefilo.presentation.mediadetail.similarmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.graphics.Palette;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.util.ImageUtils;
import timber.log.Timber;

/**
 * Created by Iury Souza on 03/02/2017.
 */

public class ItemColorManager {

  private Context context;
  private String backDropPath;
  private FrameLayout overlay;
  private ImageView imageView;
  private Target loadtarget;

  @Builder
  public ItemColorManager(
      Context context,
      String posterPath,
      FrameLayout overlay,
      ImageView imageView) {
    this.context = context;
    this.backDropPath = posterPath;
    this.overlay = overlay;
    this.imageView = imageView;
  }

  void loadBitmapAndCreateColorPallete() {
    if (loadtarget == null) {
      loadtarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          createPalleteFrom(bitmap);
          imageView.setImageBitmap(bitmap);
        }

        @Override public void onBitmapFailed(Drawable errorDrawable) {
          Timber.e("Failed to loadBackdrop image");
        }

        @Override public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
      };
    }

    Picasso
        .with(context)
        .load(ImageUtils.getPosterUrl(backDropPath))
        .placeholder(R.drawable.placeholder)
        .into(loadtarget);
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