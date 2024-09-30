package entity

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
    var enrolledMembers: MutableList<Member> = mutableListOf()
) {
    init {
        if (name.isBlank()) throw IllegalArgumentException("Name cannot be blank")
        if (instructor.isBlank()) throw IllegalArgumentException("Instructor cannot be blank")
    }
}
