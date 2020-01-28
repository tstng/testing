package com.techandsolve.lazyload.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InformacionProceso {
    private String fechaEjecucion;
    private String idTrabajador;
    private String nombreArchivoCargado;
    private String idProceso;

    public InformacionProceso(String fechaEjecucion, String idTrabajador, String nombreArchivoCargado, String idProceso) {
        this.fechaEjecucion = fechaEjecucion;
        this.idTrabajador = idTrabajador;
        this.nombreArchivoCargado = nombreArchivoCargado;
        this.idProceso = idProceso;
    }
}
