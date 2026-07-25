package com.example.rest;

public class SumaResponse {
    private int resultado;

    public SumaResponse() {}
    public SumaResponse(int resultado) {
        this.resultado = resultado;
    }

    public int getResultado() { return resultado; }
    public void setResultado(int resultado) { this.resultado = resultado; }
}