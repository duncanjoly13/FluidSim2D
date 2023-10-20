package cfbastian.fluidsim2d;

import cfbastian.fluidsim2d.simulation.Renderable;
import cfbastian.fluidsim2d.simulation.sph.SPHParticle;

import java.util.Arrays;

public class Renderer {

    static int[] pixels = new int[Application.width * Application.height];

    SPHParticle p = new SPHParticle(Application.width/2, Application.height/2, 0f, 0f, 0x0022FFFF, Application.height/3f);
    
    public int[] render(Renderable simulation) {
        Arrays.fill(pixels, 0xFF000000);

//        for (int i = 0; i < pixels.length; i++) {
//            int x = i % Application.width, y = i/Application.width;
//            x = (int) (x - p.getX());
//            y = (int) (y - p.getY());
//            pixels[i] = p.getColor() + ((int) (p.getInfluence((float) Math.sqrt(x*x + y*y)) * 255) << 24);
//        }
        
        simulation.render(this);

        return pixels;
    }

    public void init() {

    }

    public void setPixel(int i, int color)
    {
        pixels[i] = color;
    }

    public void setPixel(int x, int y, int color)
    {
        if(x >= 0 && x < Application.width && y >= 0 && y < Application.height) pixels[x + Application.width * y] = color;
    }

    public void drawEmptyRectangle(int x, int y, int w, int h, int color)
    {
        for (int i = x; i < x + w; i++) {
            setPixel(i, y, color);
            setPixel(i, y + h, color);
        }
        for (int i = y; i < y + h; i++) {
            setPixel(x, i, color);
            setPixel(x + w, i, color);
        }
    }

    public void drawRectangle(int x, int y, int w, int h, int color)
    {
        for (int x1 = x; x1 < x + w; x1++) {
            for (int y1 = y; y1 < y + h; y1++) {
                setPixel(x1, y1, color);
            }
        }
    }

    public void drawDottedRectangle(int x, int y, int w, int h, int color)
    {
        for (int i = x; i < x + w; i+=2) {
            setPixel(i, y, color);
            setPixel(i, y + h, color);
        }
        for (int i = y; i < y + h; i+=2) {
            setPixel(x, i, color);
            setPixel(x + w, i, color);
        }
    }

    public void drawCircle(int x, int y, int r, int color)
    {
        int rSqrd = r*r;

        for (int x1 = -r; x1 <= r; x1++) {
            for (int y1 = 0; y1 < (int) (Math.sqrt(rSqrd - x1 * x1) + 0.5); y1++) {
                setPixel(x + x1, y + y1, color);
                setPixel(x + x1, y - y1, color);
                setPixel(x - x1, y + y1, color);
                setPixel(x - x1, y - y1, color);
            }
        }
    }

    public void drawEmptyCircle(int x, int y, int r, int color)
    {
        int rSqrd = r*r;
        for (int x1 = -r; x1 <= r; x1++) {
            int y1 = (int) (Math.sqrt(rSqrd - x1 * x1) + 0.5);
            setPixel(x + x1, y + y1, color);
            setPixel(x + x1, y - y1, color);
        }

        for (int y1 = -r; y1 <= r; y1++) {
            int x1 = (int) (Math.sqrt(rSqrd - y1 * y1) + 0.5);
            setPixel(x + x1, y + y1, color);
            setPixel(x - x1, y + y1, color);
        }
    }
}
