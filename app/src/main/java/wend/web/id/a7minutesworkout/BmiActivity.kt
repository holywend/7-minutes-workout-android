package wend.web.id.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import wend.web.id.a7minutesworkout.databinding.ActivityBmiBinding
import java.util.*

class BmiActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityBmiBinding? = null
    private var t2speech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        t2speech = TextToSpeech(this, this)

        setSupportActionBar(binding?.tbBmi)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate Your BMI"
        }
        binding?.tbBmi?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.btnCalculate?.setOnClickListener {
            if (validateEntry()) {
                // centimeter to meter
                val height = binding?.etHeight?.text.toString().toFloat() / 100
                val weight = binding?.etWeight?.text.toString().toFloat()
                val bmi = weight / (height * height)
                displayBMI(bmi)
            } else {
                Toast.makeText(this, "Please enter a valid entry", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayBMI(bmi: Float) {
        val bmiString = "%.2f".format(bmi)
        val type: String
        val desc: String

        if (bmi >= 30.0f) {
            type = "Obesity"
            desc = "Whoa! You really need to take better care of yourself! Eat less exercise more"
        } else if (bmi >= 25f) {
            type = "Overweight"
            desc = "Moderately overweight. You need to start exercise!"
        } else if (bmi >= 18.5f) {
            type = "Normal"
            desc = "Good! Maintain your healthy body mass!"
        } else {
            type = "Underweight"
            desc = "Oops! You really need to take better care of yourself! Eat more!"
        }
        speakOut(desc)
        binding?.tvBMIValue?.text = bmiString
        binding?.tvBMIType?.text = type
        binding?.tvBMIDescription?.text = desc
        binding?.llResult?.visibility = View.VISIBLE
    }

    private fun validateEntry(): Boolean {
        var isValid = true
        if (binding?.etHeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (binding?.etWeight?.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = t2speech!!.setLanguage(Locale.UK)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("Text 2 Speech", "Language specified is not supported!")
            }
        }
    }

    private fun speakOut(text: String) {
        t2speech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        if (t2speech != null) {
            t2speech?.stop()
            t2speech?.shutdown()
            t2speech = null
        }
    }
}