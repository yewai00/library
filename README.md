# library

## env
- jdk 17
- mysql 8

## setup
- open terminal and type
- > cd D:
- > git clone https://github.com/yewai00/library.git
- open your mysql client and create schema name "booklib"
- open project file and Run D:\library\src\main\resources\database\migration.sql file
- Run D:\library\src\main\resources\database\sedding.sql
- In project folder: D:\library\src\main\resources\application-dev.yml
- change your database username and password
- open terminal and type that cmd to run project
- > ./mvnw spring-boot:run
  
## documentation
- api documentation at http://localhost:8080/swagger-ui/index.html
- when you create book coverImg should be base64 image