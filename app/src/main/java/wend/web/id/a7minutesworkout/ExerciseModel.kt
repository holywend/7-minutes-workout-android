package wend.web.id.a7minutesworkout

class ExerciseModel(var name: String, var img: Int, var duration: Long, var nextPrompt: String) {
var maxProgress = (duration / 1000).toInt()
}