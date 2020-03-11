package com.nikitolch.mathgamedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/*
* TODO:
*  Create levels of difficulty
*  Track high score based on score and percentage being higher
*    or create levels
* */

public class MainActivity extends AppCompatActivity {

    ArrayList gameType;

    Button startButton;

    TextView timerTextView;
    TextView scoreTextView;
    TextView questionTextView;
    android.support.v7.widget.GridLayout gridLayout;

    TextView textView8;
    TextView finalScoreTextView;
    int percentCorrect;
    Button resetButton;

    Button option0;
    Button option1;
    Button option2;
    Button option3;

    int correctAnswer;
    int positionOfCorrectAnswer;
    int score;
    int questionsAsked = 0;

    TextView highScoreTextView;
    int highScore;
//    int highPercentCorrect;

    ArrayList<Integer> answers = new ArrayList<Integer>();

    public void startTimer(MainActivity mainActivity) {

        generateQuestionAndAnswer();

        new CountDownTimer(30000 + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerTextView.setText(Integer.toString(secondsLeft));
            }

            @Override
            public void onFinish() {
                if (score > 0) {
                    percentCorrect = score * 100 / questionsAsked;
                    finalScoreTextView.setText(score + "\n" + percentCorrect + "% Correct!");
                } else if (score == 0 && questionsAsked == 0) {
                    percentCorrect = 0;
                    finalScoreTextView.setText(score + "\n" + "You didn't even try!");
                } else {
                    percentCorrect = 0;
                    finalScoreTextView.setText(score);
                }

                trackHighScore();

                // Hide game
                questionTextView.setVisibility(View.INVISIBLE);
                gridLayout.setVisibility(View.INVISIBLE);
                timerTextView.setVisibility(View.INVISIBLE);
                scoreTextView.setVisibility(View.INVISIBLE);

                // Show score
                textView8.setVisibility(View.VISIBLE);
                finalScoreTextView.setVisibility(View.VISIBLE);
                resetButton.setVisibility(View.VISIBLE);
                highScoreTextView.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void trackHighScore() {
        if (highScore == 0) {
            highScore = score;
//            highPercentCorrect = percentCorrect;
            highScoreTextView.setText("New High Score!\n" + "Score: " + highScore);
        }
        else if (score > highScore && percentCorrect >= 90) {
            highScore = score;
//            highPercentCorrect = percentCorrect;
            highScoreTextView.setText("New High Score!\n" + highScore);
        } else {
            highScoreTextView.setText("Current High Score:\n" + highScore);
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.nikitolch.mathgamedemo", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("highScore", highScore).apply();
        highScore = sharedPreferences.getInt("highScore", 0);
//        sharedPreferences.edit().putInt("highPercentCorrect", highPercentCorrect);
//        highPercentCorrect = sharedPreferences.getInt("highPercentCorrect", 0);

    }

    // operators = 1, 2, 3, 4  -- each int equating to an operator
    // questionType = random int in operators array
    // ex: if questionType == 1, generateAdditionQuestion

    ArrayList<Integer> selectedOperators = new ArrayList<>();

    int ADD = 0, SUBTRACT = 1, MULTIPLY = 2, DIVIDE = 3;

    public void getSelectedOperators() {
        if (gameType.contains("addition")) {
            selectedOperators.add(ADD);
        } if (gameType.contains("subtraction")) {
            selectedOperators.add(SUBTRACT);
        } if (gameType.contains("multiplication")) {
            selectedOperators.add(MULTIPLY);
        } if (gameType.contains("division")) {
            selectedOperators.add(DIVIDE);
        }
    }

    public void generateQuestionAndAnswer() {

        Random random = new Random();
        int questionType = selectedOperators.get(random.nextInt(selectedOperators.size()));

        if (questionType == 0) {
            generateAdditionQuestion();
        } else if (questionType == 1) {
            generateSubstractionQuestion();
        } else if (questionType == 2) {
            generateMultiplicationQuestion();
        } else if (questionType == 3) {
            generateDivisionQuestion();
        }

    }

    // Method to populate 4 options to a question
    public void populateOptions (ArrayList arrayList) {
        option0.setText(String.valueOf(answers.get(0)));
        option1.setText(String.valueOf(answers.get(1)));
        option2.setText(String.valueOf(answers.get(2)));
        option3.setText(String.valueOf(answers.get(3)));
    }

    public void generateAdditionQuestion () {
        Random random = new Random();
        int number1 = random.nextInt(100);
        int number2 = random.nextInt(100);
        questionTextView.setText(number1 + " + " + number2 + " = ");

        correctAnswer = number1 + number2;

        positionOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (positionOfCorrectAnswer == i) {
                answers.add(correctAnswer);
            } else {
                int incorrectAnswer = random.nextInt(180 + 20);
                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = random.nextInt(180 + 20);
                }
                answers.add(incorrectAnswer);
            }
        }
        populateOptions(answers);
    }
    public void generateSubstractionQuestion () {
        Random random = new Random();
        int number1 = random.nextInt(100 - 50) + 50;
        int number2 = random.nextInt(50 - 1) + 1;

        questionTextView.setText(number1 + " - " + number2 + " = ");

        correctAnswer = number1 - number2;

        positionOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (positionOfCorrectAnswer == i) {
                answers.add(correctAnswer);
            } else {
                int incorrectAnswer = random.nextInt(100 + 5);
                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = random.nextInt(100 + 5);
                }
                answers.add(incorrectAnswer);
            }
        }
        populateOptions(answers);
    }
    public void generateMultiplicationQuestion () {
        Random random = new Random();
        int number1 = random.nextInt(30) + 1;
        int number2 = random.nextInt(10) + 1;

        questionTextView.setText(number1 + " x " + number2 + " = ");

        correctAnswer = number1 * number2;

        positionOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (positionOfCorrectAnswer == i) {
                answers.add(correctAnswer);
            } else {
                int incorrectAnswer = random.nextInt(200) + 2;
                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = random.nextInt(200) + 2;
                }
                answers.add(incorrectAnswer);
            }
        }
        populateOptions(answers);
    }
    public void generateDivisionQuestion () {
        Random random = new Random();
        int number1 = random.nextInt(100 + 1) * 2;
        int number2 = random.nextInt(2) + 1 ;

        questionTextView.setText(number1 + " ÷ " + number2 + " = ");

        correctAnswer = number1 / number2;

        positionOfCorrectAnswer = random.nextInt(4);
        for (int i = 0; i < 4; i++) {
            if (positionOfCorrectAnswer == i) {
                answers.add(correctAnswer);
            } else {
                int incorrectAnswer = random.nextInt(100 + 2);
                while (incorrectAnswer == correctAnswer) {
                    incorrectAnswer = random.nextInt(100 + 2);
                }
                answers.add(incorrectAnswer);
            }
        }
        populateOptions(answers);
    }

    public void resetGame(View view) {
        answers.clear();
        questionsAsked = 0;
        score = 0;
        scoreTextView.setText(score + "/" + questionsAsked);

        startButton.setVisibility(View.VISIBLE);

        textView8.setVisibility(View.INVISIBLE);
        finalScoreTextView.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        highScoreTextView.setVisibility(View.INVISIBLE);
    }

    public void checkAnswer(View view) {
        int chosenAnswer = Integer.parseInt(view.getTag().toString());

        if (chosenAnswer == positionOfCorrectAnswer) {
            final Toast toast = Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT);
            toast.show();

            // Cut length of time toast displays down to .6 seconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 600);

            score = score + 1;
        } else {
            final Toast toast = Toast.makeText(MainActivity.this, "Incorrect!", Toast.LENGTH_SHORT);
            toast.show();

            // Cut length of time toast displays down to .6 seconds
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 600);
        }

        questionsAsked = questionsAsked + 1;
        scoreTextView.setText(score + "/" + questionsAsked);

        answers.clear();
        generateQuestionAndAnswer();
        }

    public void startGame(View view) {
        startButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        questionTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        startTimer(this);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameType = (ArrayList<String>) getIntent().getExtras().getSerializable("options");
        Toast.makeText(this, "You selected " + gameType, Toast.LENGTH_SHORT).show();

        getSelectedOperators();

        startButton = findViewById(R.id.startButton);

        timerTextView = findViewById(R.id.timerTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.questionTextView);
        gridLayout = findViewById(R.id.gridLayout);

        textView8 = findViewById(R.id.textView8);
        finalScoreTextView = findViewById(R.id.finalScoreTextView);
        resetButton = findViewById(R.id.resetButton);

        option0 = findViewById(R.id.option0);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);

        highScoreTextView = findViewById(R.id.highScoreTextView);

    }
}
