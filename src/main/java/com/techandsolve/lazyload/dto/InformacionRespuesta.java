package com.techandsolve.lazyload.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InformacionRespuesta {
    private String mensaje;
    private List<InformacionProceso> listaProcesos;
}
