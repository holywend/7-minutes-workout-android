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
    private var isMetric = true

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
                val bmi = calculate()
                displayBMI(bmi)
            } else {
                speakOut("Please enter a valid weight and height")
                Toast.makeText(this, "Please enter a valid weight and height", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding?.rbMetric?.setOnClickListener {
            if (!isMetric) switchUnits()
        }
        binding?.rbUs?.setOnClickListener {
            if (isMetric) switchUnits()
        }
    }

    private fun calculate(): Float {
        // centimeter to meter
        Log.i("calculate", "isMetric: $isMetric")
        val height = binding?.etHeight?.text.toString().toFloat() / 100
        val weight = if (isMetric) binding?.etWeight?.text.toString().toFloat() else getMetric()
        Log.i("calculate", "height: $height weight: $weight")
        return weight / (height * height)

    }

    private fun switchUnits() {
        isMetric = !isMetric
        if (isMetric) {
            binding?.tilHeight?.visibility = View.VISIBLE
            binding?.llUsHeight?.visibility = View.GONE
        } else {
            binding?.tilHeight?.visibility = View.GONE
            binding?.llUsHeight?.visibility = View.VISIBLE
        }
        resetEditText()
    }

    private fun resetEditText() {
        binding?.llResult?.visibility = View.INVISIBLE
        binding?.etWeight?.text?.clear()
        binding?.etHeight?.text?.clear()
        binding?.etFeet?.text?.clear()
        binding?.etInch?.text?.clear()
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

    private fun getMetric(): Float {
        val feet =
            if (binding?.etFeet?.text.toString().isEmpty()) 0f else binding?.etFeet?.text.toString()
                .toFloat()
        val inch =
            if (binding?.etInch?.text.toString().isEmpty()) 0f else binding?.etInch?.text.toString()
                .toFloat()
        return us2Metric(feet, inch)
    }

    private fun us2Metric(feet: Float, inch: Float): Float {
        var cm: Float = feet * 30.48f
        cm += inch * 2.54f
        return cm / 100f // return in meter
    }

    private fun validateEntry(): Boolean {
        var isValid = true
        if (binding?.etWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (isMetric) { // isMetric
            if (binding?.etHeight?.text.toString().isEmpty()) {
                isValid = false
            }
        } else { // is US Unit
            if (binding?.etFeet?.text.toString().isEmpty() && binding?.etInch?.text.toString()
                    .isEmpty()
            ) {
                isValid = false
            }
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