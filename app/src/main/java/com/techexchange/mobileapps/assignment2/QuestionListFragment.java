package com.techexchange.mobileapps.assignment2;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends Fragment {

    private static final String ARG_QUESTION = "ARG_QUESTION";
    private TextView questionView;
    private OnQuestionClickedListener answerListener;

    public QuestionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_question_list, container, false);
        questionView = rootView.findViewById(R.id.question_name);
        Bundle args = getArguments();
        if(args != null){
            questionView.setText(args.getString(ARG_QUESTION));
        }


        return rootView;
    }

    static QuestionListFragment createFragmentWith(Question question){
        QuestionListFragment frag = new QuestionListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION,question.getQuestion());
        frag.setArguments(args);
        return frag;
    }

    interface OnQuestionClickedListener {
        void onQuestionClicked();
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

}
