package com.example.starships.service

import com.example.starships.dto.StarshipDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class StarshipService(private val restTemplate: RestTemplate) {
    // getMostExpensiveStarships fetches the starships from the swapi api and
    // returns them in order most expensive with the specified limit
    fun getMostExpensiveStarships(limit:Int): List<StarshipDTO> {
        val starShips = getStarships()
         if (starShips.isEmpty()) {
            return starShips
         }

        return starShips.sortedByDescending { it.cost_in_credits!!.toLongOrNull() ?: 0 } // Make cost to long and sort descending
             .take(limit) // Take the first objects based on the limit
    }

    // getStarships gets all starships from the swapi api
    fun getStarships(): List<StarshipDTO> {
        try {
            var url = "https://swapi.dev/api/starships/"
            var starships = listOf<StarshipDTO>()
            while (true){
                val responseEntity: ResponseEntity<StarshipDTOResponse> = restTemplate.getForEntity(url, StarshipDTOResponse::class.java)
                starships = starships + responseEntity.body?.results?.toList().orEmpty()
                val nextLink = responseEntity.body?.next
                if (nextLink != null) {
                    url = nextLink
                } else {
                    // If next link is null it's the last page, break loop.
                    break
                }
            }
            return starships
        } catch (e: Exception) {
            throw Exception(e.message, e)
        }
    }
}

data class StarshipDTOResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<StarshipDTO>
)
