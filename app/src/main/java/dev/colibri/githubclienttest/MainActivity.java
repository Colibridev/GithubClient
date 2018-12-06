package dev.colibri.githubclienttest;

import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import dev.colibri.githubclienttest.R;
import dev.colibri.githubclienttest.adapter.RepositoryAdapter;
import dev.colibri.githubclienttest.entity.Repository;
import dev.colibri.githubclienttest.network.HttpClient;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "dc.MainActivity";

    private HttpClient httpClient;
    private RepositoryAdapter repositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.repository_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repositoryAdapter = new RepositoryAdapter();
        recyclerView.setAdapter(repositoryAdapter);

        httpClient = new HttpClient();
        new GetRepositoriesAsyncTask().execute("android");
    }

    private class GetRepositoriesAsyncTask extends AsyncTask<String, Void, ArrayList<Repository>> {

        @Override
        protected ArrayList<Repository> doInBackground(String... queries) {

            try {
                return httpClient.getRepositories(queries[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Repository> result) {
            if(result != null) {
                repositoryAdapter.addItems(result);
            } else {
                Toast.makeText(MainActivity.this, R.string.error_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}