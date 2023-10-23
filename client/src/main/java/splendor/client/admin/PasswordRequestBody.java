package splendor.client.admin;

import com.google.gson.Gson;

public class PasswordRequestBody {

  public String nextPassword;
  public String oldPassword;

  public PasswordRequestBody(String nextPassword, String oldPassword) {
    this.nextPassword = nextPassword;
    this.oldPassword = oldPassword;
  }

  public String asJson() {
    return new Gson().toJson(this);
  }

}
