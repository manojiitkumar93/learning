## Types of primary keys
All database tables must have one primary key column. 
The primary key uniquely identifies a row within a table therefore it’s bound by the following constraints..
*  UNIQUE
*  NOT NULL
*  IMMUTABLE

#### Points to consider while choosing primary keys
* Primary key may be used for joining other tables through a foreign key relationship
* Primary key usually has an associated default index, so the more compact the data type the less space the index will take
* Simple key performs better than a compound one
* Primary key assignment must ensure uniqueness even in highly concurrent environments

#### Primary key generator types
* **Natural keys**, using a column combination that guarantees individual rows uniqueness
* **Surrogate keys**, that are generated independently of the current row data

#### Natural Keys
Natural key's uniqueness is enforced by external factors (e.g.social security numbers, vehicle identification numbers).<br>

Natural keys are convenient because they have an outside world equivalent and they don’t require any extra database processing.
We can therefore know the primary key even before inserting the actual row into the database, 
which simplifies batch inserts.<br>

If the natural key is a single numeric value the performance is comparable to that of surrogate keys. 
Non-numerical keys are less efficient than numeric ones (integer, bigint), for both indexing and joining.

#### Surrogate keys
Surrogate keys are generated independently of the current row data, so the other column constraints may freely 
evolve according to the application business requirements.<br>

The database system may manage the surrogate key generation and most often the key is of a numeric type
(e.g. integer or bigint), being incremented whenever there is a need for a new key.<br>

If we want to control the surrogate key generation we can employ a 128-bit GUID or UUID. 
This simplifies batching and may improve the insert performance since the additional database 
key generation processing is no longer required.

When the database identifier generation responsibility falls to the database system,
there are several strategies for auto incrementing surrogate keys:

| Database engine | Auto incrementing strategy |
| ------ | ------ |
| Oracle | SEQUENCE |
| MSSQL  | IDENTITY, SEQUENCE |
| MySql  | AUTO_INCREMENT  |
| PostgreSQL | 	SEQUENCE, SERIAL TYPE|
|DB2	|IDENTITY, SEQUENCE|
|HSQLDB|	IDENTITY, SEQUENCE|

**NOTE: Using 128-bit GUID or UUID is advantageous over Surrogate keys**
* https://blog.pythian.com/case-auto-increment-mysql/
* https://blog.codinghorror.com/primary-keys-ids-versus-guids/