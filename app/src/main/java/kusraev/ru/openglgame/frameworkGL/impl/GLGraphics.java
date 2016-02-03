/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.frameworkGL.impl;

import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 1
 */
public class GLGraphics {

    GLSurfaceView glView;
    GL10 gl;
    final int VERTEX_SIZE = (2 + 2) * 4;
    FloatBuffer vertices;
    ShortBuffer indices;

    GLGraphics(GLSurfaceView glView) {
        this.glView = glView;

        Vertices vert = new Vertices(this, 4, 6, false, true);
        vert.setVertices(new float[]{0.0f, 0.0f, 0.0f, 1.0f,
                Texture.dstX - 1, 0.0f, 1.0f, 1.0f,
                Texture.dstX - 1, Texture.dstY - 1, 1.0f, 0.0f,
                0.0f, Texture.dstY - 1, 0.0f, 0.0f}, 0, 16);
        vert.setIndices(new short[]{0, 1, 2, 2, 3, 0}, 0, 6);
        vertices = vert.vertices;
        indices = vert.indices;
    }

    public GL10 getGL() {
        return gl;
    }

    void setGL(GL10 gl) {
        this.gl = gl;
    }

    public int getWidth() {
        return glView.getWidth();
    }

    public int getHeight() {
        return glView.getHeight();
    }


}


