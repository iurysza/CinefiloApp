package site.iurysouza.cinefilo.presentation.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class BaseFragment extends RxFragment {
  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupFragmentComponent();
  }

  protected abstract void setupFragmentComponent();
}