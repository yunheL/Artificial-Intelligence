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

### 10/21 5:08am
Finished all implementation 
need to clean up code

### 10/21 8:23pm
fixed "TIE handling bug" solution is added one more base case for recursive build tree:
```
if(all 16 attribute has been checked)
	return plurality;
```
The reason on adding this base case can be found in the algorithm on textbook page 703.
The way to detect an attribute has already been checked is to try to split on the attribute again on
the current node (split based on YEA or NAY). If one of the branch has no elements in it, then it
means either the current dataset is homogenuous or the current attribute has already been checked.
Loop through all the 16 attribute to check the 16 attributes.
Code cleaned
final version

### Conclude the project at 8:28pm 10/21/2015
