package SecretSpeak2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebUtils {

    private static final String FORM_ID = "1FAIpQLScSLK5iKF-N3MMG9iSur9PeQSIASFsAYLdlu3ZbFIhUlSNBYQ";

    private OkHttpClient client;

    public WebUtils() throws IOException {
        client = new OkHttpClient();
    }

    private String enc(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Response get(Request request) {
        try {
            return this.client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject get_body(Response response) {
        try {
            return new JSONObject(response.body().string());
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public float get_simmilarity(String text1, String text2) {
        Request request = new Request.Builder()
            .url("https://twinword-text-similarity-v1.p.rapidapi.com/similarity/?"
                + "text1=" + this.enc(text1)
                + "&text2=" + this.enc(text2))
            .get().addHeader("x-rapidapi-host", "twinword-text-similarity-v1.p.rapidapi.com")
            .addHeader("x-rapidapi-key", "04a4b6ce34msheac7d4925311190p1e673djsn63e92e05b2e3").build();
        Response response = this.get(request);
        JSONObject body = this.get_body(response);
        return body.getFloat("similarity");
    }

    public void push_to_doc(String text) {
        Request request = new Request.Builder()
            .url("https://docs.google.com/forms/d/e/" + FORM_ID + "/formResponse?"
                + "usp=pp_url&entry.1115060045=" + this.enc(text) + "&submit=Submit")
            .get().build();
        this.get(request);
    }
}