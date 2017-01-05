package site.iurysouza.cinefilo.presentation.movies.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.florent37.materialviewpager.MaterialViewPager;
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

public class MoviesPagerFragment extends BaseFragment implements MoviesPagerView {

  private static final int FRAGS_IN_MEMORY = 3;

  @BindView(R.id.viewpager_fragment_movies) MaterialViewPager materialViewPager;
  @BindView(R.id.logo_white) TextView headerText;

  @Inject MoviesPagerPresenter moviesPresenter;

  public static MoviesPagerFragment newInstance() {
    MoviesPagerFragment moviesPagerFragment = new MoviesPagerFragment();
    Bundle args = new Bundle();
    moviesPagerFragment.setArguments(args);
    return moviesPagerFragment;
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
      ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
      ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
      actionBar.setDisplayHomeAsUpEnabled(false);
      actionBar.setDisplayShowTitleEnabled(true);
      actionBar.setDefaultDisplayHomeAsUpEnabled(false);
    }
  }

  @Override public void createHeaderChangeListener(List<RealmMovie> showCaseMovies) {
    if (showCaseMovies != null) {
      RealmMovie movie = showCaseMovies.get(1);
      String backDropUrl = ImageUtils.getBackDropUrl(movie.getBackdropPath());
      materialViewPager.setImageUrl(backDropUrl, 100);
      headerText.setText(movie.getOriginalTitle());
    }
  }

  //@NonNull
  //private MaterialViewPager.Listener createHeaderListener(List<RealmMovie> showCaseMovies) {
  //  return new MaterialViewPager.Listener() {
  //    @Override public HeaderDesign getHeaderDesign(int page) {
  //      int randomNumber = new Random().nextInt(showCaseMovies.size());
  //      Pair<String, String> popMovieHeader =
  //          new Pair<>(ImageUtils.getBackDropUrl(showCaseMovies.get(randomNumber).getBackdropPath()),
  //              showCaseMovies.get(randomNumber).getOriginalTitle());
  //
  //      switch (page) {
  //        case 0:
  //          headerText.setText(popMovieHeader.second);
  //          return HeaderDesign.fromColorResAndUrl(
  //              R.color.colorPrimaryDark,
  //              popMovieHeader.first);
  //      }
  //
  //      return null;
  //    }
  //  };
  //}

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }

  @OnClick(R.id.logo_white) public void onClick() {
    moviesPresenter.loadShowCaseMovies();
    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
  }

  public void refreshHeader() {
    moviesPresenter.loadShowCaseMovies();
  }
}
