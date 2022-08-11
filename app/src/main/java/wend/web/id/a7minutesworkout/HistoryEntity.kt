package wend.web.id.a7minutesworkout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl-history")
data class HistoryEntity(
    @PrimaryKey
    val date:String
)
