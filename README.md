1. Run commands-
a. Run the Spring boot application with the java -jar command:
$ java -jar target/springSecurity-0.0.1-SNAPSHOT.jar

b. Run the Spring boot application using Maven:
$ mvn spring-boot:run

2. Curl Commands to Test Each of the Use Cases:

(i). To test the signup API having URL- http://localhost:8080/api/v1/auth/signup:
curl -X POST -d '{"firstName":"user1"}' -d '{"lastName":"user1LastName"}' -d '{"email":"user1@gmail.com"}' -d '{"password":"user1#123"}' http://localhost:8080/api/v1/auth/signup

Response: User object containing details of the saved user

(ii). To test the login API having URL- http://localhost:8080/api/v1/auth/login:
curl -X POST -d '{"email":"user1@gmail.com"}' -d '{"password":"user1#123"}' http://localhost:8080/api/v1/auth/login

Response: JWT token and Refresh token

Note: for incorrect email or password, user will get 403 Forbidden error

(iii). To test the refresh token API(used to get new JWT token when it got expired to avoid user login again) having URL- http://localhost:8080/api/v1/auth/refresh:
[Note: in body pass the refresh token that we received in response of login API]
curl -X POST -d '{"token": "RefreshTokenValue"}' http://localhost:8080/api/v1/auth/refresh

Response: new JWT token and Refresh token

(iv). for Role based(User, Admin) Authentication & Authorization, after login (pass the token value in Header)-
Users with 'USER' role can access only user URL- http://localhost:8080/api/v1/user

curl -X GET -H "Authorization: tokenValueOfUserLoginApi" http://localhost:8080/api/v1/user

Response: (custom message)- "Hi User!!"

(v). Users with 'ADMIN' role can access only admin URL- http://localhost:8080/api/v1/admin

curl -X GET -H "Authorization: tokenValueOfAdminLoginApi" http://localhost:8080/api/v1/admin

Response: (custom message)- "Hi Admin!!"
