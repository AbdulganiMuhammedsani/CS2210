package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;
import a5.util.GameResult;


/**
 * A Pente game, where players take turns to place stones on board.
 * When consecutive two stones are surrounded by the opponent's stones on two ends,
 * these two stones are removed (captured).
 * A player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {


    /**
     * PairsCaptured. Number corresponds to the boardvalue captured
     * PairsCaptured1, for example, the PairsCaptured by player1
     * that are the Player 2 pieces.
     */
    private int PairsCaptured1;
    private int PairsCaptured2;

    /**
     * Create an 8-by-8 Pente game.
     */
    public Pente() {
        super(8, 8, 5);
        //initializing pairs values as one,
        //important to keep track of such elements.
        PairsCaptured1 = 0;
        PairsCaptured2 = 0;
        // TODO 1
    }

    /**
     * Creates: a copy of the game state.
     */
    public Pente(Pente game) {
        super(game);
        //when copying, we need to keep track of the values
        //of pairs captured.
        this.PairsCaptured1 = game.PairsCaptured1;
        this.PairsCaptured2 = game.PairsCaptured2;
        // TODO 2
    }


    /**
     *
     * @return the pieces captured by the player two that are player one pieces.
     */
    public int pc1()
    {
        //retrieve the value Pairscaptured1.
        return PairsCaptured1;
    }


    /**
     *
     * @return the pieces captured by the player one that are player two pieces.
     */
    public int pc2()
    {
        //retrieve the value Pairscaptured1.
        return PairsCaptured2;
    }


    @Override
    public boolean makeMove(Position p) {
        // TODO 3
        //We initialize the points of all the 8 possible directions,then we check them,
        //aware of the fact that it must be in the same direction, we nest by checking
        //1, if the direction one off from our position is on the board.if it is, then
        //we check the values so that they do not equal zero or current player, so we ensure
        //its the other players without specifying a number so it applies to both.
        //we do this nested and check in order if this is current player, next is not, next after is not,
        // and finally the last is current player. Lastly in each of there straight line checks, we check the case
        //of current player moving into a capture. so it checks if one direction is current player, and a position after this is not,
        // and the the opposite direction of our piece is not current player.

        if(!board().validPos(p))
        {
            return false;
        }
        //if there are two positoins of current player that brackets the value
        //board.erase those positons and change turn.
        board().place(p,currentPlayer());
        Position up = new Position(p.row()-1,p.col());
        Position down = new Position(p.row()+1,p.col());
        Position left = new Position(p.row(),p.col()-1);
        Position right = new Position(p.row(),p.col()+1);
        Position upleft = new Position(p.row()-1,p.col()-1);
        Position upright = new Position(p.row()-1,p.col()+1);
        Position downleft = new Position(p.row()+1,p.col()-1);
        Position downright = new Position(p.row()+1,p.col()+1);

        //case where other player moves into the captured zone, vs the player themselves move into
        //a position to cature.
        if(board().onBoard(up) && board().get(up) != 0 && board().get(up) != currentPlayer().boardValue())
        {
            if(board().onBoard(down) && board().get(down) != 0 && board().get(down) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(down.row() + 1, p.col()))
                        && board().get(new Position(down.row() + 1, p.col())) != 0
                        && board().get(new Position(down.row() + 1, p.col())) != currentPlayer().boardValue()) {
                    if(board().get(down) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(down) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(down);

                }
            }
            if(board().onBoard(new Position(up.row()-1,p.col()))
                    && board().get(new Position(up.row()-1,p.col())) != 0
                    && board().get(new Position(up.row()-1,p.col())) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(up.row()-2,p.col()))
                        && board().get(new Position(up.row()-2,p.col())) != 0
                        && board().get(new Position(up.row()-2,p.col())) == currentPlayer().boardValue())
                {
                    if(board().get(up) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(up) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(up);
                    board().erase(new Position(up.row()-1,p.col()));

                }
            }
        }
        if(board().onBoard(down) && board().get(down) != 0 && board().get(down) != currentPlayer().boardValue())
        {
            if(board().onBoard(up) && board().get(up) != 0 && board().get(up) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(up.row() - 1, p.col()))
                        && board().get(new Position(up.row() - 1, p.col())) != 0
                        && board().get(new Position(up.row() - 1, p.col())) != currentPlayer().boardValue()) {
                    if(board().get(up) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(up) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(up);

                }
            }
            if(board().onBoard(new Position(down.row()+1,p.col()))
                    && board().get(new Position(down.row()+1,p.col())) != 0
                    && board().get(new Position(down.row()+1,p.col())) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(down.row()+2,p.col()))
                        && board().get(new Position(down.row()+2,p.col())) != 0
                        && board().get(new Position(down.row()+2,p.col())) == currentPlayer().boardValue())
                {
                    if(board().get(down) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(down) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(down);
                    board().erase(new Position(down.row()+1,p.col()));

                }
            }
        }
        if(board().onBoard(left) && board().get(left) != 0 && board().get(left) != currentPlayer().boardValue())
        {
            if(board().onBoard(right) && board().get(right) != 0 && board().get(right) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(p.row(), right.col()+1))
                        && board().get(new Position(p.row(), right.col()+1)) != 0
                        && board().get(new Position(p.row(), right.col()+1)) != currentPlayer().boardValue()) {
                    if(board().get(right) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(right) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(right);

                }
            }
            if(board().onBoard(new Position(p.row(),left.col()-1))
                    && board().get(new Position(p.row(),left.col()-1)) != 0
                    && board().get(new Position(p.row(),left.col()-1)) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(p.row(),left.col()-2))
                        && board().get(new Position(p.row(),left.col()-2)) != 0
                        && board().get(new Position(p.row(),left.col()-2)) == currentPlayer().boardValue())
                {
                    if(board().get(left) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(left) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(left);
                    board().erase(new Position(p.row(),left.col()-1));

                }
            }
        }
        if(board().onBoard(right) && board().get(right) != 0 && board().get(right) != currentPlayer().boardValue())
        {
            if(board().onBoard(left) && board().get(left) != 0 && board().get(left) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(p.row(), left.col()-1))
                        && board().get(new Position(p.row(), left.col()-1)) != 0
                        && board().get(new Position(p.row(), left.col()-1)) != currentPlayer().boardValue()) {
                    if(board().get(left) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(left) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(left);

                }
            }
            if(board().onBoard(new Position(p.row(),right.col()+1))
                    && board().get(new Position(p.row(),right.col()+1)) != 0
                    && board().get(new Position(p.row(),right.col()+1)) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(p.row(),right.col()+2))
                        && board().get(new Position(p.row(),right.col()+2)) != 0
                        && board().get(new Position(p.row(),right.col()+2)) == currentPlayer().boardValue())
                {
                    if(board().get(right) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(right) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(right);
                    board().erase(new Position(p.row(),right.col()+1));

                }
            }
        }
        if(board().onBoard(upleft) && board().get(upleft) != 0 && board().get(upleft) != currentPlayer().boardValue())
        {
            if(board().onBoard(downright) && board().get(downright) != 0 && board().get(downright) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(downright.row()+1, downright.col()+1))
                        && board().get(new Position(downright.row()+1, downright.col()+1)) != 0
                        && board().get(new Position(downright.row()+1, downright.col()+1)) != currentPlayer().boardValue()) {
                    if(board().get(downright) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(downright) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(downright);

                }
            }
            if(board().onBoard(new Position(upleft.row()-1,upleft.col()-1))
                    && board().get(new Position(upleft.row()-1,upleft.col()-1)) != 0
                    && board().get(new Position(upleft.row()-1,upleft.col()-1)) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(upleft.row()-2,upleft.col()-2))
                        && board().get(new Position(upleft.row()-2,upleft.col()-2)) != 0
                        && board().get(new Position(upleft.row()-2,upleft.col()-2)) == currentPlayer().boardValue())
                {
                    if(board().get(upleft) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(upleft) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(upleft);
                    board().erase(new Position(upleft.row()-1,upleft.col()-1));

                }
            }
        }
        if(board().onBoard(upright) && board().get(upright) != 0 && board().get(upright) != currentPlayer().boardValue())
        {
            if(board().onBoard(downleft) && board().get(downleft) != 0 && board().get(downleft) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(downleft.row()+1, downleft.col()-1))
                        && board().get(new Position(downleft.row()+1, downleft.col()-1)) != 0
                        && board().get(new Position(downleft.row()+1, downleft.col()-1)) != currentPlayer().boardValue()) {
                    if(board().get(downleft) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(downleft) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(downleft);

                }
            }
            if(board().onBoard(new Position(upright.row()-1,upright.col()+1))
                    && board().get(new Position(upright.row()-1,upright.col()+1)) != 0
                    && board().get(new Position(upright.row()-1,upright.col()+1)) != currentPlayer().boardValue())
            {

                if(board().onBoard(new Position(upright.row()-2,upright.col()+2))
                        && board().get(new Position(upright.row()-2,upright.col()+2)) != 0
                        && board().get(new Position(upright.row()-2,upright.col()+2)) == currentPlayer().boardValue())
                {
                    if(board().get(upright) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(upright) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(upright);
                    board().erase(new Position(upright.row()-1,upright.col()+1));

                }
            }
        }
        if(board().onBoard(downright) && board().get(downright) != 0 && board().get(downright) != currentPlayer().boardValue()) {
            if(board().onBoard(upleft) && board().get(upleft) != 0 && board().get(upleft) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(upleft.row()-1, upleft.col()-1))
                        && board().get(new Position(upleft.row()-1, upleft.col()-1)) != 0
                        && board().get(new Position(upleft.row()-1, upleft.col()-1)) != currentPlayer().boardValue()) {
                    if(board().get(upleft) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(upleft) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(upleft);

                }
            }
            if (board().onBoard(new Position(downright.row() + 1, downright.col() + 1))
                    && board().get(new Position(downright.row() + 1, downright.col() + 1)) != 0
                    && board().get(new Position(downright.row() + 1, downright.col() + 1)) != currentPlayer().boardValue()) {
                if (board().onBoard(new Position(downright.row() + 2, downright.col() + 2))
                        && board().get(new Position(downright.row() + 2, downright.col() + 2)) != 0
                        && board().get(new Position(downright.row() + 2, downright.col() + 2)) == currentPlayer().boardValue()) {
                    if (board().get(downright) == 1) {
                        PairsCaptured2++;
                    }
                    if (board().get(downright) == 2) {
                        PairsCaptured1++;
                    }
                    board().erase(downright);
                    board().erase(new Position(downright.row() + 1, downright.col() + 1));

                }
            }
        }
        if(board().onBoard(downleft) && board().get(downleft) != 0 && board().get(downleft) != currentPlayer().boardValue()) {
            if(board().onBoard(upright) && board().get(upright) != 0 && board().get(upright) == currentPlayer().boardValue())
            {
                if (board().onBoard(new Position(upright.row()-1, upright.col()+1))
                        && board().get(new Position(upright.row()-1, upright.col()+1)) != 0
                        && board().get(new Position(upright.row()-1, upright.col()+1)) != currentPlayer().boardValue()) {
                    if(board().get(upright) == 1)
                    {
                        PairsCaptured2++;
                    }
                    if(board().get(upright) == 2)
                    {
                        PairsCaptured1++;
                    }
                    board().erase(p);
                    board().erase(upright);

                }
            }
            if (board().onBoard(new Position(downleft.row() + 1, downleft.col() - 1))
                    && board().get(new Position(downleft.row() + 1, downleft.col() - 1)) != 0
                    && board().get(new Position(downleft.row() + 1, downleft.col() - 1)) != currentPlayer().boardValue()) {

                if (board().onBoard(new Position(downleft.row() + 2, downleft.col() - 2))
                        && board().get(new Position(downleft.row() + 2, downleft.col() - 2)) != 0
                        && board().get(new Position(downleft.row() + 2, downleft.col() - 2)) == currentPlayer().boardValue()) {
                    if (board().get(downleft) == 1) {
                        PairsCaptured2++;
                    }
                    if (board().get(downleft) == 2) {
                        PairsCaptured1++;
                    }
                    board().erase(downleft);
                    board().erase(new Position(downleft.row() + 1, downleft.col() - 1));

                }
            }
        }
        changePlayer();
        advanceTurn();

        return true;
    }

    /**
     * Returns: a new game state representing the state of the game after the current player takes a
     * move {@code p}.
     */
    public Pente applyMove(Position p) {
        Pente newGame = new Pente(this);
        newGame.makeMove(p);
        return newGame;
    }

    /**
     * Returns: the number of captured pairs by {@code playerRole}.
     */
    public int capturedPairsNo(PlayerRole playerRole) {
        // TODO 4
        //if the player is 1, and we want how much they capture, we return
        //pairscaptured of it, or other player. The same for the other case.
        if(playerRole.boardValue() == 1)
        {
            return PairsCaptured1;
        }
        if(playerRole.boardValue() == 2)
        {
            return PairsCaptured2;
        }
        return 0;
    }

    @Override
    public boolean hasEnded() {
        // TODO 5
        //player 2 has captured 5 player1 pairs, then player 2 wins.
        if(pc1() == 5)
        {
            this.setResult(GameResult.FIRST_PLAYER_WON);
            return true;
        }
        //player 1 has captured 5 player2 pairs, then player 1 wins.
        if(pc2() == 5)
        {
            this.setResult(GameResult.SECOND_PLAYER_WON);
            return true;
        }
        return super.hasEnded();
    }

    @Override
    public GameType gameType() {
        return GameType.PENTE;
    }


    @Override
    public String toString() {
        String board = super.toString();
        return board + System.lineSeparator() + "Captured pairs: " +
                "first: " + capturedPairsNo(PlayerRole.FIRST_PLAYER) + ", " +
                "second: " + capturedPairsNo(PlayerRole.SECOND_PLAYER);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Pente p = (Pente) o;
        return stateEqual(p);
    }

    /**
     * Returns: true if the two games have the same state.
     */
    protected boolean stateEqual(Pente p) {
        // TODO 6
        //checj if the boardstates are equivalent, as well as the pairs of both players equivalent.
        return p.board().equals(this.board()) && (p.pc1() == this.pc1()) && (p.pc2() == this.pc2());
    }

    @Override
    public int hashCode() {
        // TODO 7
        //incorporate the pairs into the calculation
        //to ensure uniqueness based on the pente board
        //multiply pc my a factor so the computation takes
        //into account this difference.
        int x = 0;
        if(pc2() != 0)
        {
            x = this.pc2() * 2;
        }
        //return the hashcode of the board, while adding in the pairs captured
        //into the computation.
        return this.board().hashCode()+ x + pc1();
    }
}
