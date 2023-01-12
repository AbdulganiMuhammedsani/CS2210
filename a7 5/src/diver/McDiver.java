package diver;

import game.*;

import java.util.*;

import graph.ShortestPaths;


/** This is the place for your implementation of the {@code SewerDiver}.
 */
public class McDiver implements SewerDiver {

    /**
     * The visited locations in the seek implementation
     */
    Map<Long,Integer> visited = new HashMap<>();

    /**
     * The visited locations in the scram implementation
     */
    private Map<Node, Double> been = new HashMap<>();


    /** See {@code SewerDriver} for specification. */
    @Override
    public void seek(SeekState state) {
        // TODO : Look for the ring and return.
        // DO NOT WRITE ALL THE CODE HERE. DO NOT MAKE THIS METHOD RECURSIVE.
        // Instead, write your method (it may be recursive) elsewhere, with a
        // good specification, and call it from this one.
        //
        // Working this way provides you with flexibility. For example, write
        // one basic method, which always works. Then, make a method that is a
        // copy of the first one and try to optimize in that second one.
        // If you don't succeed, you can always use the first one.
        //
        // Use this same process on the second method, scram.
        //Calling implementation of dfsWalk.
        dfsWalk(state);
    }

    /** See {@code SewerDriver} for specification. */
    @Override
    public void scram(ScramState state) {
        // TODO: Get out of the sewer system before the steps are used up.
        // DO NOT WRITE ALL THE CODE HERE. Instead, write your method elsewhere,
        // with a good specification, and call it from this one.
        //Calling implementation of collecting coins and scram.
        scramMover(state);
    }

    /**Computes the best path to acquire the ring.
     * Effect: Dfs traversal seeking the ring. Preference for non-visited nodes
     * unless only option for traversal. Terminates when arrived at ring.
     */
    public void dfsWalk(SeekState state)
    {
        //Using a hashtable to store keyvalues for reference.
        //storing the current location as well as that value changes
        //in the coming loop as we mave moves.
        visited.put(state.currentLocation(),1);
        long r = state.currentLocation();
        if(state.distanceToRing() == 0)
        {
            //if we arrive at our destination, return;
            return;
        }
        //Loop through neighboring nodestatus and checking if we have visited
        //them by checking keyvalue, if they have not been visited,
        //we also take into consideration if the neighbor is closer to our
        //destination. If so, we move forward, call dfswalk recursively, and then
        //move back in the case that we return out of our loop.
            for(NodeStatus v : state.neighbors())
            {
                if (!visited.containsKey(v.getId()))
                    {

                            if (v.getDistanceToRing() < state.distanceToRing())
                            {
                                state.moveTo(v.getId());
                                dfsWalk(state);
                                if (state.distanceToRing() == 0) {
                                    return;
                                    //Check ring value again here after we moved forward
                                    //and before we move backwards with state.moveTo(r)
                                }
                                state.moveTo(r);
                            }
                    }
            }
            //Secondary loop in the case that there isnt neighboring nodes
            //that are ideally closer to the destination.
        for(NodeStatus h : state.neighbors())
        {
            if (!visited.containsKey(h.getId()))
            {
                //only requirement is that the nodestatuses have not
                //been visited.
                state.moveTo(h.getId());
                dfsWalk(state);
                if (state.distanceToRing() == 0) {
                    return;
                    //Check ring value again here after we moved forward
                    //and before we move backwards with state.moveTo(r)
                }
                state.moveTo(r);
            }
        }

    }

    /**
     * Computes the best path to collect coins and to exit the map.
     * Effect:Traverses the best paths to ideal coins based on value and distance.
     * Terminates by traversing the best path to exit.
     */
    public void scramMover(ScramState state)
    {
        //storing the current node
        //and create a maze object state.allNodes
        // provides us with a weighted digraph which
        //is extremely useful.
        Node bb = state.currentNode();
        been.put(bb,0.0);
        Maze rr = new Maze((Set<Node>) state.allNodes());
        //constuct a ShortestPaths Object with the maze as the parameter.
        ShortestPaths<Node, Edge> a = new ShortestPaths<>(rr);
                Node best = state.currentNode();
                //Initialize idealdist as very big, as we will compare it to distances,
                //find minimum and set idealdist to the minimum value.
                double idealdist = 9999;
                for (Node n : state.allNodes())
                {
                    //computing the best paths from the current node.
                    a.singleSourceDistances(state.currentNode());
                    //if we identify the maximum coin, it is worth it to
                    //seek it out first.
                    if((n.getTile().coins() == 5000))
                    {
                        best = n;
                        break;
                    }
                    //approximation of ideal situations, if coins are greater than 0 and
                    //distance from our current position is < idealdist, pursue.
                    // for guarantees of greater points, we make room for distance
                    //to be slightly greater than the ideal so we dont waste steps.
                    if ((n.getTile().coins() > 0 && a.getDistance(n) < idealdist) ||
                    (n.getTile().coins() > 800 && a.getDistance(n) < idealdist*(3/2)))
                    {
                        idealdist =  a.getDistance(n);
                        best = n;
                    }
                }
                //creating the bestpath list for the best coin to pursue at our current
                //position.
                a.singleSourceDistances(state.currentNode());
                List<Edge> ed = a.bestPath(best);

                for(Edge ddd : ed)
                {
                    //as we pursue our coins, we also track the distance to the exit,
                    //once we approach a point where our steps left are approximately
                    //the best path back, we take the path back and return.
                    a.singleSourceDistances(state.currentNode());
                    List<Edge> end = a.bestPath(state.exit());
                    if(ddd.length() + a.getDistance(state.exit()) + 17 > state.stepsToGo())
                    {
                        for(Edge ttt: end)
                        {
                          state.moveTo(ttt.destination());
                        }
                        return;
                    }
                    //if we still have steps to spare, we continue pursuing the coins
                    state.moveTo(ddd.destination());

                }
                //recursive call and starts from our new position that we have moved to.
                scramMover(state);
    }

}
