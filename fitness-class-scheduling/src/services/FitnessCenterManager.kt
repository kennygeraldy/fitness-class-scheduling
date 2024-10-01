package services

import entity.FitnessClass
import entity.Member
import enum.ClassType
import enum.MembershipType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class FitnessCenterManager {

    val fitnessClasses = mutableListOf<FitnessClass>()
    val members = mutableListOf<Member>()

    fun init(){
        val mem = Member(1,"Kenny",MembershipType.VIP,LocalDate.now().minusDays(35),LocalDate.now().minusDays(5))
        members.add(mem)
    }

    fun registerMember(name:String, membershipType: String){
        val membershipType = when (membershipType.toLowerCase()) {
            "basic" -> MembershipType.Basic
            "premium" -> MembershipType.Premium
            "vip" -> MembershipType.VIP
            else -> {
                println("===================================================================")
                println("❌ Please input a valid membership tier [Basic, Premium, or VIP].")
                println("===================================================================")
                return
            }
        }
        val newMember = Member(
            id = members.size + 1,
            name = name,
            membershipType = membershipType,
            subscriptionStartDate = LocalDate.now(),
            subscriptionEndDate = LocalDate.now().plusMonths(1)
        )
            members.add(newMember)
        println("================================================================")
        println(" ✅ Member Registered: ${newMember.name}")
        println(" 🔑 Membership ID: ${newMember.id}")
        println(" 📅 Subscription valid until: ${newMember.subscriptionEndDate}")
        println("================================================================")
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
                println(" ✅ Class Scheduled: ${newFitnessClass.name}")
                println(" 🔑 Class ID: ${newFitnessClass.id}")
                println(" 🏋️ Instructor: ${newFitnessClass.instructor}")
                println(" 📅 Date: ${newFitnessClass.date} | 🕒 Time: ${newFitnessClass.time}")
                println(" ⏳ Duration: ${newFitnessClass.duration} mins | 👥 Max Participants: ${newFitnessClass.maxParticipants}")
                println("==============================================================")
            } else {
                throw IllegalArgumentException("Invalid class type: $inputtedClassType. Must be one of ${ClassType.values().joinToString()}.")
            }
        } else {
            println("⚠️ Class cannot be scheduled because it overlaps with an existing class.")
        }
    }

    fun enrollInClass(memberId:Int, fitnessClassId:List<Int>){
        val member = members.find { it.id == memberId }

        if (member == null) {
            println("================================================================")
            println("❌ Member with ID $memberId not found.")
            println("================================================================")
            return
        }
        val accessLevel = member.membershipType

        fitnessClassId.forEach { classId ->
            val fitnessClass = fitnessClasses.find { it.id == classId }

            if (fitnessClass == null) {
                println("================================================================")
                println("❌ Class with ID $classId not found.")
                println("================================================================")
                return
            }

            val fitnessClassType = fitnessClass.classType

            when (accessLevel) {
                MembershipType.Basic -> {
                    if (fitnessClassType == ClassType.Regular) {
                        if (fitnessClass.enrolledMembers.size < fitnessClass.maxParticipants) {
                            fitnessClass.enrolledMembers.add(member)
                            markAttendance(memberId, classId)
                            println("================================================================")
                            println("✅ Enrolled in regular class: $classId")
                            println("================================================================")
                        } else {
                            println("⚠️ Class ${classId} is full. Cannot enroll.")
                        }
                    } else {
                        println("========================================================================================")
                        println("❌ Basic members can only access Regular classes. Class ID $classId is not available.")
                        println("========================================================================================")
                    }
                }
                MembershipType.Premium -> {
                    if (fitnessClassType == ClassType.Regular || fitnessClassType == ClassType.Special) {
                        if (fitnessClass.enrolledMembers.size < fitnessClass.maxParticipants) {
                            fitnessClass.enrolledMembers.add(member)
                            markAttendance(memberId, classId)
                            println("================================================================")
                            println("✅ Enrolled in premium class: $classId")
                            println("================================================================")
                        } else {
                            println("⚠️ Class ${classId} is full. Cannot enroll.")
                        }
                    } else {
                        println("======================================================================================================")
                        println("❌ Premium members can only access Regular and Special classes. Class ID $classId is not available.")
                        println("======================================================================================================")
                    }
                }
                MembershipType.VIP -> {
                    if (fitnessClass.enrolledMembers.size < fitnessClass.maxParticipants) {
                        fitnessClass.enrolledMembers.add(member)
                        markAttendance(memberId, classId)
                        println("================================================================")
                        println("✅ Enrolled in exclusive class: $classId")
                        println("================================================================")
                    } else {
                        println("⚠️ Class ${classId} is full. Cannot enroll.")
                    }
                }
            }
        }
    }

    fun classAttendanceTracking(memberId: Int) {
        val member = members.find { it.id == memberId }
        if (member != null) {
            if (member.attendedClasses.isNotEmpty()) {
                println("================================================================")
                println("📅 Classes attended by ${member.name}:")
                println("================================================================")
                for (x in member.attendedClasses) {
                    println("📍 ID: ${x.id} | Name: ${x.name} | Instructor: ${x.instructor}")
                    println(" 📅 Date: ${x.date} | 🕒 Time: ${x.time} | ⏳ Duration: ${x.duration} mins")
                    println(" Enrolled Members: ${x.enrolledMembers.size} | Max Participants: ${x.maxParticipants}")
                    println(" Class Type: ${x.classType}")
                    println("================================================================")
                }
            } else {
                println("================================================================")
                println("⚠️ ${member.name} has not attended any classes.")
                println("================================================================")
            }
        } else {
            println("================================================================")
            println("❌ Member with ID $memberId not found.")
            println("================================================================")
        }
    }

    fun membershipRenewal(memberId: Int){
        val member = members.find { it.id == memberId }
        if (member != null) {
            val today = LocalDate.now()
            val daysRemaining = ChronoUnit.DAYS.between(today, member.subscriptionEndDate)

            println("=======================================================================================")
            println("✅ Subscription Extended: Member '${member.name}' has been extended by 30 days.")
            if (daysRemaining >= 25){
                member.subscriptionEndDate = member.subscriptionEndDate.plusDays(35)
                println("✅ Subscription Bonus: Member '${member.name}' has been extended by extra 5 days.")
            } else{
                member.subscriptionEndDate = member.subscriptionEndDate.plusDays(30)
            }
            println("📅 New subscription end date: ${member.subscriptionEndDate}")
            println("=======================================================================================")
        } else{
            println("================================================================")
            println("❌ Member with ID $memberId not found.")
            println("================================================================")
        }
    }

    fun listClassesByDate(inputtedStartDate: String, inputtedEndDate: String){
        val startDate: LocalDate = try {
            LocalDate.parse(inputtedStartDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            throw IllegalArgumentException("⚠️ Invalid date format. Please enter a date in the format yyyy-mm-dd.")
        }

        val endDate: LocalDate = try {
            LocalDate.parse(inputtedEndDate, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            throw IllegalArgumentException("⚠️ Invalid date format. Please enter a date in the format yyyy-mm-dd.")
        }

        if (startDate.isAfter(endDate)) {
            println("❌ Start date cannot be after end date.")
            return
        }

        val classesInRange = fitnessClasses.filter { it.date.isAfter(startDate.minusDays(1)) && it.date.isBefore(endDate.plusDays(1)) }
        if (classesInRange.isEmpty()) {
            println("📅 No fitness classes scheduled between $inputtedStartDate and $inputtedEndDate.")
        } else {
            println("📅 Fitness Classes scheduled between $inputtedStartDate and $inputtedEndDate:")
            println("================================================================")

            for (fitnessClass in classesInRange) {
                val availableSpots = fitnessClass.maxParticipants - fitnessClass.enrolledMembers.size
                println("🏋️‍♂️ Class Name: ${fitnessClass.name}")
                println("👨‍🏫 Instructor: ${fitnessClass.instructor}")
                println("📅 Date: ${fitnessClass.date}")
                println("⏰ Time: ${fitnessClass.time}")
                println("⏳ Duration: ${fitnessClass.duration} mins")
                println("👥 Available Spots: $availableSpots")
                println("================================================================")
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

    fun markAttendance(memberId: Int, classId: Int) {
        val member = members.find { it.id == memberId }
        val fitnessClass = fitnessClasses.find { it.id == classId }

        if (member != null && fitnessClass != null) {
            if (!member.attendedClasses.contains(fitnessClass)) {
                member.attendedClasses.add(fitnessClass)
                println("=============================================================================")
                println("✅ Attendance marked for '${member.name}' in class '${fitnessClass.name}'.")
                println("=============================================================================")
            } else {
                println("=============================================================================")
                println("⚠️ ${member.name} has already attended the class '${fitnessClass.name}'.")
                println("=============================================================================")
            }
        } else {
            println("================================================================")
            println("❌ Invalid member ID ($memberId) or class ID ($classId).")
            println("================================================================")
        }
    }

    fun countDaysRemaining(memberId:Int){
        val member = members.find { it.id == memberId }
        if (member != null) {
            val today = LocalDate.now()
            val daysRemaining = ChronoUnit.DAYS.between(member.subscriptionEndDate, today)

            if (daysRemaining in 0..5) {
                println("==================================================================================")
                println("⚠️ Warning: Member '${member.name}' subscription ends in $daysRemaining day(s).")
                println("==================================================================================")
            }
        }
    }

}