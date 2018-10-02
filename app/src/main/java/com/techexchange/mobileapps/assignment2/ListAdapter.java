package com.techexchange.mobileapps.assignment2;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import com.techexchange.mobileapps.assignment2.QuestionListFragment.OnQuestionClickedListener;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
        private ArrayList<Question> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder{
            // each data item is just a string in this case
            public View mTextView;
            public MyViewHolder(View v) {
                super(v);
                mTextView = v;

            }


        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ListAdapter(ArrayList<Question> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_question_list, parent, false);
           // TextView textView = v.findViewById(R.id.question_name);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            TextView textView = holder.mTextView.findViewById(R.id.question_name);
            textView.setText("Question #"+(position+1) +"\n"+ mDataset.get(position).getQuestion());
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

}
