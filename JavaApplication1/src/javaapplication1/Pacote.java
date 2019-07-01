/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author 2016.1.08.042
 */
class Pacote {
    double tamanho;
    double tempoExecucao;
    Boolean runned = false;
    
    public Pacote(double tamanho, double tempo) {
        this.tamanho = tamanho;
        this.tempoExecucao = tempo;
    }

    public double getTempoExecucao() {
        return tempoExecucao;
    }

    public void setTempoExecucao(double tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    public Boolean getRunned() {
        return runned;
    }

    public void setRunned(Boolean runned) {
        this.runned = runned;
    }

    Pacote(double tam_pct) {
        this.tamanho = tam_pct;
    }

    public double getTamanho() {
        return tamanho;
    }

    public void setTamanho(double tamanho) {
        this.tamanho = tamanho;
    }
    
}
