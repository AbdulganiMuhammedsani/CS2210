package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Position;
import a5.logic.TicTacToe;
import a5.util.PlayerRole;
import cms.util.maybe.NoMaybeValue;
import org.junit.jupiter.api.Test;

class TranspositionTableTest {

    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        //When initializing a table, size equals zero.
        //initializing a gamestate, and initializing.
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        assertEquals(0, table.size());
        TicTacToe state = new TicTacToe();
    }


    @Test
    void getInfo() throws NoMaybeValue {
        // test case 1: look for a state that is in the table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // test case 2: look for a state not in the table
        TicTacToe stateabsent = state.applyMove(new Position(2, 2));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(stateabsent).get());

        // TODO 2: write at least 3 more test cases
        //test case 3: retrieving in a table with multiple elements, retrieve the correct
        //data.
        TicTacToe stat2 = new TicTacToe();
        TicTacToe stat3 = stat2.applyMove(new Position(2, 2));
        TicTacToe stat4 = stat3.applyMove(new Position(1, 2));
        TicTacToe stat5 = stat4.applyMove(new Position(1, 2));
        table.add(stat5, 7, GameModel.WIN);
        StateInfo info2 = table.getInfo(stat5).get();

        TicTacToe sta2 = new TicTacToe();
        TicTacToe sta3 = sta2.applyMove(new Position(1, 1));
        TicTacToe sta4 = sta3.applyMove(new Position(2, 2));
        TicTacToe sta5 = sta4.applyMove(new Position(2, 1));
        table.add(sta5, 9, 0);
        StateInfo inf3 = table.getInfo(sta5).get();
        //checking the result of the equals
        assertFalse(stat5.equals(sta5.board()));
        assertEquals(9,inf3.depth());
        assertEquals(7,info2.depth());
        //test case 4: getting info if said element is overridden. overridden if new depth is greater than
        //old depth and equivalent. adding equivalent state to ensure overriden.
        //when checking info of stat5, the value of sta5 is presented meaning it was overwritten.
        table.add(sta5, 11, 0);
        assertNotEquals(11, info2.depth());
        //test case 5:
        //nonempty table does not have duplicates. checking that all values are unique that are still
        //in the table.
        assertFalse(state.board().equals(stat5.board()));
        assertFalse(stat5.board().equals(sta5.board()));
        assertFalse(state.board().equals(sta5.board()));

    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1: add a state and check it is in there
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // TODO 3: write at least 3 more test cases
        //test case 2: adding a more complex state.
        TicTacToe state2 = new TicTacToe();
        TicTacToe state3 = state2.applyMove(new Position(2, 2));
        TicTacToe state4 = state3.applyMove(new Position(1, 2));
        TicTacToe state5 = state4.applyMove(new Position(1, 2));
        table.add(state5, 6, GameModel.WIN);

        StateInfo info2 = table.getInfo(state5).get();
        assertEquals(GameModel.WIN, info2.value());
        assertEquals(6, info2.depth());

        //test case 3: states are the same and new one has greater depth. necessitates overwriting
        TicTacToe stat2 = new TicTacToe();
        TicTacToe stat3 = stat2.applyMove(new Position(2, 2));
        TicTacToe stat4 = stat3.applyMove(new Position(1, 2));
        TicTacToe stat5 = stat4.applyMove(new Position(1, 2));
        table.add(stat5, 7, GameModel.WIN);

        StateInfo info3 = table.getInfo(stat5).get();
        assertEquals(GameModel.WIN, info3.value());
        assertEquals(info3.depth(), 7);

        //test case 4: chaining resulting from values computed to be equivalent. anything with same hashcode should
        //chain sample, although not likely in this case, we can use the above example, it has the same hash code. so we
        //can duplicate with a depth that does NOT necessitate overwriting. they are different states but result in the same
        //index, and thus, both datas should be retrievable.
        TicTacToe sta2 = new TicTacToe();
        TicTacToe sta3 = sta2.applyMove(new Position(1, 1));
        TicTacToe sta4 = sta3.applyMove(new Position(2, 2));
        TicTacToe sta5 = sta4.applyMove(new Position(2, 1));
        table.add(sta5, 9, 0);
        StateInfo inf3 = table.getInfo(sta5).get();
        assertEquals(GameModel.WIN, info3.value());
        assertEquals(info3.depth(), 7);
        assertEquals(inf3.depth(), 9);
        assertEquals(info3.value(), GameModel.WIN);
        assertEquals(inf3.value(), 0);


    }
}