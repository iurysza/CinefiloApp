package site.iurysouza.cinefilo.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import site.iurysouza.cinefilo.CineApplication;

public abstract class BaseFragment extends Fragment {
  protected CineApplication appInstance;
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getCineApplication();
    setupFragmentComponent();
  }

  protected abstract void setupFragmentComponent();

  protected CineApplication getCineApplication() {
    return appInstance = CineApplication.getAppInstance();
  }
}