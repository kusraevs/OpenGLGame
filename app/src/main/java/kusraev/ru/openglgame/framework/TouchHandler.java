/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.framework;

import android.view.View.OnTouchListener;

import java.util.List;

import kusraev.ru.openglgame.framework.Input.TouchEvent;

/**
 * @author 1
 */

public interface TouchHandler extends OnTouchListener {

    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
