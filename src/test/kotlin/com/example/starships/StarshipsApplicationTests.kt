package com.example.starships

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
)
class StarshipsApplicationTests(@Autowired val client : TestRestTemplate){

	@Test
	fun `test list starships without specified limit, should use default limit 10`() {
		val result = client.getForEntity("/api/starships", Array<Starship>::class.java)
		val starships: List<Starship> = result.body?.toList() ?: emptyList()

		assertEquals(HttpStatus.OK, result.statusCode, "expect status OK")

		val expectedStarships = listOf(
			Starship("Death Star", "1000000000000"),
			Starship("Executor", "1143350000"),
			Starship("Star Destroyer", "150000000"),
			Starship("Trade Federation cruiser", "125000000"),
			Starship("Calamari Cruiser", "104000000"),
			Starship("Republic attack cruiser", "59000000"),
			Starship("Banking clan frigte", "57000000"),
			Starship("Scimitar", "55000000"),
			Starship("EF76 Nebulon-B escort frigate", "8500000"),
			Starship("CR90 corvette", "3500000")
		)

		assertEquals(10, starships.size, "expect 10 starships")
		assertEquals(expectedStarships, starships, "expect sort by cost")
	}

	@Test
	fun `test list starships with limit 1`() {
		val result = client.getForEntity("/api/starships?limit=1", Array<Starship>::class.java)
		val starships: List<Starship> = result.body?.toList() ?: emptyList()

		val expectedStarships = listOf(
			Starship("Death Star", "1000000000000")
		)
		assertEquals(expectedStarships, starships, "expect one starship")
	}

	@Test
	fun `test list starships with limit as a negative number, should return bad request`() {
		val result = client.getForEntity("/api/starships?limit=-5", Array<Starship>::class.java)
		assertEquals(HttpStatus.BAD_REQUEST, result.statusCode, "expect status bad request")
	}
}

data class Starship(
	val name: String,
	val cost_in_credits: String,
)

data class StarshipResponse(val results: Array<Starship>)