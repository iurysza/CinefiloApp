package site.iurysouza.cinefilo.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Iury Souza on 06/01/2017.
 */

public class Utils {
  public static void safeRegisterEventBus(Object classToRegister) {
    if (!EventBus.getDefault().isRegistered(classToRegister)) {
      EventBus.getDefault().register(classToRegister);
    }
  }
}
