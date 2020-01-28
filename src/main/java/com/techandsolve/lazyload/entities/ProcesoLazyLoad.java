package com.techandsolve.lazyload.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "procesoLazyLoad")
public class ProcesoLazyLoad {
    @Id
    private String id;
    private String idTrabajador;
    private String nombreArchivoCargado;
    private LocalDateTime fechaEjecucion;
    private String casosProcesados;
}
