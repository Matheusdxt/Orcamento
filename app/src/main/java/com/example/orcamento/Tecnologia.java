package com.example.orcamento;

import androidx.annotation.NonNull;

public class Tecnologia {

    private int id;
    private String tec;

    private Double custo_mm;

    public Tecnologia() {
    }

    @NonNull
    @Override
    public String toString() {
        return tec;
    }

    public Tecnologia(int id, String tec, double custo_mm) {
            this.id = id;
            this.tec = tec;
            this.custo_mm = custo_mm;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTec() {
        return tec;
    }

    public void setTec(String tec) {
        this.tec = tec;
    }

    public Double getCusto_mm() {
        return custo_mm;
    }

    public void setCusto_mm(Double custo_mm) {
        if (custo_mm > 0)
            this.custo_mm = custo_mm;
    }
}




