/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.mygame.model;

import kusraev.ru.openglgame.mygame.model.Animation;

/**
 * @author 1
 */
public class AnimationBright extends Animation {
    public AnimationBright() {
        super();
        bright = true;
    }

    @Override
    public void update(float deltaTime) {
        if (isStarted) {
            if (animationTime > fullTime) {
                isStarted = false;
                bright = false;
                return;
            }
            animationTime += deltaTime;
            alpha = (3 - 3 * animationTime / fullTime) % 1;
        }
    }

    @Override
    public boolean getBright() {
        return bright;
    }
}
