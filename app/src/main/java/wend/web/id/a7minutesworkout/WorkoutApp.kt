package wend.web.id.a7minutesworkout

import android.app.Application

// since the database run across multiple activities in the app
// we need to create a application to make sure the database
// run on application context
// also need to set this in the AndroidManifest.xml
class WorkoutApp: Application() {
    val db by lazy {
        HistoryDatabase.getInstance(this)
    }
}