package wend.web.id.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import wend.web.id.a7minutesworkout.databinding.ActivityFinishBinding
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.tbFinish)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.tbFinish?.setNavigationOnClickListener {
            onBackPressed()
        }
        binding?.btnFinish?.setOnClickListener {
            finish()
        }

        // add record to database
        val historyDao = (application as WorkoutApp).db.historyDao()
        addRecord(historyDao)

    }

    private fun addRecord(historyDao: HistoryDao){
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd MMM yyyy: HH:mm:ss", Locale.getDefault())

        // run on coroutine
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(sdf.format(date)))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}