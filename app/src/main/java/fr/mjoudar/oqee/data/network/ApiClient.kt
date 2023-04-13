package fr.mjoudar.oqee.data.network


import fr.mjoudar.oqee.domain.dto.MoviesItem
import retrofit2.Response

/***************************************************************************************************
 * ApiClient class - to handle Api calls
 ***************************************************************************************************/
class ApiClient (private val movieService : MovieService) {

    suspend fun getData() : SimpleResponse<List<MoviesItem>> {
        return safeApiCall { movieService.getData() }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}