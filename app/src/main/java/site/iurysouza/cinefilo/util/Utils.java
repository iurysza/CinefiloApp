package site.iurysouza.cinefilo.util;

import java.util.Random;
import org.greenrobot.eventbus.EventBus;
import rx.Subscription;

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

  public static Subscription resetSubscription(Subscription subscription) {
    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }
    return subscription;
  }
}
