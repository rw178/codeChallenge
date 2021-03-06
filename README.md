# codeChallenge

Below are some notes regarding the classes and design decisions
### Classes
####  `MemoryMessagingFactory`
* the action of adding receivers/senders and shutting down share the same lock. If the shutdown function did not share the lock, it could lead to a `Topic` missing a shutdown command (see below for a better alternative)
* `waitForMessages()` - adding a `isBusyProcessing` flag to `Topic` together with the existing `shouldBeShuttingDown` flag can provide 
a mechanism to verify that there are no more unprocessed messages in any `Topic`. If the approach using queues below is implemented, the 
queue sizes can be used to check if there are still unprocessed messages.
####  `Topic`
* the publishing and subsequent processing of a message (as well as the updating of the receiver list) 
share the same lock. Since the in memory implementation is to be used purely for testing, 
the implementation was kept as simple as possible to enable easy debugging and testing.
<br>If performance does become a requirement (e.g. to test throughput) the actions
should be decoupled; e.g. messages could be pushed to a topic specific queue where "processing" threads would then read them from 
and push to the receivers.
* see below for an approach to elimate the locking required when shutting down
### Other
* if a more enterprise grade like solution is needed, classes from `java.net` such as `MulticastSocket` etc. could be used
* adding a "shutdown" `Message` (together with a timeout) could be an effective way to signal to all processes to shut down - this removes the locking required when shutting down (see above).
* it might be useful to investigate if `Message` could be made immutable
* code coverage was checked via Intellij
