import services.FitnessCenterManager
import java.util.*

fun showMenu(){
    println("\n🎉 **Project Management System** 🎉")
    println("1. Register Member")
    println("2. Schedule Fitness Classes")
    println("3. Enroll in Class")
    println("4. Class Attendance Tracking")
    println("5. Membership Renewal")
    println("6. List Classes by Date")
    println("7. Exit")
    print("Please select an option: ")
}


fun main() {

    val fcm = FitnessCenterManager()
    val scanner = Scanner(System.`in`)
    var selectedMenu = 0

    while (selectedMenu != 7) {
        showMenu()
        try {
            selectedMenu = scanner.nextInt()
            scanner.nextLine()
            when (selectedMenu) {
                1 -> {
                    println("\n👤 **Register Member**")
                    println("Member Name: ")
                    val name: String = scanner.nextLine()

                    println("Choose your membership Tiers [Basic, Premium, VIP]: ")
                    val membershipType: String = scanner.nextLine()

                    fcm.registerMember(name, membershipType)
                }
                2 -> {
                    println("\n📅 **Schedule Fitness Classes**")
                    println("Class Name: ")
                    val name: String = scanner.nextLine()

                    println("Class Instructor: ")
                    val instructor: String = scanner.nextLine()

                    println("Class Date[yyyy-MM-dd]: ")
                    val date: String = scanner.nextLine()

                    println("Class Time[HH:mm:ss]: ")
                    val time:String = scanner.nextLine()

                    println("Class Duration [0-60 minutes]: ")
                    val duration:Int = scanner.nextInt()
                    scanner.nextLine()

                    println("Class Max Participants: ")
                    val maxParticipants:Int = scanner.nextInt()
                    scanner.nextLine()

                    println("Class Type [ Regular | Special | Exclusive]: ")
                    val classType = scanner.nextLine()

                    fcm.scheduleFitnessClasses(name, instructor, date, time, duration, maxParticipants, classType)
                }
                3 -> {
                    println("\n📋 **Enroll in Class**")
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
                    println("\n📊 **Class Attendance Tracking**")
                    println("Member ID: ")
                    val memberId: Int = scanner.nextInt()
                    fcm.classAttendanceTracking(memberId)
                }
                5 -> {
                    println("\n🔄 **Membership Renewal**")
                    println("Do you want to renew your membership? [Y|N] ")
                    val renew: String = scanner.nextLine();
                    if (renew == "Y") {
                        println("Member ID: ")
                        val memberId: Int = scanner.nextInt()
                        scanner.nextLine()
                        fcm.membershipRenewal(memberId)
                    } else if (renew == "N") {
                        println("❌ Successfully cancelled your renewal")
                    } else {
                        println("❌ Please enter either Y or N")
                    }
                }
                6 -> {
                    println("\n📅 **List Classes by Date**")
                    println("Class Start Date[yyyy-MM-dd]: ")
                    val startDate: String = scanner.nextLine()

                    println("Class End Date[yyyy-MM-dd]: ")
                    val endDate: String = scanner.nextLine()

                    fcm.listClassesByDate(startDate, endDate)
                }
                7 -> {
                    println("👋 Exiting...")
                }
                else -> {
                    println("❌ Invalid option. Please select a valid menu.")
                }
            }
        } catch (e: Exception) {
            scanner.nextLine()
            println("Error: ${e.message}")
        }
    }
}

