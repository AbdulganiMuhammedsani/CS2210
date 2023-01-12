package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class ShortestPathsTest {
    /** The graph example from lecture */
    static final String[] vertices1 = { "a", "b", "c", "d", "e", "f", "g" };
    static final int[][] edges1 = {
        {0, 1, 9}, {0, 2, 14}, {0, 3, 15},
        {1, 4, 23},
        {2, 4, 17}, {2, 3, 5}, {2, 5, 30},
        {3, 5, 20}, {3, 6, 37},
        {4, 5, 3}, {4, 6, 20},
        {5, 6, 16}
    };
    static class TestGraph implements WeightedDigraph<String, int[]> {
        int[][] edges;
        String[] vertices;
        Map<String, Set<int[]>> outgoing;

        TestGraph(String[] vertices, int[][] edges) {
            this.vertices = vertices;
            this.edges = edges;
            this.outgoing = new HashMap<>();
            for (String v : vertices) {
                outgoing.put(v, new HashSet<>());
            }
            for (int[] edge : edges) {
                outgoing.get(vertices[edge[0]]).add(edge);
            }
        }
        public Iterable<int[]> outgoingEdges(String vertex) { return outgoing.get(vertex); }
        public String source(int[] edge) { return vertices[edge[0]]; }
        public String dest(int[] edge) { return vertices[edge[1]]; }
        public double weight(int[] edge) { return edge[2]; }
    }
    static TestGraph testGraph1() {
        return new TestGraph(vertices1, edges1);
    }


    @Test
    void lectureNotesTest() {
        TestGraph graph = testGraph1();
        ShortestPaths<String, int[]> ssp = new ShortestPaths<>(graph);
        ssp.singleSourceDistances("a");
        assertEquals(50, ssp.getDistance("g"));
        StringBuilder sb = new StringBuilder();
        sb.append("best path:");
        for (int[] e : ssp.bestPath("g")) {
            sb.append(" " + vertices1[e[0]]);
        }
        sb.append(" g");
        assertEquals("best path: a c e f g", sb.toString());
    }


    @Test
    void SecondaryTest() {
        //using the lecture notes table, we test the ability to arrive at
        //the best path of a node for the first node that contains more than one path to destination.
        //Providing the correct value for distance, as well as order representation.
        TestGraph graph = testGraph1();
        ShortestPaths<String, int[]> ssp = new ShortestPaths<>(graph);
        ssp.singleSourceDistances("a");
        assertEquals(31, ssp.getDistance("e"));
        StringBuilder sb = new StringBuilder();
        sb.append("best path:");
        for (int[] e : ssp.bestPath("e")) {
            sb.append(" " + vertices1[e[0]]);
        }
        sb.append(" e");
        assertEquals("best path: a c e", sb.toString());
    }


    @Test
    void TertiaryTest() {
        //using the lecture notes table, we test the ability to arrive at
        //the best path of a node in the middle of the graph,surrounded by incoming
        // and outgoing edges. Providing the correct value for distance, as
        // well as order representation.
        TestGraph graph = testGraph1();
        ShortestPaths<String, int[]> ssp = new ShortestPaths<>(graph);
        ssp.singleSourceDistances("a");
        assertEquals(34, ssp.getDistance("f"));
        StringBuilder sb = new StringBuilder();
        sb.append("best path:");
        for (int[] e : ssp.bestPath("f")) {
            sb.append(" " + vertices1[e[0]]);
        }
        sb.append(" f");
        assertEquals("best path: a c e f", sb.toString());
    }


    @Test
    void QuarternaryTest() {
        //using the lecture notes table, we test the ability to arrive at
        //the best path of a node with two paths to the destination, one that is
        // one edge from source, and another that is two edges away from source.
        // Providing the ideal value for distance, as well as order representation.
        TestGraph graph = testGraph1();
        ShortestPaths<String, int[]> ssp = new ShortestPaths<>(graph);
        ssp.singleSourceDistances("a");
        assertEquals(15, ssp.getDistance("d"));
        StringBuilder sb = new StringBuilder();
        sb.append("best path:");
        for (int[] e : ssp.bestPath("d")) {
            sb.append(" " + vertices1[e[0]]);
        }
        sb.append(" d");
        assertEquals("best path: a d", sb.toString());
    }

    // TODO: Add 2 more tests
}
