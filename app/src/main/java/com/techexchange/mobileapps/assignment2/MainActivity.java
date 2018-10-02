package com.techexchange.mobileapps.assignment2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.techexchange.mobileapps.assignment2.QuestionListFragment.OnQuestionClickedListener;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements OnQuestionClickedListener {

    public static ArrayList<Question> questionsList;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

   // private QuestionListFragmentAdapter myAdapter;
   // private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionsList = getList();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_questions);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ListAdapter(questionsList);
        mRecyclerView.setAdapter(mAdapter);

       // FragmentManager fm = getSupportFragmentManager();
        //viewPager = findViewById(R.id.question_pager);
        //myAdapter = new QuestionListFragmentAdapter(getSupportFragmentManager());
        //myAdapter = new QuestionListFragmentAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(myAdapter);

    }

    @Override
    public void onQuestionClicked() {
        Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Question> getList(){
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("countrylist",
                        "raw", getPackageName()));
        InputStream ins2 = getResources().openRawResource(
                getResources().getIdentifier("countrylist",
                        "raw", getPackageName()));
        QuestionListFactory.generateCountriesList(ins);
        return QuestionListFactory.genratingList(ins2);
    }
//
//    private static final class QuestionListFragmentAdapter
//            extends FragmentStatePagerAdapter {
//
//        public QuestionListFragmentAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Question listQuestion = MainActivity.questionsList.get(position);
////            Question question = new Question(listQuestion.getQuestion()
////                    , listQuestion.getCorrectAnswer(), listQuestion.getWrongAnswer());
//            return QuestionListFragment.createFragmentWith(listQuestion);
//        }
//
//        @Override
//        public int getCount() {
//            return questionsList.size();
//        }
//
//
//    }











}

