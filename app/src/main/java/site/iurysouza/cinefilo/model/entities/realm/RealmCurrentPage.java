package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * Created by Iury Souza on 10/01/2017.
 */
@Data
public class RealmCurrentPage extends RealmObject {
  public static final String ID = "id";
  public static final String MEDIA_TYPE = "mediaType";
  public static final String LIST_TYPE = "listType";
  public static final int MEDIA_MOVIE = 0;
  public static final int MEDIA_SERIES = 100;
  public static final int NOW_PLAYING_LIST = 0;
  public static final int MOST_POPULAR_LIST = 1;
  public static final int TOP_RATED_LIST = 2;
  public static final int TYPE_SERIES = 1;
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private int id;
  private int currentPage;
  private int mediaType;
  private int listType;
}
