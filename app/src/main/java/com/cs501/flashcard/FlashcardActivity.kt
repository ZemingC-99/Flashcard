/*
References:
https://stackoverflow.com/questions/16769654/how-to-use-onsaveinstancestate-and-onrestoreinstancestate
https://www.geeksforgeeks.org/how-to-create-landscape-layout-in-android-studio/#
https://www.geeksforgeeks.org/application-manifest-file-in-android/
https://www.geeksforgeeks.org/relative-layout-in-android/
https://stackoverflow.com/questions/55645273/how-to-disable-a-button-in-kotlin
 */
package com.cs501.flashcard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.VisibleForTesting
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
    var currentAnswer: Int = 0
    private var questionsAnswered: Int = 0
    private var correctAnswered: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard)

        if (savedInstanceState == null) {
            val username = intent.getStringExtra("USERNAME")
            if (username != null) {
                Toast.makeText(this, "Welcome $username !", Toast.LENGTH_SHORT).show()
            }
        }

        operand1TextView = findViewById(R.id.op1TextView)
        operand2TextView = findViewById(R.id.op2TextView)
        operatorTextView = findViewById(R.id.operatorTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitAnswerButton)
        generateButton = findViewById(R.id.generateProblemsButton)

        savedInstanceState?.let {
            currentAnswer = it.getInt("CURRENT_ANSWER")
            questionsAnswered = it.getInt("QUESTIONS_ANSWERED")
            correctAnswered = it.getInt("CORRECT_ANSWERED")
            operand1TextView.text = it.getString("OPERAND_1_TEXT")
            operand2TextView.text = it.getString("OPERAND_2_TEXT")
            operatorTextView.text = it.getString("OPERATOR_TEXT")
            generateButton.isEnabled = it.getBoolean("GENERATE_BUTTON_STATE")
            submitButton.isEnabled = it.getBoolean("SUBMIT_BUTTON_STATE")
        }

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("CURRENT_ANSWER", currentAnswer)
        outState.putInt("QUESTIONS_ANSWERED", questionsAnswered)
        outState.putInt("CORRECT_ANSWERED", correctAnswered)
        outState.putString("OPERAND_1_TEXT", operand1TextView.text.toString())
        outState.putString("OPERAND_2_TEXT", operand2TextView.text.toString())
        outState.putString("OPERATOR_TEXT", operatorTextView.text.toString())
        outState.putBoolean("GENERATE_BUTTON_STATE", generateButton.isEnabled)
        outState.putBoolean("SUBMIT_BUTTON_STATE", submitButton.isEnabled)
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

    @VisibleForTesting
    fun isAnswerCorrect(userAnswer: Int): Boolean {
        questionsAnswered++
        val correct = userAnswer == currentAnswer
        if (correct) {
            correctAnswered++
        }
        return correct
    }

    private fun checkAnswer(userAnswer: Int) {
        if (isAnswerCorrect(userAnswer)) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrect! Correct answer: $currentAnswer", Toast.LENGTH_SHORT).show()
        }
    }
}