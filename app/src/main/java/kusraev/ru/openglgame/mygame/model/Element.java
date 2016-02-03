/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.mygame.model;

import android.content.Context;

import java.io.IOException;

import kusraev.ru.openglgame.framework.Audio;
import kusraev.ru.openglgame.framework.Sound;
import kusraev.ru.openglgame.frameworkGL.impl.GLGraphics;
import kusraev.ru.openglgame.frameworkGL.impl.Texture;

/**
 * @author 1
 */
public class Element {
    public WordName name;
    public String animType;
    public int cordX;
    public int cordY;
    public Texture texture;
    public Animation anim;
    public boolean disable = false;
    public Sound iSound;

    public Element(Context c, String iSound, String r, String e, String i, String d, String iPath, int x, int y, String animT, GLGraphics glg, Audio audio) throws IOException {
        name = new WordName(r, e, i, d);
        this.iSound = audio.newSound("sounds/" + iSound);
        animType = animT;
        cordX = (int) (x);
        cordY = (int) (y);
        texture = new Texture(iPath, c, glg);
        if (animType.compareTo("blink") == 0)
            anim = new AnimationBright();
        else
            anim = new Animation();
    }
}