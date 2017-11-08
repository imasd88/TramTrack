package au.com.realestate.hometime;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import au.com.realestate.hometime.helper.RequestToken;
import au.com.realestate.hometime.helper.RequestTrams;
import au.com.realestate.hometime.models.Tram;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity implements RequestTrams.TramsListener {
    @BindView(R.id.recyclerView)
    RecyclerView northRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<Tram> trams = new ArrayList<>();
    private String mToken;
    private TramAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.initialize();
    }

    public void initialize() {
        northRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        northRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TramAdapter(this, trams);
        northRecyclerView.setAdapter(adapter);
        this.updateTime();
    }

    @OnClick(R.id.refreshButton)
    public void refreshClick() {
        updateTime();
    }

    private void updateTime() {
        progressBar.setVisibility(View.VISIBLE);
        new RequestToken(this);
    }

    @OnClick(R.id.clearButton)
    public void clearClick() {
        trams.clear();
        northRecyclerView.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onTramsLoaded(List<Tram> mTrams, boolean northTrams) {
        progressBar.setVisibility(View.GONE);
        if (northTrams) {
            trams.clear();
            new RequestTrams(mToken, false, this);
        }
        trams.addAll(mTrams);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    @Override
    public void onTokenRefreshed(String token) {
        mToken = token;
        new RequestTrams(token, true, this);
    }
}
