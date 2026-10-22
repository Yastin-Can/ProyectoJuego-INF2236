package puppy.code.niveles;

/** Conoce el orden de construccion, no los valores de cada dificultad. */
public class DirectorNivel {
    public Nivel construir(NivelBuilder builder) {
        builder.reiniciar();
        builder.definirFrecuencia();
        builder.definirProbabilidades();
        builder.definirMovimiento();
        return builder.obtenerNivel();
    }
}
