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
        try {
            selectedMenu = scanner.nextInt()
            scanner.nextLine()
            when (selectedMenu) {
                1 -> {
                    println("Member Name: ")
                    val name: String = scanner.nextLine()

                    println("Choose your membership Tiers [Basic, Premium, VIP]: ")
                    val membershipType: String = scanner.nextLine()

                    fcm.registerMember(name, membershipType)
                }
                2 -> {
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
                3 -> {
                    println("Member ID: ")
                    val memberId: Int = scanner.nextInt()

                    val fitnessClassId = arrayListOf<Int>()
                    do {
                        for (x in fcm.fitnessClasses){
                            println("ID: ${x.id} Name:${x.name} Instructor: ${x.instructor} Date: ${x.date} Time: ${x.time} Duration: ${x.duration} Enrroled Members: ${x.enrolledMembers.size} Max Participants: ${x.maxParticipants} Class Type: ${x.classType}")
                        }
                        println("Fitness Class ID [0 to exit]: ")
                        val temp = scanner.nextInt()
                        if(temp == 0) break
                        fitnessClassId.add(temp)
                    } while(true)
                    fcm.enrollInClass(memberId, fitnessClassId)
                }
                4 -> {
                    println("Member ID: ")
                    val memberId: Int = scanner.nextInt()
                    fcm.classAttendanceTracking(memberId)
                }
                5 -> {
                    println("Member ID: ")
                    val memberId: Int = scanner.nextInt()
                    fcm.classAttendanceTracking(memberId)
                    fcm.membershipRenewal(memberId)
                }
                6 -> {
                    println("Class Date: ")
                    val date: String = scanner.nextLine()

                    println("Class Time: ")
                    val time: String = scanner.nextLine()

                    fcm.listClassesByDate(date, time)
                }
                7 -> {
                    println("Exiting...")
                }
                else -> {
                    println("Invalid option. Please select a valid menu.")
                }
            }
        } catch (e: NumberFormatException) {
            scanner.nextLine()
            println("Error: Invalid number format. Please enter a valid integer.")
        } catch (e: Exception) {
            scanner.nextLine()
            println("Error: ${e.message}")
        }
    }
}

