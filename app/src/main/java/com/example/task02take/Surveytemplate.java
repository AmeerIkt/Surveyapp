package com.example.task02take;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Surveytemplate extends AppCompatActivity {

    TextView questionText;
    RadioGroup chooisgroup;
    RadioButton chooise1, chooise2, chooise3 , chooise4;

    List<Question> quizQuestions;
    Question question;


    int questionID = 0 , mark = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.surveytemplate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadquestions();
        StartTheShow();


        chooisgroup = findViewById(R.id.chooiseset);
        chooisgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (question.getRightanswer() == getanswernumber(checkedId))
                    mark++;
                RadioButton temp = findViewById(checkedId);
                temp.setChecked(false);

                if (questionID < quizQuestions.size() -1){

                    questionID++;
                    question = quizQuestions.get(questionID);
                    LoadQuestion();

                }
                else {
                    Intent socrepage = new Intent(Surveytemplate.this , ResultPage.class) ;
                    socrepage.putExtra("mark" , mark);
                    startActivity(socrepage);
                    finish();
                }

            }
        });




    }


    private void StartTheShow(){

        question =  quizQuestions.get(questionID);

        questionText =  findViewById(R.id.questiontxt);
        chooise1 =  findViewById(R.id.chooise1);
        chooise2 =  findViewById(R.id.chooise2);
        chooise3 =  findViewById(R.id.chooise3);
        chooise4 =  findViewById(R.id.chooise4);
        LoadQuestion();

    }

    private void LoadQuestion(){
        questionText.setText(question.getQuestion());
        chooise1.setText(question.getChooise1());
        chooise2.setText(question.getChooise2());
        chooise3.setText(question.getChooise3());
        chooise4.setText(question.getChooise4());


    }

    private int getanswernumber (int id){

        if (id == R.id.chooise1)
            return 1;
        else if (id == R.id.chooise2)
            return 2;
        else if (id == R.id.chooise3)
            return 3;
        else if (id == R.id.chooise4)
            return 4;

        else return -1;


    }


    public void loadquestions (){
        quizQuestions  = new ArrayList<>();
        quizQuestions.add(new Question("What is the capital of France?" , "Paris" , "London" , "Berlin" ,"Madrid",1));
        quizQuestions.add(new Question("What is the largest planet in our solar system?" , "Earth" , "Mars" , "Jupiter" ,"Saturn",3));
        quizQuestions.add(new Question("Which element has the chemical symbol 'O'?" , "Oxygen" , "Gold" , "Osmium" ,"Carbon",1));
        quizQuestions.add(new Question("How many continents are there on Earth?" , "5" , "6" , "7" ,"8",3));
        quizQuestions.add(new Question("Which ocean is the largest by surface area?" , "Atlantic" , "Pacific" , "Indian" ,"Arctic",2));
        quizQuestions.add(new Question("Who painted the Mona Lisa?" , "Leonardo da Vinci" , "Pablo Picasso" , "Vincent van Gogh" ,"Michelangelo",1));
        quizQuestions.add(new Question("What is the square root of 64?" , "6" , "8" , "10" ,"12",2));
        quizQuestions.add(new Question("Which country is known as the Land of the Rising Sun?" , "China" , "South Korea" , "Japan" ,"Thailand",3));
        quizQuestions.add(new Question("What is the freezing point of water in Celsius?" , "0" , "32" , "-1" ,"100",1));
        quizQuestions.add(new Question("What is the smallest prime number?" , "1" , "2" , "3" ,"5",2));

    }
}

