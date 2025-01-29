package com.example.mindguard

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class SurveyActivity : AppCompatActivity() {

    private lateinit var radioGroup1: RadioGroup
    private lateinit var radioGroup4: RadioGroup
    private lateinit var seekBar: SeekBar
    private lateinit var editText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        radioGroup1 = findViewById(R.id.radioGroup1)
        radioGroup4 = findViewById(R.id.radioGroup4)
        seekBar = findViewById(R.id.seekBar)
        editText = findViewById(R.id.editText)
        resultTextView = findViewById(R.id.resultTextView)
        checkBox1= findViewById(R.id.checkBox1)
        checkBox2= findViewById(R.id.checkBox2)
        checkBox3= findViewById(R.id.checkBox3)
        checkBox4= findViewById(R.id.checkBox4)


        val submitButton: Button = findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            showResult()
        }
    }

    private fun getSelectedCheckBoxes(): List<String> {
        val selectedCheckboxes = mutableListOf<String>()
        if (checkBox1.isChecked) selectedCheckboxes.add(checkBox1.text.toString())
        if (checkBox2.isChecked) selectedCheckboxes.add(checkBox2.text.toString())
        if (checkBox3.isChecked) selectedCheckboxes.add(checkBox3.text.toString())
        if (checkBox4.isChecked) selectedCheckboxes.add(checkBox4.text.toString())
        return selectedCheckboxes
    }

    private fun showResult() {
        val odpowiedz1 = radioGroup1.checkedRadioButtonId?.let { findViewById<RadioButton>(it)?.text.toString() } ?: ""
        val odpowiedzi2 = getSelectedCheckBoxes()
        val odpowiedz3 = seekBar.progress
        val odpowiedz4 = radioGroup4.checkedRadioButtonId?.let { findViewById<RadioButton>(it)?.text.toString() } ?: ""
        val odpowiedz5 = editText.text.toString()

        val result = analyzeAnswers(odpowiedz1, odpowiedzi2, odpowiedz3, odpowiedz4, odpowiedz5)
        resultTextView.text = result
        resultTextView.visibility = android.view.View.VISIBLE
    }

    private fun analyzeAnswers(odpowiedz1: String, odpowiedzi2: List<String>, odpowiedz3: Int, odpowiedz4: String, odpowiedz5: String): String {
        var message = "Podsumowanie ankiety:\n"
        message += "Częstotliwość odczuwania smutku: $odpowiedz1\n"
        message += "Trudności ze snem: $odpowiedzi2\n"
        message += "Poziom stresu (1-10): $odpowiedz3\n"
        message += "Brak motywacji/energii: $odpowiedz4\n"
        message += "Ostatnie zmartwienia: $odpowiedz5\n\n"


        if (odpowiedz1 == "Bardzo często" && odpowiedz3 >= 7 && odpowiedz4 == "Zdecydowanie tak") {
            message += "Wygląda na to, że możesz potrzebować wsparcia. Rozważ rozmowę z bliską osobą lub specjalistą."
        } else if (odpowiedz3 >= 8) {
            message += "Odczuwasz wysoki poziom stresu. Spróbuj technik relaksacyjnych zawartych w aplikacji sekcja ćwiczenia mindfulness."
        } else {
            message += "Dziękujemy za wypełnienie ankiety. Pamiętaj o dbałości o swoje zdrowie psychiczne."
        }

        return message
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}