package com.example.quizapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz_Page extends AppCompatActivity {

    TextView time,correct,wrong;
    TextView a,b,c,d,question;
    Button next,finish;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Questions");


    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String correctAnswer;

    int questionCount;
    int questionNumber = 1;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        time = findViewById(R.id.textViewtime);
        correct = findViewById(R.id.textViewCorrect);
        wrong = findViewById(R.id.textViewWrong);
        a = findViewById(R.id.textViewA);
        b = findViewById(R.id.textViewB);
        c = findViewById(R.id.textViewC);
        d = findViewById(R.id.textViewD);
        next = findViewById(R.id.buttonNext);
        finish = findViewById(R.id.buttonFinish);

        game();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                game();

            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void game()
    {

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                questionCount = (int) dataSnapshot.getChildrenCount();


                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("Q")
                        .getValue().toString();
                quizAnswerA = dataSnapshot.child(String.valueOf(questionNumber)).child("A")
                        .getValue().toString();
                quizAnswerB = dataSnapshot.child(String.valueOf(questionNumber)).child("B")
                        .getValue().toString();
                quizAnswerC = dataSnapshot.child(String.valueOf(questionNumber)).child("C")
                        .getValue().toString();
                quizAnswerD = dataSnapshot.child(String.valueOf(questionNumber)).child("D")
                        .getValue().toString();
                correctAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("answer")
                        .getValue().toString();

                question.setText(quizQuestion);
                a.setText(quizAnswerA);
                b.setText(quizAnswerB);
                c.setText(quizAnswerC);
                d.setText(quizAnswerD);

                if (questionNumber < questionCount){
                    questionNumber++;
                }
                else {

                    Toast.makeText(Quiz_Page.this, "You answered all Questions..!!"
                            , Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

                Toast.makeText(Quiz_Page.this, "There is an error."
                        , Toast.LENGTH_LONG).show();

            }
        });

    }

}