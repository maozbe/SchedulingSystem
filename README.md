# SchedulingSystem
Design of a simple workforce management scheduling system.
The system contains multiple field service engineers which are available for an assignment(customer visit requests).
The assignments received from a CRM system and its demonsrated by assignment generator 
(optional TODO: make the generator send assignment to Kafka/RabitMQ queue and then retrieve the assignmt from the queue)

The flow:
The whole logic happens inside ScheduleManager.
First it generate the employer(Engineer Obj) & Assignment.
Then its enter a while loop the doing the next stages:
1. Update the availabilty of the employers.
2. Randomaly generate a new missions and add them to the quque.
3. Set assignments to employers by the availability & closets distance.
4. Clean the assignment queue from mission that got complete.

Set assignments to employer ThreadPool:
* The Engineer class implemets Runnable interface.
	when employer get a new mission the executer use it.


Generating random object:
* For Assignment: the code randomly choosing location from LocationEnum (optional TODO: use api call to retreive all the cities in specific country),
	also the average time for mission is generated randomly and can be between 1-4 sec.

* For Engineer: the code randomly choosing starting location from LocationEnum, also the startShift & endShift time.
