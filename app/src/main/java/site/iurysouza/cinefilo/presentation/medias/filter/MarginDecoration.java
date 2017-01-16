package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 15/01/2017.
 */
public class MarginDecoration extends RecyclerView.ItemDecoration {
  private int margin;

  public MarginDecoration(Context context) {
    margin = context.getResources().getDimensionPixelSize(R.dimen.genre_item_margin);
  }

  @Override
  public void getItemOffsets(
      Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    outRect.set(margin, margin, margin, margin);
  }
}