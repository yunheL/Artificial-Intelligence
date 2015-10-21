### 10/19 3:13am
6/9 implemented, infinite loop exists
B function passed test, most other function seems suffer from same infinite loop problem.

### 10/20 1:41am
6/9 implemented, infinite loop bug solved. (because did not update counter in a while loop)
B function special cases B(0),B(1) fixed

### 10/21 1:06am
7/9 implemented, finished recursiveBudidingTree
used a lot of time to debug the following part:
```
tree.addChild(YEA);
tree.addChild(NAY);
		
YEA = tree.getChild(0);
NAY = tree.getChild(1);
```
for some reason you have to do both but I haven't figure why yet. Going to put more discussion in it.

### 10/21 3:40am
refined based case for recursiveBuidlingTree()
finished predict function
