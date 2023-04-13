package fr.mjoudar.oqee.data.network

import fr.mjoudar.oqee.domain.dto.MoviesItem
import retrofit2.Response
import retrofit2.http.GET

/***************************************************************************************************
 * ModuloService interface - to handle our HTTP Api
 ***************************************************************************************************/
interface MovieService {

    @GET("movies.json")
    suspend fun getData(): Response<List<MoviesItem>>

}