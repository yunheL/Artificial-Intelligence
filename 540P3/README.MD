### 11/2 3:46pm
initial commit. Let's try to get this done as soon as possble! :)

### 11/13 7:59pm
initial set up done. We got this! :)

### 11/14 2:45am
finished 60% of Search Point class. Still need to work on g() method and compareTo() method.

After finish the search point class can move on the next half section.

### 11/14 3:29am
Finished CompareTo method

### 11/14 4:55am
Finished raw implementation. Time to debug and re-implement

### 11/14 6:54am
Raw implementation

### 11/14 10:57pm
First time partially worked

### 11/14 11:53PM
NullPointException ressolved. Realize that the bug is with g() function. Currently the g() is always giving the value 0. And that is because the bug in line 338. Where we try to add things in to the SPpath list. The newly created SearchPoint always have the prev field as null so nothing could be added into the list.
