package puppy.code.niveles;

import java.util.function.Supplier;
import puppy.code.movimiento.MovimientoStrategy;
import puppy.code.movimiento.CaidaRecta;

public class NivelLloviznaBuilder implements NivelBuilder {
    private float intervalo;
    private float mala;
    private float dorada;
    private Supplier<MovimientoStrategy> movimiento;

    @Override
    public void reiniciar() {
        intervalo = 0;
        mala = dorada = -1;
        movimiento = null;
    }

    @Override
    public void definirFrecuencia() { intervalo = 0.65f; }

    @Override
    public void definirProbabilidades() {
        mala = 0.2f;
        dorada = 0.03f;
    }

    @Override
    public void definirMovimiento() { movimiento = () -> new CaidaRecta(220f); }

    @Override
    public Nivel obtenerNivel() {
        if (intervalo <= 0 || mala < 0 || dorada < 0 || movimiento == null) {
            throw new IllegalStateException("Faltan pasos de construccion del nivel");
        }
        return new Nivel("Llovizna", intervalo, mala, dorada, movimiento);
    }
}
