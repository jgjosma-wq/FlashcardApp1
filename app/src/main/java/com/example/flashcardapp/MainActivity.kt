package com.example.flashcardapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val flashcards = mutableListOf<Flashcard>()
    private var currentCardIndex = 0

    private val addCartLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val question = data?.getStringExtra(EXTRA_QUESTION)
            val answer1 = data?.getStringExtra(EXTRA_ANSWER1)
            val answer2 = data?.getStringExtra(EXTRA_ANSWER2)
            val answer3 = data?.getStringExtra(EXTRA_ANSWER3)
            val correctAnswer = data?.getIntExtra(EXTRA_CORRECT_ANSWER, 0) ?: 0

            if (!question.isNullOrBlank() && !answer1.isNullOrBlank() && !answer2.isNullOrBlank() && !answer3.isNullOrBlank()) {
                flashcards.add(Flashcard(question, answer1, answer2, answer3, correctAnswer))
                currentCardIndex = flashcards.size - 1
                displayCard()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add default flashcard
        flashcards.add(Flashcard("Qui est le 44e président des États-Unis ?", "George Washington", "Barack Obama", "Donald Trump", 2))
        displayCard()

        binding.btnAnswer1.setOnClickListener {
            checkAnswer(1)
        }

        binding.btnAnswer2.setOnClickListener {
            checkAnswer(2)
        }

        binding.btnAnswer3.setOnClickListener {
            checkAnswer(3)
        }

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddCartActivity::class.java)
            addCartLauncher.launch(intent)
        }

        binding.btnNext.setOnClickListener {
            if (flashcards.isNotEmpty()) {
                currentCardIndex = (currentCardIndex + 1) % flashcards.size
                displayCard()
            }
        }
    }

    private fun displayCard() {
        val currentCard = flashcards[currentCardIndex]
        binding.tvQuestion.text = currentCard.question
        binding.btnAnswer1.text = currentCard.answer1
        binding.btnAnswer2.text = currentCard.answer2
        binding.btnAnswer3.text = currentCard.answer3
        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswer: Int) {
        val currentCard = flashcards[currentCardIndex]
        if (selectedAnswer == currentCard.correctAnswer) {
            Toast.makeText(this, "Bonne réponse!", Toast.LENGTH_SHORT).show()
            highlightCorrectAnswer()
        } else {
            Toast.makeText(this, "Mauvaise réponse!", Toast.LENGTH_SHORT).show()
            highlightCorrectAnswer(selectedAnswer)
        }
    }

    private fun highlightCorrectAnswer(selectedIncorrect: Int = 0) {
        val currentCard = flashcards[currentCardIndex]
        val buttons = listOf(binding.btnAnswer1, binding.btnAnswer2, binding.btnAnswer3)
        buttons.getOrNull(currentCard.correctAnswer - 1)?.setBackgroundColor(Color.GREEN)
        if (selectedIncorrect != 0) {
            buttons.getOrNull(selectedIncorrect - 1)?.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        // You may want to use a color from your theme instead of a hardcoded color
        binding.btnAnswer1.setBackgroundColor(Color.parseColor("#6200EE"))
        binding.btnAnswer2.setBackgroundColor(Color.parseColor("#6200EE"))
        binding.btnAnswer3.setBackgroundColor(Color.parseColor("#6200EE"))
    }


    companion object {
        const val EXTRA_QUESTION = "extra_question"
        const val EXTRA_ANSWER1 = "extra_answer1"
        const val EXTRA_ANSWER2 = "extra_answer2"
        const val EXTRA_ANSWER3 = "extra_answer3"
        const val EXTRA_CORRECT_ANSWER = "extra_correct_answer"
    }
}