package com.techandsolve.lazyload.files.validators;

import com.techandsolve.lazyload.constants.MensajesError;
import com.techandsolve.lazyload.exceptions.BusinessException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ValidadorArchivoTest {

    @InjectMocks
    ValidadorArchivo validadorArchivo;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<String> contenidoArchivo;
    @Before
    public void setUp(){
        contenidoArchivo = buildContenidoArchivo();
    }

    @Test
    public void validarContenidoArchivo_validarCantidadLineas_exception() throws BusinessException {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_ARCHIVO_SIN_CONTENIDO);
        validadorArchivo.validarContenidoArchivo(new ArrayList<>());
    }

    @Test
    public void validarContenidoArchivo_validarLineasArchivo_exception() throws BusinessException {
        contenidoArchivo.remove(contenidoArchivo.size()-1);
        contenidoArchivo.add("a");

        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_LINEAS_DEBEN_SER_NUMEROS);
        validadorArchivo.validarContenidoArchivo(contenidoArchivo);
    }

    @Test
    public void validarContenidoArchivo_validarTotalLineas_diasTrabajoCero_exception() throws BusinessException {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_DIAS_TRABAJO_CERO);
        validadorArchivo.validarContenidoArchivo(Arrays.asList("0"));
    }

    @Test
    public void validarContenidoArchivo_validarTotalLineas_diasTrabajoIncompletos_exception() throws BusinessException {
        List<String> contenidoSubList = contenidoArchivo.subList(0,contenidoArchivo.size()-11);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_DIAS_TRABAJO_NO_VALIDOS);
        validadorArchivo.validarContenidoArchivo(contenidoSubList);
    }

    @Test
    public void validarContenidoArchivo_validarTotalLineas_numeroLineasNoValidas_exception() throws BusinessException {
        contenidoArchivo.remove(contenidoArchivo.size()-1);

        thrown.expect(BusinessException.class);
        thrown.expectMessage(String.format(MensajesError.ERROR_NUMERO_LINEAS_ARCHIVO_NO_VALIDAS,"40",contenidoArchivo.size()));
        validadorArchivo.validarContenidoArchivo(contenidoArchivo);
    }

    private List<String> buildContenidoArchivo() {
        List<String> contenido = new ArrayList<>();
        contenido.addAll(Arrays.asList("5","4","30","30","1","1","3","20","20","20","11","1","2","3","4","5","6","7","8","9",
                "10","11","6","9","19","29","39","49","59","10","32","56","76","8","44","60","47","85","71","91"));
        return contenido;
    }
}
