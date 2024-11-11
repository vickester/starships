package com.example.starships.controller

import com.example.starships.dto.StarshipDTO
import com.example.starships.service.StarshipService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class StarshipController(private val starshipService: StarshipService) {

    @GetMapping("api/starships")
    fun getMostExpensiveStarships(@RequestParam(required = false) limit: Int?): ResponseEntity<List<StarshipDTO>> {
        // Validate limit
        var validatedLimit = 10 // Default limit is 10
        if (limit != null) {
            if (limit < 0) {
                return ResponseEntity.badRequest().body(emptyList()) // Return bad request if limit is less then 0
            }
            validatedLimit = limit
        }
        return try {
            ResponseEntity.ok(starshipService.getMostExpensiveStarships(validatedLimit))
        } catch (e: Exception) {
            // Return internal server error if any exception is thrown
            ResponseEntity.internalServerError().body(emptyList())
        }
    }
}
