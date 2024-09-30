package entity

import enum.ClassType
import java.time.LocalDate
import java.time.LocalTime

class FitnessClass(
    var id: Int,
    var name: String,
    var instructor: String,
    var date: LocalDate,
    var time: LocalTime,
    var duration: Int,
    var maxParticipants: Int,
    var enrolledMembers: MutableList<Member> = mutableListOf(),
    var classType: ClassType
) {
    init {
        if (name.isBlank()) throw IllegalArgumentException("Name cannot be blank")
        if (instructor.isBlank()) throw IllegalArgumentException("Instructor cannot be blank")
        require(duration > 0 && duration <= 60) { "Duration must be between 1 and 60 minutes." }
        require(maxParticipants > 0) { "Max participants must be greater than 0." }
    }
}
