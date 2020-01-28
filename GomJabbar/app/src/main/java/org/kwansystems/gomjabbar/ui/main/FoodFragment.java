package org.kwansystems.gomjabbar.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.kwansystems.gomjabbar.FoodAdapter;
import org.kwansystems.gomjabbar.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class FoodFragment extends Fragment {
    //Controls in Food Instance fragment
    private RecyclerView recFoodInstances;

    //Stuff for handling recyclerview
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    //Sample data
    private String[] myDataset=new String[] {"Alfa","Bravo","Charlie","Delta",
            "Echo","Foxtrot","Golf","Hotel",
            "India","Juliet","Kilo","Lima",
            "Mike","November","Oscar","Papa",
            "Quebec","Romeo","Sierra","Tango",
            "Uniform","Victor","Whisky","X-ray",
            "Yankee","Zulu",
            "1","2","3","4","5","6","7","8","9","10"
    };

    public static FoodFragment newInstance(int index) {
        FoodFragment fragment = new FoodFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recFoodInstances = root.findViewById(R.id.recFoodInstances);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recFoodInstances.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recFoodInstances.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new FoodAdapter(myDataset);
        recFoodInstances.setAdapter(mAdapter);
        return root;
    }
}