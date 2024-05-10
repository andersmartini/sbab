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

I've also aggressively limited the scope here, in a way I would not in a real working situation.
For example this solution does not support adding new transactions after classification.

it's also missing some really low-hanging fruit functionalities, such as being able to apply a custom 
classification to all transactions to a given recipientId. This would be quite easy to add
but <s>my friends just arrived and want me to light up the BBQ</s> Anna Dahlen says we have
to prioritize other functionality for now. ;)

Testing is also very minimal, especially considering that this is a banking environment. Tests verifying 
at least a few failure modes is minimal in any production code, but for a codingtest like this, i hope you'll forgive this shortcut.
