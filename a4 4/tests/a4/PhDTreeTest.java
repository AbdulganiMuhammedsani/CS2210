package a4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;

/** @author Amy Huang, Maya Leong */
public class PhDTreeTest {
    private static final Professor prof1 = new Professor("Amy", "Huang");
    private static final Professor prof2 = new Professor("Maya", "Leong");
    private static final Professor prof3 = new Professor("Matthew", "Hui");
    private static final Professor prof4 = new Professor("Arianna", "Curillo");
    private static final Professor prof5 = new Professor("Michelle", "Gao");
    private static final Professor prof6 = new Professor("Isa", "Siu");

    private static PhDTree tree1() {
        return new PhDTree(prof1, 2023);
    }
    private static PhDTree tree2() {
        return new PhDTree(prof4, 2019);
    }

    private static PhDTree tree3() {
        PhDTree t = new PhDTree(prof1, 1950);
        t.insert(prof1, prof2, 1960);
        t.insert(prof2, prof3, 1970);
        return t;
    }

    @Test
    public void constructorTests() {
        assertEquals("Amy Huang", tree1().toString());
        assertEquals("Arianna Curillo", tree2().toString());
    }

    @Test
    public void getterTests() {
        assertEquals(prof1, tree1().prof());
        // we have not inserted anything into the tree yet
        PhDTree t = new PhDTree(prof1, 2000);
        assertEquals(0, t.numAdvisees());
    }

    @Test
    public void insertTests() {
        // add professor 2 as a child of professor 1
        PhDTree t = new PhDTree(prof1, 1950);
        t.insert(prof1, prof2, 1960);
        t.insert(prof2, prof3, 1970);
        assertEquals(1, t.numAdvisees());
        assertEquals(3, t.size());
        assertEquals("Amy Huang[Maya Leong[Matthew Hui]]", t.toString());
    }

    @Test
    public void findTreeTests() throws NotFound {
        PhDTree tree1 = tree1();
        tree1.insert(prof1, prof2, 1950);
        tree1.insert(prof2, prof3, 1960);
        PhDTree tree4 = new PhDTree(prof2, 1950);
        tree4.insert(prof2, prof3, 1980);
        assertEquals(tree4.prof(), tree1.findTree(prof2).prof());
        assertEquals("Maya Leong[Matthew Hui]", tree1.findTree(prof2).toString());

        assertThrows(NotFound.class, () -> tree2().findTree(prof5));
        assertThrows(NotFound.class, () -> tree1.findTree(prof4));
        assertEquals(1, tree1.findTree(prof3).size());
    }

    @Test
    public void sizeTest() {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        assertEquals(3, t.size());
    }

    @Test
    public void containsTest() {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        assertTrue(t.contains(new Professor("Amy", "Huang")));
        assertFalse(t.contains(prof6));
    }

    @Test
    public void findAcademicLineageTest() throws NotFound {
        PhDTree t = new PhDTree(prof1, 1900);
        t.insert(prof1, prof2, 1920);
        t.insert(prof2, prof3, 1930);
        List<Professor> lineage1 = new LinkedList<>();
        lineage1.add(prof1);
        lineage1.add(prof2);
        lineage1.add(prof3);
        assertEquals(lineage1, t.findAcademicLineage(prof3));
    }

    @Test
    public void commonAncestorTest() throws NotFound {
        PhDTree t = tree3();
        assertEquals(prof2, t.commonAncestor(prof2, prof3));
        assertEquals(prof1, t.commonAncestor(prof1, prof3));
        assertThrows(NotFound.class, () -> t.commonAncestor(prof5, prof3));
    }

    @Test
    public void maxDepthTest() {
        PhDTree t = tree3();
        assertEquals(2, t.maxDepth());
    }

    @Test
    public void getAdvisorTest() throws NotFound {
        PhDTree t = tree3();
        assertEquals(prof2.toString(), t.findAdvisor(prof3).toString());
        assertThrows(NotFound.class, () -> t.findAdvisor(prof1));
    }

    @Test
    public void toStringVerbose() {
        PhDTree t = tree3();
        String[] lines = t.toStringVerbose().split("\n");
        String[] expected = {
            "Amy Huang - 1950",
            "Maya Leong - 1960",
            "Matthew Hui - 1970"
        };
        Arrays.sort(lines);
        Arrays.sort(expected);
        assertTrue(Arrays.equals(lines, expected));
    }
}
