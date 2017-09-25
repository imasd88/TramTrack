package au.com.realestate.hometime.Helper;

import android.os.AsyncTask;

import java.io.IOException;

import au.com.realestate.hometime.models.ApiResponse;
import au.com.realestate.hometime.models.Token;
import retrofit2.Call;

public class RequestToken extends AsyncTask<String, Integer, String> {

    private TramsApi api;

    public RequestToken(TramsApi api) {
        this.api = api;
    }

    @Override
    protected String doInBackground(String... params) {
        Call<ApiResponse<Token>> call = api.token();
        try {
            return call.execute().body().responseObject.get(0).value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
