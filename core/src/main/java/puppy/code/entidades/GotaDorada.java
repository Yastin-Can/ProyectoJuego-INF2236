package puppy.code.entidades;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import puppy.code.Tarro;
import puppy.code.movimiento.MovimientoStrategy;

/**
 * Gota especial que recupera vidas (y opcionalmente otorga puntos extra) al colisionar con el tarro (GM-4).
 *
 * @author Benja
 */
public class GotaDorada extends Gota {

    private static final int VIDAS_POR_DEFECTO = 1;
    private static final int PUNTOS_EXTRA_POR_DEFECTO = 0;

    private int vidasExtra;
    private int puntosExtra;
    private Sound sonidoRecoleccion;

    public GotaDorada(float x, float y, Texture textura, MovimientoStrategy movimiento) {
        this(x, y, textura, movimiento, VIDAS_POR_DEFECTO, PUNTOS_EXTRA_POR_DEFECTO, null);
    }

    public GotaDorada(float x, float y, Texture textura, MovimientoStrategy movimiento, Sound sonidoRecoleccion) {
        this(x, y, textura, movimiento, VIDAS_POR_DEFECTO, PUNTOS_EXTRA_POR_DEFECTO, sonidoRecoleccion);
    }

    public GotaDorada(float x, float y, Texture textura, MovimientoStrategy movimiento, int vidasExtra) {
        this(x, y, textura, movimiento, vidasExtra, PUNTOS_EXTRA_POR_DEFECTO, null);
    }

    public GotaDorada(float x, float y, Texture textura, MovimientoStrategy movimiento, int vidasExtra, int puntosExtra, Sound sonidoRecoleccion) {
        super(x, y, textura, movimiento);
        this.vidasExtra = (vidasExtra > 0) ? vidasExtra : VIDAS_POR_DEFECTO;
        this.puntosExtra = Math.max(0, puntosExtra);
        this.sonidoRecoleccion = sonidoRecoleccion;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        if (tarro != null) {
            tarro.sumarVida(this.vidasExtra);
            if (this.puntosExtra > 0) {
                tarro.sumarPuntos(this.puntosExtra);
            }
            if (this.sonidoRecoleccion != null) {
                this.sonidoRecoleccion.play();
            }
        }
    }

    public int getVidasExtra() {
        return this.vidasExtra;
    }

    public void setVidasExtra(int vidasExtra) {
        if (vidasExtra > 0) {
            this.vidasExtra = vidasExtra;
        }
    }

    public int getPuntosExtra() {
        return this.puntosExtra;
    }

    public void setPuntosExtra(int puntosExtra) {
        if (puntosExtra >= 0) {
            this.puntosExtra = puntosExtra;
        }
    }
}