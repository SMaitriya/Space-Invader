
import javax.swing.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.*;
import javax.swing.JFrame;
import java.awt.*;
import java.util.Objects;

public class MainGL implements GLEventListener {
    private Game game;
    private static GLCanvas canvas;
    private static FPSAnimator animator;



    public static void main(String[] args) {
        // Création de la fenêtre
        JFrame frame = new JFrame("Space Invader");
        canvas = new GLCanvas(); // Création du canvas OpenGL
        MainGL mainGL = new MainGL();
        canvas.addGLEventListener(mainGL); // Ajout du GLEventListener pour l'affichage
        frame.add(canvas);
        frame.setSize(800, 600);
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
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Définir la couleur de fond (noir)

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

        if (game != null) {
            game.update(); // Met à jour la logique du jeu
            game.draw(gl); // Dessine l'état du jeu
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