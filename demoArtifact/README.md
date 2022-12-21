# Receipt Application
## Description
The application is designed to manage store discount cards, manage goods in a warehouse, as well as generate and display sales receipts, taking into account the formation of a price with a discount from a discount card and a discount for wholesale


## Features
- CRUD operation for Discount_card and Products 
   (opportunity getAll, saveAll, deleteAll)
- get, save, delete for Receipt  
  ** I didn’t do the update operation, 
     since in practice sales receipts are not changed in stores
- update support @Version (Version - LocalDateTime)
- UUID as an ID parameter
- the ability to receive a sales receipt as an Excel file
- custom Exception
- custom Pageable
- the project was written with the expectation of dividing into microservices and further expansion

## Tech
Finance application uses next technologies:
- Java 17
- Spring Boot
- Hibernate
- PostgreSQL
- Gradle
- Apache POI
- Open API
- RESTFUL


