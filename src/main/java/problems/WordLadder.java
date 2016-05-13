package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by allenc289 on 4/13/16.
 */
public class WordLadder {
    /*
    start = "hit"
end = "cog"
dict = ["hot","dot","dog","lot","log"]
     */

    public static void testFindLadders() {
        ArrayList<String> dict = new ArrayList<>();
        Collections.addAll(dict, new String[]{"hot", "dot", "dog", "lot", "log"});

        findLadders("hit", "cog", dict);
    }

    public static class BreadthFirstPaths {
        private static final int INFINITY = Integer.MAX_VALUE;
        private boolean[] marked;  // marked[v] = is there an s-v path
        private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
        private int[] distTo;      // distTo[v] = number of edges shortest s-v path

        public BreadthFirstPaths(Graph G, String s) {
            marked = new boolean[G.V];
            distTo = new int[G.V];
            edgeTo = new int[G.V];
            bfs(G, s);
        }

        private void bfs(Graph g, String s) {

        }
    }
    public static class Graph {
        public int V;

        public Graph (int V) {
            this.V = V;
        }

        private Map<String, Set<String>> adjancencyList = new HashMap<String, Set<String>>();

        public void addEdge(String v, String w) {
            Set<String> edges = adjancencyList.get(v);
            if (edges == null) {
                edges = new HashSet<String>();
                adjancencyList.put(v, edges);
            }
            edges.add(w);
        }
    }

    public static ArrayList<ArrayList<String>> findLadders(String start, String end, ArrayList<String> dict) {
        Graph graph = new Graph(dict.size() + 2);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (String word1 : dict) {
            for (String word2 : dict) {
                if (wordsDifferByOneCharacter(word1, word2)) {
                    graph.addEdge(word1, word2);
                }
            }
        }

        BreadthFirstPaths bfp = new BreadthFirstPaths(graph, start);

        return result;
        //ArrayList<ArrayList<String>> result = graph.bfs(start, end);
    }

    private static boolean wordsDifferByOneCharacter(String word1, String word2) {
        return true;
    }
}
