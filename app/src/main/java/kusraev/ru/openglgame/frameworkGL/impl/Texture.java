/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kusraev.ru.openglgame.frameworkGL.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author 1
 */
public class Texture {

    public static final float dstX = 480;
    public static final float dstY = 320;


    private int id;
    private GLGraphics glg;
    private int width;
    private int height;

    Vertices vert;

    public Texture(String path, Context c, GLGraphics glg) throws IOException {
        this.glg = glg;
        id = getIdTexture(path, c);
    }

    public Texture(String path, int fontSize, GLGraphics glg) {
        this.glg = glg;
        id = makeTextTexture(path, fontSize);
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public int loadTexture(Bitmap bitmap) {

        GL10 gl = glg.getGL();
        int textureIds[] = new int[1];
        gl.glGenTextures(1, textureIds, 0);
        int textureId = textureIds[0];

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);//
        setFilters(GL10.GL_LINEAR, GL10.GL_LINEAR);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
        return textureId;
    }

    public void reloadTexture(String s, Context c) throws IOException {
        id = getIdTexture(s, c);
    }

    private void setFilters(int minFilter, int magFilter) {
        GL10 gl = glg.getGL();
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                minFilter);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                magFilter);
    }

    public int makeTextTexture(String text, int fontSize) {
        Bitmap bitmap = Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        bitmap.eraseColor(0);
        // Draw the text
        Paint textPaint = new Paint();
        textPaint.setTextSize(fontSize);
        textPaint.setAntiAlias(true);
        textPaint.setARGB(0xff, 0x00, 0x00, 0x00);
        // draw the text centered
        canvas.drawText(text, 16, 112, textPaint);
        int idTextTexture = loadTexture(bitmap);
        bitmap.recycle();
        return idTextTexture;
    }

    public void renderTexture(int x, int y, float zoomX, float zoomY, float alpha, boolean isBright) {
        GL10 gl = glg.getGL();
        gl.glViewport(0, 0, glg.getWidth(), glg.getHeight());
        //gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, dstX, 0, dstY, 1, -1);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
        gl.glEnable(GL10.GL_BLEND);
        if (!isBright) {
            gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glColor4f(1f, 1f, 1f, alpha);
        } else {
            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
            gl.glColor4f(1f, 1f, 1f, alpha);
        }
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glTranslatef(x, y, 0);
        gl.glScalef(zoomX, zoomY, 0);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        glg.vertices.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, glg.VERTEX_SIZE, glg.vertices);
        glg.vertices.position(2);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, glg.VERTEX_SIZE, glg.vertices);
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT,
                glg.indices);
    }

    public void dispose() {
        GL10 gl = glg.getGL();
        gl.glBindTexture(GL10.GL_TEXTURE_2D, id);
        int[] textureIds = {id};
        gl.glDeleteTextures(1, textureIds, 0);
    }

    private int getIdTexture(String path, Context c) throws IOException {
        int idt;
        InputStream is;
        is = c.getAssets().open(path);
        Bitmap image = BitmapFactory.decodeStream(is);
        width = image.getWidth();
        height = image.getHeight();
        float scaleWidth = ((float) 1024) / width;
        float scaleHeight = ((float) 1024) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedImage = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);


        idt = loadTexture(resizedImage);
        is.close();
        image.recycle();
        resizedImage.recycle();
        return idt;
    }
}
