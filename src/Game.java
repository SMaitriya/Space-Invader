import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;


public class Game implements KeyListener, GLEventListener {
    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bullet> bullets;
    private boolean movingRight;
    private float invaderSpeed;
    private float invaderDropDistance;
    private int windowWidth;
    private int windowHeight;

    public Game() {
        player = new Player();
        invaders = new ArrayList<>();
        bullets = new ArrayList<>();
        movingRight = true;
        invaderSpeed = 0.004f;
        invaderDropDistance = 0.1f;  // Distance de chute des envahisseurs lorsqu'ils atteignent le bord de l'écran
        initializeInvaders();
    }

    // Initialisation des envahisseurs sur plusieurs lignes et colonnes

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

    // Mise à jour de l'état du jeu à chaque frame

    public void update() {
        player.update();
        updateBullets();
        updateInvaders();
        checkGameStatus();
    }

    private void win(){
        MainGL.switchToWinCanvas();
    }

    private void lose() {
        MainGL.switchToLoseCanvas();
    }

    private void checkGameStatus() {
        if (invaders.isEmpty()) {
            win();
        }

        for (Invader invader : invaders) {
            if (Math.abs(invader.getY() - player.getY()) < 0.1f) {
                lose();
                break;
            }
        }
    }


    // Mise à jour des balles tirées par le joueur

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

    // Vérifie la collision entre une balle et un envahisseur

    private boolean checkCollision(Bullet bullet, Invader invader) {
        return Math.abs(bullet.getX() - invader.getX()) < (bullet.getSize() + invader.getSize()) &&
                Math.abs(bullet.getY() - invader.getY()) < (bullet.getSize() + invader.getSize());
    }

    // Dessine tous les éléments du jeu

    public void draw(GL2 gl) {

        player.draw(gl); // Dessine le joueur
        for (Bullet bullet : bullets) {
            bullet.draw(gl);
        }
        for (Invader invader : invaders) {
            invader.draw(gl);
        }
    }


    // Gère les événements clavier (touche pressée)

    @Override
    public void keyPressed(KeyEvent e) {


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
    }

    @Override
    public void init(GLAutoDrawable drawable) {
    }

    // Réglage de la fenêtre OpenGL lors d'un redimensionnement

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

    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}