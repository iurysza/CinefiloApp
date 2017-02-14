package site.iurysouza.cinefilo.tests;

import android.content.Intent;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.model.data.entity.WatchMedia;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity.WATCH_MEDIA_DATA;

/**
 * Created by Iury Souza on 13/02/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MediaDetailActivityTest {
  @Rule
  public ActivityTestRule<MediaDetailActivity> activityRule =
      new ActivityTestRule<>(MediaDetailActivity.class,
          true,
          false);


  @Test
  public void shouldShowFilterMenu() throws Exception {
    Movie validMovieStub = MovieFactory.createValidMovieStub();
    WatchMedia watchMedia = WatchMedia.valueOf(validMovieStub);
    WatchMediaValue watchMediaValue = WatchMediaValue.valueOf(watchMedia);
    Intent startIntent = new Intent();
    Bundle bundle = new Bundle();

    bundle.putParcelable(WATCH_MEDIA_DATA, watchMediaValue);
    startIntent.putExtra(WATCH_MEDIA_DATA, bundle);
    activityRule.launchActivity(startIntent);

    onView(withId(R.id.media_detail_title_text))
        .check(matches(withText(validMovieStub.getOriginalTitle())));

    //activityRule.launchActivity(new Intent());
    //onView(withId(R.id.filter_view_title)).check(matches(isDisplayed()));
  }

}
