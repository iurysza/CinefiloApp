package site.iurysouza.cinefilo.testhelpers;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.test.runner.AndroidJUnitRunner;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

/**
 * Created by Iury Souza on 13/02/2017.
 */

public class CineTestRunner extends AndroidJUnitRunner {
  @Override
  public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return super.newApplication(cl, CinefiloTestApplication.class.getName(), context);
  }

  @Override public void onStart() {
    super.onStart();
    RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR));
  }

  @Override public void onDestroy() {
    super.onDestroy();
    RxJavaHooks.reset();
  }
}