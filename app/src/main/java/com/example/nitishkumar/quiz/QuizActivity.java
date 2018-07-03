package com.example.nitishkumar.quiz;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private String playerName;
    private TextView playerText;
    private TextView question;
    private TextView questionNo;
    private ProgressBar mProgressBar;
    private int index;
    private TextView questionTypeHintText;
    private Button submitButton;
    private LinearLayout checkBoxLayout;
    private CheckBox checkboxOption1;
    private CheckBox checkboxOption2;
    private CheckBox checkboxOption3;
    private CheckBox checkboxOption4;
    private LinearLayout radioGroupLayout;
    private RadioButton radioboxOption1;
    private RadioButton radioboxOption2;
    private RadioButton radioboxOption3;
    private RadioButton radioboxOption4;
    private LinearLayout editTextLayout;
    private EditText editTextAnswer;
    private ImageView resultDialogueImage;
    private RadioGroup radioGroup;
    private boolean noOptionChecked;
    private int totalScoreAchieved;
    private TextView scoreUpdateText;

    private String[] questionBox = {
            "Q.1. The Only Indian Cricketer To feature in top 10 of the ICC player ranking for ODIs on May 2017?",
            "Q.2. The \"Shri Shiv Chatrapati Chatrapati Sports Complex\" also known as Balewadi Stadium, is situated in : ?",
            "Q.3. Who Created a new World Record of Fastest Century in test cricket in feb 2016?",
            "Q.4. In which all year India won cricket World Cup in any format?",
            "Q.5. Who all are Tennis Player?",
            "Q.6. Which of the terms belongs to Cricket?",
            "Q.7. Which Was the 1st Non Test Playing Country To beat India in an International Match?",
            "Q.8. What is NickName of Glenn McGrath?",
            "Q.9. India won its first Olympic hockey gold in which year?"
    };

    private String[] hintBox = {
            "Virat Kohli is lone cricketer from country to hold 3rd Spot in BatsMan Chart after de Villiers and Warner.",
            "Balewadi Stadium is a football stadium in Pune.",
            "McCullum  takes only 54 ball to hit century in his last ever Test VS Australia in 2nd Test at Halgey Oval",
            "1983 - Prudential World Cup(60Over), 2007 - T20 World Cup, 2011 - ODI 50ov world Cup",
            "Roger Federe is from SwitzerLand, Rafael Nadal is from Spain",
            "Third Umpire reffered By ground Umpire and Bye if run is taken when ball hits body",
            "SriLanka beat India by 47 runs in 1979 world cup, SriLanka began Playing test in 1982",
            "Named Pegion Because of His thin legs as youngsters",
            "In 1928 India won first Gold in Hockey in Olympics and remain Unbeaten Untill 1956"
    };

    private String[][] answerBox = {
            {"Virat Kohli", "Amit Mishra", "Shikhar Dhawan", "Rohit Sharma"},
            {"Nashik", "Mumbai", "Aurangabd", "Pune"},
            {"Steve Smith", "Chris Gayle", "Brendon McCullum", "Virat Kohli"},
            {"2007", "2003", "1983", "2011"},
            {"Roger Federer", "Sachin Tendulkar", "Rafael Nadal", "Lionel Messi"},
            {"Shuttle cock", "Third Umpire", "Bye", "Badminton"},
            {""},
            {""},
            {""},
    };

    /******************************* here for multiple right Answer no. of right answer is passed in array ********/
    private String[] correctAnswerBox = {
            "Virat Kohli", "Pune", "Brendon McCullum", "3", "2", "2", "Sri Lanka", "Pigeon", "1928"
    };

    /******************* S - Single right answer, M - Multiple right Answer, D - Descriptive Answer **********/
    private String[] questionType = {"S", "S", "S", "M", "M", "M", "D", "D", "D"};

    /************************** in contains the right option no for multiplr right Answer uestion ***********
     *********************** For simplycity and genealised way singles or discriptive right answer are left blank ********/
    private int[][] multipleRightAnswerBox = {{}, {}, {}, {1, 3, 4}, {1, 3}, {2, 3}, {}, {}, {}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getPlayerName();
        init();
        quizInstructionAlert();
        setQuestioAndOption();
        submitButton.setOnClickListener(this);
    }

    /***************** Recieve Player Name and Set it to required textView *****************/
    public void getPlayerName() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            playerName = extra.get("name").toString();
            Toast.makeText(this, "Hi, " + playerName + " Play QuiZ. ALL THE BEST !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something Went Wrong Player Name Has Not Set !!!", Toast.LENGTH_SHORT).show();
        }
    }

    /********************************* Initialise A variables and Views *********************/
    public void init() {
        playerText = (TextView) findViewById(R.id.playerNameText);
        setPlayerNameToTextView();
        question = (TextView) findViewById(R.id.questionTextView);
        questionNo = (TextView) findViewById(R.id.questionNoText);
        mProgressBar = (ProgressBar) findViewById(R.id.questionProgressBar);
        mProgressBar.setProgress(0);
        submitButton = (Button) findViewById(R.id.submitButtonView);
        checkBoxLayout = (LinearLayout) findViewById(R.id.checkboxOptionLayout);
        checkboxOption1 = (CheckBox) findViewById(R.id.option1Checkbox);
        checkboxOption2 = (CheckBox) findViewById(R.id.option2Checkbox);
        checkboxOption3 = (CheckBox) findViewById(R.id.option3Checkbox);
        checkboxOption4 = (CheckBox) findViewById(R.id.option4Checkbox);
        radioGroupLayout = (LinearLayout) findViewById(R.id.radioOptionLayout);
        radioboxOption1 = (RadioButton) findViewById(R.id.option1RadioButton);
        radioboxOption2 = (RadioButton) findViewById(R.id.option2RadioButton);
        radioboxOption3 = (RadioButton) findViewById(R.id.option3RadioButton);
        radioboxOption4 = (RadioButton) findViewById(R.id.option4RadioButton);
        editTextLayout = (LinearLayout) findViewById(R.id.ediTextOptionLayout);
        editTextAnswer = (EditText) findViewById(R.id.optionEditText);
        questionTypeHintText = (TextView) findViewById(R.id.questionTypeHint);
        resultDialogueImage = new ImageView(this);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupView);
        noOptionChecked = false;
        totalScoreAchieved = 0; // its for settion 0 if again started quiz
        scoreUpdateText = (TextView) findViewById(R.id.scoreText);
    }

    /********************************** set playerName to TextView ************************/
    public void setPlayerNameToTextView() {
        playerText.setText(playerName);
    }

    /***************************** Set Question And Options To the Required View *************/
    public void setQuestioAndOption() {
        int temp = index;
        question.setText(questionBox[index]);
        mProgressBar.setProgress(index + 1);
        questionNo.setText("Question No. :" + ++temp + "\\" + questionBox.length);
        if (questionType[index].equalsIgnoreCase("M")) {
            setCheckboxOption();
            checkboxOption1.setChecked(false);
            checkboxOption2.setChecked(false);
            checkboxOption3.setChecked(false);
            checkboxOption4.setChecked(false);
        } else if (questionType[index].equalsIgnoreCase("S")) {
            setRadioboxOption();
            radioGroup.clearCheck();
        } else {
            setEditTextOption();
            editTextAnswer.setText("");
        }
        Toast.makeText(this, "Here You Go, Here is Question " + temp, Toast.LENGTH_SHORT).show();
    }

    /********************************** Set CheckBox AnswerOption **********************/
    public void setCheckboxOption() {
        questionTypeHintText.setText("Multiple Right Answer Question");
        radioGroupLayout.setVisibility(View.GONE);
        editTextLayout.setVisibility(View.GONE);
        checkBoxLayout.setVisibility(View.VISIBLE);
        checkboxOption1.setText(answerBox[index][0]);
        checkboxOption2.setText(answerBox[index][1]);
        checkboxOption3.setText(answerBox[index][2]);
        checkboxOption4.setText(answerBox[index][3]);
    }

    /********************************** Set CheckBox AnswerOption **********************/
    public void setRadioboxOption() {
        questionTypeHintText.setText("Single Right Answer Question");
        checkBoxLayout.setVisibility(View.GONE);
        editTextLayout.setVisibility(View.GONE);
        radioGroupLayout.setVisibility(View.VISIBLE);
        radioboxOption1.setText(answerBox[index][0]);
        radioboxOption2.setText(answerBox[index][1]);
        radioboxOption3.setText(answerBox[index][2]);
        radioboxOption4.setText(answerBox[index][3]);
    }

    /********************************** Set CheckBox AnswerOption **********************/
    public void setEditTextOption() {
        questionTypeHintText.setText("Descriptive Answer Question");
        checkBoxLayout.setVisibility(View.GONE);
        radioGroupLayout.setVisibility(View.GONE);
        editTextLayout.setVisibility(View.VISIBLE);
    }

    /*********************************** check Submit button click ********************/
    @Override
    public void onClick(View view) {
        boolean finalResult = false;
        if (questionType[index].equalsIgnoreCase("M")) {
            finalResult = chechboxQuestionAnswerCheck();
        } else if (questionType[index].equalsIgnoreCase("S")) {
            finalResult = radioboxQuestionAnswerCheck();
        } else if (questionType[index].equalsIgnoreCase("D")) {
            finalResult = descriptiveQuestionAnswerCheck();
        }
        if (noOptionChecked) {
            noOptionChecked = false;
            return;
        }
        eachQuestionResultDialogue(finalResult);
        index++;
    }

    /************************** it checks for checkBox Answers and return true for correct and false for wrong **********/
    public boolean radioboxQuestionAnswerCheck() {
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please Select Any Answer!!! All Questions Are mandatory!!!",
                    Toast.LENGTH_SHORT).show();
            noOptionChecked = true;
        } else {
            noOptionChecked = false;
        }
        boolean result = false;
        if (radioboxOption1.isChecked()) {
            if (radioboxOption1.getText().toString().equalsIgnoreCase(correctAnswerBox[index])) {
                result = true;
            }
        } else if (radioboxOption2.isChecked()) {
            if (radioboxOption2.getText().toString().equalsIgnoreCase(correctAnswerBox[index])) {
                result = true;
            }
        } else if (radioboxOption3.isChecked()) {
            if (radioboxOption3.getText().toString().equalsIgnoreCase(correctAnswerBox[index])) {
                result = true;
            }
        } else if (radioboxOption4.isChecked()) {
            if (radioboxOption4.getText().toString().equalsIgnoreCase(correctAnswerBox[index])) {
                result = true;
            }
        } else {
            result = false;
        }

        return result;
    }

    /************************** it checks for checkBox Answers and return true for correct and false for wrong **********/
    public boolean chechboxQuestionAnswerCheck() {
        if (!checkboxOption1.isChecked() && !checkboxOption2.isChecked() &&
                !checkboxOption3.isChecked() && !checkboxOption4.isChecked()) {
            Toast.makeText(this, "Please Select Any Answer!!! All Questions Are mandatory!!!",
                    Toast.LENGTH_SHORT).show();
            noOptionChecked = true;
        } else {
            noOptionChecked = false;
        }
        boolean result = false;
        ArrayList<Integer> rightAnswerNoArray = new ArrayList<Integer>();
        if (checkboxOption1.isChecked()) {
            rightAnswerNoArray.add(1);
        }
        if (checkboxOption2.isChecked()) {
            rightAnswerNoArray.add(2);
        }
        if (checkboxOption3.isChecked()) {
            rightAnswerNoArray.add(3);
        }
        if (checkboxOption4.isChecked()) {
            rightAnswerNoArray.add(4);
        }
        if (multipleRightAnswerBox[index].length == rightAnswerNoArray.size()) {
            for (int i = 0; i < rightAnswerNoArray.size(); i++) {
                if (multipleRightAnswerBox[index][i] == rightAnswerNoArray.get(i)) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /************************** it checks for EditText Descriptive Answers and return true for correct and false for wrong **********/
    public boolean descriptiveQuestionAnswerCheck() {
        if (editTextAnswer.getText().toString().equals("") && editTextAnswer.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Select Any Answer!!! All Questions Are mandatory!!!",
                    Toast.LENGTH_SHORT).show();
            editTextAnswer.setError("Please Type Your Answer!!!");
            noOptionChecked = true;
        } else {
            noOptionChecked = false;
        }
        boolean result = false;
        if (editTextAnswer.getText().toString().equalsIgnoreCase(correctAnswerBox[index])) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    /********************************** instruction Alert Dialogue Box *********************/
    public void quizInstructionAlert() {
        AlertDialog instructionAlertDialog = new AlertDialog.Builder(this).create();
        instructionAlertDialog.setTitle("Instructions For Quiz");
        instructionAlertDialog.setCancelable(false);
        instructionAlertDialog.setMessage("Hi, " + playerName + "\n1. All Questions are compulsory.\n" +
                "2. Toatl 9 Question 3 each (Single Right Answer, Multiple Right Answer, Discriptive Answer).\n" +
                "3.For multiple right Answer if all answer is Right than only full marks else 0 marks " +
                "even if one option out many right option is right \n 4. For each correct answer 1+ score awarded" +
                "\n5. No negative Marks for Wrong Answers\n 6.Total " + questionBox.length + " Marks" +
                "\n7.BEST OF LUCK!!!");
        instructionAlertDialog.setButton(Dialog.BUTTON_POSITIVE, "PLAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        instructionAlertDialog.show();
        instructionAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.start_screen_gradient);
    }

    /********************************** instruction Alert Dialogue Box *********************/
    public void eachQuestionResultDialogue(boolean resultStatus) {
        AlertDialog instructionAlertDialog = new AlertDialog.Builder(this).create();
        instructionAlertDialog.setTitle("RESULT");
        instructionAlertDialog.setCancelable(false);
        String message = "";
        if (resultStatus) {
            totalScoreAchieved++;
            message = "--> Awsome !!!, Correct Answer!!!\n-- >" + hintBox[index] +
                    "\n--> Better Luck for Next Question!!!" + "\n -->Net Current Score : " + totalScoreAchieved;
            resultDialogueImage.setImageResource(R.drawable.ic_check_circle_black_24dp);
            scoreUpdateText.setText("SCORE : " + totalScoreAchieved);
        } else {
            message = "-->Bad Luck !!!, Wrong!!!\n-->" + correctAnswerBox[index] + " is Correct Answer!!!\n-->" + hintBox[index] +
                    "\n--> Better Luck for Next Question!!!" + "\n -->Net Current Score : " + totalScoreAchieved;
            resultDialogueImage.setImageResource(R.drawable.ic_cancel_black_24dp);
            scoreUpdateText.setText("SCORE : " + totalScoreAchieved);
        }
        if (resultDialogueImage.getParent() != null)
            ((ViewGroup) resultDialogueImage.getParent()).removeView(resultDialogueImage);
        instructionAlertDialog.setView(resultDialogueImage);
        instructionAlertDialog.setMessage(message);
        instructionAlertDialog.setButton(Dialog.BUTTON_POSITIVE, "NEXT QUESTION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (index < questionBox.length) {
                    setQuestioAndOption();
                }
                if (index == questionBox.length) {
                    finalResultDialogue();
                }
            }
        });
        instructionAlertDialog.show();
        instructionAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.start_screen_gradient);
    }

    /********************************** instruction Alert Dialogue Box for Final Score *********************/
    public void finalResultDialogue() {
        AlertDialog instructionAlertDialog = new AlertDialog.Builder(this).create();
        instructionAlertDialog.setTitle("FINAL RESULT");
        instructionAlertDialog.setCancelable(false);
        resultDialogueImage.setImageResource(R.drawable.logo);
        if (resultDialogueImage.getParent() != null)
            ((ViewGroup) resultDialogueImage.getParent()).removeView(resultDialogueImage);
        instructionAlertDialog.setView(resultDialogueImage);
        instructionAlertDialog.setMessage("YOU HAVE GOT " + totalScoreAchieved + " Answer Right Out of " + questionBox.length +
                " That's Great Effort, GOOD LUCK !!!");
        instructionAlertDialog.setButton(Dialog.BUTTON_POSITIVE, "RESTART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(QuizActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        instructionAlertDialog.setButton(Dialog.BUTTON_NEGATIVE, "EXIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        instructionAlertDialog.show();
        instructionAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.start_screen_gradient);
        Toast.makeText(this, "YOU HAVE GOT " + totalScoreAchieved + " Answer Right Out of " + questionBox.length +
                " That's Great Effort, GOOD LUCK !!!", Toast.LENGTH_SHORT).show();
    }
}