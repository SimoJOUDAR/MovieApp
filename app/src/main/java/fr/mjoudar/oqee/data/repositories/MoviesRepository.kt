package fr.mjoudar.oqee.data.repositories

import fr.mjoudar.oqee.data.network.ApiClient
import javax.inject.Inject

class MoviesRepository@Inject constructor( private val apiClient : ApiClient) {

    // Retrieve a Response object from our Api
    suspend fun getData() = apiClient.getData()

}