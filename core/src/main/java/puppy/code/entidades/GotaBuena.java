package puppy.code.entidades;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.Tarro;
import puppy.code.movimiento.MovimientoStrategy;

/**
 * Gota recolectable que otorga puntos al jugador al colisionar con el tarro (GM-4).
 *
 * @author Benja
 */
public class GotaBuena extends Gota {

    private static final int PUNTOS_POR_DEFECTO = 10;

    private int puntos;
    private Sound sonidoRecoleccion;

    public GotaBuena(float x, float y, Texture textura, MovimientoStrategy movimiento) {
        this(x, y, textura, movimiento, PUNTOS_POR_DEFECTO, null);
    }

    public GotaBuena(float x, float y, Texture textura, MovimientoStrategy movimiento, Sound sonidoRecoleccion) {
        this(x, y, textura, movimiento, PUNTOS_POR_DEFECTO, sonidoRecoleccion);
    }

    public GotaBuena(float x, float y, Texture textura, MovimientoStrategy movimiento, int puntos) {
        this(x, y, textura, movimiento, puntos, null);
    }

    public GotaBuena(float x, float y, Texture textura, MovimientoStrategy movimiento, int puntos, Sound sonidoRecoleccion) {
        super(x, y, textura, movimiento);
        this.puntos = (puntos > 0) ? puntos : PUNTOS_POR_DEFECTO;
        this.sonidoRecoleccion = sonidoRecoleccion;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        if (tarro != null) {
            tarro.sumarPuntos(this.puntos);
            if (this.sonidoRecoleccion != null) {
                this.sonidoRecoleccion.play();
            }
        }
    }

    public int getPuntos() {
        return this.puntos;
    }

    public void setPuntos(int puntos) {
        if (puntos > 0) {
            this.puntos = puntos;
        }
    }
}