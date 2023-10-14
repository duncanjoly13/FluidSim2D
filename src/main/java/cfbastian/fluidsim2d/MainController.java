package cfbastian.fluidsim2d;

import cfbastian.fluidsim2d.simulation.Simulation;
import cfbastian.fluidsim2d.simulation.iterativemethods.IterativeSimulation;
import cfbastian.fluidsim2d.simulation.sph.SPHParticle;
import cfbastian.fluidsim2d.simulation.sph.SPHSimulation;
import cfbastian.fluidsim2d.simulation.util.Bounds;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

import java.nio.IntBuffer;
import java.util.Arrays;

public class MainController {
    @FXML
    ImageView imageView;

    WritableImage image;
    PixelWriter pixelWriter;
    WritablePixelFormat<IntBuffer> pixelFormat;

    Renderer renderer;
    RenderLoop renderLoop;

    Simulation simulation;

    static int[] pixels = new int[Application.width * Application.height];

    long startTime;

    @FXML
    public void initialize() {
        Arrays.fill(pixels, 0xFF000000);

        renderer = new Renderer();
        renderLoop = new RenderLoop();

        simulation = new IterativeSimulation(new Bounds(0f, 16f, 0f, 9f), 100000);
//        simulation = new SPHSimulation(new Bounds(0f, 16f, 0f, 9f), 1);
//        ((SPHSimulation) simulation).getParticles()[0] = new SPHParticle(16/2f, 9/2f, -4.532852f, 10f, 0xFF22FFFF, 0.5f);

        imageView.setFitWidth(Application.width);
        imageView.setFitHeight(Application.height);
        image = new WritableImage(Application.width, Application.height);
        pixelWriter = image.getPixelWriter();
        pixelFormat = WritablePixelFormat.getIntArgbInstance();
        pixelWriter.setPixels(0, 0, Application.width, Application.height, pixelFormat, pixels, 0, Application.width);
        imageView.setImage(image);

        renderer.init();

        startTime = System.nanoTime();
        renderLoop.start();
    }

    private class RenderLoop extends AnimationTimer {

        double timer;
        int frames;

        double lastTime = startTime / 1000000000D;
        @Override
        public void handle(long now) {
            double elapsedTime = (now - startTime)/1000000000D;

            simulation.update((float) (elapsedTime - lastTime));
            pixels = renderer.render(simulation);

            pixelWriter.setPixels(0, 0, Application.width, Application.height, pixelFormat, pixels, 0, Application.width);
            imageView.setImage(image);

            lastTime = elapsedTime;

            frames++;

            while(elapsedTime - timer > 1)
            {
                System.out.println(frames);
                timer++;
                frames = 0;
            }
        }
    }
}