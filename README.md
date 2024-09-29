# DropBoxService
# Simplified Dropbox-Like File Storage Service

This project is a simple file storage service, similar to Dropbox, built using Java, Spring Boot, and MySQL. It allows users to upload, retrieve, update, and delete files through RESTful APIs. Files are stored on the local filesystem, while metadata (such as file name, size, and upload time) is stored in a MySQL database.

## Features

1. **Upload File**: Users can upload files to the platform. The file is saved on the server, and information about the file is stored in a database.
   - **Endpoint**: `POST /files/upload`
   - **Input**: File binary data (using `form-data` in Postman)
   - **Output**: A unique file identifier (UUID) for the uploaded file

2. **Retrieve File**: Users can retrieve a file by its unique identifier.
   - **Endpoint**: `GET /files/{fileId}`
   - **Input**: Unique file identifier (UUID)
   - **Output**: The file's binary data, ready for download

3. **Update File**: Users can update an existing file by uploading a new version of it.
   - **Endpoint**: `PUT /files/{fileId}`
   - **Input**: Unique file identifier (UUID) and new file binary data
   - **Output**: Success message if the update is successful

4. **Delete File**: Users can delete a file by its unique identifier.
   - **Endpoint**: `DELETE /files/{fileId}`
   - **Input**: Unique file identifier (UUID)
   - **Output**: Success message if the deletion is successful

5. **List Files**: Users can list all files stored in the system along with their metadata.
   - **Endpoint**: `GET /files`
   - **Input**: None
   - **Output**: A list of all files' metadata (file name, upload time, etc.)

## Technologies Used

- **Backend**: Java with Spring Boot
- **Database**: MySQL for storing file metadata
- **Storage**: Local filesystem for storing the actual file content

## Setup and Installation

1. **Set Up MySQL**:
   - Create a MySQL database (e.g., `dropbox_db`).
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/dropbox_db
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     spring.jpa.hibernate.ddl-auto=update
     ```

2. **Configure File Storage Directory**:
   - Specify the directory where files will be stored in the `application.properties` file:
     ```properties
     file.upload-dir=E:/project files/Dropbox storage
     ```

3. **Run the Application**:
   - Use Maven to build and run the application:
     ```bash
     mvn spring-boot:run
     ```

4. **Testing the APIs**:
   - Use Postman or any REST client to interact with the APIs. For example:
     - To upload a file, send a `POST` request to `http://localhost:8080/files/upload` with the file in `form-data`.
     - To retrieve a file, send a `GET` request to `http://localhost:8080/files/{fileId}`.

## Example API Requests

### 1. Upload a File
- **Request**: `POST /files/upload`
- **Body**: `form-data` with a key named `file`
- **Response**:
  ```json
  {
    "message": "File uploaded successfully with ID: abc12345-def6-7890-ghij-klmnopqrstuv"
  }
  ```

### 2. Retrieve a File
- **Request**: `GET /files/{fileId}`
- **Response**: The file's binary data, downloadable as an attachment

### 3. Update a File
- **Request**: `PUT /files/{fileId}`
- **Body**: `form-data` with a key named `file`
- **Response**:
  ```json
  {
    "message": "File updated successfully"
  }
  ```

### 4. Delete a File
- **Request**: `DELETE /files/{fileId}`
- **Response**:
  ```json
  {
    "message": "File deleted successfully"
  }
  ```

### 5. List All Files
- **Request**: `GET /files`
- **Response**: A JSON array containing metadata for all files

## Important Notes

- **UUID**: Each file is uniquely identified by a UUID, which ensures that even if files have the same name, they are stored and managed separately.
- **Error Handling**: If a file is not found, the system returns a `404 Not Found` status with a message indicating that the file is missing.
- **Local Storage**: Files are stored in a directory on the server's filesystem. Ensure that the directory specified in `application.properties` exists and is writable.

## Conclusion

This project provides a simple, yet effective, file storage service with basic CRUD operations. It’s a good starting point for building more complex file storage solutions and can be easily extended to include features like user authentication, versioning, and cloud storage integration.

---

This README should give anyone a good overview of your project, how to set it up, and how to use the provided APIs.
