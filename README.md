# OVERVIEW

### 1. Working Principle
### 2. Class Descriptions
### 3. How to run


## Working Principle

The data structure is based on the Segment Tree. However the segment tree is dynamic where a node is only generated when an insert query is made for that node. The range of values considered are from 0 to LONG.MAX - 1, which includes all possible timestamps.

For each node of the Segment Tree, an agrregate of data is stored, that is for each error code (key) the max, min, sum and count are stored. When inserting a new log entry, a severity for the particular error code is inserted in a top down approach from the root to the leaves.

### Additional Points

The increasing order of the timestamps is not a necessity here, logs can be replayed and older ones can be inserted.

The approach supports aggregates within a random range, l to r, and is not restricted to prefixs (BEFORE) or suffixes (AFTER). This could be usedful to finding statistics within some event like an outage with a defined start and end time.

Having tested with the RandomGenerator, this approach works well for a random list of 2e6 entries, facing OOM at 3e6

### Complexity

#### a. Time Complexity

1. Insert - Each insert involves a top down approach from root to leaves, **O(log(Long.MAX_VALUE))** complexity
2. Query - Each range query also involves a top down traversal, hence also **O(log(Long.MAX_VALUE))** complexity

#### b. Space Complexity

Each inserted log entry changes a path of nodes from root to leaf. The length of such a path is O(log ( Long.MAX_VALUE )). Therefor the maximum memory is **O(log(Long.MAX_VALUE ) * NUM_ENTRIES)** where NUM_ENTRIES is the number of inserted log entries


## Class Descriptions

#### SingleDataNode

This is a data node which contains the data values for each error code. Has fields max, min, sum and count. Methods are provided to merge two data nodes and update the values accordingly

#### AggregateDataNode

This the the node that is present in the nodes of the Segment Tree. This contains a map with error codes as key and SingleDataNodes and values. Contains methods to add a key and SingleDataNode as well as merge two AggregateDataNode. This merging is required in the Segment Tree

#### SegTree

This is the main class performing all the operations. The class is recursively defined with a AggregateDataNode data member as well as left and right references to its children

##### 1. Insert

Insert takes as input a timestamp (ts), error code (key) and severity. A new SingleDataNode is created and the SegTree is updated in a top down manner. If current node contains aggregate values for range tl to tr. Then left child has aggregates for range tl to mid and right child for range mid + 1 to tr. mid is (tl+tr)/2.

The left and right nodes are created during insert

##### 2. Query

Supports general range queries given a left range l and right range r. For the spefific use cases, l is set to 0 and/or r is set to Long.MAX_VALUE - 1. Each query proceeds in a top down manner if the range contained in the current node matches the query node, then the aggregate is directly used, else the input range is clipped within the left and right children and the query proceeds recursively.

Filter By Errorcode - A single default SingleDataNode is generated and this same object is aggregated with all SingleDataNodes found in a top down query. This conserves memory

Filter All Errorcodes - A single default AggregateDataNode is generated and this same object is aggregated with all SingleDataNodes found in a top down query. This conserves memory


#### Logger

This handle all the code related to parsing inputs, outputs and handling empty results. Contains methods parseInput and parseBySpace to parse the queries. Contains methods to format the AggregateDateNode and SingleDataNode into a String with the provided format. Handles the case when an empty result is returned

insert and query process the query as a String and outputs a String.
handleQuery parses the input String and based on first character does the required operation.

Contains methods to take input a list of queries and write the outputs to file (writeQueriesToFile). Also contains methods to take input and output filenames, read from input file and write to output file (readFromFile)

#### RandomGenerator

This class generates a list of random queries where the number of queries is provided as input. MAX and MIN SEV denothe the range of possible severities. DAYS_IN_FUTURE denotes the days.

For generating a single query, the index is chosen randomly, if required the timestamp, type (BEFORE/AFTER) and error codes are chosen randomly.

#### Test

This class is just for testing the code, pipes input from an input file and pipes the output to the output file


## How to run

### Running locally

```bash

javac Curieo/Test.java
java Curieo/Test < input.txt > output.txt

```

input.txt should contain the inputs in current working directory
output.txt will be generated in current working directory

### Running docker image

The docker daemon should be up and running, the docker image has been pushed to public dockerhub registry

```bash

chmod +x execute.sh
./execute.sh input.txt output.txt

```

input.txt should contain the inputs in current working directory
output.txt will be generated in current working directory


input.txt contains sample input, output.txt contains sample output
input1.txt contains 300 randomly generated queries, output1.txt contains associated output


