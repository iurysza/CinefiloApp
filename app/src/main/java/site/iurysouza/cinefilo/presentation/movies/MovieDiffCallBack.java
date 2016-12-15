package site.iurysouza.cinefilo.presentation.movies;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 15/12/2016.
 */

public class MovieDiffCallBack extends DiffUtil.Callback{

  private final List<RealmMovie> oldList;
  private final List<RealmMovie> newList;

  public MovieDiffCallBack(List<RealmMovie> oldList, List<RealmMovie> newList) {
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
    return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    final RealmMovie oldItem = oldList.get(oldItemPosition);
    final RealmMovie newItem = newList.get(newItemPosition);

    return oldItem.getOriginalTitle().equals(newItem.getOriginalTitle());
  }

  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    // Implement method if you're going to use ItemAnimator
    return super.getChangePayload(oldItemPosition, newItemPosition);
  }
}