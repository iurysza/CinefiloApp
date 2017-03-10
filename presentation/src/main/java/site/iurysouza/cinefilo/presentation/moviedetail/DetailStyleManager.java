package site.iurysouza.cinefilo.presentation.moviedetail;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flaviofaria.kenburnsview.KenBurnsView;
import lombok.experimental.Builder;
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 02/02/2017.
 */

public class DetailStyleManager {

  private static final int REVEAL_DURATION = 450;
  private static final int REVEAL_DELAY = 150;
  private Context context;
  private String backDropPath;
  private String posterPath;
  private CollapsingToolbarLayout collapsingToolbarLayout;
  private Toolbar toolbar;
  private KenBurnsView kenBurnsView;
  private AppBarLayout appBarLayout;
  private TabLayout tabLayout;

  @Builder
  public DetailStyleManager(
      Context context,
      String backDropPath,
      String posterPath,
      CollapsingToolbarLayout collapsingToolbarLayout,
      Toolbar toolbar,
      KenBurnsView kenBurnsView,
      AppBarLayout appBarLayout,
      TabLayout tabLayout
  ) {
    this.context = context;
    this.backDropPath = backDropPath;
    this.posterPath = posterPath;
    this.collapsingToolbarLayout = collapsingToolbarLayout;
    this.toolbar = toolbar;
    this.kenBurnsView = kenBurnsView;
    this.appBarLayout = appBarLayout;
    this.tabLayout = tabLayout;
  }

  void loadBitmapAndCreateColorPallete() {
    SimpleTarget posterTarget = new SimpleTarget<Bitmap>() {
      @Override
      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        createPalleteFrom(resource);
      }
    };


    SimpleTarget backDropTarget = new SimpleTarget<Bitmap>() {
      @Override
      public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
        revealBackDrop(resource);
      }
    };

    Glide
        .with(context)
        .load(ImageUtils.getPosterUrl(posterPath))
        .asBitmap()
        .into(posterTarget);

    Glide
        .with(context)
        .load(ImageUtils.getBackDropUrl(backDropPath))
        .asBitmap()
        .into(backDropTarget);
  }

  private void createPalleteFrom(Bitmap bitmap) {
    if (bitmap != null && !bitmap.isRecycled()) {
      Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        @Override public void onGenerated(Palette palette) {
          int defaultColor = 0x000000;
          int dominantColor = palette.getDominantColor(defaultColor);
          Palette.Swatch dominantSwatch = palette.getDominantSwatch();
          int titleTextColor;
          if (dominantSwatch != null) {
            titleTextColor = dominantSwatch.getTitleTextColor();
          } else {
            titleTextColor = Color.BLACK;
          }

          Palette.Swatch lightMutedSwatch = palette.getLightMutedSwatch();
          int tabDefaultTextColor;
          int tabSelectedTextColor;
          if (lightMutedSwatch != null) {
            tabDefaultTextColor = lightMutedSwatch.getTitleTextColor();
            tabSelectedTextColor = lightMutedSwatch.getBodyTextColor();
          } else {
            tabDefaultTextColor = Color.GRAY;
            tabSelectedTextColor = Color.BLACK;
          }
          collapsingToolbarLayout.setContentScrimColor(dominantColor);
          collapsingToolbarLayout.setStatusBarScrimColor(dominantColor);
          collapsingToolbarLayout.setBackgroundColor(dominantColor);
          toolbar.setTitleTextColor(titleTextColor);
          tabLayout.setTabTextColors(tabDefaultTextColor, tabSelectedTextColor);
          tabLayout.setSelectedTabIndicatorColor(dominantColor);
        }
      });
    }
  }

  private void revealBackDrop(Bitmap bitmap) {
    kenBurnsView.post(new Runnable() {
      @Override public void run() {
        int x = collapsingToolbarLayout.getRight() / 2;
        int y = collapsingToolbarLayout.getBottom();

        int startRadius = 0;
        int endRadius = (int) Math.hypot(appBarLayout.getWidth(), appBarLayout.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(
            kenBurnsView,
            x,
            y,
            startRadius,
            endRadius);

        kenBurnsView.setVisibility(View.VISIBLE);
        anim.setStartDelay(REVEAL_DELAY);
        anim
            .setDuration(REVEAL_DURATION)
            .addListener(new Animator.AnimatorListener() {
              @Override public void onAnimationStart(Animator animation) {
                kenBurnsView.setImageBitmap(bitmap);
              }

              @Override public void onAnimationEnd(Animator animation) {

              }

              @Override public void onAnimationCancel(Animator animation) {

              }

              @Override public void onAnimationRepeat(Animator animation) {

              }
            });
        anim.start();
      }
    });
  }
}