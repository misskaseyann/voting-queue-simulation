# voting-queue-simulation
A live simulation of people in a voting queue with updating statistics.

![alt text](http://i63.tinypic.com/2hrl9pz.gif "Example of the visualization.")

Inputs include:
* Seconds to next person.
* Average seconds for check-in.
* Total time in seconds.
* Average seconds for voting.
* Seconds before person leaves.
* Number of booths.

Statistics cover:
* Total throughput.
* Average length in the central queue.
* Number of deserters.
* Average wait for each check-in table for every type of voter (regular, limited, special, super-special).
* Average wait for the central queue for every type of voter (regular, limited, special, super-special).
* Average total wait for every type of voter (regular, limited, special, super-special).

Different voters have different characteristics and can be identified by the color of their shirt. Some of their unique differences include greater or lesser patience for waiting in line, faster or slower time to vote, and more.
