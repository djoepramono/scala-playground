# Fold

Fold is basically going through a collection's item and perform an operation
on them.

```
val myList = List(1,2,3,4,5)

val result = myList.foldLeft(0) {
  (x,y) => {
    println(s"${x} + ${y} is ${x+y}")
    x + y
  }
}

// result = 15
```

Basically the above code goes like this
- start with an integer 0
- add 0 with the left most element in the list (1)
- then add the result to next element on the right (2)
- and so on ...
- continue this until the last integer

There are three kinds of folds, the only difference is just the direction of
of the operation

- `fold left`, starts from the left to the right
- `fold right`, perform the operation from the right to the left
- `fold` , perform the operation based on the order of how the elements are added.
Which means this is pretty similar to fold left,
as the elements are mostly added from the left
