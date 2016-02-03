/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.mygame;

import android.content.Context;

import java.util.List;

import kusraev.ru.openglgame.framework.Audio;
import kusraev.ru.openglgame.framework.Game;
import kusraev.ru.openglgame.framework.Input.TouchEvent;
import kusraev.ru.openglgame.framework.Screen;
import kusraev.ru.openglgame.frameworkGL.impl.FPSCounter;
import kusraev.ru.openglgame.frameworkGL.impl.GLGame;
import kusraev.ru.openglgame.frameworkGL.impl.GLGraphics;
import kusraev.ru.openglgame.frameworkGL.impl.Texture;
import kusraev.ru.openglgame.mygame.model.Element;
import kusraev.ru.openglgame.mygame.model.Level;

/**
 * @author 1
 */
class LevelScreen extends Screen {

    private GLGraphics glGraphics;
    private Audio audio;
    private Level level;
    private Element element;
    private FPSCounter fpsCounter;
    Context c;

    public LevelScreen(Game game, Context c) {
        super(game);
        glGraphics = ((GLGame) game).getGLGraphics();
        audio =  ((GLGame) game).getAudio();
        level = new Level(c, glGraphics, audio);
        fpsCounter = new FPSCounter();
        this.c = c;
    }

    @Override
    public void present(float deltaTime) {
        fpsCounter.logFrame();
        renderLevel(level);
    }

    @Override
    public void update(float deltaTime) {
        level.updateAnim(deltaTime);
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_DOWN) {
                element = level.getElementToShow();
                if (element != null) {
                    if (inBounds(event, element.cordX, element.cordY, element.texture.getWidth(), element.texture.getHeight())) {
                        element.disable = true;
                        element.anim.start();
                        element.iSound.play(1);
                        level.setNewElementToShow();
                    }
                }
            }
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        //level = new Level(c, glGraphics);
    }

    @Override
    public void dispose() {
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        x *= glGraphics.getWidth() / MyGLGame.srcX;
        width *= glGraphics.getWidth() / MyGLGame.srcX;
        y *= glGraphics.getHeight() / MyGLGame.srcY;
        height *= glGraphics.getHeight() / MyGLGame.srcY;
        if (event.x > x && event.x < x + width - 1
                && event.y > y && event.y < y + height - 1) {
            return true;
        } else {
            return false;
        }
    }


    private void renderLevel(Level level) {
        float coeffX = Texture.dstX / MyGLGame.srcX;
        float coeffY = Texture.dstY / MyGLGame.srcY;
        level.background.renderTexture(0, 0, 1, 1, 1, false);
        for (Element element : level.elements) {
            //if (!element.disable)
            element.texture.renderTexture((int) (element.cordX * coeffX), (int) ((MyGLGame.srcY - element.cordY - element.texture.getHeight()) * coeffY),
                    element.texture.getWidth() / MyGLGame.srcX, element.texture.getHeight() / MyGLGame.srcY, element.anim.getAlpha(), element.anim.getBright());

        }
        if (level.elementToShow != null)
            level.textElToShow.renderTexture((int) (240 - level.elementToShow.name.iron.length() * MyGLGame.FONT_SIZE / 2), 200, 0.5f, 0.5f, 1, false);
        //level.scoreBoard.renderTexture((int)240, 200, 0.5f, 0.5f, 1, false);
    }

}
