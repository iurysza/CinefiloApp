package site.iurysouza.cinefilo.presentation.medialist.filter;

/**
 * Created by Iury Souza on 24/01/2017.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;
import site.iurysouza.cinefilo.R;
import timber.log.Timber;

public class SortButton extends ImageButton {

  private final int MAX_STATES = 3;
  int state;
  Drawable srcSortOff;
  Drawable srcSortTop;
  Drawable srcSortAll;
  int sortState;
  Context context;

  public SortButton(Context context) {
    super(context);
    this.context = context;
  }

  public SortButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    this.context = context;

    TypedArray a = context.obtainStyledAttributes(attrs,
        R.styleable.SortButton);

    try {

      srcSortOff = a
          .getDrawable(R.styleable.SortButton_src_sort_off);
      srcSortTop = a
          .getDrawable(R.styleable.SortButton_src_sort_top);
      srcSortAll = a
          .getDrawable(R.styleable.SortButton_src_sort_bottom);
    } catch (Exception e) {

    } finally {
      a.recycle();
    }

    switch (sortState) {
      case 0:
        this.setBackground(srcSortOff);
        break;
      case 1:
        this.setBackground(srcSortTop);
        break;
      case 2:
        this.setBackground(srcSortAll);
        break;
      default:
        break;
    }
  }

  @Override
  public boolean performClick() {
    super.performClick();
    nextState();
    setStateBackground();
    return true;
  }

  private void nextState() {
    state++;

    if (state == MAX_STATES) {
      state = 0;
    }
  }

  private void setStateBackground() {

    switch (state) {
      case 0:
        this.setBackground(srcSortOff);
        showButtonText("Sort Off");
        break;
      case 1:
        this.setBackground(srcSortTop);
        showButtonText("Sort Top");
        break;
      case 2:
        this.setBackground(srcSortAll);
        showButtonText("Sort bottom");

        break;
      default:
        break;
    }
  }

  public void showButtonText(String text) {
    Timber.e("Clicked: %s", text);
  }

  public SORT getSortState() {

    switch (state) {
      case 0:
        return SORT.OFF;
      case 1:
        return SORT.TOP;
      case 2:
        return SORT.BOTTOM;
      default:
        return SORT.OFF;
    }
  }

  public SORT getClickedState() {
    int clickedState = state;
    clickedState++;

    if (clickedState == MAX_STATES) {
      clickedState = 0;
    }



    switch (clickedState) {
      case 0:
        return SORT.OFF;
      case 1:
        return SORT.TOP;
      case 2:
        return SORT.BOTTOM;
      default:
        return SORT.OFF;
    }
  }

  public void setSortState(SORT sortState) {

    switch (sortState) {
      case OFF:
        state = 0;

        break;
      case TOP:
        state = 1;
        break;
      case BOTTOM:
        state = 2;
        break;
      default:
        break;
    }

    setStateBackground();
  }

  public enum SORT {
    OFF, TOP, BOTTOM
  }
}