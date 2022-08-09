package wend.web.id.a7minutesworkout

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import wend.web.id.a7minutesworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding? = null
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        val flStartButton: FrameLayout = findViewById(R.id.fl_start)
//        flStartButton.setOnClickListener {
        binding?.flStart?.setOnClickListener {
            // play sound effect when workout finished
            try {
                val soundUri =
                    Uri.parse("android.resource://wend.web.id.a7minutesworkout/" + R.raw.press_start)
                player = MediaPlayer.create(applicationContext, soundUri)
                player?.isLooping = false
                player?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Toast.makeText(this, "Start Clicked", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
//        if finish() used then we can not press back button from exercise activity
//            to get back to main activity
        //            finish()
        }
    }

    // since we are using binding we need to make sure to properly destroy it
    override fun onDestroy() {
        super.onDestroy()
        if (player != null) {
            player?.stop()
            player = null
        }
        binding = null
    }
}