package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        //Initialize the Pente Game, checking if the colsize and the rowsize of the table is initialized
        //correctly.
        Pente game = new Pente();
        assertEquals(8, game.colSize());
        assertEquals(8, game.rowSize());
    }

    @Test
    void testCopyConstructor() {
        // test case 1: can a board state be copied to an equal state
        Pente game1 = new Pente();
        game1.makeMove(new Position(2, 2));
        Pente game2 = new Pente(game1);
        assertTrue(game1.stateEqual(game2));

        // TODO 2: write at least 3 test cases
        // test case 2: copy a board with more than one element.
        Pente game4 = new Pente();
        game4.makeMove(new Position(2, 2));
        game4.makeMove(new Position(3,7));
        game4.makeMove(new Position(5,5));
        Pente game5 = new Pente(game4);
        assertTrue(game4.stateEqual(game5));
        // test case 3: copy a board with capturedPairs
        Pente game6 = new Pente();
        game6.makeMove(new Position(2,2));
        game6.makeMove(new Position(3,2));
        game6.makeMove(new Position(5,2));
        game6.makeMove(new Position(4,2));
        //testing equality with our definition that takes into account
        //the captured pairs amount.
        Pente game7 = new Pente(game6);
        assertTrue(game6.equals(game7));
        // test case 4: copy a board with no elements.
        Pente game8 = new Pente();
        Pente game9 = new Pente(game8);
        assertTrue(game8.equals(game9));
        assertEquals(game8.rowSize(),game9.rowSize());
        assertEquals(game8.colSize(),game9.colSize());
    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test case 1: do two equal nonempty board states have the same hash code
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());

        // test case 2: non-equal board states should be very unlikely to have the
        // same hash code.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());
        // TODO 3: write at least 3 test cases
        //test case 3: do copied board have the same hashcode
        Pente game4 = new Pente();
        game4.makeMove(new Position(2, 2));
        game4.makeMove(new Position(3,7));
        game4.makeMove(new Position(5,5));
        Pente game5 = new Pente(game4);
        assertEquals(game4.hashCode(), game5.hashCode());
        //test case 4: board with captured pairs and equivalent states
        //have accurate hashcodes.
        Pente game6 = new Pente();
        game6.makeMove(new Position(2,2));
        game6.makeMove(new Position(3,2));
        game6.makeMove(new Position(5,2));
        game6.makeMove(new Position(4,2));
        Pente game7 = new Pente(game6);
        assertEquals(game6.hashCode(),game7.hashCode());
        //test case 5: multiple position table with different positions have
        // unique hashcode.
        Pente game8 = new Pente();
        game8.makeMove(new Position(2,4));
        game8.makeMove(new Position(7,2));
        game8.makeMove(new Position(2,1));
        game8.makeMove(new Position(1,6));
        Pente game9 = new Pente();
        game9.makeMove(new Position(5,2));
        game9.makeMove(new Position(2,3));
        game9.makeMove(new Position(4,1));
        game9.makeMove(new Position(3,4));
        assertNotEquals(game8.hashCode(),game9.hashCode());

    }

    @Test
    void makeMove() {
        // test case 1: a simple move
        Pente game = new Pente();
        Position p = new Position(2, 2);
        game.makeMove(p); // a move by the first player
        assertEquals(PlayerRole.SECOND_PLAYER, game.currentPlayer());
        assertEquals(2, game.currentTurn());
        assertFalse(game.hasEnded());
        assertEquals(0, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), game.board().get(p));

        // test case 2: try a capture
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(2, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.board().get(new Position(2, 3))); // the stone should be removed
        assertEquals(0, game.board().get(new Position(2, 4))); // the stone should be removed

        // TODO 4: write at least 3 test cases
        //test case 3: try a capture where the captured moves into the position
        Pente game2 = new Pente();
        Position p2 = new Position(3, 7);
        game2.makeMove(p2);//first player move
        game2.makeMove(new Position(3, 6)); // a move by the second player
        game2.makeMove(new Position(7, 5)); // a move by the first player
        game2.makeMove(new Position(3, 5)); // a move by the second player
        game2.makeMove(new Position(3, 4)); // a move by the first player which should capture the pair [(3, 4), (3, 5)]
        assertEquals(1, game2.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game2.board().get(new Position(3, 6))); // the stone should be removed
        assertEquals(0, game2.board().get(new Position(3, 5))); // the stone should be removed
        assertFalse(game2.hasEnded());
        //test case 4: complex moves.
        Pente game3 = new Pente();
        Position p3 = new Position(3, 3);
        game3.makeMove(p3);//move by first player
        game3.makeMove(new Position(1, 5)); // a move by the second player
        game3.makeMove(new Position(4, 2)); // a move by the first player
        game3.makeMove(new Position(2, 2)); // a move by the second player
        assertEquals(PlayerRole.FIRST_PLAYER, game3.currentPlayer());
        assertEquals(5, game3.currentTurn());
        assertFalse(game3.hasEnded());
        assertEquals(0, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game3.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), game3.board().get(p3));

        //test case 5: ending with one player winning. Game has ended is evaluated to true.
        Pente game4 = new Pente();
        Position p4 = new Position(3, 3);
        game4.makeMove(p4);//move by first player
        game4.makeMove(new Position(1, 2)); // a move by the second player
        game4.makeMove(new Position(4, 2)); // a move by the first player
        game4.makeMove(new Position(1, 3)); // a move by the second player
        game4.makeMove(new Position(3, 1)); // a move by the second player
        game4.makeMove(new Position(1, 4)); // a move by the first player
        game4.makeMove(new Position(0, 1)); // a move by the second player
        game4.makeMove(new Position(1, 5)); // a move by the second player
        game4.makeMove(new Position(1, 1)); // a move by the first player
        game4.makeMove(new Position(1, 6)); // a move by the second player
        game4.makeMove(new Position(0, 0)); // a move by the first player
        assertTrue(game4.hasEnded());
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), game4.board().get(p4));



    }

    @Test
    void capturedPairsNo() {
        // test case 1: are captured pairs registered?
        Pente game = new Pente();
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(3, 3)); // a move by the second player
        game.makeMove(new Position(4, 2)); // a move by the first player
        game.makeMove(new Position(3, 4)); // a move by the second player
        game.makeMove(new Position(3, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));


        // TODO 5: write at least 3 test cases
        // test 2: multiple captures register
        Pente game2 = new Pente();
        Position p2 = new Position(3, 3);
        game2.makeMove(p2);//move by first player
        game2.makeMove(new Position(3, 2)); // a move by the second player
        game2.makeMove(new Position(1, 1)); // a move by the first player
        game2.makeMove(new Position(3, 1)); // a move by the second player
        game2.makeMove(new Position(3, 0)); // a move by the first player which should capture
        assertEquals(1, game2.capturedPairsNo(PlayerRole.FIRST_PLAYER));

        //test 3: when initialized, the captured pairs should be zero.
        Pente game3 = new Pente();
        assertEquals(0,game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0,game3.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        //test 4: captures work at corners of the board.
        Pente game4 = new Pente();
        game4.makeMove(new Position(0, 0)); // a move by the first player
        game4.makeMove(new Position(1, 1)); // a move by the second player
        game4.makeMove(new Position(5, 5)); // a move by the first player
        game4.makeMove(new Position(2, 2)); // a move by the second player
        game4.makeMove(new Position(3, 3)); // a move by the first pair [(1, 1), (2, 2)]
        assertEquals(1, game4.capturedPairsNo(PlayerRole.FIRST_PLAYER));


    }

    @Test
    void hasEnded() {
        // test case 1: is a board with 5 in a row an ended game?
        Pente game = new Pente();
        assertFalse(game.hasEnded());
        game.makeMove(new Position(1, 1)); // a move by the first player
        game.makeMove(new Position(2, 1)); // a move by the second player
        game.makeMove(new Position(1, 2)); // a move by the first player
        game.makeMove(new Position(2, 2)); // a move by the second player
        game.makeMove(new Position(1, 3)); // a move by the first player
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(1, 4)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(1, 5)); // a move by the first player
        assertTrue(game.hasEnded());

        // TODO 6: write at least 3 test cases
        //test 2: The game is not ended when initialized/testing whether hasended state ever
        //evaluates false
        Pente game2 = new Pente();
        assertFalse(game2.hasEnded());

        // test case 3: Typical case of not ending. A position added but not enough in a row
        // to end. a line of 2, not long enough to end.
        Pente game3 = new Pente();
        game3.makeMove(new Position(1, 1)); // a move by the first player
        game3.makeMove(new Position(2, 1)); // a move by the second player
        game3.makeMove(new Position(1, 2)); // a move by the first player
        game3.makeMove(new Position(2, 2)); // a move by the second player
        assertFalse(game3.hasEnded());

        //test case 4: capturing but an inadequate amount/ not enough captured.
        Pente game4 = new Pente();
        Position p4 = new Position(1, 5);
        game4.makeMove(p4);//first player move
        game4.makeMove(new Position(1,4)); // a move by the second player
        game4.makeMove(new Position(3,4)); // a move by the first player
        game4.makeMove(new Position(1, 3)); // a move by the second player
        game4.makeMove(new Position(1, 2)); // a move by the first player which should capture the pair [(3, 4), (3, 5)]
        assertEquals(1, game4.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertFalse(game4.hasEnded());

    }

    @Test
    void stateEqual() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test 1: games with equal board states should be stateEqual()
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));

        // test 2: games with unequal board states should not be stateEqual()
        game3.makeMove(new Position(0, 0));
        assertFalse(game1.stateEqual(game3));
        // TODO 7: write at least 3 test cases

        //test case 3: do copied board have the same hashcode
        Pente game4 = new Pente();
        game4.makeMove(new Position(3, 2));
        game4.makeMove(new Position(5,2));
        game4.makeMove(new Position(1,5));
        Pente game5 = new Pente(game4);
        assertTrue(game4.equals(game5));
        //test case 4: board with captured pairs and equivalent states
        //have accurate hashcodes. also ensures that removal and update to pairs captured.
        Pente game6 = new Pente();
        game6.makeMove(new Position(2,2));
        game6.makeMove(new Position(3,2));
        game6.makeMove(new Position(5,2));
        game6.makeMove(new Position(4,2));
        Pente game7 = new Pente(game6);
        assertTrue(game6.equals(game7));
        //test case 5: multiple position table with different positions should evaluate to
        // not equal.
        Pente game8 = new Pente();
        game8.makeMove(new Position(2,4));
        game8.makeMove(new Position(7,2));
        game8.makeMove(new Position(2,1));
        game8.makeMove(new Position(1,6));
        Pente game9 = new Pente();
        game9.makeMove(new Position(5,2));
        game9.makeMove(new Position(2,3));
        game9.makeMove(new Position(4,1));
        game9.makeMove(new Position(3,4));
        assertFalse(game8.equals(game9));
    }
}