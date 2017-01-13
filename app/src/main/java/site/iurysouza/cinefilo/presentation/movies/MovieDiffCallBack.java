package site.iurysouza.cinefilo.presentation.movies;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import java.util.List;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 15/12/2016.
 */

public class MovieDiffCallBack extends DiffUtil.Callback{

  private final List<WatchMediaValue> oldList;
  private final List<WatchMediaValue> newList;

  public MovieDiffCallBack(List<WatchMediaValue> oldList, List<WatchMediaValue> newList) {
    this.oldList = oldList;
    this.newList = newList;
  }

  @Override
  public int getOldListSize() {
    return oldList.size();
  }

  @Override
  public int getNewListSize() {
    return newList.size();
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return oldList.get(oldItemPosition).id() == newList.get(newItemPosition).id();
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    final WatchMediaValue oldItem = oldList.get(oldItemPosition);
    final WatchMediaValue newItem = newList.get(newItemPosition);

    return oldItem.name().equals(newItem.name());
  }

  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    // Implement method if you're going to use ItemAnimator
    return super.getChangePayload(oldItemPosition, newItemPosition);
  }
}