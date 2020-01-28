package com.techandsolve.lazyload.files.validators;

import com.techandsolve.lazyload.constants.MensajesError;
import com.techandsolve.lazyload.exceptions.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidadorArchivo {

    public void validarContenidoArchivo(List<String> contenidoArchivo) throws BusinessException{
        validarCantidadLineas(contenidoArchivo);
        validarLineasArchivo(contenidoArchivo);
        validarTotalLineas(contenidoArchivo);
    }

    private void validarCantidadLineas(List<String> contenidoArchivo) throws BusinessException {
        if(contenidoArchivo.isEmpty()){
            throw new BusinessException(MensajesError.ERROR_ARCHIVO_SIN_CONTENIDO);
        }
    }

    private void validarTotalLineas(List<String> contenidoArchivo) throws BusinessException{
        Integer cantidadLineasRecibidas = contenidoArchivo.size();
        String primeraLinea = contenidoArchivo.stream().findFirst().orElse(null);
        Integer numeroDiasTrabajo = primeraLinea!=null? Integer.valueOf(primeraLinea):0;

        if(numeroDiasTrabajo!=0){

            List<Integer> cantidadElemantosDia = new ArrayList<>();
            try{
                for(int i=1;i<=cantidadLineasRecibidas;i++){
                    Integer elementosDia = Integer.valueOf(contenidoArchivo.get(i));
                    cantidadElemantosDia.add(elementosDia);
                    i=i+elementosDia+1;
                }

                Integer cantidadLineasEsperadas = numeroDiasTrabajo+cantidadElemantosDia.stream().reduce(Integer::sum).orElse(0)+1;
                if(cantidadLineasEsperadas!=cantidadLineasRecibidas){
                    throw new BusinessException(String.format(MensajesError.ERROR_NUMERO_LINEAS_ARCHIVO_NO_VALIDAS,cantidadLineasEsperadas,cantidadLineasRecibidas));
                }
            }catch (IndexOutOfBoundsException e){
                throw new BusinessException(MensajesError.ERROR_DIAS_TRABAJO_NO_VALIDOS);
            }

        }else{
            throw new BusinessException(MensajesError.ERROR_DIAS_TRABAJO_CERO);
        }
    }

    private void validarLineasArchivo(List<String> contenidoArchivo) throws BusinessException {
        if(contenidoArchivo.stream().anyMatch(linea -> !StringUtils.isNumeric(linea))){
            throw new BusinessException(MensajesError.ERROR_LINEAS_DEBEN_SER_NUMEROS);
        }
    }
}
