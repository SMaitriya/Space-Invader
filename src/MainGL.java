import javax.swing.*;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.*;
import javax.swing.JFrame;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;

public class MainGL implements GLEventListener {
    private Game game;
    private static GLCanvas canvas;
    private static FPSAnimator animator;
    private Texture backgroundTexture;  // Texture pour le fond

    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Space Invader");
        canvas = new GLCanvas(); // Création du canvas OpenGL
        MainGL mainGL = new MainGL();
        canvas.addGLEventListener(mainGL); // Ajout du GLEventListener pour l'affichage
        frame.add(canvas);
        frame.setSize(1200, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        FPSAnimator animator = new FPSAnimator(canvas, 60); // Crée un animateur pour afficher à 60 FPS
        animator.start();

        // Ajout du KeyListener pour gérer les événements clavier
        mainGL.game = new Game();
        canvas.addKeyListener(mainGL.game);
        canvas.setFocusable(true);
        canvas.requestFocus();
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        // Chargement de la texture du fond
        try {
            backgroundTexture = TextureIO.newTexture(new File("src/img/space.jpg"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialisation de l'objet Game, qui sera utilisé pour la logique du jeu
        game = new Game();
        canvas.addKeyListener(game); // Ajout du KeyListener après l'initialisation de l'objet game
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Gérer les ressources au moment de la fermeture
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT); // Efface l'écran

        // Dessiner l'image de fond
        drawBackground(gl);

        if (game != null) {
            game.update(); // Met à jour la logique du jeu
            game.draw(gl); // Dessine l'état du jeu
        }
    }

    private void drawBackground(GL2 gl) {
        if (backgroundTexture != null) {
            backgroundTexture.enable(gl);
            backgroundTexture.bind(gl);

            // Dessiner un quadrilatère couvrant l'écran avec la texture
            gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex2f(-1.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex2f(1.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex2f(1.0f, 1.0f);
            gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex2f(-1.0f, 1.0f);
            gl.glEnd();

            backgroundTexture.disable(gl);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height); // Ajuste la vue du canvas
    }

    public static void switchToWinCanvas() {
        SwingUtilities.invokeLater(() -> {
            if (animator != null && animator.isAnimating()) {
                animator.stop();
            }

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(canvas);
            GLCanvas winCanvas = new GLCanvas();
            WinScreen winScreen = new WinScreen();
            winCanvas.addGLEventListener(winScreen);

            frame.remove(canvas);
            frame.add(winCanvas);
            frame.revalidate();
            frame.repaint();

            FPSAnimator winAnimator = new FPSAnimator(winCanvas, 60);
            winAnimator.start();

            winCanvas.setFocusable(true);
            winCanvas.requestFocus();
        });
    }

    public static void switchToLoseCanvas() {
        SwingUtilities.invokeLater(() -> {
            if (animator != null && animator.isAnimating()) {
                animator.stop();
            }

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(canvas);
            GLCanvas loseCanvas = new GLCanvas();
            LoseScreen loseScreen = new LoseScreen();
            loseCanvas.addGLEventListener(loseScreen);

            frame.remove(canvas);
            frame.add(loseCanvas);
            frame.revalidate();
            frame.repaint();

            FPSAnimator loseAnimator = new FPSAnimator(loseCanvas, 60);
            loseAnimator.start();

            loseCanvas.setFocusable(true);
            loseCanvas.requestFocus();
        });
    }
}
