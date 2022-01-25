# event listener

Acts as an application that comes with one the listeners that subscribes to the events published on Kafka cluster for topic - "response-status".
These are the events that help track status of the response received for calls to the endpoints provided to request-processor api. 

For more details on how it is utilised as part of entire system refer `smaato/README.md`


### Approach

- Utilised kafka consumer api in order to receive and listen to response status published on kafka cluster in real time.
 This also gives the flexibility to end and attach streams to this system for further processing or actions if required for future requirements
- As of now listener logs  status received in container location which is configured in application.yml.


## How to use?

event-listener is part of the docker-compose file thus doesn't need separate set up to start logging response status.
The logs can be found under the location - response-status-logs.txt (can be configured via application.yml)
