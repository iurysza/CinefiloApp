package site.iurysouza.cinefilo.presentation.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import site.iurysouza.cinefilo.presentation.CineApplication;

public abstract class BaseActivity extends AppCompatActivity {

  protected CineApplication appInstance;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getCineApplication();
    setupActivityComponent();
  }

  protected abstract void setupActivityComponent();

  protected CineApplication getCineApplication() {
    return appInstance = CineApplication.getAppInstance();
  }
}


