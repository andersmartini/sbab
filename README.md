Thanks for considering me (Anders Martini) for employment!

In this repository I've chosen to use Spring Boot combined with RxJava for 
a reactive approach that in the real world would perform better, 
by making all IO async.
Of course, since the task specifically detailed not to
install any database, this repo uses in-memory storage instead,
making any performanceboost an async approach could deliver moot.
Still, I wanted to demonstrate my familiarity with the concept,
so I used it anyway.

Due to poor planning on my part, I won't have time to implement batching at all, which is kinda
ironic given that this will affect theoretical performance of this application
far more than asyncIO does. 

