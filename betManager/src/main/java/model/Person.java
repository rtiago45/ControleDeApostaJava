package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "person")
public class Person {

    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private String id;
    private double saldoInicial;
    private double saldoAtual;
    private String estiloDeAposta;
    private String name;

    public Person() {}

    public Person(String id, double saldoInicial, String estiloDeAposta, String name) {
        this.id = id;
        this.saldoInicial = saldoInicial;
        this.estiloDeAposta = estiloDeAposta;
        this.name = name;
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

    public String getEstiloDeAposta() {
        return estiloDeAposta;
    }

    public void setEstiloDeAposta(String estiloDeAposta) {
        this.estiloDeAposta = estiloDeAposta;
    }

    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public double getSaldoAtual() {
        return saldoAtual;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
