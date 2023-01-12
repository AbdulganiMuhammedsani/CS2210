package graph;

import cms.util.maybe.Maybe;
import cms.util.maybe.NoMaybeValue;
import datastructures.PQueue;
import datastructures.SlowPQueue;
import game.Edge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** This object computes and remembers shortest paths through a
 *  weighted, directed graph. Once shortest paths are computed from
 *  a specified source vertex, it allows querying the distance to
 *  arbitrary vertices and the best paths to arbitrary destination
 *  vertices.
 *<p>
 *  Types Vertex and Edge are parameters, so their operations are
 *  supplied by a model object supplied to the constructor.
 */
public class ShortestPaths<Vertex, Edge> {

    /** The model for treating types Vertex and Edge as forming
     * a weighted directed graph.
     */
    private final WeightedDigraph<Vertex, Edge> graph;

    /** The distance to each vertex from the source.
     */
    private Maybe<Map<Vertex, Double>> distances;

    /** The incoming edge for the best path to each vertex from the
     *  source vertex.
     */
    private Maybe<Map<Vertex, Edge>> bestEdges;

    /** Creates: a single-source shortest-path finder for a weighted graph.
     *
     * @param graph The model that supplies all graph operations.
     */
    public ShortestPaths(WeightedDigraph<Vertex, Edge> graph) {
        this.graph = graph;
    }

    /** Effect: Computes the best paths from a given source vertex, which
     *  can then be queried using bestPath().
     */
    public void singleSourceDistances(Vertex source) {
        // Implementation: uses Dijkstra's single-source shortest paths
        //   algorithm.
        PQueue<Vertex> frontier = new SlowPQueue<>();
        Map<Vertex, Double> distances = new HashMap<>();
        Map<Vertex, Edge> bestEdges = new HashMap<>();
           // TODO: Complete computation of distances and best-path edges
        //Add the source to the frontier as well as a distance value
        //to represent the source.
        frontier.add(source,0.0);
        distances.put(source,0.0);
        //manually update the distance.
        this.distances = Maybe.some(distances);
        //set the source to Vertex b to access outgoing edges.
        Vertex b = source;
        //while the frontier is not empty,
        //first we extract the minimum, and then look at outgoing
        //edges of that minumum.
        while(!frontier.isEmpty())
        {
            b = frontier.extractMin();
           for(Edge x : graph.outgoingEdges(b))
           {
               //if the distances Hashmap doesn't have that key, we add,
               //the distance values and add to frontier and bestedges the appropriate
               //information.
               if(!distances.containsKey(graph.dest(x)))
               {
                   distances.put(graph.dest(x), getDistance(graph.source(x)) + graph.weight(x));
                   frontier.add(graph.dest(x),getDistance(graph.dest(x)));
                   bestEdges.put(graph.dest(x),x);
               }
               else {
                   //if it does contain the keys.
                   //we check only if the distance from the up to this edge, plus its weight
                   //is a shorter path than the path stored at this key. when this is true, we replace
                   //best edges as well as change priority in frontier to take this change into account.
                   if (getDistance(graph.source(x)) + graph.weight(x) <= getDistance(graph.dest(x))) {
                       distances.put(graph.dest(x), getDistance(graph.source(x)) + graph.weight(x));
                       frontier.changePriority(graph.dest(x), getDistance(graph.dest(x)));
                       bestEdges.replace(graph.dest(x), x);
                   }
               }
               //update the distances after each iteration.
               this.distances = Maybe.some(distances);
           }
           //update the best Edges at the end of the loop.
            this.bestEdges = Maybe.some(bestEdges);
            this.distances = Maybe.some(distances);
        }
    }

    /** Returns: the distance from the source vertex to the given vertex.
     *  Checks: distances have been computed from a source vertex,
     *    and vertex v is reachable from that vertex.
     */
    public double getDistance(Vertex v) {
        try {
            Double d = distances.get().get(v);
            assert d != null : "Implementation incomplete";
            return d;
        } catch (NoMaybeValue exc) {
            throw new Error("Distances not computed yet");
        }
    }

    /**
     * Returns: the best path from the source vertex to a given target
     * vertex. The path is represented as a list of edges.
     * Requires: singleSourceDistances() has already been used to compute
     * best paths.
     */
    public List<Edge> bestPath(Vertex target) {
        LinkedList<Edge> path = new LinkedList<>();
        Map<Vertex, Edge> bestEdges = this.bestEdges.orElseGet(() -> {
            throw new Error("best distances not computed yet");
        });
        Vertex v = target;
        while (true) {
            Edge e = bestEdges.get(v);
            if (e == null) break; // must be the source vertex
            path.addFirst(e);
            v = graph.source(e);
        }
        return path;
    }
}
