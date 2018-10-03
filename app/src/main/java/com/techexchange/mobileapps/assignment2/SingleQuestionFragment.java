package com.techexchange.mobileapps.assignment2;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Button;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

public class SingleQuestionFragment extends Fragment {

    private OnSubmitSingleQuestionClickedListener answerListener;
    TextView questionText;
    RadioButton optionOne, optionTwo, optionThree, optionFour;
    RadioButton[] radioArray = new RadioButton[4];
    Button submit;
    String question,previousSelection;
    String[] options;
    Bundle bundle;

    private static final String ARG_QUESTION = "ARG_QUESTION";
    private static final String ARG_OPTIONS = "ARG_OPTIONS";
    private String ARG_SELECTED = "ARG_SELECTED";
    private String selection;
    public SingleQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_single_question, container, false);
        questionText = rootView.findViewById(R.id.question_text);

        optionOne = rootView.findViewById(R.id.radio_country_one);
        optionOne.setOnClickListener(v -> onClickRadioButton(v));
        radioArray[0] = optionOne;

        optionTwo = rootView.findViewById(R.id.radio_country_two);
        optionTwo.setOnClickListener(v -> onClickRadioButton(v));
        radioArray[1] = optionTwo;

        optionThree = rootView.findViewById(R.id.radio_country_three);
        optionThree.setOnClickListener(v -> onClickRadioButton(v));
        radioArray[2] = optionThree;

        optionFour = rootView.findViewById(R.id.radio_country_four);
        optionFour.setOnClickListener(v -> onClickRadioButton(v));
        radioArray[3] = optionFour;

        submit = rootView.findViewById(R.id.submit_question_button);
        submit.setOnClickListener(v -> answerListener.onSubmitSingleQuestionClicked(v,selection));

        bundle = this.getArguments();
        if(bundle!=null){
            question = bundle.getString(ARG_QUESTION);
            options = bundle.getStringArray(ARG_OPTIONS);
            previousSelection = bundle.getString(ARG_SELECTED);
        }
        updateUI();
        return rootView;
    }
    public void updateUI(){
        questionText.setText(question);
        List<String> shuffled = Arrays.asList(options);
        Collections.shuffle(shuffled);
        for(int i=0;i<shuffled.size();i++){
            radioArray[i].setText(shuffled.get(i));
        }
        if(previousSelection!=null){
            selection=previousSelection;
            for(int i=0;i<radioArray.length;i++){
                if(radioArray[i].getText().toString().equals(previousSelection)){radioArray[i].setChecked(true);}
            }
        }
    }
    public interface OnSubmitSingleQuestionClickedListener {
        void onSubmitSingleQuestionClicked(View view, String selectedAnswer);
    }
    public void onClickRadioButton(View view){
        selection = ((RadioButton)view).getText().toString();
        bundle.putString(ARG_SELECTED, selection);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            answerListener = (OnSubmitSingleQuestionClickedListener) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(
                    "The Context did not implement OnQuestionClickedListener!");
        }
    }
}