* One
```
var person = {};
person[0] = "manoj";
person.hello = "hello";
console.log(person);

--> Result : {0:"manoj", hello:"hello"}
```

* Two
```
var person = [];
if(typeof person === "array"){
console.log("SSSSSSSSSSSSSSSSSSs");
} else{
console.log("NNNNNNNNNNN");
}

--> Result : NNNNNNNNNNN

--> This concludes that array also is an object in Js but with some extra methods to it like .push(), .pop() and so on..
```

* Three
```
var obj = {};
obj[function(){}] = "hello";
typeof Objects.keys(obj)[0];

--> Result : 'String'

--> Meaning the value *function(){}* is internally stringified and considered as a string.
```

* Four
```
var obj = {};
obj[(function(){return 3;})()] = "hello";
Objects.keys(obj)[0];

--> Result : 3

--> Meaning *(function(){return 3;})()* is an fucntion which is evaluated and returned *3*. Hence *3* i considered as a key here.
```

* Five
```
var manoj = [];
manoj[0] = true;
manoj.length;

--> Result : 1

manoj.hello = "hello";
manoj.length;

--> Result : 1 
--> Meaning array behaves as an object. By doing *manoj.hello*, we are actually adding one property the *manoj* object
```
