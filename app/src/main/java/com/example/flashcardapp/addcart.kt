package com.example.flashcardapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flashcardapp.databinding.ActivityAddcartBinding

class AddCartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddcartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val question = binding.etQuestion.text.toString().trim()
            val answer1 = binding.etAnswer1.text.toString().trim()
            val answer2 = binding.etAnswer2.text.toString().trim()
            val answer3 = binding.etAnswer3.text.toString().trim()

            if (question.isBlank() || answer1.isBlank() || answer2.isBlank() || answer3.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val checkedRadioButtonId = binding.rgCorrectAnswer.checkedRadioButtonId
            if (checkedRadioButtonId == -1) {
                Toast.makeText(this, "Veuillez sélectionner la bonne réponse", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val correctAnswer = when (checkedRadioButtonId) {
                R.id.rbAnswer1 -> 1
                R.id.rbAnswer2 -> 2
                R.id.rbAnswer3 -> 3
                else -> 0
            }

            val resultIntent = Intent().apply {
                putExtra(MainActivity.EXTRA_QUESTION, question)
                putExtra(MainActivity.EXTRA_ANSWER1, answer1)
                putExtra(MainActivity.EXTRA_ANSWER2, answer2)
                putExtra(MainActivity.EXTRA_ANSWER3, answer3)
                putExtra(MainActivity.EXTRA_CORRECT_ANSWER, correctAnswer)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}