package com.sri.graphdatastructures;

//simple class for graph nodes... only label and ID
public class GraphTestNode implements VertexIntf, Comparable<GraphTestNode> {

    private String nodeID;

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    @Override
    public String getVertexLabel() {
        return nodeID;
    }

    @Override
    public VertexType getVertexType() {
        return null;
    }

    @Override
    public int compareTo(GraphTestNode o) {
        return nodeID.compareTo(o.nodeID);
    }
}
