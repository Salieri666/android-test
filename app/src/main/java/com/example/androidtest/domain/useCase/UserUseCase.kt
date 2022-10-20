package com.example.androidtest.domain.useCase

import android.content.Context
import com.example.androidtest.data.repo.UserRepo
import com.example.androidtest.data.room.entity.UserCacheEntity
import com.example.androidtest.di.module.IoDispatcher
import com.example.androidtest.domain.exception.NetworkConnectionException
import com.example.androidtest.domain.utils.ConnectHelper
import com.example.androidtest.domain.utils.LocationConverter
import com.example.androidtest.ui.model.UserUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserUseCase @Inject constructor(
    private val userRepo: UserRepo,
    private val connectHelper: ConnectHelper,
    private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    companion object {
        const val CACHED = "CACHED"
    }

    fun getAllUsers(update: Boolean = false): Flow<List<UserUiModel>> = flow {
        if (!checkIfCached() || update) {

            if (!connectHelper.isConnected())
                throw NetworkConnectionException("Connection problem")

            val users = userRepo.getUserListFromApi()
            userRepo.updateCache(users)
            markAsCached()
        }

        emit(userRepo.getUserListFromCache())
    }
        .map { list ->
            list.map { toUiModel(it) }
        }.flowOn(ioDispatcher)


    suspend fun getUserDetails(userId: Long): UserUiModel = withContext(ioDispatcher) {
        val user = userRepo.getUserById(userId)
        val userFriends = userRepo.getUserFriendsByOuterId(user.outerId).map { toUiModel(it) }

        return@withContext toUiModel(user, userFriends)
    }

    private fun checkIfCached(): Boolean {
        val sharedPref = context.getSharedPreferences(CACHED, Context.MODE_PRIVATE)
        return sharedPref.getBoolean(CACHED, false)
    }

    private fun markAsCached() {
        val sharedPref = context.getSharedPreferences(CACHED, Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            putBoolean(CACHED, true)
            apply()
        }
    }

    private fun convertDate(date: String): String {
        val formatterFrom = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss ZZZZZ")

        val currentLocale: Locale = context.resources.configuration.locales[0]
        val dateTime = ZonedDateTime.parse(date, formatterFrom)
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy", currentLocale)
        return dateTime.format(formatter)
    }

    private fun toUiModel(user: UserCacheEntity, userFriends: List<UserUiModel> = emptyList()): UserUiModel {
        val locationConverter = LocationConverter()

        val latitude = locationConverter.latitudeAsDMS(user.latitude, 2)
        val longitude = locationConverter.longitudeAsDMS(user.longitude, 2)

        return UserUiModel(
            user.id,
            user.outerId,
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
            convertDate(user.registered),
            userFriends,
            "$latitude $longitude",
            user.latitude,
            user.longitude
        )
    }

}