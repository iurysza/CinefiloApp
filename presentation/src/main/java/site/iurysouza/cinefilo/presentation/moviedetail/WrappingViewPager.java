package site.iurysouza.cinefilo.presentation.moviedetail;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Iury Souza on 02/02/2017.
 */

public class WrappingViewPager extends ViewPager {
  /**
   * Constructor
   *
   * @param context the context
   */
  public WrappingViewPager(Context context) {
    super(context);
  }

  /**
   * Constructor
   *
   * @param context the context
   * @param attrs the attribute set
   */
  public WrappingViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    int height = 0;
    for (int i = 0; i < getChildCount(); i++) {
      View child = getChildAt(i);
      child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
      int h = child.getMeasuredHeight();
      if (h > height) height = h;
    }

    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
