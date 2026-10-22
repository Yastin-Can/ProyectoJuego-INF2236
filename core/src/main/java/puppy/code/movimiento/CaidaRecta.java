package puppy.code.movimiento;

import com.badlogic.gdx.math.Rectangle;

/** Estrategia sin estado temporal: puede compartirse entre gotas. */
public class CaidaRecta implements MovimientoStrategy {
    private final float velocidad;

    public CaidaRecta(float velocidad) {
        if (!Float.isFinite(velocidad) || velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva y finita");
        }
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        area.y -= velocidad * delta;
    }
}
