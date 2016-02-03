/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.mygame.model;

import kusraev.ru.openglgame.mygame.MyGLGame;

/**
 * @author 1
 */
public class Animation {
    protected boolean isStarted = false;
    protected float fullTime;
    protected float animationTime = 0;
    protected float alpha = 1;
    protected boolean bright = false;

    public Animation() {
        fullTime = MyGLGame.ANIMATION_TIME;
    }

    public void update(float deltaTime) {
        if (isStarted) {
            if (animationTime > fullTime) {
                isStarted = false;
                return;
            }
            animationTime += deltaTime;
            alpha = 1 - animationTime / fullTime;
        }
    }

    public void start() {
        isStarted = true;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean getBright() {
        return false;
    }
}