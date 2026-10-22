package puppy.code.niveles;

import java.util.Objects;
import java.util.function.Supplier;
import puppy.code.movimiento.MovimientoStrategy;

/** Producto inmutable construido por los builders de nivel. */
public final class Nivel {
    private final String nombre;
    private final float intervalo;
    private final float probabilidadMala;
    private final float probabilidadDorada;
    private final Supplier<MovimientoStrategy> movimiento;

    public Nivel(String nombre, float intervalo, float mala, float dorada,
                 Supplier<MovimientoStrategy> movimiento) {
        if (!Float.isFinite(intervalo) || intervalo <= 0
                || !Float.isFinite(mala) || !Float.isFinite(dorada)
                || mala < 0 || dorada < 0 || mala + dorada > 1) {
            throw new IllegalArgumentException("Configuracion de nivel invalida");
        }
        this.nombre = Objects.requireNonNull(nombre);
        this.intervalo = intervalo;
        this.probabilidadMala = mala;
        this.probabilidadDorada = dorada;
        this.movimiento = Objects.requireNonNull(movimiento);
    }

    public String getNombre() { return nombre; }
    public float getIntervalo() { return intervalo; }
    public float getProbabilidadMala() { return probabilidadMala; }
    public float getProbabilidadDorada() { return probabilidadDorada; }
    public MovimientoStrategy crearMovimiento() { return movimiento.get(); }
}
