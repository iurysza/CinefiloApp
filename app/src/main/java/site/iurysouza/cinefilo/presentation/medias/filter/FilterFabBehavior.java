package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Iury Souza on 15/01/2017.
 */
public class FilterFabBehavior extends CoordinatorLayout.Behavior<FloatingActionButton>  {
  public FilterFabBehavior(Context context, AttributeSet attrs) {
    super();
  }

  @Override
  public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
      final View directTargetChild, final View target, final int nestedScrollAxes) {
    return true;
  }

  @Override
  public void onNestedScroll(final CoordinatorLayout coordinatorLayout,
      final FloatingActionButton child,
      final View target, final int dxConsumed, final int dyConsumed,
      final int dxUnconsumed, final int dyUnconsumed) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,dxUnconsumed, dyUnconsumed);
    if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
      child.hide();
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
      child.show();
    }
  }
}
