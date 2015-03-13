package com.sri.graphdatastructures;

import com.sri.main.DataStructureTest;
import java.util.Iterator;

import com.sri.datastructures.List;

public class Vertex<E extends VertexIntf> implements Comparable<Vertex<?>>, Iterable<Edge> {

    //the graph is represented as an adjacency list

    private List<Edge> adjacencyList;

    //object that makes this vertex
    private E vertex;

	//label - we don't have the member in this class, instead we will just 
    //invoke the 'vertex's' getvertexlabel function. Since the class requires 
    //all type E objects to inherit from VertexIntf, so all 'vertex' will
    //support the function
    //these values are used for BFS and DFS algorithms
    private VertexColor color;
    private Double distance;
    private int startTime;
    private int finishTime;
    private Vertex<?> parent;

    public Vertex(E v) {
        vertex = v;

		//initialize Breadth First Search variables
        //color is always white, to start with
        setColor(VertexColor.WHITE_UNDISCOVERED);

        //and the distance is INFINITY (-1)
        setDistance(Double.NEGATIVE_INFINITY);
        setParent(null);
        adjacencyList = new List<Edge>();
    }

    public boolean equals(Object o) {
        @SuppressWarnings("unchecked")
        Vertex<E> v = (Vertex<E>) o;
        if (DataStructureTest.stringCompare(getVertexLabel(), v.getVertexLabel()) == 0) {
            return true;
        }
        return false;
    }

    //number of vertices connected to this Vertex
    public int getOutDegree() {
		//NOTE: This is only the out-degree - this graph implementation DOES NOT 
        //      provide an in-degree (at least not yet)
        return adjacencyList.getCount();
    }

    public void addAdjacency(Vertex<?> v, double weight) {
        //create a new edge
        Edge newEdge = new Edge();
        newEdge.setV2(v);
        newEdge.setParent(this);
        newEdge.setWeight(weight);
        adjacencyList.add(newEdge);
    }

    public String toString() {
        String s = "";

        //label of the vertex
        s += "Label: " + getVertexLabel() + "\tParent: " + parent + "\tDistance: " + distance; /*+ " Distance: " + distance + " Start Time: " + startTime + " Finish Time: " + finishTime;*/ /*+ "\r" + "Edges:\r";*/
//		Iterator<Edge> itE = adjacencyList.iterator();
//		while (itE.hasNext()) {
//			Edge e = itE.next();
//			Vertex<?> v = e.getV2();
//			s += "Label: " + v.getVertexLabel() + " Weight: " + e.getWeight() + "\r";
//		}
        //s += "\r";


        return s;
    }

    /*
     * Overidden functions
     * 
     */
    //because we are implementing the Iterable class
    @Override
    public Iterator<Edge> iterator() {
        return adjacencyList.iterator();
    }

    //because we are implementing the Comparable class
    @Override
    public int compareTo(Vertex<?> v) {
        return DataStructureTest.stringCompare(getVertexLabel(), v.getVertexLabel());
    }

    /*
     * From here it is all setters/getters for the variables
     */
    public E getVertex() {
        return vertex;
    }

    public void setVertex(E vertex) {
        this.vertex = vertex;
    }

    public VertexColor getColor() {
        return color;
    }

    public void setColor(VertexColor color) {
        this.color = color;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    public Vertex<?> getParent() {
        return parent;
    }

    public void setParent(Vertex<?> parent) {
        this.parent = parent;
    }

	//return the type of vertex represented by 'vertex'
    //since they are supposed to be of "VertexIntf" they will support
    //the two functions
    public VertexType getVertexType() {
        return vertex.getVertexType();
    }

    public String getVertexLabel() {
        return vertex.getVertexLabel();
    }
}
