package site.iurysouza.cinefilo.data.entities;

/**
 * Created by Iury Souza on 12/10/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Response404Entity {

  @SerializedName("status_message")
  @Expose
  private String statusMessage;
  @SerializedName("status_code")
  @Expose
  private Integer statusCode;

  /**
   * @return The statusMessage
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * @param statusMessage The status_message
   */
  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * @return The statusCode
   */
  public Integer getStatusCode() {
    return statusCode;
  }

  /**
   * @param statusCode The status_code
   */
  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }
}