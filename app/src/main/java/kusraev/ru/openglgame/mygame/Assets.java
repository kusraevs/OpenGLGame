package kusraev.ru.openglgame.mygame;

import android.content.Context;

import java.io.IOException;

import kusraev.ru.openglgame.framework.Sound;
import kusraev.ru.openglgame.frameworkGL.impl.GLGame;
import kusraev.ru.openglgame.frameworkGL.impl.Texture;

/**
 * Created by Soslan on 12.08.13.
 */
public class Assets {
    public static Texture logo;
    public static Texture mainMenu;
    public static Sound[] levelSounds = new Sound[10];

    public static void load(GLGame game) {
        try {
            mainMenu = new Texture("GameMenu.png", game.getApplicationContext(), game.getGLGraphics());
            logo = new Texture("GameLogo.png", game.getApplicationContext(), game.getGLGraphics());

            for (int i = 0;i < 10;i++){
                String path = "sounds/i_0" + (i + 1) + ".MP3";
                if (i == 9)
                    path = "sounds/i_" + (i + 1) + ".MP3";
                levelSounds[i] = game.getAudio().newSound(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
