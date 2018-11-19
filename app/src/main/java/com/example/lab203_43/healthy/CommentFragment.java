package com.example.lab203_43.healthy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CommentFragment extends Fragment {

    int postId;
    String result;
    JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        postId = bundle.getInt("post id");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackButton();
        initRestAPI();
    }

    void initBackButton()
    {
        Button backButton = (Button) getView().findViewById(R.id.comment_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    void initRestAPI()
    {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    String url = "https://jsonplaceholder.typicode.com/posts/" + postId + "/comments";
                    Request request = new Request.Builder().url(url).build();

                    Response response = client.newCall(request).execute();
                    result = response.body().string();
                    jsonArray = new JSONArray(result);
                }
                catch (IOException e)
                {
                    Log.d("test", "catch IOException : " + e.getMessage());
                }
                catch (JSONException e)
                {
                    Log.d("test", "catch JSONException : " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                try
                {
                    final ArrayList<JSONObject> commentList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentList.add(obj);
                    }
                    ProgressBar progressBar = (ProgressBar) getView().findViewById(R.id.comment_progress_bar);
                    progressBar.setVisibility(View.GONE);
                    ListView commentListView = (ListView) getView().findViewById(R.id.comment_list_view);
                    CommentAdapter commentAdapter = new CommentAdapter(getContext(), R.layout.fragment_comment_item_list, commentList);
                    commentListView.setAdapter(commentAdapter);
                }
                catch (JSONException e)
                {
                    Log.d("test", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }
}
