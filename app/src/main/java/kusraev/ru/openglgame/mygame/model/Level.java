
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.mygame.model;

import android.content.Context;
import android.content.res.XmlResourceParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import kusraev.ru.openglgame.R;
import kusraev.ru.openglgame.framework.Audio;
import kusraev.ru.openglgame.frameworkGL.impl.GLGraphics;
import kusraev.ru.openglgame.frameworkGL.impl.Texture;
import kusraev.ru.openglgame.mygame.MyGLGame;

/**
 * @author 1
 */
public class Level {
    public ArrayList<Element> elements;
    public Element elementToShow;
    public Texture background;
    public Texture textElToShow;
    public Texture scoreBoard;
    private GLGraphics glg;

    public Level(Context c, GLGraphics glg, Audio audio) {
        try {
            elements = new ArrayList<Element>();
            loadUIElements(c, glg, audio);
            background = new Texture("background.png", c, glg);
            scoreBoard = new Texture("0", MyGLGame.FONT_SIZE, glg);
        } catch (IOException ex) {
            Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        this.glg = glg;
        setNewElementToShow();
    }

    public Element getElementToShow() {
        return elementToShow;
    }

    public void setNewElementToShow() {
        elementToShow = getFirstAbleElement();
        if (elementToShow != null)
            textElToShow = new Texture(elementToShow.name.iron, MyGLGame.FONT_SIZE, glg);
    }

    public Element getFirstAbleElement() {
        for (int i = 0; i < elements.size(); i++)
            if (!elements.get(i).disable)
                return elements.get(i);
        return null;
    }

    public void updateAnim(float deltaTime) {
        for (Element element : elements) {
            element.anim.update(deltaTime);
        }
    }


    private void addElement(Context c, String iSound, String r, String e, String i, String d, String iPath, int cordX, int cordY, String animType, GLGraphics glg, Audio audio) throws IOException {
        Element el = new Element(c, iSound, r, e, i, d, iPath, cordX, cordY, animType, glg, audio);
        elements.add(el);
    }


    private void loadUIElements(Context c, GLGraphics glg, Audio audio) {
        final int ATT_NOT_EXIST_ID = -1;
        int eventType;
        String rName = null, eName = null, oName = null, dName = null;
        String iSound = null;
        String imagePath = null, animType = null;
        int cordX = 0, cordY = 0;
        int i = 0;
        XmlResourceParser xpp = c.getResources().getXml(R.xml.level1);
        try {
            eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().compareTo("word") == 0) {
                        rName = xpp.getAttributeValue(0);
                        eName = xpp.getAttributeValue(1);
                        oName = xpp.getAttributeValue(2);
                        dName = xpp.getAttributeValue(3);
                    }
                    if (xpp.getName().compareTo("image") == 0) {
                        imagePath = xpp.getAttributeValue(0);
                        cordX = xpp.getAttributeIntValue(1, ATT_NOT_EXIST_ID);
                        cordY = xpp.getAttributeIntValue(2, ATT_NOT_EXIST_ID);
                        animType = xpp.getAttributeValue(3);
                    }
                    if (xpp.getName().compareTo("iron") == 0) {
                        iSound = xpp.getAttributeValue(0);
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (xpp.getName().compareTo("word") == 0) {
                        addElement(c, iSound, rName, eName, oName, dName, imagePath, cordX, cordY, animType, glg, audio);
                    }
                }
                eventType = xpp.next();
            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
