package services

import entity.FitnessClass
import entity.Member
import enum.ClassType
import enum.MembershipType
import java.time.LocalDate

class FitnessCenterManager {

    val fitnessClasses = mutableListOf<FitnessClass>()
    val members = mutableListOf<Member>()

    fun registerMember(name:String, membershipType: String){
        val membershipType = when (membershipType.toLowerCase()) {
            "basic" -> MembershipType.Basic
            "premium" -> MembershipType.Premium
            "vip" -> MembershipType.VIP
            else -> {
                println("================================================================")
                println("Please input a valid membership tiers [Basic, Premium, and VIP]")
                println("================================================================")
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
            println("'${newMember.name}' has been sucessfully registered as a member with ID: ${newMember.id}.")
            println("================================================================")
    }

    fun scheduleFitnessClasses(name:String, instructor:String, date:String, time:String, duration:Int, maxParticipants:Int){

    }

    fun enrollInClass(memberId:Int, fitnessClassId:List<Int>){
        val member = members.find { it.id == memberId }

        if (member == null) {
            println("================================================================")
            println("Member with ID $memberId not found.")
            println("================================================================")
            return
        }
        val accessLevel = member.membershipType

        fitnessClassId.forEach { classId ->
            val fitnessClass = fitnessClasses.find { it.id == classId }

            if (fitnessClass == null) {
                println("================================================================")
                println("Class with ID $classId not found.")
                println("================================================================")
                return
            }

            val fitnessClassType = fitnessClass.classType

            when (accessLevel) {
                MembershipType.Basic -> {
                    if (fitnessClassType == ClassType.Regular) {
                        println("================================================================")
                        println("Enrolled in regular class: $classId")
                        println("================================================================")
                    } else {
                        println("================================================================")
                        println("Cannot enroll in class $classId. Basic members can only access regular classes.")
                        println("================================================================")
                    }
                }
                MembershipType.Premium -> {
                    if (fitnessClassType == ClassType.Regular || fitnessClassType == ClassType.Special) {
                        println("================================================================")
                        println("Enrolled in class: $classId")
                        println("================================================================")
                    } else {
                        println("================================================================")
                        println("Cannot enroll in class $classId. Premium members can only access regular and special classes.")
                        println("================================================================")
                    }
                }
                MembershipType.VIP -> {
                    println("================================================================")
                    println("Enrolled in class: $classId")
                    println("================================================================")
                }
                else -> {
                    println("================================================================")
                    println("Unknown membership type: $accessLevel")
                    println("================================================================")
                }
            }
        }
    }

    fun classAttendanceTracking(memberId: Int){

    }

    fun membershipRenewal(memberId: Int){

    }

    fun listClassesByDate(date:String, time:String){

    }
}