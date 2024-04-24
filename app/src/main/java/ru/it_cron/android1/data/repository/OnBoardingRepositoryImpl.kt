package ru.it_cron.android1.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.it_cron.android1.domain.repository.OnBoardingRepository

private const val PREF_NAME = "onboarding_pref"
private const val KEY_NAME = "onboarding_key"
val Context.datastore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

class OnBoardingRepositoryImpl(
    context: Context,
) : OnBoardingRepository {

    private object PreferenceKey {
        val onBoardingKey = booleanPreferencesKey(KEY_NAME)
    }

    private val dataStore = context.datastore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferenceKey.onBoardingKey]?: false
                onBoardingState
            }
    }
}