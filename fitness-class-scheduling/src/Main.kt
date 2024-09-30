import services.FitnessCenterManager
import java.util.*

fun menus(){
    println("**Project Management System**")
    println("1. Register Member")
    println("2. Schedule Fitness Classes")
    println("3. Enroll in Class")
    println("4. Class Attendance Tracking")
    println("5. Membership Renewal")
    println("6. List Classes by Date")
    println(">> ")
}


fun main() {

    val fcm = FitnessCenterManager()
    val scanner = Scanner(System.`in`)
    var selectedMenu = 0

    while (selectedMenu != 7) {
        menus()
        selectedMenu = scanner.nextInt()
        scanner.nextLine()
        if (selectedMenu == 1) {
            println("Member Name: ")
            val name: String = scanner.nextLine()

            println("Choose your membership Tiers [Basic, Premium, VIP]: ")
            val membershipType: String = scanner.nextLine()

            fcm.registerMember(name, membershipType)
        }
        if (selectedMenu == 2){
            println("Class Name: ")
            val name: String = scanner.nextLine()

            println("Class Instructor: ")
            val instructor: String = scanner.nextLine()

            println("Class Date: ")
            val date: String = scanner.nextLine()

            println("Class Date: ")
            val time:String = scanner.nextLine()

            println("Class Duration: ")
            val duration:Int = scanner.nextInt()

            println("Class Max Participants: ")
            val maxParticipants:Int = scanner.nextInt()

            fcm.scheduleFitnessClasses(name, instructor, date, time, duration, maxParticipants)
        }
        if (selectedMenu == 3){
            println("Member ID: ")
            val memberId: Int = scanner.nextInt()

            val fitnessClassId = arrayListOf<Int>()
            do {
                for (x in fcm.fitnessClasss){
                    println("ID: ${x.id} Name:${x.name}")
                }
                println("Fitness Class ID: ")
            }
            fcm.enrollInClass(memberId, fitnessClassId)
        }
        if (selectedMenu == 4){
            fcm.classAttendanceTracking()
        }
        if (selectedMenu == 5){
            fcm.membershipRenewal()
        }
        if (selectedMenu == 6){
            fcm.listClassesByDate()
        }
    }
}

