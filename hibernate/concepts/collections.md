### Hibernate Collections
Hibernate has its own internal data types that it maps to for the collection objects we are creating. And it 
has some scematics for the collection objects.
* Bag scemantic ---> List / Array List

This **Bag Scemantic** means you can put elements in any order and retrive them in any order. This does not 
consider the Order in whihc you put the objects. This **Bag Scemantic** can be implemented by using  List / ArrayList.

* Bag scemantic with ID  ---> List / Array List

This can be again used with List / ArrayList which have an index property. 

* List scemanti  ---> List / Array List

This is similar to the **Bag scamantic**, the only difference is that the elements are in order.

* Set Scemantic  ---> Set
* Map Scemantic  ---> Map

This Set and Map scemantics are similar to the java objects.<br>

So basically hibernate uses these one of the scemantics to store the collection of data.