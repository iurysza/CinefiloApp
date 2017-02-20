package site.iurysouza.cinefilo.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle.components.support.RxFragment;
import site.iurysouza.cinefilo.CineApplication;

public abstract class BaseFragment extends RxFragment {
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