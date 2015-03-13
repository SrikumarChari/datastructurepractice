package com.sri.graphdatastructures;

//this is the line between two vertices or connector (in some examples on the net)
public class Edge implements Comparable<Edge> {

    //the vertex 'v' that the parent connects to

    private Vertex<?> v = null;
    private Vertex<?> parent = null;
    private Double weight = 0.0;

    public boolean equals(Object o) {
        System.out.println("in edge equals");
        Edge anotherEdge = (Edge) o;
        return weight == anotherEdge.weight ? true : false;
    }

    public Vertex<?> getV2() {
        return v;
    }

    public void setV2(Vertex<?> v2) {
        this.v = v2;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

	//NOTE: there is no setter as we just want to use the labels from the two vertices 
    //      this edge represents
    public String getLabel() {
        return weight.toString();
    }

    public Vertex<?> getParent() {
        return parent;
    }

    public void setParent(Vertex<?> parent) {
        this.parent = parent;
    }

    public String toString() {
        String outStr = "";

        outStr += "From: " + parent.getVertexLabel() + " To: " + v.getVertexLabel() + " Weight: " + weight.toString();
        return outStr;
    }

    @Override
    public int compareTo(Edge e) {
		//vertex compares the label... if the labels are the same then
        //compare the weights
        int retVal1 = v.compareTo(e.getV2());
        int retVal2 = Double.compare(weight, e.weight);
        if (retVal2 == 0) //if the weights are the same, then check the labels of the vertices
        {
            return retVal1;
        } else {
            return retVal2;
        }
//		return Double.compare(weight, e.weight);

    }
}
