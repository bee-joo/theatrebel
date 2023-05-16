package ru.theatrebel.data.production

data class ProductionDto(
    var directorsIds: List<Long>?,
    var actorsRoles: List<ActorRoleDto>?,
    var playId: Long?,
    var theatresIds: List<Long>?,
    var description: String?
)

data class ActorRoleDto(
    var actorId: Long?,
    var role: String?
)
