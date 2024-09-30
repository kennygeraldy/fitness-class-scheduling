package services

import entity.FitnessClass
import entity.Member
import enum.MembershipType
import java.time.LocalDate

class FitnessCenterManager {

    val fitnessClasss = mutableListOf<FitnessClass>()
    val members = mutableListOf<Member>()

    fun registerMember(name:String, membershipType: String){

    }

    fun scheduleFitnessClasses(name:String, instructor:String, date:String, time:String, duration:Int, maxParticipants:Int){

    }

    fun enrollInClass(memberId:Int, fitnessClassId:List<Int>){

    }

    fun classAttendanceTracking(memberId: Int){

    }

    fun membershipRenewal(memberId: Int){

    }

    fun listClassesByDate(date:String, time:String){

    }
}