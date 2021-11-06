```
@GeneratedValue
```

Supported Strategies are..
* IDENTITY
* SEQUENCE
* TABLE

#### IDENTITY
The IDENTITY type (included in the SQL:2003 standard) is supported by:
* Oracle 12c
* SQL Server
* MySQL (AUTO_INCREMENT)
* DB2
* HSQLDB

The ***IDENTITY*** generator allows an integer/bigint column to be auto-incremented on demand.
The increment process happens outside of the current running transaction,
so a roll-back may end-up discarding already assigned values (value gaps may happen).<br>
<br>
The increment process is very efficient since it uses a database internal 
lightweight locking mechanism as opposed to the more heavyweight transactional course-grain locks.<br>

**DrawBacks**<br>
We can’t know the newly assigned value prior to executing the INSERT statement.
This restriction is hindering the ***transactional write-behind*** strategy adopted by Hibernate.
For this reason, Hibernates cannot use JDBC batching when persisting entities that are using the IDENTITY generator.<br>
<br>
IDENTITY generator strategy doesn’t work with the ***TABLE_PER_CLASS*** inheritance model because there could be multiple 
subclass entities having the same identifier and a base class query will end up retrieving entities with the same 
identifier (even if belonging to different types).
#### SEQUENCE
The SEQUENCE generator (defined in the SQL:2003 standard) is supported by:
* Oracle
* SQL Server 2012
* PostgreSQL
* DB2
* HSQLDB

A ***SEQUENCE*** is a database object that generates incremental integers on each successive request.
***SEQUENCES*** are much more flexible than IDENTIFIER columns because:
* A SEQUENCE is table free and the same sequence can be assigned to multiple columns or tables
* A SEQUENCE may preallocate values to improve performance
* A SEQUENCE may define an incremental step, allowing us to benefit from a ***“pooled” Hilo algorithm***
* A SEQUENCE doesn’t restrict Hibernate JDBC batching and inheritance models

#### TABLE
This is another database independent alternative to generating sequences.
One or multiple tables can be used to hold the identifier sequence counter.
But it means trading write performance for database portability.<br>
<br>

While ***IDENTITY*** and ***SEQUENCES*** are transaction-less, using a database table mandate ***ACID***,
for synchronizing multiple concurrent id generation requests.<br>
<br>

This is made possible by using row-level locking which comes at a higher cost than 
IDENTITY or SEQUENCE generators.<br>
<br>

The sequence must be calculated in a separate database transaction and this requires 
the IsolationDelegate mechanism, which has support for both local (JDBC) and global(JTA) transactions.
* For local transactions, it must open a new JDBC connection, therefore putting more pressure on the current connection pooling mechanism.
* For global transactions, it requires suspending the current running transaction. After the sequence value is generated, the actual transaction has to be resumed. This process has its own cost, so the overall application performance might be affected.

#### Conclusion
So, based on your application requirements you have multiple options to choose from. There isn’t one single winning strategy,
each one having both advantages and disadvantages.

#### Other key concepts which are related to this annotation
**Transactional write-behind**<br>
Calling session.save(entity) in the code does not cause an immediate sql insert to be fired. 
Similarly session.delete(entity) or session.update(entity) will not result immediately in the execution 
of the sql delete or update queries being fired.<br>
<br>
When objects associated with the persistence context are modified, they are not immediately propagated to the database.<br>
<br>
What Hibernate does is instead collect all such database operations associated with a transaction, creating the minimum set of sql queries and executing them. 
This gives us two advantages..
* Every property change in the entity does not cause a separate sql update to be executed.
* Avoiding unwanted sql queries ensures minimum trips to database thus reducing the network latency

In case of multiple updates/inserts or deletes, Hibernate is able to make use of the JDBC Batch API to optimize performance.<br>
<br>
This delayed execution of sql queries is known as transactional write behind.
On calling a transaction.commit(), Hibernate flushes all the sql to the database.<br>
<br>
**Hilo Algorithm**<br>
HiLo algorithm is applicable when you need unique keys. Let us understand with an example..<br>
<br>
Let us say you need unique identifiers for the rows in a table. You immediately go for sequence. 
Let us say, I am having ***4 nodes*** that interact with ***1 DB***. I have implemented a ***L1 cache*** on these nodes. 
The purpose of this cache is to store the values and write it to DB later **(using writebehind technique)**. 
I have a table that stores the rows of the users registered to my website. I obviously need an unique id.
But I am not going to hit DB for each and every registration happened. Instead I am going to store them 
in cache and write behind logic would take care of dumping these values into DB every ***N minutes***. 
But still I need a unique ID right?<br>
<br>
I have created a ***sequence S*** in the DB that starts with ***1***. 
So every time a new user registers into my system, I can get the unique key by getting the sequence 
***nextVal()*** in DB. But you see, I don't like to hit DB everytime just for a sequence. 
Instead I can do this in the cache itself (initialize a counter and increment this counter 
every time an user registers). But since I have ***N nodes***, I cannot make sure that all the N nodes 
generate unique keys.(Sure I can have the counter in the distributed cache but that is whole another 
story). This is where the HiLo algorithm comes into picture.<br>
<br>

We have...<br>
<br>
**A sequence S in the DB with initial value**<br>
**high = S.currentVal()**<br>
**low= 101 (some arbitrary number)**<br>
<br>
**Now**<br>
<br>
**increment the sequence S.**<br>
**Calculate the lower bound = high * low**<br>
**and the higher bound = lower bound + low -1**<br>
<br>
So the node should use the ID from lower bound to upper bound.<br>
once it runs out of it, this procedure has to be repeated again.<br>
<br>
***For Example***<br>
<br>
lets say S=1 <br>
we have 3 nodes N1, N2 and N3<br>
and low = 101<br>
<br>
***For N1***<br>
  high = 1<br>
  low =101<br>
  S=2 (increment the sequence)<br>
  Lower bound= high x low = 1 x 101 = 101<br>
  Higher bound = lower bound + low -1 = 101 + 101 -1 = 201<br>
  <br>
***For N2***<br>
  high =2<br>
  low = 101 <br>
  S=3 (increment the sequence)<br>
  Lower bound= high x low = 2 x 101 = 202<br>
  Higher bound = lower bound + low -1 = 202 + 101 -1 = 302<br>
<br>
***For N3***<br>
  high =3<br>
  low = 101<br>
  S=4 (increment the sequence)<br>
  Lower bound= high x low = 3 x 101 = 303<br>
  Higher bound = lower bound + low -1 = 303 + 101 -1 = 403<br>
  <br>
**As you can see the algorithm assigns unique identifiers to the nodes.**<br>
<br>
**N1 - (101,201)**<br>
**N2 - (202,302)**<br>
**N3 - (303,403)**<br>
<br>
Once any of the nodes reaches it upper bound, it will repeat the algorithm again 
to get the range and since S has been incremented everytime we run the algorithm,
you will get the unique identifiers everytime.<br>
<br>
For eg lets say N1 has used up all the keys,<br>
S = 5 (increment the sequence)<br>
high = 4<br>
low =101<br>
<br>
Lower bound= high x low = 4 x 101 = 404<br>
Higher bound = lower bound + low -1 = 404 + 101 -1 = 504<br>
<br>
Now the range is (404,504)<br>
<br>
There are many other criterion to consider. Like what would happen if you choose to change(increase or decrease) the value of low now? 
How do you choose this low value?<br>
<br>
When you decrease the value of low, collision occurs. i.e. the range assigned after changing the value of low might collide with 
the already calculated range before the value of low was changed.<br>
Refer [Once a HiLo is in use, what happens if you change the capacity (maximum Lo)?](https://stackoverflow.com/questions/3084119/once-a-hilo-is-in-use-what-happens-if-you-change-the-capacity-maximum-lo) for more details..<br>
<br>
Can you think of any disadvantages in this approach?
currently we have <br>
**N1 - (101,201)**<br>
**N2 - (202,302)**<br>
**N3 - (303,403)**<br>
<br>
Consider the case where N1 has used up 5 Ids (101 to 105). Now we are restarting the node N1. 
So this restarted node, again calls the HiLo algorithm and gets the range and everything is fine. 
But what happened to (106 to 201)? Under such cases a big hole is left and significant values are wasted.<br>
<br>
Refer to these links to know more:<br>
[What's a good MaxLo value for the HiLo algorithm?](https://stackoverflow.com/questions/5938231/whats-a-good-maxlo-value-for-the-hilo-algorithm)<br>
[What's the Hi/Lo algorithm?](https://stackoverflow.com/questions/282099/whats-the-hi-lo-algorithm)<br>








