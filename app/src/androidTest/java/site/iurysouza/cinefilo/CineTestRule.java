package site.iurysouza.cinefilo;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

/**
 * Created by Iury Souza on 12/02/2017.
 */

public class CineTestRule<T extends Activity> extends ActivityTestRule<T> {
  public CineTestRule(Class<T> activityClass) {
    super(activityClass);
  }

  @Override public T getActivity() {
    final T activity = super.getActivity();
    activity.overridePendingTransition(0, 0);
    return activity;
  }
}
