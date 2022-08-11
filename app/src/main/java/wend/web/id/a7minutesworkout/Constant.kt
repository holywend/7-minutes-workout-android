package wend.web.id.a7minutesworkout

object Constant {
    const val interval: Long = 1000
    const val restDuration: Long = 2000
    const val restProgress = (restDuration / 1000).toInt()
    const val restDisplayName = "Get Ready"

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        exerciseList.add(
            ExerciseModel(1, "Jumping Jack", R.drawable.ic_jumping_jack, 3000)
        )
        exerciseList.add(
            ExerciseModel(2, "Push Up", R.drawable.ic_push_up, 3000)
        )
        exerciseList.add(
            ExerciseModel(3, "Forward lunge", R.drawable.ic_lunge, 3000)
        )
        exerciseList.add(
            ExerciseModel(4, "Forearm Plank", R.drawable.ic_plank, 3000)
        )
        exerciseList.add(
            ExerciseModel(5,"Squat", R.drawable.ic_squat, 3000)
        )
        exerciseList.add(
            ExerciseModel(6,"Push Up Rotation", R.drawable.ic_push_up_rotation, 3000)
        )
        exerciseList.add(
            ExerciseModel(7,"Step up on chair", R.drawable.ic_step_up_chair, 3000)
        )
        exerciseList.add(
            ExerciseModel(8, "Side Plank", R.drawable.ic_side_plank, 3000)
        )
        exerciseList.add(
            ExerciseModel(9, "Triceps dips", R.drawable.ic_triceps_dips_on_chair, 3000)
        )
        exerciseList.add(
            ExerciseModel(10, "Wall sit", R.drawable.ic_wall_sit, 3000)
        )
        return exerciseList
    }
}