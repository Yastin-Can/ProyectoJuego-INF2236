package puppy.code.entidades;

import com.badlogic.gdx.graphics.Texture;
import puppy.code.Tarro;
import puppy.code.movimiento.MovimientoStrategy;

/**
 * Gota dañina que resta vidas al tarro cuando colisiona con él (GM-4).
 *
 * @author Benja
 */
public class GotaMala extends Gota {

    private static final int DANO_POR_DEFECTO = 1;

    private int dano;

    public GotaMala(float x, float y, Texture textura, MovimientoStrategy movimiento) {
        this(x, y, textura, movimiento, DANO_POR_DEFECTO);
    }

    public GotaMala(float x, float y, Texture textura, MovimientoStrategy movimiento, int dano) {
        super(x, y, textura, movimiento);
        this.dano = (dano > 0) ? dano : DANO_POR_DEFECTO;
    }

    @Override
    public void aplicarEfecto(Tarro tarro) {
        if (tarro != null) {
            if (this.dano == DANO_POR_DEFECTO) {
                tarro.danar();
            } else {
                tarro.danar(this.dano);
            }
        }
    }

    public int getDano() {
        return this.dano;
    }

    public void setDano(int dano) {
        if (dano > 0) {
            this.dano = dano;
        }
    }
}