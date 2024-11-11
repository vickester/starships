# Starships API

This is a REST API built with Spring Boot and written in Kotlin that fetches the most expensive starships from the SWAPI API. 🚀

# Getting started

### Prerequisites
- **Java 17**
- **Spring boot 3.3.5** 
- **Maven 3.9.9**

### Installation 
Clone this repo to your local machine. Make sure you have all required dependencies installed as mentioned in the list above. 

### Run the application
Open the terminal, go into the folder starships and run this command:
```
mvn spring-boot:run
```

Now you can visit http://localhost:8080/api/starships to explore the most expensive starships from all Star Wars movies! 

# Endpoints 

### /api/starships
This endpoint returns the starships with the default sorting of the most expensive starship first. 

**Request params** 
`limit` - limits the number of starships being returned. Default is 10.  


