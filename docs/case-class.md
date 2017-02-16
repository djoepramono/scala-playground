# Case Class

Case class is pretty much the same as regular classes, except
- it is immutable
- it can be pattern matched
- it can be instantiated without `new` syntax

This is extremely useful in data modelling world when you are doing
perfoming an operation on a specific data structure.
You can break down data into pieces with traits and abstract classes.  

When not to use? When it contains complex behaviour or stateful computations.
In other words, just use this to model data not operations

## Abstract class

It is similar to Object Oriented programming,
a class can be extended/inherited from one abtract classes.
Abstract class can have constructor.

## Traits

Basically it serves the same purpose as abstract classes,
but a class can inherit from multiple trait
at the cost of you can put a constructor there.

So basically traits is Scala way of dealing with multiple inheritance.
Though again in it's more data modelling not object modelling.
