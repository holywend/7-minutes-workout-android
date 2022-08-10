package wend.web.id.a7minutesworkout

class ExerciseModel(
    val id: Int,
    val name: String,
    val img: Int,
    val duration: Long
) {
    val maxProgress = (duration / 1000).toInt()
    var isCompleted: Boolean = false
    var isCurrent: Boolean = false
}