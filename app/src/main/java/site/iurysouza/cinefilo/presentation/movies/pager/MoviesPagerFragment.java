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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;
import site.iurysouza.cinefilo.presentation.CineApplication;
import site.iurysouza.cinefilo.presentation.base.BaseFragment;
import site.iurysouza.cinefilo.presentation.movies.BackDropChangedEvent;
import site.iurysouza.cinefilo.util.ImageUtils;
import site.iurysouza.cinefilo.util.Utils;

/**
 * Created by Iury Souza on 09/11/2016.
 */

public class MoviesPagerFragment extends BaseFragment implements MoviesPagerView {

  private static final int FRAGS_IN_MEMORY = 3;
  public static final String MEDIA_TYPE = "MEDIA_TYPE";

  @BindView(R.id.viewpager_fragment_movies) MaterialViewPager materialViewPager;
  @BindView(R.id.logo_white) TextView headerText;

  public static MoviesPagerFragment newInstance(int mediaType) {
    MoviesPagerFragment moviesPagerFragment = new MoviesPagerFragment();
    Bundle args = new Bundle();
    args.putInt(MEDIA_TYPE, mediaType);
    moviesPagerFragment.setArguments(args);
    return moviesPagerFragment;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.content_movies_fragment, container, false);
    ButterKnife.bind(this, view);
    Utils.safeRegisterEventBus(this);

    int mediaType = getArguments().getInt(MEDIA_TYPE);

    MoviesPagerAdapter adapter = new MoviesPagerAdapter(getChildFragmentManager(), getContext(), mediaType);

    ViewPager viewPager = materialViewPager.getViewPager();
    viewPager.setAdapter(adapter);
    viewPager.setOffscreenPageLimit(FRAGS_IN_MEMORY);
    materialViewPager.getPagerTitleStrip().setViewPager(viewPager);
    handleToolbar(materialViewPager.getToolbar());
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

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBackDropChanged(BackDropChangedEvent event) {
    WatchMediaValue featuredMovie = event.featuredMovie;
    String backDropUrl = ImageUtils.getBackDropUrl(featuredMovie.backdropPath());
    materialViewPager.setImageUrl(backDropUrl, 200);
    headerText.setText(featuredMovie.name());
  }

  @Override protected void setupFragmentComponent() {
    ((CineApplication) getContext().getApplicationContext()).getRepositoryComponent().inject(this);
  }

  @OnClick(R.id.logo_white) public void onClick() {
    Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
  }
}
