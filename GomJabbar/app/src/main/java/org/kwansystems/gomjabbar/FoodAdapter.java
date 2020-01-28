package org.kwansystems.gomjabbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public Button btnDelete;
        public TextView txtQuantity;
        public TextView txtItem;
        public TextView txtCalories;
        public FoodViewHolder(ConstraintLayout v) {
            super(v);
            btnDelete   = (Button  )v.getChildAt(0);
            txtQuantity = (TextView)v.getChildAt(1);
            txtItem     = (TextView)v.getChildAt(2);
            txtCalories = (TextView)v.getChildAt(3);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_instance, parent, false);
        FoodViewHolder vh = new FoodViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FoodViewHolder holder, int Lposition) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final int position=Lposition;
        holder.txtItem.setText(mDataset[position]);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btnDelete","onclick"+position);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}

