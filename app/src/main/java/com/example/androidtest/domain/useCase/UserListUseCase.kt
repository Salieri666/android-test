package com.example.androidtest.domain.useCase

import android.location.Location
import com.example.androidtest.data.model.UserBody
import com.example.androidtest.data.repo.UserRepo
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.absoluteValue

@Singleton
class UserListUseCase @Inject constructor(
    private val userRepo: UserRepo
) {

    fun getUserList(): Flow<List<UserUiModel>> = flow {
        emit(userRepo.getUserList())
    }
        .map { list ->
            list.map { toUiModel(it) }
        }

    private fun toUiModel(user: UserBody): UserUiModel {
        val latitude = latitudeAsDMS(user.latitude, 2)
        val longitude = longitudeAsDMS(user.longitude, 2)

        return UserUiModel(
            user.id,
            user.guid,
            user.name,
            user.email,
            user.isActive,
            user.age,
            user.company,
            user.phone,
            user.address,
            user.about,
            user.eyeColor,
            user.favoriteFruit,
            user.registered,
            emptyList(),
            "$latitude $longitude"
        )
    }

    private fun latitudeAsDMS(latitude: Double, decimalPlace: Int): String {
        val direction = if (latitude > 0) "N" else "S"
        var strLatitude = Location.convert(latitude.absoluteValue, Location.FORMAT_SECONDS)
        strLatitude = replaceDelimiters(strLatitude, decimalPlace)
        strLatitude += " $direction"
        return strLatitude
    }

    private fun longitudeAsDMS(longitude: Double, decimalPlace: Int): String {
        val direction = if (longitude > 0) "W" else "E"
        var strLongitude = Location.convert(longitude.absoluteValue, Location.FORMAT_SECONDS)
        strLongitude = replaceDelimiters(strLongitude, decimalPlace)
        strLongitude += " $direction"
        return strLongitude
    }

    private fun replaceDelimiters(str: String, decimalPlace: Int): String {
        var str = str
        str = str.replaceFirst(":".toRegex(), "°")
        str = str.replaceFirst(":".toRegex(), "'")
        val pointIndex = str.indexOf(".")
        val endIndex = pointIndex + 1 + decimalPlace
        if (endIndex < str.length) {
            str = str.substring(0, endIndex)
        }
        str += "\""
        return str
    }
}