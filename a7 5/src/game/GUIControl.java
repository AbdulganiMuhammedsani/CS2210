package game;

import cms.util.maybe.Maybe;
import cms.util.maybe.NoMaybeValue;
import gui.GUI;

import javax.swing.*;

/** Methods for controlling the interaction between the main thread and the GUI, if a GUI is present. */
public class GUIControl {

    /** No objects. */
    private GUIControl(){}

    /** Register that an animation is starting on the GUI, if the GUI is present.
     *  It is the job of the GUI to stop the animation.
     */
    public static void startAnimation(Maybe<GUI> gui) {
        gui.thenDo(g -> g.startAnimating());
    }
    /** Wait until the GUI animation, if any, is complete. */

    public static void waitForAnimation(Maybe<GUI> gui) {
        // TODO: Avoid inefficient spinning while waiting for GUI
        gui.thenDo(g -> {
            //Initialize a GUI mutex as null.
            GUI mutex = null;
            try{
                //we set the mutex to be the gui given as parameter
                mutex = gui.get();
            }
            catch(NoMaybeValue e)
            {

            }
            while (g.isAnimating())
        {
            //while animation occurs, we wait for a notify when the condition has changed.
            //Once the notify signal is sent via GUI.finishAnimating,mutex stops
            // waiting and we check condition again and we continue.
            synchronized (mutex)
            {
                try {
                    mutex.wait();
                }
                catch (InterruptedException a) {

                }
            }
        }
        });

    }
}
