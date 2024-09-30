package entity
import enum.MembershipType
import java.time.LocalDate

class Member (
    val id: Int,
    var name: String,
    var membershipType: MembershipType,
)