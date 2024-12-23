package com.example.task02take;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    ProgressBar progressBar;

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

        StartTheShow();


        chooisgroup = findViewById(R.id.chooiseset);
        chooisgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (question.getRightanswer() == getanswernumber(checkedId))
                    mark++;

                if (questionID <= quizQuestions.size()-1)
                    questionID++;
                question = quizQuestions.get(questionID);
                LoadQuestion();

            }
        });




    }


    private void StartTheShow(){
        quizQuestions  = new ArrayList<>();
        quizQuestions.add(new Question("test" , "1" , "2" , "3" ,"4",2));
        quizQuestions.add(new Question("test2" , "1" , "2" , "3" ,"4",2));
        quizQuestions.add(new Question("test3" , "1" , "2" , "3" ,"4",2));

        question =  quizQuestions.get(questionID);

        questionText =  findViewById(R.id.questiontxt);
        chooise1 =  findViewById(R.id.chooise1);
        chooise2 =  findViewById(R.id.chooise2);
        chooise3 =  findViewById(R.id.chooise3);
        chooise4 =  findViewById(R.id.chooise4);
        progressBar =  findViewById(R.id.progressBar);
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


}

