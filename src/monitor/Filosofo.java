package monitor;


import semaforo.EstadoFilosofo;

public class Filosofo extends Thread {
    int id;
    long TEMPO_OPERACAO = 1000L;

    public Filosofo(String nome, int id) {
        super(nome);
        this.id = id;
    }

    public void comFome() {
        monitor.Main.estado[this.id] = EstadoFilosofo.COM_FOME.getValue();
        System.out.println(getName() + " está FAMINTO");
    }

    public void comer() {
        monitor.Main.estado[this.id] = EstadoFilosofo.COMENDO.getValue();
        System.out.println(getName() + " está COMENDO");
        try {
            Thread.sleep(TEMPO_OPERACAO);
        } catch (InterruptedException e) {
            System.out.println("ERROR >> " + e.getMessage());
        }
    }

    public void pensar() {
        monitor.Main.estado[this.id] = EstadoFilosofo.PENSANDO.getValue();
        System.out.println(getName() + " está PENSANDO");
        try {
            Thread.sleep(TEMPO_OPERACAO);
        } catch (InterruptedException e) {
            System.out.println("ERROR >> " + e.getMessage());
        }
    }

    public synchronized void largarGarfo() {
        this.pensar();
        monitor.Main.filosofos[vizinhoEsquerda()].tentaObterGarfos();
        monitor.Main.filosofos[vizinhoDireita()].tentaObterGarfos();
    }

    public synchronized void  pegarGarfo() {
        this.comFome();
        tentaObterGarfos();
    }

    public void tentaObterGarfos() {
        if (monitor.Main.estado[this.id] == EstadoFilosofo.COM_FOME.getValue()
                && monitor.Main.estado[vizinhoEsquerda()] != EstadoFilosofo.COMENDO.getValue()
                && monitor.Main.estado[vizinhoDireita()] != EstadoFilosofo.COMENDO.getValue()) {
            this.comer();
        } else {
            System.out.println(getName() + " nao conseguiu comer");
        }
    }

    @Override
    public void run() {
        try {
            this.pensar();
            while (true) {
                this.pegarGarfo();
                Thread.sleep(TEMPO_OPERACAO);
                this.largarGarfo();
            }
        } catch (InterruptedException ex) {
            System.out.println("ERROR >> " + ex.getMessage());
            return;
        }
    }

    private int vizinhoDireita() {
        return  (this.id + 1) % 5;
    }

    private int vizinhoEsquerda() {
        if (this.id == 0) {
            return 4;
        } else {
            return  (this.id - 1) % 5;
        }
    }
}

