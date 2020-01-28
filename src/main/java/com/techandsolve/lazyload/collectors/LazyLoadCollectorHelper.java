package com.techandsolve.lazyload.collectors;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LazyLoadCollectorHelper {

    private List<String> listaCasos;
    private Integer numeroDiasTrabajo = null;
    private Integer numeroElementosDiaTrabajo = null;
    private Integer elementosPorCaso = 0;
    private List<Integer> elementosDiaTrabajoActual = new ArrayList<>();

    public LazyLoadCollectorHelper(){
        listaCasos = new ArrayList<>();
    }

    public void addCaso(Integer elemento){
        if(numeroDiasTrabajo==null){
            numeroDiasTrabajo = elemento;
        }else if(numeroElementosDiaTrabajo==null){
            numeroElementosDiaTrabajo = elemento;
        }else{
            elementosDiaTrabajoActual.add(elemento);
        }

        if(numeroElementosDiaTrabajo!=null && !elementosDiaTrabajoActual.isEmpty() &&
                elementosDiaTrabajoActual.size()==numeroElementosDiaTrabajo){
            Integer cnt = calcularViajes();
            listaCasos.add(cnt.toString());
            numeroElementosDiaTrabajo=null;
            elementosDiaTrabajoActual = new ArrayList<>();
        }
    }

    private Integer calcularViajes() {
        List<Integer> elementosOrdenados = elementosDiaTrabajoActual.stream().sorted().collect(Collectors.toList());
        Integer cantidadElementos = elementosOrdenados.size();
        Integer viajes = 0;
        Integer iteracion = 0;
        Integer indiceElementos = cantidadElementos - 1;

        while (iteracion <= indiceElementos) {
            Integer elementoSuperior = elementosOrdenados.get(indiceElementos--);
            Integer cantidadElementosViaje = 1;
            while (iteracion <= indiceElementos && elementoSuperior * cantidadElementosViaje < 50) {
                iteracion++;
                cantidadElementosViaje++;
            }
            if (elementoSuperior * cantidadElementosViaje >= 50) viajes++;
        }
        return viajes;
    }
}
