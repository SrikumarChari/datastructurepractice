package com.sri.main;


import com.sri.graphdatastructures.Graph;
import com.sri.graphdatastructures.GraphTestNode;
import com.sri.graphdatastructures.Vertex;
import com.sri.graphdatastructures.VertexIntf;
import com.sri.sort.SortType;
import com.sri.sort.SortUtilities;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class DataStructureTest {

    private static final String PERSISTENCE_UNIT_NAME = "database_basics";
    private static Integer MAX_NUMBERS = 100;
    private static Integer ORIGIN = 1;
    private static Integer UPPER_BOUND = 1000000;

    public static void main(String[] args) {
        
        //generate random numbers
        Integer[] unsorted = generateRandomNumber(MAX_NUMBERS);
        
        System.out.println(Arrays.toString(unsorted));
        SortUtilities.insertionSort(unsorted, SortType.ASCENDING);
        System.out.println(Arrays.toString(unsorted));
        SortUtilities.insertionSort(unsorted, SortType.DESCENDING);
        System.out.println(Arrays.toString(unsorted));
    }

    private static Integer[] generateRandomNumber(int maxNumbers) {
        Integer[] randomNumbers = new Integer[maxNumbers];
        Random rand = new Random();
        int[] tmp = rand.ints(maxNumbers, ORIGIN, UPPER_BOUND).toArray();
        for (int i = 0; i < tmp.length; i++) {
            randomNumbers[i] = tmp[i];
        }
        return randomNumbers;
    }
    private static void processWeightedGraph(String fileName, List<GraphTestNode> listNodes, Graph myGraph) throws IOException {
        String fileLine;
        Charset cs = Charset.forName("ISO-8859-2");
        Path inputPath = Paths.get(fileName);

        try {
            BufferedReader reader = Files.newBufferedReader(inputPath, cs);
            while ((fileLine = reader.readLine()) != null) {
                //the format of each line 'v1 space v2 space edgeWeight"
                String tokens[] = fileLine.split("[ ]");

                //the first two lines of the file have only 1 entry, skip them
                if (tokens.length < 3) {
                    continue;
                }

                //create a node object
                GraphTestNode gtNode1 = new GraphTestNode();
//				gtNode1.setNodeID(Integer.valueOf(tokens[0]));
                gtNode1.setNodeID(tokens[0]);

                //search the graph to see if the node exists
                Vertex<? extends VertexIntf> v1 = myGraph.search(gtNode1.getVertexLabel());
                if (v1 == null) {
                    v1 = (Vertex<? extends VertexIntf>) new Vertex<GraphTestNode>(gtNode1);
                    myGraph.addVertex(v1);
                }

                //the second token is the vertex vertex 'v' connects to
                Vertex<?> v2 = myGraph.search(tokens[1]);
                if (v2 == null) {
                    GraphTestNode gtNode2 = new GraphTestNode();
//					gtNode2.setNodeID(Integer.valueOf(tokens[1]));
                    gtNode2.setNodeID(tokens[1]);
                    v2 = new Vertex<>(gtNode2);
                    myGraph.addVertex(v2);
                }
                v1.addAdjacency(v2, Double.valueOf(tokens[2]));
                //v2.addAdjacency(v1, Double.valueOf(tokens[2]));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + fileName);
        }
    }

//	private static void processMovies(String fileName, List<Movie> movies, List<Artist> artists, Graph myGraph) throws IOException {
//		String fileLine;
//		Charset cs = Charset.forName("ISO-8859-2");
//		Path inputPath = Paths.get(fileName);
//		
//		//random generator the edge weight --> in practical applications weight will have meaning
//		//but here we will just use a random number with a max of 1000
//		Random rGen = new Random();
//
//		try {
//			BufferedReader reader = Files.newBufferedReader(inputPath, cs);
//			while ((fileLine = reader.readLine()) != null) {
//				String tokens[] = fileLine.split("[/]");
//				String tmp  = "", movieName = "", releaseYear = "";
//				
//				tmp = tokens[0];
//				String nameTokens [] = tmp.split("[()]");
//				if (nameTokens.length > 0) {
//					for (int i = 0; i < nameTokens.length-1;i++) {
//						movieName += nameTokens[i];
//					}
//				} else
//					movieName = tokens[0];
//				releaseYear = nameTokens[nameTokens.length-1];
//				
//				//create a new vertex
//				Movie m = new Movie(movieName.trim(), releaseYear.trim());
//				movies.add(m);
//				Vertex<Movie> vm = new Vertex<Movie> (m);
//				myGraph.addVertex(vm);
//				
//				for (int i = 1; i < tokens.length; i++) {
//					Artist a = new Artist();
//					String artistName = tokens[i];
//					String artistNameTokens [] = artistName.split("[,]");
//					
//					//if there is a last name, first name combo split it accordingly
//					//otherwise just use the name as first name
//					if (artistNameTokens.length == 2) {
//						a.setLastName(artistNameTokens[0].trim());
//						a.setFirstName(artistNameTokens[1].trim());
//					} else
//						a.setFirstName(artistName.trim());
//					
//					//add the movie as a role for the artist
//					Artist tmpA = artists.search(a);
//					if (tmpA == null) {
//						//add the new artist to the list
//						artists.add(a);
//						
//						//add the movie as a role for the new artist
//						a.addRole(m);
//
//						//each new actor will also be a vertex
//						Vertex<Artist> va = new Vertex<Artist>(a);
//						myGraph.addVertex(va);
//						
//						//add to the adjacency list
//						va.addAdjacency(vm, rGen.nextDouble());
//						vm.addAdjacency(va, rGen.nextDouble());
//					} else {
//						//actor already exists, so just add the new movie
//						tmpA.addRole(m);
//						
//						//find the vertex representing this actor
//						Vertex<?> tmpV = (Vertex<?>) myGraph.search(tmpA.getVertexLabel());
//						if (tmpV != null) {
//							//add to the adjacency list
//							tmpV.addAdjacency(vm, rGen.nextDouble());
//							vm.addAdjacency(tmpV, rGen.nextDouble());
//						}
//					}
//				}
//			}
//			reader.close();
//		} catch (IOException e) {
//			System.out.println("Error opening input file");
//		}
//	}


    public static int[] mergeSort(int A[], int low, int high) {
        if (low < high) {
            int middle = (high + low) / 2;

            mergeSort(A, low, middle);
            mergeSort(A, middle + 1, high);
            merge(A, low, middle, high);
        }
        return A;
    }

    private static int[] merge(int[] A, int low, int middle, int high) {
        // int middle = low + (high - low)/2;
        int n1 = middle - low + 1; // number of elements in the left array
        int n2 = high - middle; // number of elements in the right array
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];
        int i, j, k = 0; // scratch/loop variables

        // build the left array
        for (i = 0; i < n1; i++) // CLR subtracts a one because the algorithm starts i with 1, Java arrays start at 0
        {
            leftArray[i] = A[low + i];
        }

        // build the right array
        for (j = 0; j < n2; j++) {
            rightArray[j] = A[middle + j + 1];
        }

        // reinitialize counters
        i = j = 0;

        for (k = low; k < high && i < n1 && j < n2; k++) {
            if (leftArray[i] <= rightArray[j]) {
                A[k] = leftArray[i];
                i++;
            } else {
                A[k] = rightArray[j];
                j++;
            }
        }

        // Copy the rest of the left side of the array into the target array
        while (i < n1) {
            A[k] = leftArray[i];
            k++;
            i++;
        }

        return A;
    }
}
