package puppy.code.niveles;

public interface NivelBuilder {
    void reiniciar();
    void definirFrecuencia();
    void definirProbabilidades();
    void definirMovimiento();
    Nivel obtenerNivel();
}
