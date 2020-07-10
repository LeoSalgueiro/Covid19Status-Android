package com.example.covid19status.EntidadesDB;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ubicacion_table")
public class UbicacionUsuario {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String provinciaId;

    private String provinciaNombre;

    private String departamentoId;

    private String departamentoNombre;

    private long createdAt;

    public UbicacionUsuario(String provinciaId, String provinciaNombre, String departamentoId, String departamentoNombre) {
        this.provinciaId = provinciaId;
        this.provinciaNombre = provinciaNombre;
        this.departamentoId = departamentoId;
        this.departamentoNombre = departamentoNombre;
        this.createdAt = System.currentTimeMillis();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(String provinciaId) {
        this.provinciaId = provinciaId;
    }

    public String getProvinciaNombre() {
        return provinciaNombre;
    }

    public void setProvinciaNombre(String provinciaNombre) {
        this.provinciaNombre = provinciaNombre;
    }

    public String getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(String departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getDepartamentoNombre() {
        return departamentoNombre;
    }

    public void setDepartamentoNombre(String departamentoNombre) {
        this.departamentoNombre = departamentoNombre;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UbicacionUsuario{" +
                "uid=" + uid +
                ", provinciaId='" + provinciaId + '\'' +
                ", provinciaNombre='" + provinciaNombre + '\'' +
                ", departamentoId='" + departamentoId + '\'' +
                ", departamentoNombre='" + departamentoNombre + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
