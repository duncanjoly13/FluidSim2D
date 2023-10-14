package cfbastian.fluidsim2d;

import cfbastian.fluidsim2d.simulation.Renderable;

import java.util.Arrays;

public class Renderer {

    static int[] pixels = new int[Application.width * Application.height];

    public int[] render(Renderable simulation) {
        Arrays.fill(pixels, 0xFF101010);
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
