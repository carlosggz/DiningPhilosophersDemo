# The dining philosophers problem

Example of a solution for the dining philosophers problem using java concurrency, based 
on the solution explained at https://www.baeldung.com/java-dining-philoshophers.

This example uses an executor service to control the execution of the threads and
a cancellation token (using an atomic boolean) to limit the time of execution. Then,
it displays a small statistic of the execution.

The logic is based on the use of two synchronized blocks, to get access to the forks,
and to avoid the deadlock due to a circular wait, it changes the conditions for the 
last philosopher.