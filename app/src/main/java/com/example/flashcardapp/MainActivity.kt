package com.example.flashcardapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val questionTextView = findViewById<TextView>(R.id.flashcard_question)
        val answerTextView = findViewById<TextView>(R.id.flashcard_answer)

        questionTextView.setOnClickListener {
            questionTextView.visibility = View.INVISIBLE
            answerTextView.visibility = View.VISIBLE
        }

        answerTextView.setOnClickListener {
            answerTextView.visibility = View.INVISIBLE
            questionTextView.visibility = View.VISIBLE
        }
    }
}