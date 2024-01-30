# Balance Sheet Import/export API

The Balance Sheet Import API is a Spring Boot application that facilitates the uploading and processing of Excel files containing balance sheet data. It allows users to upload Excel files containing balance detail information, which are then parsed and saved into a database for further processing.

## Features


- Excel File Upload: Users can upload Excel files containing balance sheet data via HTTP POST requests.
- Excel Parsing: The application parses the uploaded Excel files to extract balance detail information.
- Database Persistence: Parsed balance detail information is stored in a MySQL database for future retrieval and analysis.
- API Endpoints: The API provides endpoints for uploading Excel files and managing balance sheet data.

 ## Technologies Used
- Java 8
- Spring Boot 2.5.3
- Apache POI for Excel parsing
- MySQL for database storage
