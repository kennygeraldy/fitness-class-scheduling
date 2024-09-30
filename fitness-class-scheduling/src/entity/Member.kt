package entity
import enum.MembershipType
import java.time.LocalDate

class Member (
    val id: Int,
    var name: String,
    var membershipType: MembershipType,
    var subscriptionStartDate: LocalDate,
    var subscriptionEndDate: LocalDate,
    val attendedClasses: MutableList<FitnessClass> = mutableListOf()
) {
    init {
        if (name.isBlank()) {
            throw IllegalArgumentException("Member name must not be empty.")
        }
    }
}