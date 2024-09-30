package services

import entity.FitnessClass
import entity.Member
import enum.ClassType
import enum.MembershipType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class FitnessCenterManager {

    val fitnessClasses = mutableListOf<FitnessClass>()
    val members = mutableListOf<Member>()

    fun registerMember(name:String, membershipType: String){

    }

    fun scheduleFitnessClasses(name:String, instructor:String, inputtedDate:String, inputtedTime:String, duration:Int, maxParticipants:Int, inputtedClassType: String){
        val date: LocalDate = try {
            LocalDate.parse(inputtedDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid date format. Please enter a date in the format yyyy-mm-dd.")
        }

        val time: LocalTime = try {
            LocalTime.parse(inputtedTime, DateTimeFormatter.ISO_LOCAL_TIME)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid time format. Please enter a time in the format HH:mm:ss.")
        }

        if (!isClassOverlapping(date, time, duration)) {
            if (isValidClassType(inputtedClassType)) {
                val classType = ClassType.valueOf(inputtedClassType)
                val newFitnessClass = FitnessClass(fitnessClasses.size+1, name, instructor, date, time, duration, maxParticipants, mutableListOf(), classType)
                fitnessClasses.add(newFitnessClass)
                println("==============================================================")
                println("'${newFitnessClass.name}' Class  has been created with ID: ${newFitnessClass.id}.")
                println("==============================================================")
            } else {
                throw IllegalArgumentException("Invalid class type: $inputtedClassType. Must be one of ${ClassType.values().joinToString()}.")
            }
        } else {
            println("Class cannot be scheduled because it overlaps with an existing class.")
        }

    }

    fun enrollInClass(memberId:Int, fitnessClassId:List<Int>){

    }

    fun classAttendanceTracking(memberId: Int){

    }

    fun membershipRenewal(memberId: Int){

    }

    fun listClassesByDate(inputtedStartDate: String, inputtedEndDate: String){
        val startDate: LocalDate = try {
            LocalDate.parse(inputtedStartDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid date format. Please enter a date in the format yyyy-mm-dd.")
        }

        val endDate: LocalDate = try {
            LocalDate.parse(inputtedEndDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid date format. Please enter a date in the format yyyy-mm-dd.")
        }

        if (startDate.isAfter(endDate)) {
            println("Start date cannot be after end date.")
            return
        }

        val classesInRange = fitnessClasses.filter { it.date.isAfter(startDate.minusDays(1)) && it.date.isBefore(endDate.plusDays(1)) }
        if (classesInRange.isEmpty()) {
            println("No fitness classes scheduled between $inputtedStartDate and $inputtedEndDate.")
        } else {
            println("Fitness Classes scheduled between $inputtedStartDate and $inputtedEndDate:")
            for (fitnessClass in classesInRange) {
                val availableSpots = fitnessClass.maxParticipants - fitnessClass.enrolledMembers.size
                println("Class Name: ${fitnessClass.name}")
                println("Instructor: ${fitnessClass.instructor}")
                println("Date: ${fitnessClass.date}")
                println("Time: ${fitnessClass.time}")
                println("Duration: ${fitnessClass.duration} mins")
                println("Available Spots: $availableSpots")
            }
        }

    }

    fun isValidClassType(classType: String): Boolean {
        return try {
            ClassType.valueOf(classType)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun isClassOverlapping(date: LocalDate, startTime: LocalTime, duration: Int): Boolean {
        val endTime = startTime.plusMinutes(duration.toLong())

        for (existingClass in fitnessClasses) {
            if (existingClass.date == date) {
                if ((startTime.isBefore(existingClass.time.plusMinutes(existingClass.duration.toLong())) && endTime.isAfter(existingClass.time)) ||
                    (existingClass.time.isBefore(endTime) && existingClass.time.plusMinutes(existingClass.duration.toLong()).isAfter(startTime))
                ) {
                    return true
                }
            }
        }
        return false
    }
}