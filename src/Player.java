import com.jogamp.opengl.GL2;



public class Player {
    private float x, y, size;
    private boolean moveLeft, moveRight;

    // Constructeur qui initialise la position, taille et direction du joueur
    public Player() {
        this.x = 0.0f;
        this.y = -0.7f;
        this.size = 0.04f;
        this.moveLeft = false;
        this.moveRight = false;
    }

    // Met à jour la position du joueur en fonction de la direction
    public void update() {
        if (moveLeft && x > -0.9f) x -= 0.04f; // Déplace à gauche si possible
        if (moveRight && x < 0.9f) x += 0.04f; // Déplace à droite si possible
    }

    public void draw(GL2 gl) {
        float time = (float) System.currentTimeMillis() / 1000.0f;

        // bouclier
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        float shieldPulse = 0.15f + 0.05f * (float)Math.sin(time * 2.0f);
        gl.glColor4f(0.0f, 0.8f, 1.0f, shieldPulse);
        drawShield(gl, size * 1.8f);

        // Corps principal
        gl.glColor3f(0.0f, 0.9f, 0.6f); // Cyan néon
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(x - size, y);
        gl.glVertex2f(x + size, y);
        gl.glVertex2f(x + size * 0.7f, y + size);
        gl.glVertex2f(x - size * 0.7f, y + size);
        gl.glEnd();

        // Bordure lumineuse du corps
        gl.glLineWidth(2.0f);
        gl.glColor4f(0.0f, 1.0f, 0.8f, 0.8f);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(x - size, y);
        gl.glVertex2f(x + size, y);
        gl.glVertex2f(x + size * 0.7f, y + size);
        gl.glVertex2f(x - size * 0.7f, y + size);
        gl.glEnd();

        drawHolographicCockpit(gl, time);

        // Ailes énergétiques
        drawEnergyWing(gl, -1.0f, time); // Gauche
        drawEnergyWing(gl, 1.0f, time);  // Droite

        float thrustIntensity = 0.8f + 0.2f * (float)Math.sin(time * 10.0f);
        drawMainThrusters(gl, thrustIntensity);

        drawSideThrusters(gl, thrustIntensity);

        drawTechDetails(gl);

        gl.glDisable(GL2.GL_BLEND);
    }

    private void drawShield(GL2 gl, float radius) {
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i += 5) {
            double angle = Math.toRadians(i);
            float variation = 1.0f + 0.05f * (float)Math.sin(angle * 6);
            gl.glVertex2f(
                    x + (float)Math.cos(angle) * radius * variation,
                    y + (float)Math.sin(angle) * radius * variation
            );
        }
        gl.glEnd();
    }

    private void drawHolographicCockpit(GL2 gl, float time) {
        gl.glColor4f(0.0f, 1.0f, 0.8f, 0.4f + 0.2f * (float)Math.sin(time * 3.0f));
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2f(x - size * 0.4f, y + size * 0.3f);
        gl.glVertex2f(x + size * 0.4f, y + size * 0.3f);
        gl.glVertex2f(x, y + size * 0.8f);
        gl.glEnd();

        gl.glColor4f(0.0f, 1.0f, 1.0f, 0.3f);
        float scanOffset = (time % 1.0f) * size;
        for (int i = 0; i < 3; i++) {
            gl.glBegin(GL2.GL_LINES);
            float yPos = y + size * 0.3f + scanOffset + (i * size * 0.2f);
            if (yPos < y + size * 0.8f) {
                gl.glVertex2f(x - size * 0.3f, yPos);
                gl.glVertex2f(x + size * 0.3f, yPos);
            }
            gl.glEnd();
        }
    }

    private void drawEnergyWing(GL2 gl, float side, float time) {
        // Base de l'aile
        gl.glColor4f(0.0f, 0.9f, 0.6f, 0.9f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(x + side * size, y);
        gl.glVertex2f(x + side * size * 1.5f, y - size * 0.5f);
        gl.glVertex2f(x + side * size * 1.2f, y - size * 0.8f);
        gl.glVertex2f(x + side * size * 0.5f, y - size * 0.5f);
        gl.glEnd();

        // Effet d'énergie pulsante
        gl.glColor4f(0.0f, 1.0f, 0.8f, 0.3f + 0.2f * (float)Math.sin(time * 5.0f + side));
        gl.glBegin(GL2.GL_LINES);
        for (int i = 0; i < 5; i++) {
            float offset = i * 0.2f * size;
            gl.glVertex2f(x + side * (size + offset), y - offset * 0.5f);
            gl.glVertex2f(x + side * (size + offset + 0.2f), y - (offset + 0.2f) * 0.5f);
        }
        gl.glEnd();
    }

    private void drawMainThrusters(GL2 gl, float intensity) {
        float[][] thrusterPositions = {{-0.3f, 0.3f}, {0.3f, 0.3f}};

        for (float[] pos : thrusterPositions) {
            // Noyau du propulseur
            gl.glColor4f(0.0f, 1.0f, 0.8f, 0.9f);
            drawThrusterCore(gl, x + pos[0] * size, y - size * 0.8f, size * 0.15f);

            // Effet de plasma
            gl.glColor4f(0.0f, 1.0f, 0.6f, intensity * 0.5f);
            drawPlasmaEffect(gl, x + pos[0] * size, y - size * 0.8f, size * 0.5f * intensity);
        }
    }

    private void drawThrusterCore(GL2 gl, float cx, float cy, float radius) {
        gl.glBegin(GL2.GL_POLYGON);
        for (int i = 0; i < 360; i += 10) {
            double angle = Math.toRadians(i);
            gl.glVertex2f(
                    cx + (float)Math.cos(angle) * radius,
                    cy + (float)Math.sin(angle) * radius
            );
        }
        gl.glEnd();
    }

    private void drawPlasmaEffect(GL2 gl, float cx, float cy, float length) {
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(cx, cy);
        for (int i = -30; i <= 30; i += 5) {
            double angle = Math.toRadians(i);
            float l = length * (0.8f + 0.2f * (float)Math.random());
            gl.glVertex2f(
                    cx + (float)Math.cos(angle) * size * 0.1f,
                    cy - l
            );
        }
        gl.glEnd();
    }

    private void drawSideThrusters(GL2 gl, float intensity) {
        float[][] sideThrusters = {
                {-1.4f, -0.5f}, {1.4f, -0.5f},
                {-1.2f, -0.3f}, {1.2f, -0.3f}
        };

        for (float[] pos : sideThrusters) {
            gl.glColor4f(0.0f, 1.0f, 0.8f, intensity * 0.7f);
            drawThrusterCore(gl, x + pos[0] * size, y + pos[1] * size, size * 0.08f);
        }
    }

    private void drawTechDetails(GL2 gl) {
        // Lignes technologiques décoratives
        gl.glColor4f(0.0f, 1.0f, 0.8f, 0.5f);
        gl.glLineWidth(1.0f);

        // Circuit patterns
        gl.glBegin(GL2.GL_LINES);
        for (int i = 1; i <= 3; i++) {
            float yOffset = y + size * 0.2f * i;
            gl.glVertex2f(x - size * 0.5f, yOffset);
            gl.glVertex2f(x + size * 0.5f, yOffset);
        }
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