# AgodaHotelSearchAPI
This is a Rate Limited API project.

## Project Execution Instruction

JDK Required: 1.8+ 
 
You can run/test the project directly from intellij IDEA IDE,Or by using following commands.

## commands: 
 
To run the project :  mvn spring-boot:run 

To run all test cases:  mvn test 

To run specific test case:  

mvn -Dtest=HotelRepositoryTest test 

mvn -Dtest=WebClientIntegrationTest test 

mvn -Dtest=HotelServiceImplTest test 
 
## API calls: 
 
You can use Postman or any browser to see the outputs of these api endpoints. 
 
GET Hotels by city: http://localhost:8080/city/{city_name}/{optional_search_order} 
 
### Example: 

http://localhost:8080/city/bangkok/asc

http://localhost:8080/city/bangkok/desc 

http://localhost:8080/city/bangkok 
 
GET Hotels by room category: 
http://localhost:8080/room/{room_category}/{optional_search_order} 
 
### Example:

http://localhost:8080/room/Deluxe/asc 

http://localhost:8080/room/Deluxe/desc 
