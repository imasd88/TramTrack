package au.com.realestate.hometime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import au.com.realestate.hometime.Helper.RequestToken;
import au.com.realestate.hometime.Helper.RequestTrams;
import au.com.realestate.hometime.Helper.TramsApi;
import au.com.realestate.hometime.models.Tram;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Tram> southTrams;
    private List<Tram> northTrams;

    private RecyclerView northRecyclerView, southRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        northRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewNorth);
        southRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewSouth);


        RecyclerView.LayoutManager layoutManagerNorth = new LinearLayoutManager(getApplicationContext());
        northRecyclerView.setLayoutManager(layoutManagerNorth);
        northRecyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager layoutManagerSouth = new LinearLayoutManager(getApplicationContext());
        southRecyclerView.setLayoutManager(layoutManagerSouth);
        southRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.updateTime();
    }

    public void refreshClick(View view) {
        if (!updateTime())
            Toast.makeText(getApplicationContext(), "Tram's time is not currently available!\n" +
                    "Please check later.", Toast.LENGTH_SHORT).show();
    }

    private boolean updateTime() {
        TramsApi tramsApi = createApiClient();
        try {
            String token = new RequestToken(tramsApi).execute("").get();
            this.northTrams = new RequestTrams(tramsApi, token).execute("4055").get();
            this.southTrams = new RequestTrams(tramsApi, token).execute("4155").get();
            showTrams();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void clearClick(View view) {
        northTrams = new ArrayList<>();
        southTrams = new ArrayList<>();
        showTrams();
    }

    private void showTrams() {
        TramAdapter adapter = new TramAdapter(northTrams);
        northRecyclerView.setAdapter(adapter);

        adapter = new TramAdapter(southTrams);
        southRecyclerView.setAdapter(adapter);
    }


    ////////////
    // API
    ////////////

    private TramsApi createApiClient() {

        String BASE_URL = "http://ws3.tramtracker.com.au";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        return retrofit.create(TramsApi.class);
    }
}
