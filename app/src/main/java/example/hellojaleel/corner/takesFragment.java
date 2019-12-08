package example.hellojaleel.corner;

import android.content.Context;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import example.hellojaleel.corner.adapters.takesAdapter;
import example.hellojaleel.corner.models.Take;

import static com.parse.Parse.getApplicationContext;


public class takesFragment extends Fragment {

    public static final String TAG = "TakeFragment";

    private RecyclerView rvTakes;
    protected takesAdapter adapter;
    protected List<Take> mTakes;
    private EndlessRecyclerViewScrollListener scrollListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvTakes = view.findViewById(R.id.rvTakes);

        setParameters();

        queryTakes();

        recyclerViewConfig(view);

    }

    public void loadNextDataFromApi(int offset) {
        queryTakes();

    }

    public void recyclerViewConfig(View view) {
        // Configure the RecyclerView
        RecyclerView rvTakes = (RecyclerView) view.findViewById(R.id.rvTakes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvTakes.setLayoutManager(gridLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvTakes.addOnScrollListener(scrollListener);
    }


    private void setParameters() {
        // create the data source
        mTakes = new ArrayList<>();
        // create the adapter
        adapter = new takesAdapter(getContext(), mTakes);
        // set the adapter on the recycler view
        rvTakes.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvTakes.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    protected void queryTakes() {
        ParseQuery<Take> takeQuery = new ParseQuery<Take>(Take.class);
        ArrayList Users = new ArrayList<>();
        Users.add(ParseUser.getCurrentUser());
        takeQuery.include(Take.KEY_USER);
        takeQuery.setLimit(20);
        ArrayList<ParseUser> currentUser = new ArrayList<>();
        currentUser.add(ParseUser.getCurrentUser());
        takeQuery.whereContainedIn("users", currentUser);
        takeQuery.addDescendingOrder(Take.KEY_CREATED_AT);
        if (mTakes.size()>0){
            takeQuery.whereLessThan(Take.KEY_CREATED_AT, mTakes.get(mTakes.size()-1).getCreatedAt());
        }
        takeQuery.findInBackground(new FindCallback<Take>() {
            @Override
            public void done(List<Take> objects, com.parse.ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mTakes.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
