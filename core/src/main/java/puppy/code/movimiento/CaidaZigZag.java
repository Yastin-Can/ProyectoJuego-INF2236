package puppy.code.movimiento;

import com.badlogic.gdx.math.Rectangle;

/** Cada gota necesita su propia instancia para conservar su fase. */
public class CaidaZigZag implements MovimientoStrategy {
    private final float velocidad;
    private float tiempo;

    public CaidaZigZag(float velocidad) {
        if (!Float.isFinite(velocidad) || velocidad <= 0) {
            throw new IllegalArgumentException("La velocidad debe ser positiva y finita");
        }
        this.velocidad = velocidad;
    }

    @Override
    public void mover(Rectangle area, float delta) {
        float anterior = tiempo;
        tiempo += delta;
        area.y -= velocidad * delta;
        // Diferencia de posiciones sinusoidales: independiente de los FPS.
        area.x += 45f * (float) (Math.sin(tiempo * 4) - Math.sin(anterior * 4));
        area.x = Math.max(0, Math.min(800 - area.width, area.x));
    }
}
