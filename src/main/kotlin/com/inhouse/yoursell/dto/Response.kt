package com.inhouse.yoursell.dto

import com.inhouse.yoursell.entity.user.ERole

data class LoginResponseDto(
    val token: String
)

data class RoleDto(
    val name: ERole
)

data class UserDto(
    val id: Long,
    val username: String,
    val email: String,
    val userRoles: MutableSet<RoleDto>,
    val vehicles: MutableList<VehicleDto>
)

data class VehicleDto(
    var id: Long,
    var make: String,
    var model: String,
    var mileage: Double,
    var vin: String,
    var year: String,
    var expectedBid: Double,
    var damaged: Boolean,
    var sellerId: Long,
    var sellerUsername: String,
    var images: MutableList<String>
)