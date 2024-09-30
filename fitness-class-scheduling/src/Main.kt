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
    fcm.Init()
    val scanner = Scanner(System.`in`)
    var selectedMenu = 0

    while (selectedMenu != 7) {
        menus()
        selectedMenu = scanner.nextInt()
        scanner.nextLine()
        if (selectedMenu == 1) {
            println("Member Name: ")
            val name: String = scanner.nextLine()

            if (name.isEmpty()) {
                println("Member name must not be empty.")
                continue
            }

            println("Choose your membership Tiers [Basic, Premium, VIP] ")
            val membershipType: String = scanner.nextLine()

            if (membershipType.isEmpty()) {
                println("Membership tiers  must not be empty.")
                continue
            }
            fcm.registerMember()
        }
        if (selectedMenu == 2){
            fcm.scheduleFitnessClasses()
        }
        if (selectedMenu == 3){
            fcm.enrollInClass()
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

