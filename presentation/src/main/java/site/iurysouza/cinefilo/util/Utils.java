package site.iurysouza.cinefilo.util;

import android.net.Uri;
import android.support.annotation.NonNull;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import rx.Subscription;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.SHORT;

/**
 * Created by Iury Souza on 06/01/2017.
 */

public class Utils {
  public static void safeRegisterEventBus(Object classToRegister) {
    if (!EventBus.getDefault().isRegistered(classToRegister)) {
      EventBus.getDefault().register(classToRegister);
    }
  }

  public static int getRandomNumberInRange(int range) {
    int min = 0;
    Random r = new Random();
    return r.nextInt(range - min + 1) + min;
  }

  public static Subscription safelyUnsubscribe(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
    return subscription;
  }

  @NonNull public static String parseDateText(Date releaseDate) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(releaseDate);
    String month = cal.getDisplayName(MONTH, SHORT, Locale.US);
    return month + ", " + (cal.get(Calendar.YEAR));
  }

  public static boolean isEmptyString(final String s) {
    // Null-safe, short-circuit evaluation.
    return s == null || s.trim().isEmpty();
  }

  public static Uri getWebIntentUri(String id) {
    return Uri.parse("http://www.youtube.com/watch?v=" + id);
  }

  public static Uri getappIntentUri(String id) {
    return Uri.parse("vnd.youtube:" + id);
  }
}