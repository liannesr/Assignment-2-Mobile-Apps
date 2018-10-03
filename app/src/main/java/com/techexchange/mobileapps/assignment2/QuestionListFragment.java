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
import com.techexchange.mobileapps.assignment2.MainActivity;

public class QuestionListFragment extends Fragment {

    private static final String ARG_QUESTION = "ARG_QUESTION";
    private static final String ARG_OPTIONS = "ARG_OPTIONS";
    private static String ARG_SELECTED = "ARG_SELECTED";

    private TextView questionView;
    private View rootView;
    private OnQuestionClickedListener answerListener;
    private RecyclerView mRecyclerView;
    private QuestionAdapter qAdapter;
    public  ArrayList<Question> questionsList;
    private Button submitQuizButton, sendEmailButton;
    private int finalScore;

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
        Bundle args = getArguments();
        if(args != null){
            questionView.setText(args.getString(ARG_QUESTION));
           // if(args.get(ARG_SELECTED)!=null){questionView.setBackgroundResource(R.drawable.my_rectangle_blue);}
        }

        submitQuizButton = rootView.findViewById(R.id.submit_button);
        submitQuizButton.setOnClickListener(v -> submitQuizCliked(v));

        sendEmailButton = rootView.findViewById(R.id.send_email_button);
        sendEmailButton.setOnClickListener(v -> sendEmailClicked(v));
        sendEmailButton.setEnabled(false);

        return rootView;
    }

    public void submitQuizCliked(View view){
        for (Question question:questionsList) {
            if(question.getCorrectAnswer().equals(question.getAnsweredByUser())){
                finalScore++;
                question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_green);
            }
            else{
                question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_red);
            }
        }
        Toast.makeText(getActivity(),"Final Score! "+ finalScore,Toast.LENGTH_SHORT).show();
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

    public class QuestionHolder extends RecyclerView.ViewHolder {
        public TextView questionTextView;
        public Question questionIn;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.question_list_item,parent,false));
            questionTextView = (TextView) itemView.findViewById(R.id.question_name);
            questionTextView.setOnClickListener(v-> answerListener.onQuestionClicked(v, questionIn));
        }

        public void bind(Question question, int position){
            questionIn = question;
            question.setHolder(this);
            questionTextView.setText("Question #"+(position+1)+ "\n"+question.getQuestion().toString());
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
            if(question.getAnsweredByUser()!=null){
                question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_blue);
            }
            else{
                question.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle);
            }
        }
        @Override
        public int getItemCount(){
            return mQuestions.size();
        }
    }
}
