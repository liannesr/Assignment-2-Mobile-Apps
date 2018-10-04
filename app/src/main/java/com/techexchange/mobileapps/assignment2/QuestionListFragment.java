package com.techexchange.mobileapps.assignment2;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Button;

public class QuestionListFragment extends Fragment {

    private static final String ARG_QUESTION = "ARG_QUESTION";
    private static final String ARG_OPTIONS = "ARG_OPTIONS";
    private static final String ARG_SELECTED = "ARG_SELECTED";
    public TextView questionView;
    public View rootView;
    public OnQuestionClickedListener answerListener;
    public RecyclerView mRecyclerView;
    public QuestionAdapter qAdapter;
    public  ArrayList<Question> questionsList;
    public Button submitQuizButton, sendEmailButton;
    public int finalScore;
    public boolean isSubmitted;

    public QuestionListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_question_list, container, false);
        questionView = rootView.findViewById(R.id.question_name);
        mRecyclerView = rootView.findViewById(R.id.recycler_questions);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        isSubmitted = false;
        Bundle args = getArguments();
        if(args != null){
            questionView.setText(args.getString(ARG_QUESTION));
        }

        submitQuizButton = rootView.findViewById(R.id.submit_button);
        submitQuizButton.setOnClickListener(v -> submitQuizClicked(v));
        sendEmailButton = rootView.findViewById(R.id.send_email_button);
        sendEmailButton.setOnClickListener(v -> sendEmailClicked(v));
        sendEmailButton.setEnabled(false);

        return rootView;
    }

    public void submitQuizClicked(View view){
        isSubmitted = true;
        finalScore=0;
        for (Question question:questionsList) {
            QuestionHolder holder = question.getHolder();
            if(holder!=null){
                if (question.getAnsweredByUser() != null) {
                    if (question.getCorrectAnswer().equals(question.getAnsweredByUser())) {
                        finalScore++;
                        holder.questionTextView.setBackgroundResource(R.drawable.my_rectangle_green);

                    } else {
                        holder.questionTextView.setBackgroundResource(R.drawable.my_rectangle_red);
                    }
                    question.getHolder().questionTextView.setClickable(false);
                } else {
                    holder.questionTextView.findViewById(R.id.question_name).setBackgroundResource(R.drawable.my_rectangle_red);
                    holder.questionTextView.findViewById(R.id.question_name).setClickable(false);
                }
            }
        }
        sendEmailButton.setEnabled(true);
    }

    public void sendEmailClicked(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quiz Score Report");
        String emailText = "Summary " + finalScore + " out of "+questionsList.size()+"\n Here is the complete score report: \n\n";

        for (Question question:questionsList) {
           emailText = emailText + question.toString();
        }
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivity(intent);
        }
        else{
            Toast.makeText(getActivity(), "The activity could not be resolved", Toast.LENGTH_SHORT).show();
        }
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
        InputStream ins = getResources().openRawResource(getResources().getIdentifier("countrylist",
                        "raw", getActivity().getPackageName()));
        InputStream ins2 = getResources().openRawResource(getResources().getIdentifier("countrylist",
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

    public class QuestionHolder extends RecyclerView.ViewHolder {
        public TextView questionTextView;
        public Question questionIn;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.question_list_item,parent,false));
            questionTextView = itemView.findViewById(R.id.question_name);
            questionTextView.setOnClickListener(v-> answerListener.onQuestionClicked(v, questionIn));
        }

        public void bind(Question question, int position){
            questionIn = question;
            question.setHolder(this);
            String questionText = "Question #"+(position+1)+ "\n"+question.getQuestion();
            questionTextView.setText(questionText);
        }
    }

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private ArrayList<Question> mQuestions;
        private QuestionAdapter(ArrayList<Question> questions){
            mQuestions = questions;
        }
        @Override
        public QuestionHolder onCreateViewHolder( ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestionHolder(layoutInflater,parent);
        }
        @Override
        public void onBindViewHolder(QuestionHolder holder, int position){
            Question question = mQuestions.get(position);
            holder.bind(question,position);
            if(isSubmitted){
                if (question.getAnsweredByUser() != null) {
                    question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_green);
                } else {
                    question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_red);
                }
                question.getHolder().questionTextView.setClickable(false);
            }
            else {
                if (question.getAnsweredByUser() != null) {
                    question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_blue);
                } else {
                    question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle);
                }
            }
        }
        @Override
        public int getItemCount(){
            return mQuestions.size();
        }
    }
}