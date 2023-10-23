package splendor.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.HttpURLConnection;


/**
 * Class where Client sends requests to server.
 */
public class ClientRequest {
    private static HttpURLConnection connection;

    /**
     * This method sends a POST request of an ActionID to the server.
     *
     * @param actionId the ID of the action to be sent to the server
     */
    public static void makeActionRequest(long actionId) throws UnirestException {

        System.out.println("Sending action request for: " + actionId);

        // Removed some parameters to instead get them from GlobalUserInfo class
        String urlStr = GlobalUserInfo.getServerAddress() + GlobalUserInfo.getGameService()
            + "/api/games/" + GlobalUserInfo.getSessionID()
            + "/players/" + GlobalUserInfo.getUsername()
            + "/actions/" + actionId;

        String accessToken = Login.getUserAccessToken(
            GlobalUserInfo.getUsername(),
            GlobalUserInfo.getPassword());

        try {

            HttpResponse<String> response = Unirest.post(urlStr)
                //.header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .queryString("access_token" , accessToken)
                .body("").asString();
        } catch (UnirestException e) {
            System.out.println("ERROR WHEN REQUESTING ACTION");
        }
    }

    /**
     * This method sends a POST request of an ActionID to the server.
     */
    public static void makeSaveRequest() throws UnirestException {

        System.out.println("Sending save request for");

        String urlStr = GlobalUserInfo.getServerAddress() + GlobalUserInfo.getGameService()
            + "/api/games/" + GlobalUserInfo.getSessionID()
            + "/savegames";

        String accessToken = Login.getUserAccessToken(
            GlobalUserInfo.getUsername(),
            GlobalUserInfo.getPassword());

        try {

            HttpResponse<String> response = Unirest.post(urlStr)
                //.header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .queryString("access_token" , accessToken)
                .body("").asString();
        } catch (UnirestException e) {
            System.out.println("ERROR WHEN REQUESTING ACTION");
        }
    }
}