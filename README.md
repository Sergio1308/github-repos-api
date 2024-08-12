# GitHub Repository API
## Overview
The GitHub Repository API is a Spring Boot application that interacts with the GitHub API to retrieve information about a user's repositories. It filters out forked repositories and provides detailed information on branches and commits. The API handles various error scenarios and offers a structured response for different cases.
## Features
Retrieve Non-Forked Repositories: Lists all repositories for a given GitHub user that are not forks.
Branch Information: Provides branch names and commit SHAs for each repository.
Error Handling: Returns appropriate error responses for invalid requests or non-existing users.
## Prerequisites
- Java 21
- Spring Boot 3 with Spring WebFlux for asynchronous data processing
- Maven

## Setup Instructions
### Clone the Repository
```
git clone https://github.com/Sergio1308/repo.git
cd repo
```
### Configure Application Properties
Edit the src/main/resources/application.properties file to include your GitHub API access token:
```
github.api.token=your_github_api_token_here
```
### Build and Run
1) Build the Project:
```
mvn clean install
```
2) Run the Application:
```
mvn spring-boot:run
```
The application will start on http://localhost:8080

## Usage
### API Endpoints
- Get User Repositories:
```
GET /users/{username}/repos
```
Headers:
Accept: application/json

Response:

Success (200 OK): Returns a list of repositories with branch information.

Not Found (404 Not Found): Returns an error response if the user does not exist.
Example Request:
```
curl -H "Accept: application/json" http://localhost:8080/api/users/sergio1308/repos
```
Example Response:
```
[
  {
    "name": "application-manager-telegram-bot",
    "ownerLogin": "Sergio1308",
    "branches": [
      {
        "name": "dev",
        "commit_sha": "62b4f595064599b0d923aa0d882ecff423edf374"
      },
      {
        "name": "main",
        "commit_sha": "cf632272adff5f82a71780cff10b7b9850360e52"
      }
    ]
  }
]
```
### Error Responses
User Not Found (404 Not Found):
```
{
"status": 404,
"message": "User not found: {username}."
}
```
Unauthorized (401 Unauthorized):
```
{
"status": 401,
"message": "Provided GitHub access token is not valid or expired."
}
```
Forbidden (403 Forbidden):
```
{
"status": 403,
"message": "Unauthenticated requests have exceeded the rate limit. Please authenticate using your GitHub personal access token."
}
```
Internal Server Error (500 Internal Server Error):
```
{
"status": 500,
"message": "An unexpected error occurred. Please try again later."
}
```