package wend.web.id.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import wend.web.id.a7minutesworkout.databinding.ActivityExerciseBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null

    private var timer: CountDownTimer? = null

    private var lapsedProgress = 0

    private val timerInterval: Long = 1000

    private val exerciseModelList = Constant.defaultExerciseList()
    private var currentIndex = 0

    private var t2speech: TextToSpeech? = null

    private var lapsedMinutes = 0
    private var lapsedSeconds = 0
    private var totalTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbExercise)

        t2speech = TextToSpeech(this, this)
        if (supportActionBar != null) {
            // add back button to navigation
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        startExerciseTimer()
    }

    private fun startTimer() {
        binding?.pbExercise?.progress = lapsedProgress
        binding?.pbExercise?.max = exerciseModelList[currentIndex].maxProgress
        binding?.tvTitle?.text = exerciseModelList[currentIndex].name
        if (exerciseModelList[currentIndex].img == 0) { // resting
            binding?.ivExercise?.visibility = View.GONE
            binding?.tvNext?.visibility = View.VISIBLE
            binding?.ivNext?.visibility = View.VISIBLE
            binding?.ivNext?.setImageResource(exerciseModelList[currentIndex + 1].img)
            val next = "Next Exercise\n" + exerciseModelList[currentIndex + 1].name
            binding?.tvNext?.text = next

        } else { // exercise
            binding?.ivExercise?.visibility = View.VISIBLE
            binding?.tvNext?.visibility = View.GONE
            binding?.ivNext?.visibility = View.GONE
            binding?.ivExercise?.setImageResource(exerciseModelList[currentIndex].img)
        }
        timer = object : CountDownTimer(exerciseModelList[currentIndex].duration, timerInterval) {
            override fun onTick(p0: Long) {
                lapsedSeconds++
                if (lapsedSeconds == 60) {
                    lapsedSeconds = 0
                    lapsedMinutes++
                }
                totalTime = "%02d".format(lapsedMinutes) + ":" + "%02d".format(lapsedSeconds)
                binding?.tvTotalTime?.text = totalTime
                lapsedProgress++
                val remainingTime = exerciseModelList[currentIndex].maxProgress - lapsedProgress
                binding?.pbExercise?.progress = remainingTime
                binding?.tvTimer?.text = remainingTime.toString()

                if (remainingTime == 0) {
                    speakOut(exerciseModelList[currentIndex].nextPrompt)
                } else if (remainingTime <= 8) {
                    speakOut(remainingTime.toString())
                }
            }

            override fun onFinish() {
                if (currentIndex + 1 < exerciseModelList.size) {
                    currentIndex++
                    lapsedProgress = 0
                    startTimer() // continue call itself
                } else {

                    Toast.makeText(
                        this@ExerciseActivity,
                        "Workout Finished!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }.start()
    }

    private fun startExerciseTimer() {
        if (timer != null) {
            timer?.cancel()
            lapsedProgress = 0
            lapsedSeconds = 0
            lapsedMinutes = 0
        }
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer?.cancel()
            lapsedProgress = 0
        }
        if (t2speech != null) {
            t2speech?.stop()
            t2speech?.shutdown()
        }
        t2speech = null
        binding = null
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


}