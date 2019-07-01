/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author 2016.1.08.042
 */
public class Resolve {
    
    /**
     * @param args the command line arguments
     */
    /* 
 * File:   main.c
 * Author: bruno
 *
 * Created on 26 de Abril de 2019, 16:28
 */


        ArrayList<Conexao> conexoes = new ArrayList<>();

/**
 * 
 * @return numero aleatorio entre (0,1]
 */
    Random gerador;
    public double aleatorio() {
        double u = gerador.nextDouble();    //Retorna um número entre 0 e 1
        //resultado sera algo entre [0,0.999999...] proximo a 1.0
        return u;
    }


/**
 * 
 * @param l parametro da exponencial
 * @return intervalo de tempo, com media tendendo ao intervalo
 * informado pelo usuario.
 */
double chegada_pct(double l) {
	return ((-1.0 / l) * Math.log(aleatorio()));
}

/**
 * 
 * @return tamanho do pacote que acabou de chegar,
 * seguindo a proporcao aproximada de 50% = 550 Bytes,
 * 40% = 40 Bytes e 10% = 1500 Bytes.
 */
double gera_tam_pct() {
	double a = aleatorio();
	//tamanhos convertidos para Mb
	if (a <= 0.5) {
		return ((550.0 * 8.0) / (1000000.0));
	} else if (a <= 0.9) {
		return ((40.0 * 8.0) / (1000000.0));
	}
	return ((1500.0 * 8.0) / (1000000.0));
}

/**
 * 
 * @param a valor
 * @param b valor
 * @return menor dentre os valosres
 */
double minimo(double a, double b) {
	if (a <= b)
		return a;
	return b;
}

/*
 * 
 */

    private Pacote calculaChegadaPacote() {
        Pacote nextPack = null;
        double tempoMin = Double.MAX_VALUE;
        Conexao aux = null;
        for (Conexao c : conexoes){
            if ((c.getPacotes().get(0).getTempoExecucao() < tempoMin)){
                nextPack = c.getPacotes().get(0);
                tempoMin = c.getPacotes().get(0).getTempoExecucao();
                aux = c;
            }
        }
        if (nextPack == null) return null;
        aux.removePacote(nextPack);
        if (aux.getPacotes().isEmpty()){
            conexoes.remove(aux);
        }
        return (nextPack);
    }
    
    double intervaloPacote(double LB, double ocupacao, double tamMedioPacote) {
        double result = (LB * ocupacao) / tamMedioPacote;
        return (1.0 / result);
    }
    
    int run() {
        ArrayList<Pacote> fila =new ArrayList<>();
	Pacote atual = new Pacote(0.0);
        Little en = new Little();
        Little ew_in = new Little();
        Little ew_out = new Little();
        
	//iniciando a semente 
	//para a geracao dos numeros
	//pseudoaleatorios

	//int semente = time(NULL);
	int semente = 1556915527;
	System.out.println("Semente: "+ semente);
	gerador = new Random(semente);
	//tempo atual
	double tempo = 0.0;
	//tempo total
	double tempo_total = 1000;
	System.out.println("Informe o tempo total de simulacao: ");
	//scanf("%lF", &tempo_total);

	//intervalo medio entre chegadas
	double intervalo = 0.000705;
        //double intervalo = intervaloPacote(10, 0.4, 0.00441);
	System.out.println("Informe o intervalo médio de tempo (em segundos) entre pacotes: ");
	//scanf("%lF", &intervalo);
	//ajustando parametro para a exponencial
	intervalo = 1.0 / intervalo;

	//contador de pacotes
	//double cont_pcts = 0.0;

	//tamanho do pacote gerado
	double tam_pct = 0;

	//tamanho do link de saida do roteador
	double link = 10;
	System.out.println("Informe o tamanho do link (Mbps): ");
	//scanf("%lF", &link);

	//fila, onde fila == 0 indica
	//roteador vazio; fila == 1
	//indica 1 pacote, ja em transmissao;
	//fila > 1 indica 1 pacote em transmissao,
	//e demais em espera
	//double fila = 0.0;

	//tempo de chegada do proximo pacote
	//ao sistema
	double chegada_proximo_pct = chegada_pct(intervalo);
	//System.out.println("Chegada do primeiro pacote: ", chegada_proximo_pct);

	//tempo de chegada do proximo pacote
	//cbr ao sistema, com intervalo
	//de 20 ms entre pacotes
	double chegada_proximo_pct_cbr = 0.0;


	//tempo de saida do pacote que esta
	//sendo atendido atualmente
	double saida_pct_atendimento = 0.1;

	//variavel para o calculo da ocupacao
	//do roteador
	double ocupacao = 0.0;
        
        double chegada_prox_conexao = 0.0;
        double intervalo_conexao = 0.8;
        double duracao_conexao = 8.33333;
        
	while (tempo <= tempo_total) {
		//roteador vazio. Logo, avanco
		//no tempo para a chegada do
		//proximo pacote.
		if (fila.size() == 0)
			tempo = minimo(minimo(chegada_proximo_pct_cbr, chegada_proximo_pct), chegada_prox_conexao);
		else {
			//ha fila!
			tempo = minimo(minimo(minimo(chegada_proximo_pct_cbr, chegada_proximo_pct), saida_pct_atendimento), chegada_prox_conexao);
		}
                //System.out.println(tempo);
                if (tempo == chegada_prox_conexao){
                    conexoes.add(new Conexao(duracao_conexao, ((1200.0 * 8.0) / (1000000.0)), tempo));
                    
                    chegada_prox_conexao += intervalo_conexao;
                    chegada_proximo_pct_cbr = tempo;
                }
                	//chegada de pacote
                if (tempo == chegada_proximo_pct) {
			//gero o tamanho do pacote
			tam_pct = gera_tam_pct();

			if (fila.isEmpty()) {
				//gerando o tempo de atendimento
				saida_pct_atendimento = tempo + tam_pct / link;
				//calculo da ocupacao
				ocupacao += saida_pct_atendimento - tempo;
			}
			//pacote colocado na fila
                        fila.add(new Pacote(tam_pct));

			//gerar o tempo de chegada do proximo
			chegada_proximo_pct = tempo + chegada_pct(intervalo);
                        
                        //calcular area anterior (little)
                        
                        en.sum += (tempo - en.lastTime) * en.qtdPk;
                        en.lastTime = tempo;
                        en.qtdPk++;
                        
                        ew_in.sum += (tempo - ew_in.lastTime) * ew_in.qtdPk;
                        ew_in.lastTime = tempo;
                        ew_in.qtdPk++;
		} else if (tempo == chegada_proximo_pct_cbr) {
			//System.out.println("CBR chegou!\n");
			
			//chega pct cbr 1200 Bytes
			tam_pct = ((1200.0 * 8.0) / (1000000.0));

			if (fila.isEmpty()) {
				//gerando o tempo de atendimento
				saida_pct_atendimento = tempo + tam_pct / link;
				//calculo da ocupacao
				ocupacao += saida_pct_atendimento - tempo;
			}
			//pacote colocado na fila
                        Pacote nextPack = calculaChegadaPacote();
                        if (nextPack == null){
                            chegada_proximo_pct_cbr = Double.MAX_VALUE;
                        }else{
                            fila.add(nextPack);
                            chegada_proximo_pct_cbr = nextPack.getTempoExecucao();
                        }

			//gerar o tempo de chegada do proximo
			
                        
                        
                        en.sum +=  (tempo - en.lastTime) * en.qtdPk;
                        en.lastTime = tempo;
                        en.qtdPk++;
                           
                        ew_in.sum += (tempo - ew_in.lastTime) * ew_in.qtdPk;
                        ew_in.lastTime = tempo;
                        ew_in.qtdPk++;
		} else {//saida de pacote
			//System.out.println("Saida de pacote no tempo: ", tempo);
			
                        fila.remove(0);
			//System.out.println("Fila: ", fila);

			if (!fila.isEmpty()) {
				//obtem o tamanho do pacote
				tam_pct = fila.get(0).tamanho;
				//gerando o tempo em que o pacote
				//atual saira do sistema
				saida_pct_atendimento = tempo + tam_pct / link;

				ocupacao += saida_pct_atendimento - tempo;
			}
                        en.sum += (tempo - en.lastTime) * en.qtdPk;
                        en.lastTime = tempo;
                        en.qtdPk--;
                        
                        
                        ew_out.sum += (tempo - ew_out.lastTime) * ew_out.qtdPk;
                        ew_out.lastTime = tempo;
                        ew_out.qtdPk++;
                        

		}
		//System.out.println("==========================\n");
		//getchar();
	}
        ew_out.sum += (tempo - ew_out.lastTime) * ew_out.qtdPk;
        ew_in.sum += (tempo - ew_in.lastTime) * ew_in.qtdPk;
	System.out.println("Ocupacao: "+ ocupacao / tempo);
	System.out.println("Little: E[N] = "+ (en.sum / tempo));
	System.out.println("Little: E[W] = "+ ((ew_in.sum - ew_out.sum) / ew_in.qtdPk));
	System.out.println("Little: λ = "+ (ew_in.qtdPk / tempo));
        double ll = ((ew_in.qtdPk / tempo) * ((ew_in.sum - ew_out.sum) / ew_in.qtdPk));
        System.out.println("Little: little = "+ ll);
        double eps = 0.03;
        if (Math.abs(ll-(en.sum / tempo)) < eps){
            System.out.println ("Para obter uma ocupação de "+(int) (100 *(ocupacao/tempo))+"\\% utilizamos "
            + "o intervalo de "+chegada_pct(intervalo)+" para pacotes web, e de 0.02 para pacote"
            + " CBR dentro das conexões. A duração da conexão CBR foi de "+duracao_conexao+" "
            + "e o intervalo entre conexões foi de "+intervalo_conexao+". O link utilizado na simulação foi de "
            +link+ " Mbps, tamanho de pacote CBR de "+tam_pct+" e média de tamanho de pacotes web em "+gera_tam_pct()+". As variáveis de "
            + "little no final da simulação ficaram em: E[N] = "+ (en.sum / tempo)+""
            + ", E[W] = "+ ((ew_in.sum - ew_out.sum) / ew_in.qtdPk)+", λ = "+ (ew_in.qtdPk / tempo)+""
            + " e finalmente um little de "+ll+". A validação do little ficou em: "+Math.abs(ll-(en.sum / tempo))+""
            + " apresentando assim ser uma boa simulação. ");                                                                                                                 
        }else{
            System.out.println("Bad simulation - ");
        }
        
        
	//	System.out.println("Pacotes gerados: "+ cont_pcts);
	//	System.out.println("Media do intervalo: "+ tempo/cont_pcts);	

	return (0);
}



}
