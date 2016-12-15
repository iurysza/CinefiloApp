package site.iurysouza.cinefilo.presentation.movies;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import io.realm.RealmList;
import java.util.Collections;
import java.util.List;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.entities.realm.RealmMovie;

/**
 * Created by Iury Souza on 15/12/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

  private final Picasso picasso;
  private final RealmMovieClickListener movieClickListener;
  private List<RealmMovie> realmMovieList = Collections.emptyList();

  public MovieAdapter(Picasso picasso, RealmMovieClickListener movieClickListener) {
    this.picasso = picasso;
    this.movieClickListener = movieClickListener;
    setHasStableIds(true);
  }


  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    MovieItemView view = (MovieItemView) LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.list_item_movie, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {
    viewHolder.bindTo(realmMovieList.get(i));
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public int getItemCount() {
    return realmMovieList.size();
  }


  public void addMovies(RealmList<RealmMovie> movieList) {
    realmMovieList = movieList;
    notifyDataSetChanged();
  }

  public void onMovieListChanged(List<RealmMovie> newMovieList) {
    DiffUtil.DiffResult diffResult =
        DiffUtil.calculateDiff(new MovieDiffCallBack(realmMovieList, newMovieList));
    diffResult.dispatchUpdatesTo(this);
  }

  public interface RealmMovieClickListener {
    void onRealmMovieClick(RealmMovie movie);
  }


  public final class ViewHolder extends RecyclerView.ViewHolder {
    public final MovieItemView itemView;

    public ViewHolder(MovieItemView itemView) {
      super(itemView);
      this.itemView = itemView;
      this.itemView.setOnClickListener(v -> {
        RealmMovie movie = realmMovieList.get(getAdapterPosition());
        movieClickListener.onRealmMovieClick(movie);
      });
    }

    public void bindTo(RealmMovie movie) {
      itemView.bindTo(movie, picasso);
    }
  }
}