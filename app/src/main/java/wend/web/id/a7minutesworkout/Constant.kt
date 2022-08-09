package wend.web.id.a7minutesworkout

object Constant {
    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Jumping Jack"))
        exerciseList.add(
            ExerciseModel(
                "Jumping Jack",
                R.drawable.ic_jumping_jack,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Forward lunge"))
        exerciseList.add(
            ExerciseModel(
                "Forward lunge",
                R.drawable.ic_lunge,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Forearm plank"))
        exerciseList.add(ExerciseModel("Forearm Plank", R.drawable.ic_plank, 30000, "Take a breath!"))
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Push Up"))
        exerciseList.add(ExerciseModel("Push Up", R.drawable.ic_push_up, 30000, "Take a breath!"))
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Push Up Rotation"))
        exerciseList.add(
            ExerciseModel(
                "Push Up Rotation",
                R.drawable.ic_push_up_rotation,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Side plank"))
        exerciseList.add(
            ExerciseModel(
                "Side Plank",
                R.drawable.ic_side_plank,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Squat"))
        exerciseList.add(ExerciseModel("Squat", R.drawable.ic_squat, 30000, "Take a breath!"))
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Step up on chair"))
        exerciseList.add(
            ExerciseModel(
                "Step up on chair",
                R.drawable.ic_step_up_chair,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Triceps dips on chair"))
        exerciseList.add(
            ExerciseModel(
                "Triceps dips on chair",
                R.drawable.ic_triceps_dips_on_chair,
                30000,
                "Take a breath!"
            )
        )
        exerciseList.add(ExerciseModel("Get Ready", 0, 12000, "Wall sit"))
        exerciseList.add(ExerciseModel("Wall sit", R.drawable.ic_wall_sit, 30000, "Congratulation for completing the 7 minutes workout"))
        return exerciseList
    }
}