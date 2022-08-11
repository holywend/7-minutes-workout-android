package wend.web.id.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import wend.web.id.a7minutesworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // toolbar
        setSupportActionBar(binding?.tbHistory)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Exercise History"
        }
        binding?.tbHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDao = (application as WorkoutApp).db.historyDao()
        getAllHistory(historyDao)
    }

    private fun getAllHistory(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllHistory().collect { items ->
                if (items.isNotEmpty()) {
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoHistory?.visibility = View.GONE
                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@HistoryActivity)
                    val dates = ArrayList<String>()
                    for (item in items){
                        dates.add(item.date)
                    }
                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistory?.adapter = historyAdapter
                } else {
                    binding?.rvHistory?.visibility = View.GONE
                    binding?.tvNoHistory?.visibility = View.VISIBLE
                }
            }
        }
    }
}