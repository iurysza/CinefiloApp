package site.iurysouza.cinefilo.testhelpers;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by Iury Souza on 13/02/2017.
 */

public class CineTestRunner extends AndroidJUnitRunner {
  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, CinefiloTestApplication.class.getName(), context);
  }
}