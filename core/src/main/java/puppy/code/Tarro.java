package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import puppy.code.entidades.Dibujable;

/**
 * Entidad que representa el tarro controlado por el jugador.
 * Implementa Dibujable (GM-4) y aplica encapsulamiento estricto (GM-5).
 *
 * @author Benja
 */
public class Tarro implements Dibujable {

    private static final int VIDAS_INICIALES = 3;
    private static final int PUNTOS_INICIALES = 0;
    private static final int VELOCIDAD_INICIAL = 400;
    private static final int TIEMPO_HERIDO_MAX_DEFAULT = 50;
    private static final float ANCHO_PANTALLA = 800f;
    private static final float ANCHO_TARRO = 64f;
    private static final float ALTO_TARRO = 64f;
    private static final float POS_Y_INICIAL = 20f;
    private static final int SACUDIDA_HERIDO = 5;

    private Rectangle bucket;
    private Texture bucketImage;
    private Sound sonidoHerido;
    private int vidas;
    private int puntos;
    private int velx;
    private boolean herido;
    private int tiempoHeridoMax;
    private int tiempoHerido;

    public Tarro(Texture tex, Sound ss) {
        this.bucketImage = tex;
        this.sonidoHerido = ss;
        this.vidas = VIDAS_INICIALES;
        this.puntos = PUNTOS_INICIALES;
        this.velx = VELOCIDAD_INICIAL;
        this.herido = false;
        this.tiempoHeridoMax = TIEMPO_HERIDO_MAX_DEFAULT;
        this.tiempoHerido = 0;
        this.bucket = new Rectangle(
                ANCHO_PANTALLA / 2f - ANCHO_TARRO / 2f,
                POS_Y_INICIAL,
                ANCHO_TARRO,
                ALTO_TARRO
        );
    }

    public void crear() {
        this.bucket = new Rectangle(
                ANCHO_PANTALLA / 2f - ANCHO_TARRO / 2f,
                POS_Y_INICIAL,
                ANCHO_TARRO,
                ALTO_TARRO
        );
    }

    public void sumarPuntos(int pp) {
        if (pp > 0) {
            this.puntos += pp;
        }
    }

    public void sumarVida() {
        sumarVida(1);
    }

    public void sumarVida(int cantidad) {
        if (cantidad > 0) {
            this.vidas += cantidad;
        }
    }

    public void danar() {
        danar(1);
    }

    public void danar(int dano) {
        if (dano > 0) {
            this.vidas = Math.max(0, this.vidas - dano);
            this.herido = true;
            this.tiempoHerido = this.tiempoHeridoMax;
            if (this.sonidoHerido != null) {
                this.sonidoHerido.play();
            }
        }
    }

    @Override
    public void dibujar(SpriteBatch batch) {
        if (!this.herido) {
            batch.draw(this.bucketImage, this.bucket.x, this.bucket.y);
        } else {
            batch.draw(
                    this.bucketImage,
                    this.bucket.x,
                    this.bucket.y + MathUtils.random(-SACUDIDA_HERIDO, SACUDIDA_HERIDO)
            );
            this.tiempoHerido--;
            if (this.tiempoHerido <= 0) {
                this.herido = false;
            }
        }
    }

    public void actualizarMovimiento() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.bucket.x -= this.velx * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.bucket.x += this.velx * Gdx.graphics.getDeltaTime();
        }
        if (this.bucket.x < 0) {
            this.bucket.x = 0;
        }
        if (this.bucket.x > ANCHO_PANTALLA - ANCHO_TARRO) {
            this.bucket.x = ANCHO_PANTALLA - ANCHO_TARRO;
        }
    }

    public void destruir() {
        if (this.bucketImage != null) {
            this.bucketImage.dispose();
        }
    }

    public int getVidas() {
        return this.vidas;
    }

    public int getPuntos() {
        return this.puntos;
    }

    /**
     * Retorna una copia defensiva del área de colisión para preservar el encapsulamiento (GM-5).
     *
     * @return copia del Rectangle del tarro
     */
    public Rectangle getArea() {
        return new Rectangle(this.bucket);
    }

    public boolean estaHerido() {
        return this.herido;
    }

    public boolean estaVivo() {
        return this.vidas > 0;
    }

    public int getVelx() {
        return this.velx;
    }

    public void setVelx(int velx) {
        if (velx > 0) {
            this.velx = velx;
        }
    }
}