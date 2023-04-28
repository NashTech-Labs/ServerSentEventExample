# Server Sent Events with Spring Webflux
In this template we have implemented the working of Server Sent Events.

* SSE is an HTTP standard that allows a web application to handle a unidirectional event stream and receive updates whenever server emits data.
* Server-Sent Events (SSE) is a standard that enables Web servers to push data in real time to clients.

## Setup

openjdk 17

* Step-1: Run the application.
* Step-2: make a POST request.
```json
{
    "id": 1,
    "name": "Aasif",
    "email": "aasif@gmail.com"
}
```
 * open the terminal and run this command to get the data.
```shell
curl -i http://localhost:8080/users/stream
```

