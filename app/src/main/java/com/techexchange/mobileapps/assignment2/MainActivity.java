package com.techexchange.mobileapps.assignment2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.techexchange.mobileapps.assignment2.QuestionListFragment.OnQuestionClickedListener;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements OnQuestionClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.fragment);

        if(frag==null){
            frag = new QuestionListFragment();
            fm.beginTransaction().add(R.id.fragment,frag).commit();
        }
        //viewPager = findViewById(R.id.question_pager);
        //myAdapter = new QuestionListFragmentAdapter(getSupportFragmentManager());
        //myAdapter = new QuestionListFragmentAdapter(getSupportFragmentManager());
        //viewPager.setAdapter(myAdapter);

    }

    @Override
    public void onQuestionClicked() {
        Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
    }
}

