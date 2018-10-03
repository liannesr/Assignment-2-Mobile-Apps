package com.techexchange.mobileapps.assignment2;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.techexchange.mobileapps.assignment2.QuestionListFragment.OnQuestionClickedListener;
import com.techexchange.mobileapps.assignment2.SingleQuestionFragment.OnSubmitSingleQuestionClickedListener;
import android.view.View;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements OnQuestionClickedListener, OnSubmitSingleQuestionClickedListener {

    public Fragment questionFrag;
    public Fragment listFrag;
    public FragmentManager fm;
    public Question questionMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        listFrag = fm.findFragmentById(R.id.fragment_list);
        questionFrag = fm.findFragmentById(R.id.fragment_single_question);

        if(listFrag==null) {
            listFrag = new QuestionListFragment();
            questionFrag = new SingleQuestionFragment();
            fm.beginTransaction().add(R.id.fragment_list, listFrag).commit();
        }
    }

    @Override
    public void onQuestionClicked(View view, Question question) {
        questionMain = question;
        questionFrag = QuestionListFragment.createFragmentWith(question);
        fm.beginTransaction().replace(R.id.fragment_single_question,questionFrag).addToBackStack(null).commit();
    }

    @Override
    public void onSubmitSingleQuestionClicked(View view, String selectedAnswer){
        questionMain.setAnsweredByUser(selectedAnswer);
        if(questionMain.getAnsweredByUser()!=null){
            questionMain.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle_blue);
        }
        else{
            questionMain.getHolder().questionTextView.setBackgroundResource(R.drawable.my_rectangle);
        }
        fm.beginTransaction().remove(questionFrag).commit();
    }


}

