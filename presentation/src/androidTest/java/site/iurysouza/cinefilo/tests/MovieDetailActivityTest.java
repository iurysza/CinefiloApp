package site.iurysouza.cinefilo.tests;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.components.CineTestComponent;
import site.iurysouza.cinefilo.domain.watchmedialist.WatchMedia;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.presentation.medialist.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.moviedetail.MovieDetailActivity;
import site.iurysouza.cinefilo.testhelpers.CinefiloTestApplication;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static site.iurysouza.cinefilo.model.entities.pojo.Movie.valueOf;
import static site.iurysouza.cinefilo.presentation.moviedetail.MovieDetailActivity.WATCH_MEDIA_DATA;

/**
 * Created by Iury Souza on 13/02/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MovieDetailActivityTest {

  @Rule
  public ActivityTestRule<MovieDetailActivity> activityRule =
      new ActivityTestRule<>(MovieDetailActivity.class,
          true,
          false);
  @Inject OkHttpClient okHttpClient;

  private static final String OKHTTP = "okhttp";
  private Intent startIntent;
  private Movie movie = MovieFactory.createMovieWithId(15);
  private Movie detailWithId = MovieFactory.createMovieDetailWithId(15);

  @Before
  public void setUp() throws Exception {
    //creates launching intent
    WatchMedia watchMedia = valueOf(movie);
    WatchMediaValue watchMediaValue = WatchMediaValue.valueOf(watchMedia);
    startIntent = new Intent();
    Bundle bundle = new Bundle();
    bundle.putParcelable(WATCH_MEDIA_DATA, watchMediaValue);
    startIntent.putExtra(WATCH_MEDIA_DATA, bundle);

    CineTestComponent appComponent = (CineTestComponent) getTestApp().getAppComponent();
    appComponent.inject(this);
  }

  @Test
  public void shouldUpdateMovieData() throws Exception {
    activityRule.launchActivity(startIntent);

    IdlingResource idlingResource = OkHttp3IdlingResource.create(OKHTTP, okHttpClient);
    Espresso.registerIdlingResources(idlingResource);
    onView(withId(R.id.overview_page_tagline))
        .check(matches(withText(detailWithId.getTagline())));

    Espresso.unregisterIdlingResources(idlingResource);
  }

  private CinefiloTestApplication getTestApp() {
    return
        (CinefiloTestApplication) InstrumentationRegistry.getInstrumentation()
            .getTargetContext()
            .getApplicationContext();
  }
}
