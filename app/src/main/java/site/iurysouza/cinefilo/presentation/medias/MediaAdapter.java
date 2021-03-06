package site.iurysouza.cinefilo.presentation.medias;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;

/**
 * Created by Iury Souza on 15/12/2016.
 */

class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

  private final Picasso picasso;
  private final OnAdapterClickListener movieClickListener;
  private List<WatchMediaValue> mediaValueList = Collections.emptyList();

  MediaAdapter(Picasso picasso, OnAdapterClickListener movieClickListener) {
    this.picasso = picasso;
    this.movieClickListener = movieClickListener;
    setHasStableIds(true);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    MediaItemView view = (MediaItemView) LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.list_item_movie, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {
    viewHolder.bindTo(mediaValueList.get(i));
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public int getItemCount() {
    return mediaValueList.size();
  }

  void addAllMedia(List<WatchMediaValue> mediaValues) {

    if (mediaValueList.isEmpty()) {
      mediaValueList = mediaValues;
      notifyDataSetChanged();
      return;
    }

    appendMedia(mediaValues);
  }

  private void appendMedia(List<WatchMediaValue> mediaList) {
    int positionStart = mediaValueList.size() - 1;
    mediaValueList.addAll(mediaList);
    notifyItemRangeInserted(positionStart, mediaList.size());
  //    Handler handler = new Handler(Looper.getMainLooper());
  //    handler.post(() -> notifyItemRangeInserted(positionStart, mediaList.size()));
  }

  private void swapItems(List<WatchMediaValue> mediaValues) {
    final MediaDiffCallBack diffCallback = new MediaDiffCallBack(mediaValueList, mediaValues);
    final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
    this.mediaValueList.clear();
    this.mediaValueList.addAll(mediaValues);
    diffResult.dispatchUpdatesTo(this);
  }

  WatchMediaValue getFeauturedMovie() {
    return getRandomMovieWithBackDrop(mediaValueList);
  }

  private WatchMediaValue getRandomMovieWithBackDrop(List<WatchMediaValue> movieList) {
    WatchMediaValue movie = mediaValueList.get((new Random()).nextInt(mediaValueList.size()));
    if (movie.backdropPath() == null) {
      return getRandomMovieWithBackDrop(movieList);
    } else {
      return movie;
    }
  }

  interface OnAdapterClickListener {
    void onRealmMovieClick(WatchMediaValue mediaValue);
  }

  final class ViewHolder extends RecyclerView.ViewHolder {
    final MediaItemView itemView;

    ViewHolder(MediaItemView itemView) {
      super(itemView);
      this.itemView = itemView;
      this.itemView.setOnClickListener(v -> {
        WatchMediaValue movie = mediaValueList.get(getAdapterPosition());
        movieClickListener.onRealmMovieClick(movie);
      });
    }

    void bindTo(WatchMediaValue movie) {
      itemView.bindTo(movie, picasso);
    }
  }
}