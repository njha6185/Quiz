package com.example.nitishkumar.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int ROTATION_DURATION = 3000;
    private TextView quizText;
    private RotateAnimation rotateAnimation;
    private EditText nameEditText;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        startButton.setOnClickListener(this);
    }

/**************************** Initialises All variables and views ************************/
    public void init()
    {
        quizText = (TextView)findViewById(R.id.sportQuiz);
        setAnimationToQuizName();
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        startButton = (Button)findViewById(R.id.startButtonView);
    }

/**************************** Set And start Animation to the Quiz Home page ***************/
    public void setAnimationToQuizName()
    {
        rotateAnimation = (RotateAnimation)AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setDuration(ROTATION_DURATION);
        rotateAnimation.setRepeatMode(Animation.REVERSE);
        quizText.setAnimation(rotateAnimation);
    }
    @Override
    protected void onDestroy() {
        rotateAnimation.cancel();
        super.onDestroy();
    }

/**************** implement Onclick and detect button click and start quiz ************/
    @Override
    public void onClick(View view) {
        String playerName = nameEditText.getText().toString();
        if ( playerName.matches("") || TextUtils.isEmpty(playerName))
        {
            nameEditText.setError("Please Enter Name To play QUIZ !!!");
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            intent.putExtra("name", playerName);
            startActivity(intent);
            finish();
        }
    }
}