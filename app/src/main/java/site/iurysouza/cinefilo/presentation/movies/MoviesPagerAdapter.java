package site.iurysouza.cinefilo.presentation.movies;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 14/12/2016.
 */

public class MoviesPagerAdapter extends FragmentPagerAdapter {
  private static final int NUM_FRAGMENTS = 3;
  public static final int REC_MOVIES = 0;
  public static final int POP_MOVIES = 1;
  public static final int TOP_MOVIES = 2;

  private MovieListFragment popularMoviesFrag;
  private MovieListFragment topMoviesFrag;
  private MovieListFragment recentMoviesFrag;
  private Resources resources;

  public MoviesPagerAdapter(FragmentManager manager, Context context) {
    super(manager);
    this.resources = context.getResources();
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case REC_MOVIES:
        recentMoviesFrag = MovieListFragment.newInstance(REC_MOVIES);
        return recentMoviesFrag;
      case POP_MOVIES:
            popularMoviesFrag = MovieListFragment.newInstance(POP_MOVIES);
        return popularMoviesFrag;
      case TOP_MOVIES:
            topMoviesFrag = MovieListFragment.newInstance(TOP_MOVIES);
        return topMoviesFrag;
      default:
            topMoviesFrag = MovieListFragment.newInstance(TOP_MOVIES);
        return topMoviesFrag;
    }
  }

  @Override
  public int getCount() {
    return NUM_FRAGMENTS;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case REC_MOVIES:
        return resources.getString(R.string.recent_movies_title);
      case POP_MOVIES:
        return resources.getString(R.string.pop_movies_title);
      case TOP_MOVIES:
        return resources.getString(R.string.top_movies_title);
      default:
        return resources.getString(R.string.top_movies_title);
    }
  }
}