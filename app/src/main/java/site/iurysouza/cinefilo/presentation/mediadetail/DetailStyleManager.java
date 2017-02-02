package site.iurysouza.cinefilo.presentation.mediadetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.util.ImageUtils;
import timber.log.Timber;

/**
 * Created by Iury Souza on 02/02/2017.
 */

public class DetailStyleManager {

  private Context context;
  private String backDropPath;
  private CollapsingToolbarLayout collapsingToolbarLayout;
  private Toolbar toolbar;
  private KenBurnsView kenBurnsView;
  private TabLayout tabLayout;
  private Target loadtarget;

  @Builder
  public DetailStyleManager(
      Context context,
      String backDropPath,
      CollapsingToolbarLayout collapsingToolbarLayout,
      Toolbar toolbar,
      KenBurnsView kenBurnsView,
      TabLayout tabLayout
  ) {
    this.context = context;
    this.backDropPath = backDropPath;
    this.collapsingToolbarLayout = collapsingToolbarLayout;
    this.toolbar = toolbar;
    this.kenBurnsView = kenBurnsView;
    this.tabLayout = tabLayout;
  }

  void loadBitmapAndCreateColorPallete() {
    if (loadtarget == null) {
      loadtarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
          createPalleteFrom(bitmap);
          kenBurnsView.setImageBitmap(bitmap);
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
        .into(loadtarget);
  }

  private void createPalleteFrom(Bitmap bitmap) {
    if (bitmap != null && !bitmap.isRecycled()) {
      Palette.from(bitmap).generate(palette -> {
        int defaultColor = 0x000000;
        int dominantColor = palette.getDominantColor(defaultColor);
        int titleTextColor = palette.getDominantSwatch().getTitleTextColor();

        collapsingToolbarLayout.setContentScrimColor(dominantColor);
        collapsingToolbarLayout.setStatusBarScrimColor(dominantColor);
        toolbar.setTitleTextColor(titleTextColor);

        Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
        int tabDefaultTextColor = lightMutedSwatch.getTitleTextColor();
        int tabSelectedTextColor = lightMutedSwatch.getBodyTextColor();

        tabLayout.setTabTextColors(tabDefaultTextColor, tabSelectedTextColor);
        tabLayout.setSelectedTabIndicatorColor(dominantColor);
      });
    }
  }
}
