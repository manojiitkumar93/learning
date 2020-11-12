### Introduction
A single request from the client point of view might be forwarded through many different services and it is possible that one of thee services is down 
because of a failure, maintenance or just might be overloaded , which causes an extremely slow response to client requests coming into the system.

**For example**, consider a e-commerce site that is overloaded with requests on peak period, and the vendor providing the payment operations goes offline for a few seconds due to heavy traffic. The users begin to see long wait times for their checkouts due to the high concurrency of requests. These conditions also keep all of the application servers clogged with the threads that are waiting to receive a response from the vendor. After a long wait time, the eventual result is a failure.

Such events lead to abandoned carts or users trying to refresh or retry their checkouts, increasing the load on the application servers—which already have long-waiting threads, leading to network congestion.

A **circuit breaker** is a simple structure that constantly remains vigilant, monitoring for faults. In the above-mentioned scenario, the circuit breaker identifies long waiting times among the calls to the vendor and fails-fast, returning an error response to the user instead of making the threads wait. Thus, the circuit breaker prevents the users from having a very sub-optimal response time.

### Recovery in a circuit breaker
A circuit breaker takes care of tripping the dependencies at the appropriate time. However, a more sophisticated system needs to continue the vigilance to determine if the dependency is available, and if so to close the circuit again to let dependent calls go through.

This behavior can be achieved in two ways:
1. Allow all calls to go through during a regular time interval and check for errors.
2. Allow one single call to go through at a more frequent rate to gauge the availability.

**Type 1** is the case where the circuit is closed without any proof of recovery, relying on errors to identify an issue where as **Type 2** is a more sophisticated mechanism as it does not allow multiple calls to go through because the calls might take a long time to execute and still fail. Rather, allowing  only a single call ensures more frequent execution, enabling faster closure of the circuit and revival of the system.

### Ideal circuit breaker
A harmonious system is one where we have an ideal circuit breaker, real-time monitoring, and a fast recovery variable setup, making the application truly resilient.

*Circuit Breaker + Real-time Monitoring + Recovery = Resiliency*


Using the example of the e-commerce site from above, with a resilient system in place, the circuit breaker keeps an ongoing evaluation of the faults from the payments processor and identifies long wait times or errors from the vendor. On such occurrences, it breaks the circuit, failing fast. As a result, users are notified of the problem and the vendor has enough time to recover.

In the meantime, the circuit breaker also keeps sending one request at regular intervals to evaluate if the vendor system is back again. If so, the circuit breaker closes the circuit immediately, allowing the rest of the calls to go through successfully, thereby effectively removing the problem of network congestion and long wait times.
