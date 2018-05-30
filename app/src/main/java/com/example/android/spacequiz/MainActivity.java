package com.example.android.spacequiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    returns true if option c is chosen or false for all other options
     */
    public boolean getAnswerQOne() {
        RadioGroup questionOne = findViewById(R.id.question_one);
        return (questionOne.getCheckedRadioButtonId() == R.id.q1_option_c);
    }

    /*
    returns true if a variation of "neil armstrong" is typed into the field
    spelling has to be correct but caps do not matter
     */
    public boolean getAnswerQTwo() {
        EditText questionTwoField = findViewById(R.id.q2_answer);
        String answer = questionTwoField.getText().toString().trim();
        return answer.equalsIgnoreCase("neil armstrong") || answer.equalsIgnoreCase("armstrong");
    }

    /*
    returns true if option A and option D are checked while options B and option C are not checked
    any other selections will return false
     */
    public boolean getAnswerQThree() {
        CheckBox checkQ3OptionA = findViewById(R.id.q3_option_a);
        CheckBox checkQ3OptionB = findViewById(R.id.q3_option_b);
        CheckBox checkQ3OptionC = findViewById(R.id.q3_option_c);
        CheckBox checkQ3OptionD = findViewById(R.id.q3_option_d);
        return (checkQ3OptionA.isChecked() && checkQ3OptionD.isChecked() && !checkQ3OptionB.isChecked() && !checkQ3OptionC.isChecked());
    }

    /*
    returns true if option a is selected
     */
    public boolean getAnswerQFour() {
        RadioGroup questionFour = findViewById(R.id.question_four);
        return (questionFour.getCheckedRadioButtonId() == R.id.q4_option_a);
    }

    /*
    checks answers and creates a toast message
    toast message is created here so user can return to answering questions without reloading app
     */
    public void submitAnswers(View view) {
        float score = 4;
        String toastMessage = "";
        if (getAnswerQOne()) {
            toastMessage += getString(R.string.question_one_correct) + "\n";
        } else {
            toastMessage += getString(R.string.question_one_incorrect) + "\n";
            score -= 1;
        }
        if (getAnswerQTwo()) {
            toastMessage += getString(R.string.question_two_correct) + "\n";
        } else {
            toastMessage += getString(R.string.question_two_incorrect) + "\n";
            score -= 1;
        }
        if (getAnswerQThree()) {
            toastMessage += getString(R.string.question_three_correct) + "\n";
        } else {
            toastMessage += getString(R.string.question_three_incorrect) + "\n";
            score -= 1;
        }
        if (getAnswerQFour()) {
            toastMessage += getString(R.string.question_four_correct) + "\n";
        } else {
            toastMessage += getString(R.string.question_four_incorrect) + "\n";
            score -= 1;
        }

        float totalScore = score / 4;
        totalScore = totalScore * 100;
        String scoreString = String.format(Locale.US, "%.2f", totalScore);
        if (scoreString.equals("100.00")) {
            toastMessage = getString(R.string.toast_score, scoreString) + "\n" + toastMessage;
            toastMessage = getString(R.string.toast_congrats) + "\n" + toastMessage;
        } else {
            toastMessage = getString(R.string.toast_score, scoreString) + "\n" + toastMessage;
        }

        /* uses a custom toast message */
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        TextView text = layout.findViewById(R.id.text);
        text.setText(toastMessage);

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    /* when an image is clicked, gets android:tag and opens intent to browser with it
     */
    public void openBrowser(View view) {

        String url = (String) view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        intent.setData(Uri.parse(url));

        startActivity(intent);
    }
}