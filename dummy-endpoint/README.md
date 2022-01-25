# dummy-endpoint api

Helps to test the request-processor api by exposing a post end point that accepts requests in below form:

 Here uniqueRequestsCount is the count forwarded by request-processor.

`curl --location --request POST 'http://192.168.1.5:1000/dummy/statistics' \
--header 'Content-Type: application/json' \
--data-raw '{"uniqueRequestsCount":2}' `

Note that this application is not part of docker-compose, thus will need to be started separately if required.
Console prints the external ip that can be used to access this application.