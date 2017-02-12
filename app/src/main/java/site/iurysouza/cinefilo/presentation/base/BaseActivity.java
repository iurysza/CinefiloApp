package site.iurysouza.cinefilo.presentation.base;

import android.os.Bundle;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import site.iurysouza.cinefilo.CineApplication;

public abstract class BaseActivity extends RxAppCompatActivity  {

  protected CineApplication appInstance;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getCineApplication();
    setupActivityComponent(savedInstanceState);
  }

  protected abstract void setupActivityComponent(Bundle savedInstanceState);

  protected CineApplication getCineApplication() {
    return appInstance = CineApplication.getAppInstance();
  }
}


