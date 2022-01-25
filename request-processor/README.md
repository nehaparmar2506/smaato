# Request processor Service

Accepts the requests with given id parameter and forwards unique requests count for current minute to the requested end point.
Helps track the count of requests received per minute and response received from the end point by persisting/publishing to a common cache/distributed event queue so that other interested applications can listen and act on it.
 

## Approach 

This is a spring boot Java rest API with below features :
 - Deployed and shipped utilizing docker containers
 - Load balanced by ngnix to serve desired throughput and beyond. Currently, 2 instances of request processor are configured which can be changed based on requirement.
 - Integrated with redis cache as a source of truth for identifying unique requests for a minute between multiple instances.
 - Integrates with kafka cluster and acts as a producer to help forward endpoint response status eventually, having a distributed queue mechanism here enables to publish response (status) which could be 10k requests/sec.
 
Please find below implementation details on how a request is being processed -

1. Every request id received is persisted in redis cache in key, value form where key is current minute and value is a set of request ids.
2. These persisted data points can be utilised by other services. 
   For example in this case a scheduled job component ie logger which runs every minute and reads from cache and writes the count of unique requests to a log file.
3. After persisting to redis, async calls are made to the endpoint if provided and response status is sent as an event to a kafka topic thus avoiding any latency impact on accept end point.
4. These published event can be utilised to track the status by consumers, for demonstration purpose there is a consumer implemented as part of event-listener module.








