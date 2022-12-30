# tenant-manager
This is a back-end application that manages tenants.
Technologies:
- Java
- Spring Boot
- PostgreSQL

How To Run This Application:
- Create a database in PostgreSQL named "tenantmanager".
- Put the database access credentials in the corresponding fields
  in the application.properties file under the resources folder

You can use Postman to POST tenants as a JSON object in the body following the example format:

{
    "firstName": "John",
    "lastName": "Doe",
    "apartmentNumber": "1",
    "phoneNumber": 1112223333
}

