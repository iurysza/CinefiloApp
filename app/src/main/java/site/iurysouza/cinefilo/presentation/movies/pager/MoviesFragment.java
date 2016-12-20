package site.iurysouza.cinefilo.presentation.movies.pager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import java.util.List;
import javax.inject.Inject;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.util.ImageUtils;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesFragment extends BaseFragment implements MoviesPagerView {

  private static final int FRAGS_IN_MEMORY = 3;

  @BindView(R.id.viewpager_fragment_movies) MaterialViewPager materialViewPager;
  @Inject MoviesPagerPresenter moviesPresenter;

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
    materialViewPager.getViewPager().setOffscreenPageLimit(FRAGS_IN_MEMORY);

    handleToolbar(materialViewPager.getToolbar());
    moviesPresenter.attachView(this);
    moviesPresenter.loadShowCaseMovies();
    return view;
  }

  private void handleToolbar(Toolbar toolbar) {
    if (toolbar != null) {
      ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
      ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setDefaultDisplayHomeAsUpEnabled(false);
    }
  }

  @Override public void createHeaderChangeListener(List<RealmMovie> showCaseMovies) {
    if (showCaseMovies != null) {
      String popMovieHeader = ImageUtils.getBackDropUrl(showCaseMovies.get(0).getBackdropPath());
      String recMovieHeader = ImageUtils.getBackDropUrl(showCaseMovies.get(1).getBackdropPath());
      String topMovieHeader = ImageUtils.getBackDropUrl(showCaseMovies.get(2).getBackdropPath());
      materialViewPager.setMaterialViewPagerListener(
          createHeaderListener(popMovieHeader, recMovieHeader, topMovieHeader));
    }
  }

  @NonNull private MaterialViewPager.Listener createHeaderListener(String popMovieHeader,
      String recMovieHeader, String topMovieHeader) {
    return page -> {
      switch (page) {
        case 0:
          return HeaderDesign.fromColorResAndUrl(
              R.color.colorPrimaryDark,
              recMovieHeader);
        case 1:
          return HeaderDesign.fromColorResAndUrl(
              R.color.colorPrimaryDark,
              popMovieHeader);
        case 2:
          return HeaderDesign.fromColorResAndUrl(
              R.color.colorPrimaryDark,
              topMovieHeader);
      }
      //execute others actions if needed (ex : modify your header logo)
      return null;
    };
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }
}
