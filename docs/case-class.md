# Case Class

Case class is pretty much the same as regular classes, except
- it is immutable
- it can be pattern matched
- it can be instantiated without `new` syntax

This is extremely useful in data modeling world when you are doing
performing an operation on a specific data structure.
You can break down data into pieces with traits and abstract classes.  

When not to use? When it contains complex behavior or stateful computations.
In other words, just use this to model data not operations

## Abstract class

It is similar to Object Oriented programming,
a class can be extended/inherited from one abstract classes.
Abstract class can have constructor.

## Traits

Basically it serves the same purpose as abstract classes,
but a class can inherit from multiple trait.
This is at the cost of you cannot put a constructor there.

So basically traits is Scala way of dealing with multiple inheritance.
Though again in it's more data modeling not object modeling.
