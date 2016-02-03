/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.frameworkGL.impl;


import android.util.Log;

/**
 * @author1
 */
public class FPSCounter {
    long startTime = System.nanoTime();
    int frames = 0;

    public void logFrame() {
        frames++;
        if (System.nanoTime() - startTime >= 1000000000) {
            Log.d("FPSCounter", "fps: " + frames);
            frames = 0;

            startTime = System.nanoTime();
        }
    }
}

