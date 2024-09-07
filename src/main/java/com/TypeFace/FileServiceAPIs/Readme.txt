Simplified Dropbox-Like File Storage Service

This project is a simple file storage service,
similar to Dropbox, built using Java, Spring Boot, and MySQL.
It allows users to upload, retrieve, update, and delete files through REST APIs.
Files are stored on the local filesystem, while metadata (such as file name, size, and upload time) is stored in a MySQL database.

_____________________________________________________________________________________________________________
Features
Upload File: Users can upload files to the platform.
The file is saved on the server, and information about the file is stored in a database.

1st API: To Upload the file
Endpoint: POST /files/upload
Input: File binary data (using form-data in Postman)
Output: A unique file identifier (UUID) for the uploaded file
Retrieve File: Users can retrieve a file by its unique identifier.
Response: {
            "message": "File uploaded successfully with ID: abc12345-def6-7890-ghij-klmnopqrstuv"
          }


2nd API: To retrieve the file
Endpoint: GET /files/{fileId}
Input: Unique file identifier (UUID)
Output: The file's binary data, ready for download
Update File: Users can update an existing file by uploading a new version of it.
Response: The file's binary data, downloadable as an attachment.

3rd API: To Update the file, change the data of the existing file
Endpoint: PUT /files/{fileId}
Input: Unique file identifier (UUID) and new file binary data
Output: Success message if the update is successful
Delete File: Users can delete a file by its unique identifier.
Response: {
            "message": "File updated successfully"
          }


4th API: Delete the file
Endpoint: DELETE /files/{fileId}
Input: Unique file identifier (UUID)
Output: Success message if the deletion is successful
List Files: Users can list all files stored in the system along with their metadata.
Response: {
            "message": "File deleted successfully"
          }


5th API: Showcase all the APIs present
Endpoint: GET /files
Input: Not required
Output: A list of all files' metadata (file name, upload time, etc.)


_____________________________________________________________________________________________________________
Technologies Used:

Backend: Java with Spring Boot
Database: MySQL for storing file metadata
Storage: Local filesystem for storing the actual file content

_____________________________________________________________________________________________________________
Set Up MySQL:

Create a MySQL database (ex: drop_box, typeface).
Update the application.properties file with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/drop_box
spring.datasource.username=your_username (eg: "root" in my case)
spring.datasource.password=your_password (eg: "root" in my case)
spring.jpa.hibernate.ddl-auto=update

_____________________________________________________________________________________________________________
Configure File Storage Directory:
Specify the directory where files will be stored in the application.properties file:
file.upload-dir=E:/project files/Dropbox storage

_____________________________________________________________________________________________________________
I have used in the project:

UUID: Each file is uniquely identified by a UUID, which ensures that even if files have the same name, they are stored and managed separately.
Error Handling: If a file is not found, the system returns a 404 Not Found status with a message indicating that the file is missing.
Local Storage: Files are stored in a directory on the server's filesystem. Ensure that the directory specified in application.properties exists and is writable.