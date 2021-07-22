package semaforo;

public enum EstadoFilosofo {
    PENSANDO(0),
    COM_FOME(1),
    COMENDO(2);

    public int estadoFilosofo;

    EstadoFilosofo(int estadoFilosofo) {
        this.estadoFilosofo = estadoFilosofo;
    }

    public int getValue() {
        return this.estadoFilosofo;
    }
}
