package com.sri.graphdatastructures;

import com.sri.datastructures.DisjointSet;
import com.sri.datastructures.MinPriorityQueue;
import com.sri.utility.KVPair;
import com.sri.datastructures.List;
import com.sri.datastructures.Queue;
import static com.sri.utility.StringUtilities.stringCompare;
import dataStructures.RBTree;
import java.util.Iterator;


public class Graph implements Comparable<Graph>, Iterable<Vertex<?>> {

    private List<Vertex<?>> vertices; //list of vertices
    private int time = 0;

    public Graph() {
        vertices = new List<>();
    }

    @Override
    public Iterator<Vertex<?>> iterator() {
        return vertices.iterator();
    }

    public void addVertex(Vertex<?> v) {
        vertices.add(v);
    }

    public Vertex<?> search(String l) {
        for (Vertex<?> v : vertices) {
            if (stringCompare(v.getVertexLabel(), l) == 0) {
                return v;
            }
        }
        return null;
    }

    public int getVertexCount() {
        return vertices.getCount();
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (Vertex<?> v : vertices) {
            outputStr += v.toString() + "\r";
        }
        return outputStr;
    }

    /*
     * Breadth first search functions
     */
    public void breadthFirstSeach(Vertex<?> s) {
        //reset the graph to an undiscovered state
        for (Vertex<?> tmpV : vertices) {
            tmpV.setParent(null); //parent is null
            tmpV.setDistance(Double.NEGATIVE_INFINITY); //represents infinity
            tmpV.setColor(VertexColor.WHITE_UNDISCOVERED); //undiscovered state
        }

        //since v is the start it is already "discovered"
        s.setColor(VertexColor.GREY_DISCOVERED);

        //distance is zero to itself
        s.setDistance(0.0);

        //search starts with s, so it is the root.
        s.setParent(null);

        //we will use a queue to process the adjaceny list
        Queue<Vertex<?>> q = new Queue<>();
        q.enqueue(s);

        while (!q.isEmpty()) {
            Vertex<?> u = q.dequeue();

            //iterate through the vertex's adjacency list
            for (Edge e : u) {
                Vertex<?> v = e.getV2();
                if (v.getColor() == VertexColor.WHITE_UNDISCOVERED) {
                    v.setColor(VertexColor.GREY_DISCOVERED);
                    v.setDistance(u.getDistance() + 1);
                    v.setParent(u);
                    q.enqueue(v);
                }
            }
            u.setColor(VertexColor.BLACK_PROCESSED);
        }
    }

    //prints the path from vertex 's' to vertex 'v' it assumes that breadth first search is already run 
    public void printPath(Vertex<?> s, Vertex<?> v, List<String> retStrings) {
        if (s == v && s.getVertexType() == v.getVertexType()) {
            retStrings.add(s.getVertexLabel());
        } else if (v.getParent() == null) {
            retStrings.add(("No path exists between " + v.getVertexLabel() + " and " + s.getVertexLabel()));
        } else {
            printPath(s, v.getParent(), retStrings);
            if (s.getVertexType() == v.getVertexType()) {
                retStrings.add(v.getVertexLabel());
            }
        }
    }

    /*
     * Depth first search functions
     */
    public void depthFirstSearch() {
        //reset the graph to an undiscovered state
        for (Vertex<?> u : vertices) {
            u.setParent(null); //parent is null
            u.setDistance(Double.NEGATIVE_INFINITY); //represents infinity
            u.setColor(VertexColor.WHITE_UNDISCOVERED); //undiscovered state
        }

        //initialize the time
        time = 0;

        //for each vertex, visit it using depth first serach algorithm
        for (Vertex<?> u : vertices) {
            if (u.getColor() == VertexColor.WHITE_UNDISCOVERED) {
                dfsVist(u);
            }
        }
    }

    private void dfsVist(Vertex<?> u) {
        //since this vertex has just been discovered...
        u.setColor(VertexColor.GREY_DISCOVERED);

        //set the discovery time -- note this may not been needed in the final implementation
        u.setStartTime(++time);

        //now DFS visit each node in it's adjaceny list, i.e, explore each edge where 'v' is one of the vertices
        for (Edge e : u) {
            Vertex<?> v = e.getV2();
            if (v.getColor() == VertexColor.WHITE_UNDISCOVERED) {
                v.setParent(u);
                dfsVist(v);
            }
        }

        //since u is processed, set the color and time accordingly
        u.setColor(VertexColor.BLACK_PROCESSED);
        u.setFinishTime(++time);
    }

    /*
     * Topological sort functions
     * 
     */
	//pretty much the same as DFS but here we return a linked list of sorted
    //by the "finish time" of the DFSvist function.
    public List<Vertex<?>> topologicalSort() {
        List<Vertex<?>> sortedList = new List<>();

        //reset the graph to an undiscovered state
        for (Vertex<?> u : vertices) {
            u.setParent(null); //parent is null
            u.setDistance(Double.NEGATIVE_INFINITY); //represents infinity
            u.setColor(VertexColor.WHITE_UNDISCOVERED); //undiscovered state
        }

        //initialize the time
        time = 0;

        //for each vertex, visit it using depth first serach algorithm
        for (Vertex<?> u : vertices) {
            if (u.getColor() == VertexColor.WHITE_UNDISCOVERED) {
                dfsVist(u, sortedList);
            }
        }
        return sortedList;
    }

    private void dfsVist(Vertex<?> u, List<Vertex<?>> sortedList) {
        //since this vertex has just been discovered...
        u.setColor(VertexColor.GREY_DISCOVERED);

        //set the discovery time -- note this may not been needed in the final implementation
        u.setStartTime(++time);

        //now DFS visit each node in it's adjaceny list, i.e, explore each edge where 'v' is one of the vertices
        for (Edge e : u) {
            Vertex<?> v = e.getV2();
            if (v.getColor() == VertexColor.WHITE_UNDISCOVERED) {
                v.setParent(u);
                dfsVist(v, sortedList);
            }
        }

        //since u is processed, set the color and time accordingly
        u.setColor(VertexColor.BLACK_PROCESSED);
        u.setFinishTime(++time);
        sortedList.insertHead(u);
    }

    /*
     * Prim's algorithm 
     * - use a priority min queue
     * - the Key's will be a double and start as POSITIVE INFINITY and the Value is the vertex object
     * - choose a random vertex and the set the weight to zero
     * - extract the vertex with min key and examine if the key of the vertex is less than the weigh of the edge
     * - if yes, 
     * 		a) change the key of the second vertex (of the edge) to be the weight of the edge
     *         note - need to use decrease key operation such that the min queue properties are maintained
     *      b) make the vertex the parent of the second vertex
     * - if no, continue to extract the min from the queue and repeat the process
     * - printing mst - If the parent is not null, the vertex is part of the MST - so add it to the list
     *   note - additional processing is required to print on the screen.
     * 
     */
    public List<Vertex<?>> primMST() {
        //initialze per commnets above
        MinPriorityQueue<Double, Vertex<?>> minQ = new MinPriorityQueue<>();
        Iterator<Vertex<?>> itAllVertices = vertices.iterator();
        Double[] keys = new Double[vertices.getCount()];
        for (int i = 0; i < keys.length && itAllVertices.hasNext(); i++) {
            Vertex<?> v = itAllVertices.next();
            v.setParent(null);
            keys[i] = Double.POSITIVE_INFINITY; //this will ensure that no edge weight will be bigger
            minQ.minHeapInsert(keys[i], v);
        }

        //change the Key of the first element in the priority queue
        Vertex<?> s = vertices.getHead();
        minQ.decreaseKey(0, 0.0, s);

		//a list for the MST vertices
        //All MST vertices have a parent ...
        List<Vertex<?>> minSpanTree = new List<>();
        //minSpanTree.add(s);

        while (!minQ.isEmpty()) {
            //extract the vertex with the smallest key
            KVPair<Double, Vertex<?>> kvPair = minQ.extractMin();

            //helpder variable
            Vertex<?> u = kvPair.getValue();

            //iterate through it edges and update the keys of it's edge vertices
            Iterator<Edge> itUAdjacencyList = u.iterator();
            while (itUAdjacencyList.hasNext()) {
                Edge e = itUAdjacencyList.next();

                //see if the vertex v2 (of edge (u, v2)) is still in the queue and its weight is less than the u's key
                KVPair<Double, Vertex<?>> kvTmp = minQ.searchValue(e.getV2());
                if (kvTmp != null && e.getWeight() < kvTmp.getKey()) {
                    //V2 of this edge is in the min span tree
                    e.getV2().setParent(u);

                    //change the key of vertex to be the weight of the edge
                    kvTmp.setKey(e.getWeight());

                    //decrease the key and restore min  heap properties
                    minQ.decreaseKey(kvTmp.getIndex(), kvTmp.getKey(), kvTmp.getValue());
                }
            }
        }

        //process the vertices to add 
        for (Vertex<?> v : vertices) {
            if (v.getParent() != null) {
                minSpanTree.add(v);
            }
        }
        return minSpanTree;
    }

    /*
     * Kruskal MST
     * 
     * - This algorithm requires all the edges to be sorted by edge weight, so we need pre-process the edges
     *   We will use a Red-Black tree to provide fast insertion and retrieval.
     * - We also create a Disjoint set data structure where at beginning all vertices will be a set of 1
     * - Using in-order traversal of the tree, examine each edge to see if the vertices belong to the same set
     * - if yes, a) add it to the MST b) create a union of the vertex sets c) continue examination
     * - if no, continue examination
     * - The MST is the list of edges in the list.
     *  
     */
    public List<Edge> kruskalMST() {

        List<Edge> minSpanTree = new List<>();
        RBTree<Edge> edgeTree = new RBTree<>(); //provides an almost balanced tree
        DisjointSet<Vertex<?>> dSet = new DisjointSet<>();

		//add all the edges to an RBTree and make a set with each vertex
        //kruskal's algorithm requires all edges to be sorted by weight
        //adding them to an RBTree and then traversing them in order will
        //meet the requirement
        for (Vertex<?> v : vertices) {
            //add the adjacency list to the tree
            for (Edge e : v) {
                edgeTree.rbInsert(e);
            }

            //create a set for each vertex
            dSet.makeSet(v);
        }

		//inspect the 2 vertices for each edge starting with the edge with the lowest weight
        //each unique edge is added to the MST. 
        for (Edge ee : edgeTree) {
            if (dSet.findSet(ee.getParent()) != dSet.findSet(ee.getV2())) {
                minSpanTree.add(ee);
                dSet.union(ee.getParent(), ee.getV2());
            }
        }
        return minSpanTree;
    }

    /*
     * Single Source Shortest path - the next 3 functions are all related to SINGLE SOURCE shortest path algorithms.
     * 
     * We have not implemented the following variants:
     * - Single destination shortest path - we can do so by reversing the edges (graph transpose) and then
     *                                      running the single source shortest path algorithm
     * - Single pair shortest path - this is merely a path from vertex 'u' to 'v'. This will be provided as 
     *                               a helper function. Per several books and the web there are no known 
     *                               algorithms that run faster than single source (to all vertices) shortest path.
     * 
     * The output - the function below updates the parent and distance variables of the vertices. We can use
     *              the 'printPaths' function to print the actual path to a specific vertex and also
     *              the weight/distance of the that path
     */
    public boolean bellmanFordSSShortestPath(Vertex<?> startVertex) {
        //initialize graph
        initBFSSShortestPath(startVertex);

		//iterate through edges... since the graph is represented as an
        //adjacency list, iterate through the vertices and then build a list of edges
        List<Edge> edgeList = new List<>();
        for (Vertex<?> v : vertices) {
            for (Edge e : v) {
                edgeList.add(e);
            }
        }

		//For each vertex, iterate through the list of vertices and relax the constraints.
        //Note we subtract one from the total count ...  CLRS page 583 Cycles and
        //then page 588
        for (int i = 0; i < vertices.getCount() - 1; i++) {
            for (Edge e : edgeList) {
                relaxSSShortestPath(e.getParent(), e.getV2(), e.getWeight());
            }
        }

        //check if there are any negative cycles
        for (Edge e : edgeList) {
            Vertex<?> src = e.getParent();
            Vertex<?> dst = e.getV2();
            Double w = e.getWeight();
            if (dst.getDistance() > (src.getDistance() + w)) {
                return false;
            }
        }
        //no negative cycles, so return true
        return true;
    }

    /*
     * Directed Acyclic Graph shortest path
     * 
     * - topologically sort the vertices
     * - for each vertex in the sorted order, relax each each 
     *   vertex in its adjacency list
     */
    public List<Vertex<?>> DAGShortestPath(Vertex<?> startVertex) {
        List<Vertex<?>> sortedList = topologicalSort();
        Vertex<?> sortedStart = sortedList.search(startVertex);

		//initialize the vertices - we don't cal the init function because we 
        //we need to work with the sorted list
        //set all distance of vertices to INFINITY and parent to null
        for (Vertex<?> v : sortedList) {
            v.setDistance(Double.POSITIVE_INFINITY);
            v.setParent(null);
        }
        sortedStart.setDistance(0.0);

        //relax the distances in the sorted order
        for (Vertex<?> u : sortedList) {
            for (Edge e : u) {
                Vertex<?> v = e.getV2();
                relaxSSShortestPath(u, v, e.getWeight());
            }
        }
        return sortedList;
    }

    private void initBFSSShortestPath(Vertex<?> startVertex) {
        //set all distance of vertices to INFINITY and parent to null
        for (Vertex<?> v : vertices) {
            v.setDistance(Double.POSITIVE_INFINITY);
            v.setParent(null);
        }
        startVertex.setDistance(0.0);
    }

    /*
     * Test to see whether we can improve the shortest path to dest found so far by going
     * through src and, if so, updating d[dest] and parent[dest]
     */
    private void relaxSSShortestPath(Vertex<?> src, Vertex<?> dest, Double weight) {
        if (dest.getDistance() > (src.getDistance() + weight)) {
            dest.setDistance(src.getDistance() + weight);
            dest.setParent(src);
        }
    }

    /*
     * Dijstra's Single source shortest path algorithm
     * 
     * - no negative weight edges are allowed
     * 
     */
    public void dijkstraSSShortestPath(Vertex<?> start) {
        //Init the graph
        MinPriorityQueue<Double, Vertex<?>> minQ = this.initDijkstraSSShortestPath(start);

		//NOTE: The books usually ask to create a disjoint set to hold the SSSP vertices
        //      we don't create one here because we will just update the class variable 'vertices'
        //      and the calling can simply use printPaths (with same source node) to print the SSSP
        //DisjointSet S = new DisjointSet <Vertex<?>> ();
        //while there are items in the queue
        while (!minQ.isEmpty()) {
            KVPair<Double, Vertex<?>> kvPairU = minQ.extractMin();
            Vertex<?> u = kvPairU.getValue();
            for (Edge e : u) {
                Vertex<?> v = e.getV2();
                KVPair<Double, Vertex<?>> kvPairV = minQ.searchValue(v);

                //reduce the distance if a shorter path exists
                relaxSSShortestPath(u, v, e.getWeight());

				//decrease key... it is possible that 'v' is already extracted from queue
                //so check for check for null and decrease key
                if (kvPairV != null) {
                    kvPairV.setKey(v.getDistance());
                    kvPairV.setValue(v);
                    minQ.decreaseKey(kvPairV.getIndex(), kvPairV.getKey(), kvPairV.getValue());
                }
            }
        }
    }

    private MinPriorityQueue<Double, Vertex<?>> initDijkstraSSShortestPath(Vertex<?> start) {
		//Dijkstra's init function - we don't use the other init function (above) because we 
        //we need to create a min priority queue, which requires us to iterate through
        //all the vertices. Instead of doing it twice we can re-write the function and 
        //add the queue creation and setup step
        MinPriorityQueue<Double, Vertex<?>> minQ = new MinPriorityQueue<>();
        Iterator<Vertex<?>> itAllVertices = vertices.iterator();
        Double[] keys = new Double[vertices.getCount()];
        for (int i = 0; i < keys.length && itAllVertices.hasNext(); i++) {
            Vertex<?> v = itAllVertices.next();
            if (v.compareTo(start) == 0) {
                keys[i] = 0.0; //so that the start is extracted first...
            } else {
                keys[i] = Double.POSITIVE_INFINITY;
            }
            v.setParent(null);
            v.setDistance(Double.POSITIVE_INFINITY); //this will ensure that no edge weight will be bigger
            minQ.minHeapInsert(keys[i], v);
        }
        start.setDistance(0.0);
        return minQ;
    }

    @Override
    public int compareTo(Graph o) {
        // TODO Auto-generated method stub
        return 0;
    }
}