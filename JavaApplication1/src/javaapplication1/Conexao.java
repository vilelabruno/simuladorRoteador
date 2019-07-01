/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;

/**
 *
 * @author 2016.1.08.042
 */
class Conexao {
    ArrayList<Pacote> pacotes = new ArrayList<>();

    public Conexao(double duracao, double tamPack, double tempoConexao) {
        int qtdPacotes = (int) (duracao/0.02);
        for (int i = 0; i < qtdPacotes; i++){
            
            this.pacotes.add(new Pacote(tamPack, tempoConexao));
            tempoConexao += 0.02;
        }
    }
    public void removePacote(Pacote p){
        this.pacotes.remove(p);
    }
    
    public ArrayList<Pacote> getPacotes() {
        return pacotes;
    }

    public void setPacotes(ArrayList<Pacote> pacotes) {
        this.pacotes = pacotes;
    }
    
}
