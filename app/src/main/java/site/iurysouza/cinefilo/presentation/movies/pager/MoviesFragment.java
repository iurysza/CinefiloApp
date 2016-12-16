package site.iurysouza.cinefilo.presentation.movies.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import io.realm.RealmModel;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.movies.MoviesView;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesFragment extends BaseFragment implements MoviesView {

  @BindView(R.id.viewpager_fragment_movies) MaterialViewPager materialViewPager;


  public static MoviesFragment newInstance() {
    MoviesFragment moviesFragment = new MoviesFragment();
    Bundle args = new Bundle();
    moviesFragment.setArguments(args);
    return moviesFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.content_movies_fragment, container, false);
    ButterKnife.bind(this, view);

    MoviesPagerAdapter adapter = new MoviesPagerAdapter(getFragmentManager(), getContext());
    ViewPager viewPager = materialViewPager.getViewPager();
    viewPager.setAdapter(adapter);

    materialViewPager.getPagerTitleStrip().setViewPager(materialViewPager.getViewPager());
    materialViewPager.setMaterialViewPagerListener(createHeaderListener());
    return view;
  }

  @NonNull private MaterialViewPager.Listener createHeaderListener() {
    return page -> {
      switch (page) {
        case 0:
          return HeaderDesign.fromColorResAndUrl(
              R.color.blue,
              "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
        case 1:
          return HeaderDesign.fromColorResAndUrl(
              R.color.green,
              "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
        case 2:
          return HeaderDesign.fromColorResAndUrl(
              R.color.cyan,
              "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
        case 3:
          return HeaderDesign.fromColorResAndUrl(
              R.color.red,
              "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
      }

      //execute others actions if needed (ex : modify your header logo)
      return null;
    };
  }

  @Override public void showLoadingIndicator() {

  }

  @Override public void hideLoadingIndicator() {

  }

  @Override public void showErrorIndicator() {

  }

  @Override public void showPopularMovieList(RealmModel popularMovieList) {

  }



  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }
}
