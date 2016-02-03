package kusraev.ru.openglgame.mygame;


import kusraev.ru.openglgame.framework.Screen;
import kusraev.ru.openglgame.frameworkGL.impl.GLGame;

public class MyGLGame extends GLGame {

    public static final float srcX = 1024;
    public static final float srcY = 768;
    public static final float ANIMATION_TIME = 0.5f;
    public static final int FONT_SIZE = 24;

    @Override
    public Screen getStartScreen() {
        return new LevelScreen(this, getApplicationContext());
    }
}