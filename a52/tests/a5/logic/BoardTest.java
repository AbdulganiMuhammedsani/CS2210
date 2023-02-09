package a5.logic;

import static org.junit.jupiter.api.Assertions.*;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void testEquals() {
        //testing on boards that are the appropriate size of pente and tic-tac-toe
        Board board1 = new Board(8, 8);
        Board board2 = new Board(8, 8);
        Board board3 = new Board(8, 8);
        Board board4 = new Board(8, 8);
        Board b1 = new Board(3, 3);
        Board b2 = new Board(3, 3);
        Board b3 = new Board(3, 3);
        Board b4 = new Board(3, 3);
        // test 1: empty boards should be equal
        assertEquals(board1, board2);
        assertEquals(b1, b2);

        // test 2: adding a piece should break equality
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        b2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1, board2);
        assertNotEquals(b1, b2);

        // TODO 1: write at least 3 test cases
        //test 3: board with equivalent pieces should be equal
        board1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        b1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertEquals(b1, b2);
        assertEquals(b1, b2);
        //test 4: board with positions in equivalent states but different players in that position
        //should result in not equal
        board3.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1,board3);
        assertNotEquals(b1,b3);

        //test 5:complex multi-positional boards results are equivalent.
        //testing for both tictactoe and pente with corresponding sizes.
        board3.place(new Position(2, 4), PlayerRole.FIRST_PLAYER);
        board3.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        board3.place(new Position(3, 4), PlayerRole.FIRST_PLAYER);
        board3.place(new Position(3, 2), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(2, 1), PlayerRole.FIRST_PLAYER);
        b3.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(1, 0), PlayerRole.FIRST_PLAYER);
        b3.place(new Position(0, 2), PlayerRole.SECOND_PLAYER);


        board4.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        board4.place(new Position(2, 4), PlayerRole.FIRST_PLAYER);
        board4.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        board4.place(new Position(3, 4), PlayerRole.FIRST_PLAYER);
        board4.place(new Position(3, 2), PlayerRole.SECOND_PLAYER);

        b4.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(2, 1), PlayerRole.FIRST_PLAYER);
        b4.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(1, 0), PlayerRole.FIRST_PLAYER);
        b4.place(new Position(0, 2), PlayerRole.SECOND_PLAYER);
        //checking if these boardstates are equivalent. Checking the states for two differrent
        //types of boards of different sizes.
        assertTrue(b3.equals(b4));
        assertTrue(b3.equals(b4));
        //if a single element is added, then, board3/b3 should NOT equal board4/b4.
        board4.place(new Position(4, 2), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(0, 1), PlayerRole.SECOND_PLAYER);
        assertFalse(board3.equals(board4));
        assertFalse(b3.equals(b4));


    }

    @Test
    void testHashCode() {
        Board board1 = new Board(8, 8);
        Board board2 = new Board(8, 8);
        Board board3 = new Board(8, 8);
        Board board4 = new Board(8, 8);
        Board b1 = new Board(3, 3);
        Board b2 = new Board(3, 3);
        Board b3 = new Board(3, 3);
        Board b4 = new Board(3, 3);

        // test 1: equal boards should have the same hash code
        assertEquals(board1.hashCode(), board2.hashCode());
        assertEquals(b1.hashCode(), b2.hashCode());

        // test 2: unequal boards should be very unlikely to have the
        // same hash code
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        b2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1.hashCode(), board2.hashCode());
        assertNotEquals(b2.hashCode(), b1.hashCode());

        // TODO 2: write at least 3 test cases
        //test 3: board with equivalent pieces should be have same hash code
        board1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        b1.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertEquals(b1.hashCode(), b2.hashCode());
        assertEquals(board1.hashCode(), board2.hashCode());
        //test 4: board with positions in equivalent states but different players in that position
        //should result in different hashcode.
        board3.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1.hashCode(),board3.hashCode());
        assertNotEquals(b1.hashCode(),b3.hashCode());

        //test 5:complex multi-positional boards results in the same hashcode.
        //testing for both tictactoe and pente with corresponding sizes.
        board3.place(new Position(2, 4), PlayerRole.FIRST_PLAYER);
        board3.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        board3.place(new Position(3, 4), PlayerRole.FIRST_PLAYER);
        board3.place(new Position(3, 2), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(2, 1), PlayerRole.FIRST_PLAYER);
        b3.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        b3.place(new Position(1, 0), PlayerRole.FIRST_PLAYER);
        b3.place(new Position(0, 2), PlayerRole.SECOND_PLAYER);


        board4.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        board4.place(new Position(2, 4), PlayerRole.FIRST_PLAYER);
        board4.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        board4.place(new Position(3, 4), PlayerRole.FIRST_PLAYER);
        board4.place(new Position(3, 2), PlayerRole.SECOND_PLAYER);

        b4.place(new Position(0, 0), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(2, 1), PlayerRole.FIRST_PLAYER);
        b4.place(new Position(1, 1), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(1, 0), PlayerRole.FIRST_PLAYER);
        b4.place(new Position(0, 2), PlayerRole.SECOND_PLAYER);
        //checking if these hashcodes are equivalent.
        //check tictactoe sized board and pente sized board.
        assertEquals(b3.hashCode(),b4.hashCode());
        assertEquals(board3.hashCode(),board4.hashCode());
        //if a single element is added, then, board3/b3 should NOT have
        //the same hashcode board4/b4.
        board4.place(new Position(4, 2), PlayerRole.SECOND_PLAYER);
        b4.place(new Position(0, 1), PlayerRole.SECOND_PLAYER);
        assertNotEquals(board3.hashCode(),board4.hashCode());
        assertNotEquals(b3.hashCode(),b4.hashCode());
    }

    @Test
    void get() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        // test 1: Check that an empty space reports 0
        assertEquals(0, board.get(p));

        // test 2: Check that a placed piece is seen by get()
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void place() {
        // test 1: do placed pieces show up where they are placed?
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        assertEquals(0, board.get(p));
        board.place(p, PlayerRole.SECOND_PLAYER);
        assertEquals(PlayerRole.SECOND_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void erase() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        board.place(p, PlayerRole.SECOND_PLAYER);

        // test 1: do pieces get erased?
        board.erase(p);
        assertEquals(0, board.get(p));
    }

    @Test
    void rowSize() {
        Board board = new Board(5, 6);
        assertEquals(5, board.rowSize());
    }

    @Test
    void colSize() {
        Board board = new Board(5, 6);
        assertEquals(6, board.colSize());
    }

    @Test
    void validPos() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1: is a valid position valid?
        assertTrue(board.validPos(p));
        board.place(p, PlayerRole.FIRST_PLAYER);

        // test 2: is an invalid position invalid?
        p = new Position(5, 5);
        assertFalse(board.validPos(p));
    }

    @Test
    void onBoard() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1: is a valid empty position on board?
        assertTrue(board.onBoard(p));
        // test 2: is a valid nonempty position on board?
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertTrue(board.onBoard(p));
        // test 3: is an invalid position on board?
        p = new Position(5, 5);
        assertFalse(board.onBoard(p));
    }
}