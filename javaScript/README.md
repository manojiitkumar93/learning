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

* Six
```
var manoj = [];
manoj[1] = 123;
manoj.length;

--> Result : 2
--> Meaning as we have directly defined 2 element of the array bu defualt the first element is undefined;

manoj[0];

--> Result : undefined
```

* Seven
```
const [manoj, kumar] = [true, false];
manoj
--> Result : true
kumar
--> Result : false

const [manoj, kumar] = [true];
manoj
--> Result : true
kumar
--> Result : undefined

*const* --> Its like *final* keyword in java;
```

* Eight
```
const mano = true;
mano = 1
--> Result : Syntax error, meaning we are not allowed to do it as *mano* is defined as *const*

const kumar = {first:1};
kumar.first = 2;
kumar.second = 3;
kumar
--> Result : {first:2, second:3}, meaning we are allowed to change the property values and add new properties.
```
