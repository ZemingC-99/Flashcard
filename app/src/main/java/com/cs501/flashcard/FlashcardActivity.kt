package com.cs501.flashcard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class FlashcardActivity : AppCompatActivity() {

    private lateinit var operand1TextView: TextView
    private lateinit var operand2TextView: TextView
    private lateinit var operatorTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var generateButton: Button
    private val random = Random()
    private var currentAnswer: Int = 0
    private var questionsAnswered: Int = 0
    private var correctAnswered: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        val username = intent.getStringExtra("USERNAME")
        if (username != null) {
            Toast.makeText(this, "Welcome $username !", Toast.LENGTH_SHORT).show()
        }

        operand1TextView = findViewById(R.id.op1TextView)
        operand2TextView = findViewById(R.id.op2TextView)
        operatorTextView = findViewById(R.id.operatorTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitAnswerButton)
        generateButton = findViewById(R.id.generateProblemsButton)

        generateButton.setOnClickListener {
            generateRandomProblem()
            answerEditText.text.clear()
            questionsAnswered = 0
            correctAnswered = 0
            generateButton.isEnabled = false
            submitButton.isEnabled = true
        }

        submitButton.setOnClickListener {
            if (answerEditText.text.isNotEmpty()) {
                checkAnswer(answerEditText.text.toString().toInt())
                if (questionsAnswered == 10) {
                    Toast.makeText(this, "$correctAnswered/10 correct", Toast.LENGTH_LONG).show()
                    generateButton.isEnabled = true
                    submitButton.isEnabled = false
                } else {
                    generateRandomProblem()
                    answerEditText.text.clear()
                }
            } else {
                Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateRandomProblem() {
        val operand1 = random.nextInt(99) + 1
        val operand2 = random.nextInt(20) + 1
        val operations = (List(5) { "+" } + List(5) { "-" }).shuffled()

//        if (random.nextBoolean()) {
//            operatorTextView.text = "+"
//            currentAnswer = operand1 + operand2
//        } else {
//            operatorTextView.text = "-"
//            currentAnswer = operand1 - operand2
//        }

        //5 addition 5 subtraction
        for (op in operations) {
            if (op == "+") {
                operatorTextView.text = "+"
                currentAnswer = operand1 + operand2
            } else {
                operatorTextView.text = "-"
                currentAnswer = operand1 - operand2
            }
        }

        operand1TextView.text = operand1.toString()
        operand2TextView.text = operand2.toString()
    }

    private fun checkAnswer(userAnswer: Int) {
        if (userAnswer == currentAnswer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect. Correct answer: $currentAnswer", Toast.LENGTH_SHORT).show()
        }
        questionsAnswered++
        if (userAnswer == currentAnswer) {
            correctAnswered++
        }
    }
}