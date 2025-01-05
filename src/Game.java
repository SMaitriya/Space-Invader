import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;

public class Game implements KeyListener, GLEventListener {
    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bullet> bullets;
    private boolean movingRight;
    private float invaderSpeed;
    private float invaderDropDistance;
    private boolean gameOver;
    private boolean gameWon;

    // Variables to store window dimensions
    private int windowWidth;
    private int windowHeight;

    public Game() {
        player = new Player();
        invaders = new ArrayList<>();
        bullets = new ArrayList<>();
        movingRight = true;
        invaderSpeed = 0.004f;
        invaderDropDistance = 0.1f;  // Distance de chute des envahisseurs lorsqu'ils atteignent le bord de l'écran
        gameOver = false;
        gameWon = false;
        initializeInvaders();
    }

    private void initializeInvaders() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 8; col++) {
                invaders.add(new Invader(
                        -0.8f + col * 0.2f,
                        0.8f - row * 0.2f
                ));
            }
        }
    }

    public void update() {
        if (gameOver || gameWon) return; // Stop the game if it's over or won
        player.update();
        updateBullets();
        updateInvaders();
        checkGameStatus();
    }

    private void checkGameStatus() {
        // Check if all invaders are destroyed (win condition)
        if (invaders.isEmpty()) {
            gameWon = true;

        }

        // Check if any invader has reached the player (game over condition)
        for (Invader invader : invaders) {
            if (Math.abs(invader.getY() - player.getY()) < 0.01f) {  // Vérifie si les Y sont suffisamment proches (tolérance)
                gameOver = true;
                break;
            }
        }
    }


    private void updateBullets() {
        Iterator<Bullet> bulletIt = bullets.iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            if (!bullet.update()) {
                bulletIt.remove();
                continue;
            }

            Iterator<Invader> invaderIt = invaders.iterator();
            while (invaderIt.hasNext()) {
                Invader invader = invaderIt.next();
                if (checkCollision(bullet, invader)) {
                    bulletIt.remove();
                    invaderIt.remove();
                    break;
                }
            }
        }
    }

    private void updateInvaders() {
        if (gameOver || gameWon) return; // Do not update invaders if the game is over or won

        boolean needsToDropAndReverse = false;
        float dx = movingRight ? invaderSpeed : -invaderSpeed;

        for (Invader invader : invaders) {
            invader.move(dx, 0);
            if ((movingRight && invader.getX() > 0.9f) ||
                    (!movingRight && invader.getX() < -0.9f)) {
                needsToDropAndReverse = true;
            }
        }

        if (needsToDropAndReverse) {
            movingRight = !movingRight;
            for (Invader invader : invaders) {
                invader.move(0, -invaderDropDistance);
            }
        }
    }

    private boolean checkCollision(Bullet bullet, Invader invader) {
        return Math.abs(bullet.getX() - invader.getX()) < (bullet.getSize() + invader.getSize()) &&
                Math.abs(bullet.getY() - invader.getY()) < (bullet.getSize() + invader.getSize());
    }

    public void draw(GL2 gl) {

        player.draw(gl);
        for (Bullet bullet : bullets) {
            bullet.draw(gl);
        }
        for (Invader invader : invaders) {
            invader.draw(gl);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver || gameWon) return; // Don't process input if the game is over or won

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMoveLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMoveRight(true);
                break;
            case KeyEvent.VK_SPACE:
                bullets.add(new Bullet(player.getX(), player.getY()));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMoveLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMoveRight(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Cette méthode est obligatoire, mais elle peut rester vide si vous n'en avez pas besoin
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        // Initialize the OpenGL state if needed
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        windowWidth = width;
        windowHeight = height;

        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f); // Set orthographic projection
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        draw(gl);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}