package com.sri.main;


import com.sri.graphdatastructures.Graph;
import com.sri.graphdatastructures.GraphTestNode;
import com.sri.graphdatastructures.Vertex;
import com.sri.graphdatastructures.VertexIntf;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
public class DataStructureTest {

    private static final String PERSISTENCE_UNIT_NAME = "database_basics";

    public static void main(String[] args) {
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
    public static int lengthLCS(String x, String y) {
        int m = x.length();
        int n = y.length();
		// create the arrays, they already initialized to 0 so no need
        // set the elements to 0 as shown in CLR
        int c[][] = new int[m + 1][n + 1];

        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    c[i + 1][j + 1] = c[i][j] + 1;
                } else {
                    c[i + 1][j + 1] = Math.max(c[i + 1][j], c[i][j + 1]);
                }
            }
        }
        return c[m][n];
    }

    public static String LCS(String x, String y) {
        int m = x.length();
        int n = y.length();
		// create the arrays, they already initialized to 0 so no need
        // set the elements to 0 as shown in CLR
        int c[][] = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (x.charAt(i) == y.charAt(j)) {
                    c[i + 1][j + 1] = c[i][j] + 1;
                } else {
                    c[i + 1][j + 1] = Math.max(c[i + 1][j], c[i][j + 1]);
                }
            }
        }

        // read the substring out from the matrix
        StringBuffer sb = new StringBuffer();
        for (int k = x.length(), l = y.length(); k != 0 && k != 0;) {
            if (c[k][l] == c[k - 1][l]) {
                k--;
            } else if (c[k][l] == c[k][l - 1]) {
                l--;
            } else {
                assert x.charAt(k - 1) == y.charAt(l - 1);
                sb.append(x.charAt(k - 1));
                k--;
                l--;
            }
        }
        return sb.reverse().toString();
    }

    public static int[] subStringMatch3(String mainStr, String subStr) {

        // get the hash values
        int subLen = subStr.length();
        int mainLen = mainStr.length();
        int matches[] = new int[10];
        int matchIndex = 0;

        // choose a prime number for the hashing function
        int prime = 71;

        // calculate the hash value;
        int subStrHash = 0, mainSubStrHash = 0;
        for (int i = 0; i < subLen; i++) {
            subStrHash += subStr.charAt(i) * prime ^ subLen;
        }
        for (int i = 0; i < subLen - 1; i++) //subtract a 1 from sublen because mainStr[sublen] hash will be added in the for loop below
        {
            mainSubStrHash += mainStr.charAt(i) * prime ^ subLen;
        }

        for (int i = 0; i <= mainLen - subLen; i++) {
            mainSubStrHash += mainStr.charAt(i + subLen - 1) * prime ^ subLen;
            if (mainSubStrHash == subStrHash) {
                String mainSubStr = mainStr.substring(i, i + subLen);
                if (mainSubStr.compareTo(subStr) == 0) {
                    matches[matchIndex++] = i;
                }
            }
            mainSubStrHash -= mainStr.charAt(i) * prime ^ subLen;
        }
        return matches;
    }

    public static int stringCompare(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return -1;
        }

        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 != len2) //obviously cannot be the same if they are of different lengths
        {
            return -1;
        }

        // choose a prime number for the hashing function
        int prime = 71;

        // calculate the hash value;
        int hash1 = 0, hash2 = 0;
        for (int i = 0; i < len1; i++) {
            hash1 += s1.charAt(i) * prime ^ len1;
        }
        for (int i = 0; i < len2; i++) {
            hash2 += s2.charAt(i) * prime ^ len1;
        }

        if (hash1 == hash2) {
            //now need to check of characters are the same 
            return s1.compareTo(s2);
        }

        if (hash1 < hash2) {
            return -1;
        } else if (hash1 > hash2) {
            return 1;
        }
        return 0;
    }

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
