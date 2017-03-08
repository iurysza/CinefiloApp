package site.iurysouza.cinefilo.presentation.medialist;

/**
 * Created by Iury Souza on 05/01/2017.
 */
public class ItemReselectedEvent {
  public final int itemIndex;
  public ItemReselectedEvent(int itemIndex) {
    this.itemIndex = itemIndex;
  }
}
