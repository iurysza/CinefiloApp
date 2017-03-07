package site.iurysouza.cinefilo.presentation.medialist.pager;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.medialist.MediaListFragment;

import static site.iurysouza.cinefilo.util.Constants.Media.POP_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.REC_MEDIA;
import static site.iurysouza.cinefilo.util.Constants.Media.TOP_MEDIA;

/**
 * Created by Iury Souza on 14/12/2016.
 */

class MediaPagerAdapter extends FragmentPagerAdapter {
  private static final int NUM_FRAGMENTS = 3;

  private Resources resources;
  private final int mediaType;

  MediaPagerAdapter(FragmentManager manager, Context context, int mediaType) {
    super(manager);
    this.resources = context.getResources();
    this.mediaType = mediaType;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case REC_MEDIA:
        return MediaListFragment.newInstance(REC_MEDIA, mediaType);
      case POP_MEDIA:
        return MediaListFragment.newInstance(POP_MEDIA, mediaType);
      case TOP_MEDIA:
        return MediaListFragment.newInstance(TOP_MEDIA, mediaType);
      default:
        return MediaListFragment.newInstance(TOP_MEDIA, mediaType);
    }
  }

  @Override
  public int getCount() {
    return NUM_FRAGMENTS;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case REC_MEDIA:
        return resources.getString(R.string.recent_movies_title);
      case POP_MEDIA:
        return resources.getString(R.string.pop_movies_title);
      case TOP_MEDIA:
        return resources.getString(R.string.top_movies_title);
      default:
        return resources.getString(R.string.top_movies_title);
    }
  }
}