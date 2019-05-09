/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include "fila.h"

pacote * aloca_pct(){
    return malloc (sizeof(pacote*));
}

/**
 * 
 * @param fim points to the last item of the array
 * @param tamanho of the package
 * @return 0 if error, 1 if not error
 */


int insere(pacote * inicio, pacote * fim, double tamanho){
    if (inicio == NULL){
        inicio = aloca_pct();
        fim = inicio;
        inicio->tamanho = tamanho;
        inicio->prox = NULL;
        return 1;
    }else{
        pacote * tmp  = aloca_pct();
        tmp->tamanho = tamanho;
        tmp->prox = NULL;
        fim->prox = tmp;
        fim = tmp;
        return 1;
    }
   return 0; 
}