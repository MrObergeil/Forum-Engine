package at.fhj.ima.forumengine.forumengine.entity

import javax.persistence.*

enum class UserRole {
    ROLE_USER,
    ROLE_MOD,
    ROLE_ADMIN
}

@Entity
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var userId: Int? = null,
        var username: String,
        var password: String,
        @Enumerated(EnumType.STRING)
        var role: UserRole
) {
}