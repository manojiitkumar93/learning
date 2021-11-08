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
