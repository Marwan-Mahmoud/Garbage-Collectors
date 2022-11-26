# Garbage-Collectors
- [Mark & Sweep GC](Garbage%20Collectors/src/MarkAndSweep.java)
- [Mark & Compact GC](Garbage%20Collectors/src/MarkAndCompact.java)
- [Copy GC](Garbage%20Collectors/src/Copy.java)
### Input
- File heap.csv : this is a comma separated file with three columns. Each line represents the information about a single allocated object. This object may be used or not used.
  - *object-identifier* : a unique 6 digits identifier of the allocated objects.
  - *memory-start* : the index of the first byte in heap memory representing this object.
  - *memory-end* : the index of the last byte in heap memory representing this object.
- File roots.txt : this is a text file that lists object-identifiers that are currently in use. Any object that can not be reached directly or indirectly from objects listed in this file should be considered as a garbage. Each line in this file contains a single object-identifier.
- File pointers.csv : this file stores the dependencies between different objects. It is a comma separated file with two columns
  - *parent-identifier* : a unique identifier for the parent object.
  - *child-identifier* : a unique identifier for the child object referenced by the parent.
### Output
- File new-heap.csv : this is a comma separated file with the same structure of the heap.csv showing the new memory layout after running the garbage collector.
