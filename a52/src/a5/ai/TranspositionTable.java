package a5.ai;

import a5.logic.Pente;
import a5.logic.TicTacToe;
import cms.util.maybe.Maybe;

/**
 * A transposition table for an arbitrary game. It maps a game state
 * to a search depth and a heuristic evaluation of that state to the
 * recorded depth. Unlike a conventional map abstraction, a state is
 * associated with a depth, so that clients can look for states whose
 * entry has at least the desired depth.
 *
 * @param <GameState> A type representing the state of a game.
 */
public class TranspositionTable<GameState> {

    /**
     * Information about a game state, for use by clients.
     */
    public interface StateInfo {

        /**
         * The heuristic value of this game state.
         */
        int value();

        /**
         * The depth to which the game tree was searched to determine the value.
         */
        int depth();
    }

    /**
     * A Node is a node in a linked list of nodes for a chaining-based implementation of a hash
     * table.
     *
     * @param <GameState>
     */
    static private class Node<GameState> implements StateInfo {
        /**
         * The state
         */
        GameState state;
        /**
         * The depth of this entry. >= 0
         */
        int depth;
        /**
         * The value of this entry.
         */
        int value;
        /**
         * The next node in the list. May be null.
         */
        Node<GameState> next;

        Node(GameState state, int depth, int value, Node<GameState> next) {
            this.state = state;
            this.depth = depth;
            this.value = value;
            this.next = next;
        }

        public int value() {
            return value;
        }

        public int depth() {
            return depth;
        }
    }

    /**
     * The number of entries in the transposition table.
     */
    private int size;

    /**
     * The buckets array may contain null elements.
     * Class invariant:
     * All transposition table entries are found in the linked list of the
     * bucket to which they hash, and the load factor is no more than 1.
     */
    private Node<GameState>[] buckets;

    // TODO 1: implement the classInv() method. You may also
    // strengthen the class invariant. The classInv()
    // method is likely to be expensive, so you may want to turn
    // off assertions in this file, but only after you have the transposition
    // table fully tested and working.
    // For our class invariant, we first check that size is  >= 0
    // count the elements in total in the table, and then ensure that
    // it is never greater than 1. Checking the loading factor.
    boolean classInv() {
        if (!(size >= 0))
        {
            return false;
        }
        int count = 0;
        for(int i = 0; i < buckets.length;i++)
        {
            Node<GameState> h = buckets[i];
            while(buckets[i] != null)
            {
                count++;
                if(buckets[i].next != null)
                {
                    count++;
                    buckets[i] = buckets[i].next;

                }
            }
            buckets[i] = h;
        }

        if((count/buckets.length) > 1)
        {
            return false;
        }
        return true;
    }

    //Initializing the size and buckets.
    @SuppressWarnings("unchecked")
    /** Creates: a new, empty transposition table. */
    TranspositionTable() {
        size = 0;
        buckets = new Node[7];
        // TODO 2
    }

    /** The number of entries in the transposition table. */
    public int size() {
        return size;
    }

    /**
     * Returns: the information in the transposition table for a given
     * game state, package in an Optional. If there is no information in
     * the table for this state, returns an empty Optional.
     */
    public Maybe<StateInfo> getInfo(GameState state) {
        // TODO 3
        //assert classInv();
        //I first check if my hashed index is bigger that or equal to the length, and ensure it is not.
        if(val(state) >= buckets.length)
        {
            return Maybe.none();
        }
        //checking if the value at that index is null for tictactoe
                if(buckets[val(state)] != null && buckets[val(state)].state instanceof TicTacToe)
                {
                    //then checking if it is not null, checking if that value is the correct state.
                    if(((TicTacToe)buckets[val(state)].state).board().equals(((TicTacToe)state).board()))
                    {
                        //if so, return that value at the head of the bucket index.
                        StateInfo p = buckets[val(state)];
                        return Maybe.some(p);
                    }
                    else
                    {
                        //in the other case, we traverse our list and search if the correct state is present.
                        Node<GameState>N = buckets[val(state)];
                        while(buckets[val(state)].next != null)
                        {
                            buckets[val(state)] = buckets[val(state)].next;
                            if(((TicTacToe)buckets[val(state)].state).board().equals(((TicTacToe)state).board()))
                            {
                                StateInfo p = buckets[val(state)];
                                return Maybe.some(p);
                            }
                        }
                        //ensure to reset node back to the head at the end.
                        buckets[val(state)] = N;
                    }

                }
        //checking if the value at that index is null for pente.
        if(buckets[val(state)] != null && buckets[val(state)].state instanceof Pente)
        {
            //then checking if it is not null, checking if that value is the correct state.
            if(((Pente)buckets[val(state)].state).board().equals(((Pente)state).board()))
            {

                //if so, return that value at the head of the bucket index.
                StateInfo p = buckets[val(state)];
                return Maybe.some(p);
            }
            else
            {
                //in the other case, we traverse our list and search if the correct state is present.
                Node<GameState> N = buckets[val(state)];
                while(buckets[val(state)].next != null)
                {
                    buckets[val(state)] = buckets[val(state)].next;
                    if(((Pente)buckets[val(state)].state).board().equals(((Pente)state).board()))
                    {
                        StateInfo p = buckets[val(state)];
                        return Maybe.some(p);
                    }
                }
                //ensure to reset node back to the head at the end.
                buckets[val(state)] = N;
            }

        }

        //if none of the above cases are true, return a aybe none.
        //assert classInv();
        return Maybe.none();
    }

    /**
     * Effect: Add a new entry in the transposition table for a given
     * state and depth, or overwrite the existing entry for this state
     * with the new depth and value. Requires: if overwriting an
     * existing entry, the new depth must be greater than the old one.
     */
    public void add(GameState state, int depth, int value) {
        // TODO 4
        //assert classInv();
        //checking if the size has reached the size of the buckets list,
        //which is basically stating that when load factor reaches 1.
        if(size == buckets.length)
        {
            //grow the size, and add what add what add originally called.
            grow(2 * buckets.length);
            add(state,depth,value);
            //System.out.println(val(state));
            return;
        }
        if(buckets[val(state)] == null)
        {
            //if there is nothing in this index, add element and increase size.
            buckets[val(state)] = new Node<>(state,depth,value,null);
            //System.out.println(val(state));
            size++;
        }
        else
        {
            //if tictactoe gamestate in index.
            if(buckets[val(state)].state instanceof TicTacToe)
            {
                //if equals and the new depth is greater, we overwrite.
                if(((TicTacToe)buckets[val(state)].state).board().equals(((TicTacToe)state).board()) && buckets[val(state)].depth < depth)
                {
                    buckets[val(state)] = new Node<>(state, depth, value, buckets[val(state)].next);
                    return;
                }
                else
                {

                    //and any other case, it adds.
                    Node<GameState> N = buckets[val(state)];
                    while (buckets[val(state)].next != null) {
                        buckets[val(state)] = buckets[val(state)].next;
                    }
                    buckets[val(state)].next = new Node<>(state, depth, value, null);
                    buckets[val(state)] = N;
                    size = manual();
                }
            }
            //if pente gamestate in index.
            if(buckets[val(state)].state instanceof Pente)
            {
                //if equals and the new depth is greater, we overwrite.
                if(((Pente)buckets[val(state)].state).equals(((Pente)state)) && buckets[val(state)].depth < depth)
                {
                    buckets[val(state)] = new Node<>(state, depth, value, buckets[val(state)].next);
                    return;
                }
                else
                {
                    //and any other case, it adds.
                    Node<GameState> N = buckets[val(state)];
                    while(buckets[val(state)].next != null)
                    {
                        buckets[val(state)] = buckets[val(state)].next;
                    }
                    buckets[val(state)].next = new Node<>(state,depth,value,null);
                    buckets[val(state)] = N;
                    size++;
                }
            }
        }
        //assert classInv();
    }

    /**
     * Effect: Make sure the hash table has at least {@code target} buckets.
     * Returns true if the hash table actually resized.
     */
    private boolean grow(int target) {
        // TODO 5
        //assert classInv();
        //creates new bucket containing the current info
        //then if we need to resize, it empties the table.
        @SuppressWarnings("unchecked")
        Node<GameState>[] buckets1 = new Node[target];
        Node<GameState>[] thief = buckets;
        if(thief.length < target) {
            buckets = buckets1;
            size=0;
            //we loop through our temp bucket array, if ! null, we rehash it into our
            // table mod our new table size.
            for(int i = 0; i < thief.length;i++)
            {
                if(thief[i] != null)
                {
                    add(thief[i].state, thief[i].depth, thief[i].value());
                    if(thief[i].next != null)
                    {
                        //if next is not null, we traverse and add all those elements as well.
                        Node<GameState> h = thief[i];
                        while(thief[i].next != null)
                        {
                            add(thief[i].next.state, thief[i].next.depth, thief[i].next.value());
                            thief[i] = thief[i].next;
                        }
                        thief[i] = h;
                    }
                }
            }
            return true;
        }

        //assert classInv();
        return false;
    }

    // You may want to write some additional helper methods.

    //helper method to compute the hash index given a state and taking into account
    //the hashcode.
    /**
     * Returns: Hash index corresponding to the current hashcode.
     */
    public int val(GameState p)
    {
        //used to compute the hash index from the hash code.
        if(p instanceof TicTacToe)
        {
            if(Math.abs(((TicTacToe) p).board().hashCode()) < buckets.length) return Math.abs(((TicTacToe) p).board().hashCode());
            else return Math.abs(((TicTacToe) p).board().hashCode()) % (buckets.length);
        }
        else
        {
            if(Math.abs(((Pente) p).board().hashCode()) < buckets.length) return Math.abs(((Pente) p).board().hashCode());
            else return Math.abs(((Pente) p).board().hashCode()) % (buckets.length);
        }
    }




    //second layer of filtration to ensure state equality when incrementing size.
    //Implemented in tic-tac-toe. Boardstate does not take into account row one
    //in indexing so this ensures cases where resizing happens as a second layer
    // of security.
    /**
     * Manual Size Computation. Returns the Size. A tool to prevent inappropriate size increases
     * in the add method.
     */
    public int manual() {
        int count = 0;
        for (int i = 0; i < buckets.length; i++) {
            if (buckets[i] != null) {
                count++;
                Node<GameState> H = buckets[i];
                while(buckets[i].next != null)
                {
                    buckets[i] = buckets[i].next;
                    count++;
                }
                buckets[i] = H;
            }
        }
        return count;
    }




    /**
     * Estimate clustering. With a good hash function, clustering
     * should be around 1.0. Higher values of clustering lead to worse
     * performance.
     */
    double estimateClustering() {
        final int N = 500;
        int m = buckets.length, n = size;
        double sum2 = 0;
        for (int i = 0; i < N; i++) {
            int j = Math.abs((i * 82728353) % buckets.length);
            int count = 0;
            Node<GameState> node = buckets[j];
            while (node != null) {
                count++;
                node = node.next;
            }
            sum2 += count*count;
        }
        double alpha = (double)n/m;
        return sum2/(N * alpha * (1 - 1.0/m + alpha));
    }
}
