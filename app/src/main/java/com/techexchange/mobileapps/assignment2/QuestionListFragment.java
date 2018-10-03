package com.techexchange.mobileapps.assignment2;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends Fragment {

    private static final String ARG_QUESTION = "ARG_QUESTION";
    private static final String ARG_OPTIONS = "ARG_OPTIONS";
    private static String ARG_SELECTED = "ARG_SELECTED";

    private TextView questionView;
    private OnQuestionClickedListener answerListener;
    private RecyclerView mRecyclerView;
    private QuestionAdapter qAdapter;
    public  ArrayList<Question> questionsList;

    public QuestionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_question_list, container, false);
        questionView = rootView.findViewById(R.id.question_name);
        mRecyclerView = rootView.findViewById(R.id.recycler_questions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();


        Bundle args = getArguments();
        if(args != null){
            questionView.setText(args.getString(ARG_QUESTION));
        }


        return rootView;
    }

   public static SingleQuestionFragment createFragmentWith(Question question){
        SingleQuestionFragment frag = new SingleQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION,question.getQuestion());
        args.putStringArray(ARG_OPTIONS,question.getOptions());
        args.putString(ARG_SELECTED, question.getAnsweredByUser());
        frag.setArguments(args);
        return frag;
    }

    public interface OnQuestionClickedListener {
        void onQuestionClicked(View view, Question question);
    }


    public ArrayList<Question> getList(){
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("countrylist",
                        "raw", getActivity().getPackageName()));
        InputStream ins2 = getResources().openRawResource(
                getResources().getIdentifier("countrylist",
                        "raw", getActivity().getPackageName()));
        QuestionListFactory.generateCountriesList(ins);
        return QuestionListFactory.genratingList(ins2);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            answerListener = (OnQuestionClickedListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(
                    "The Context did not implement OnQuestionClickedListener!");
        }
    }

    private void updateUI(){
        questionsList = getList();
        qAdapter = new QuestionAdapter(questionsList);
        mRecyclerView.setAdapter(qAdapter);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView questionTextView;
        private Question questionIn;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.question_list_item,parent,false));
            questionTextView = (TextView) itemView.findViewById(R.id.question_name);
            questionTextView.setOnClickListener(v-> answerListener.onQuestionClicked(v, questionIn));
        }

        public void bind(Question question, int position){
            questionIn = question;
            questionTextView.setText("Question #"+(position+1)+ "\n"+question.getQuestion().toString());
        }

        @Override
        public void onClick(View view){

            Toast.makeText(getActivity(),questionIn.getQuestion().toString()+" clicked!",Toast.LENGTH_SHORT).show();
        }
    }

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private ArrayList<Question> mQuestions;

        public QuestionAdapter(ArrayList<Question> questions){
            mQuestions = questions;
        }

        @Override
        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestionHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(QuestionHolder holder, int position){
            Question question = mQuestions.get(position);
            holder.bind(question,position);
        }

        @Override
        public int getItemCount(){
            return mQuestions.size();
        }
    }
}
