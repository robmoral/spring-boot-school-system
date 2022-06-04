
#TO BUILD AND RUN THE PROJECT:

1. mvn clean package
2. Move to docker directory
cd docker
3. docker-compose up --build

#TO CHECK THE STATUS OF THE SERVICE
http://localhost:8081/actuator/health

#SWAGGER DOCS
http://localhost:8081/swagger-ui/index.html#/


COURSES API:

1. GET COURSES: - Get Courses
   curl --location --request GET 'http://localhost:8081/api/courses'

1 a) GET COURSES - Get courses that does not have any student enrolled.
   curl --location --request GET 'http://localhost:8081/api/courses?onlyEmptyCourses=true'

2. GET COURSE: - Get Course by id
   curl --location --request GET 'http://localhost:8081/api/courses/1'
   
3. POST COURSE - Create course
curl --location --request POST 'http://localhost:8081/api/courses' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "course2",
    "description": "desc2"
}'

4. PUT COURSE - Update course
curl --location --request PUT 'http://localhost:8081/api/courses/2' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "newCoursename",
"description":"newDescription"
}'

5. DELETE COURSE - Delete course.
curl --location --request DELETE 'http://localhost:8081/api/courses/2'
   
6. GET STUDENTS OF A COURSE
curl --location --request GET 'http://localhost:8081/api/courses/4/students'



STUDENTS API: 

1. GET STUDENTS: - Get Students
curl --location --request GET 'http://localhost:8081/api/students'

1 a) GET STUDENTS- No courses assigned
curl --location --request GET 'http://localhost:8081/api/students?onlyStudentsNotAssigned=true'

2. GET STUDENT: - Get Student by id
curl --location --request GET 'http://localhost:8081/api/students/1'
   
//We assume this api does not need to handle the case of creating a student and assign courses on the same request
3. POST STUDENT: - Create Student

curl --location --request POST 'http://localhost:8081/api/students' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "name",
    "email": "email@email.com"
}'

//We assume this api does not need to handle the case of update a student and assign courses on the same request
4. PUT STUDENT - Update Student
curl --location --request PUT 'http://localhost:8081/api/students/3' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "name3-upd",
    "email": "email3-upd@email.com"
}'

5. DELETE STUDENT- Delete student
curl --location --request DELETE 'http://localhost:8081/api/students/1'

6. ENROLL STUDENT - Add course
curl --location --request POST 'http://localhost:8081/api/students/2/courses/1' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "name": "course2",
    "description": "desc2"
}'

7. GET COURSES OF A STUDENT - Get courses of a student
curl --location --request GET 'http://localhost:8081/api/students/2/courses'

8. ENROLL STUDENT - Remove course
curl --location --request DELETE 'http://localhost:8081/api/students/3/courses/3'

