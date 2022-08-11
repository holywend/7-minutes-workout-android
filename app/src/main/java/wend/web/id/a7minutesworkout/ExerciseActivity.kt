package wend.web.id.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import wend.web.id.a7minutesworkout.databinding.ActivityExerciseBinding
import wend.web.id.a7minutesworkout.databinding.DialogExitConfirmationBinding
import java.util.*

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var binding: ActivityExerciseBinding? = null
    private var exerciseAdapter: ExerciseAdapter? = null

    private val exerciseModelList = Constant.defaultExerciseList()
    private var currentIndex = 0 // current exercise index

    // music / sound related
    private var t2speech: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var timer: CountDownTimer? = null
    private var lapsedProgress = 0 // store lapsed progress second
    private var lapsedMinutes = 0 // store lapsed minutes
    private var lapsedSeconds = 0 // store lapsed seconds
    private var totalTime: String? = null // store total lapse

    private var isResting = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbExercise)

        setupExerciseAdapter()

        t2speech = TextToSpeech(this, this)
        if (supportActionBar != null) {
            // add back button to navigation
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbExercise?.setNavigationOnClickListener {
//            onBackPressed()
            dialogConfirmation()
        }
        // play music
        try {
            val soundUri =
                Uri.parse("android.resource://wend.web.id.a7minutesworkout/" + R.raw.happy_travel_pop)
            player = MediaPlayer.create(applicationContext, soundUri)
            player?.isLooping = true
            player?.setVolume(0.2f, 0.2f)
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        startExerciseTimer()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        dialogConfirmation()
    }

    private fun dialogConfirmation() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogExitConfirmationBinding.inflate(layoutInflater)
        val msg = "End current exercise"
        dialogBinding.tvMessage.text = msg
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.show()
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
    }

    private fun setupExerciseAdapter() {
        exerciseAdapter = ExerciseAdapter(exerciseModelList)
        binding?.rvExercise?.adapter = exerciseAdapter
    }

    private fun startTimer() {
        binding?.pbExercise?.progress = lapsedProgress
        if (isResting) { // resting

            // set the progress and display current progress name
            // currently it is resting progress
            binding?.pbExercise?.max = Constant.restProgress
            binding?.tvTitle?.text = Constant.restDisplayName
            // hide exercise image
            // show next exercise name and image
            binding?.ivExercise?.visibility = View.GONE
            binding?.tvNext?.visibility = View.VISIBLE
            binding?.ivNext?.visibility = View.VISIBLE
            // check if there is a next exercise
            if (currentIndex <= (exerciseModelList.size - 1)) {
                // set name and image for next exercise
                binding?.ivNext?.setImageResource(exerciseModelList[currentIndex].img)
                val next = "Next Exercise\n" + exerciseModelList[currentIndex].name
                binding?.tvNext?.text = next
            }
            // call the timer
            createTimerObject(Constant.restDuration)
        } else { // exercise
            // set is current
            exerciseModelList[currentIndex].isCurrent = true

            // set the progress and display current progress name
            // current exercise
            binding?.pbExercise?.max = exerciseModelList[currentIndex].maxProgress
            binding?.tvTitle?.text = exerciseModelList[currentIndex].name
            // show exercise image
            // hide next exercise name and image
            binding?.ivExercise?.visibility = View.VISIBLE
            binding?.tvNext?.visibility = View.GONE
            binding?.ivNext?.visibility = View.GONE
            binding?.ivExercise?.setImageResource(exerciseModelList[currentIndex].img)
            // call the timer
            createTimerObject(exerciseModelList[currentIndex].duration)
        }
    }

    private fun createTimerObject(duration: Long) {
        timer = object : CountDownTimer(duration, Constant.interval) {
            override fun onTick(p0: Long) {
                lapsedSeconds++
                if (lapsedSeconds == 60) {
                    lapsedSeconds = 0
                    lapsedMinutes++
                }
                totalTime = "%02d".format(lapsedMinutes) + ":" + "%02d".format(lapsedSeconds)
                binding?.tvTotalTime?.text = totalTime
                lapsedProgress++
                val remainingTime = if (isResting) {
                    Constant.restProgress - lapsedProgress
                } else {
                    exerciseModelList[currentIndex].maxProgress - lapsedProgress
                }
                binding?.pbExercise?.progress = remainingTime
                binding?.tvTimer?.text = remainingTime.toString()

                // text to speech
                if (isResting) {
                    if (remainingTime == 8) {
                        speakOut("Next exercise is " + exerciseModelList[currentIndex].name)
                    } else if (remainingTime == 0) {
                        speakOut("Go!")
                    } else if (remainingTime <= 3) {
                        speakOut(remainingTime.toString())
                    }
                } else {
                    if (remainingTime == 0) {
                        speakOut("Take a break")
                    } else if (remainingTime <= 8) {
                        speakOut(remainingTime.toString())
                    }
                }
            }

            override fun onFinish() {
                exerciseModelList[currentIndex].isCurrent = false
                exerciseModelList[currentIndex].isCompleted = true
                // do not increase current index if it is the last index
                // last index is size - 1
                if (!isResting) {
                    currentIndex++
                }
                if ((currentIndex + 1) < exerciseModelList.size) {
                    isResting = !isResting
                    lapsedProgress = 0
                    startTimer() // continue call itself
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "Workout Finished!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }

                // display updated exercise
                // exerciseAdapter?.notifyDataSetChanged()
                // update only specific changes
                if (currentIndex > 0) exerciseAdapter?.notifyItemChanged(currentIndex - 1)
                exerciseAdapter?.notifyItemChanged(currentIndex)
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
        if (player != null) {
            player?.stop()
            player = null
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