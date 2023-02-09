package a5.logic;

import a5.util.PlayerRole;

import java.util.Arrays;

/**
 * A mutable representation of an m-by-n board in which each cell
 * can be occupied by a player or be empty
 */
public class Board {
    /**
     * A byte array representing the state of the board.
     * It represents the state of cells on an m-by-n board
     * with a one-dimensional array of length m*n.
     * So a cell at position (rowNo, colNo) on board is stored at
     * {@code boardState[p.row() * n + p.col()]}. Zero elements
     * represent empty locations; pieces of a player are represented
     * the PlayerRole.boardValue of that player.
     */
    private final byte[] boardState;
    final private int rowSize;
    final private int colSize;

    /**
     * Creates a new {@code rowSize}-by-{@code colSize} board.
     */
    public Board(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        this.boardState = new byte[rowSize * colSize];
    }

    /**
     * Creates a new board that deep copies a provided board.
     *
     * @param board the board to copy from
     */
    public Board(Board board) {
        this.rowSize = board.rowSize;
        this.colSize = board.colSize;
        this.boardState = Arrays.copyOf(board.boardState, board.boardState.length);
    }

    /**
     * Returns: the board state of a cell.
     * Returns 0 if it is empty; otherwise, the returned value matches
     * the player role's {@code boardValue}.
     * Requires: {@code p} is on board
     * @param p the position of the cell
     */
    public int get(Position p) {
        return boardState[index(p)];
    }

    /**
     * Effect: place a stone as {@code currentPlayer} on board at position {@code p}.
     * Requires: {@code p} is on board and {@code get(p)} == 0
     *
     * @param p the position to place a stone
     * @param currentPlayer role of the current player
     */
    protected void place(Position p, PlayerRole currentPlayer) {
        int value = currentPlayer.boardValue();
        assert 0 < value && value < 127;
        assert onBoard(p);
        boardState[index(p)] = (byte)value;
    }

    /**
     * Effect: set the cell at position {@code p} to empty.
     * Requires: {@code p} is on board
     */
    protected void erase(Position p) {
        boardState[index(p)] = 0;
    }

    /** The array index at which position p is stored in the board array. */
    private int index(Position p) {
        return p.row() * colSize + p.col();
    }

    public int rowSize() {
        return rowSize;
    }

    public int colSize() {
        return colSize;
    }

    /**
     * Board equality is determined using state equality.
     */
    @Override
    public boolean equals(Object o) {
        // TODO 1
        //checking class and asserting equality if the boardstate
        //arrays are equivalent for both objects.
        if (getClass() != o.getClass()) {
            return false;
        }
        Board x  = (Board)o;

            return Arrays.equals(x.boardState,this.boardState);
    }

    @Override
    public int hashCode() {
        // TODO 2
        //Get a generated hashcode for a unique boardstate, then multiply
        //by a big prime number, as another layer of complexity, to avoid
        //the same result for negative hashvalues, it takes into account
        //if it is positive or negative.
        if(Arrays.hashCode(boardState) < 0)
        {
            return Arrays.hashCode(boardState) * 31249;
        }
        else
        {
            return Arrays.hashCode(boardState) * 52567;
        }
    }

    /**
     * Returns: true if {@code p} is a valid cell to place stones.
     */
    public boolean validPos(Position p) {
        return (onBoard(p) && boardState[index(p)] == 0);
    }

    /**
     * Returns: true if {@code p} is within the board.
     */
    public boolean onBoard(Position p) {
        return (p.row() >= 0 && p.row() < rowSize &&
                p.col() >= 0 && p.col() < colSize);
    }
}