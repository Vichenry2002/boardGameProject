package splendor.client.admin;

import com.google.gson.Gson;

public class ColourRequestBody {

  public String colour;

  public ColourRequestBody(String colour) {
    this.colour = colour;
  }

  public String asJson() {
    return new Gson().toJson(this);
  }

}
