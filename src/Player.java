import com.jogamp.opengl.GL2;

public class Player {
    private float x, y, size;
    private boolean moveLeft, moveRight;

    // Constructeur qui initialise la position, taille et direction du joueur
    public Player() {
        this.x = 0.0f;
        this.y = -0.9f;
        this.size = 0.1f;
        this.moveLeft = false;
        this.moveRight = false;
    }

    // Met à jour la position du joueur en fonction de la direction
    public void update() {
        if (moveLeft && x > -0.9f) x -= 0.04f; // Déplace à gauche si possible
        if (moveRight && x < 0.9f) x += 0.04f; // Déplace à droite si possible
    }

    // Dessine le joueur à l'écran sous forme de triangle
    public void draw(GL2 gl) {
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(x - size, y);
        gl.glVertex2f(x + size, y);
        gl.glVertex2f(x, y + size);
        gl.glEnd();
    }

    // Change la direction de mouvement à gauche
    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    // Change la direction de mouvement à droite
    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }

    // Accesseurs pour la position du joueur
    public float getX() { return x; }
    public float getY() { return y; }
}