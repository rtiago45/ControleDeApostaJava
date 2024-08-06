package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class Person {
    @Id
    private String id;
    private double saldoInicial;
    private double saldoAtual;
    private String estiloDeAposta;
    private double stakeRecomendada;

    public Person() {}

    public Person(String id, double saldoInicial, double saldoAtual, String estiloDeAposta, double stakeRecomendada) {
        this.id = id;
        this.saldoInicial = saldoInicial;
        this.saldoAtual = saldoAtual;
        this.estiloDeAposta = estiloDeAposta;
        this.stakeRecomendada = stakeRecomendada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public String getEstiloDeAposta() {
        return estiloDeAposta;
    }

    public void setEstiloDeAposta(String estiloDeAposta) {
        this.estiloDeAposta = estiloDeAposta;
    }

    public double getStakeRecomendada() {
        return stakeRecomendada;
    }

    public void setStakeRecomendada(double stakeRecomendada) {
        this.stakeRecomendada = stakeRecomendada;
    }
}
