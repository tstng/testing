package com.techandsolve.lazyload.business;

import com.techandsolve.lazyload.constants.MensajesError;
import com.techandsolve.lazyload.exceptions.TechnicalException;
import com.techandsolve.lazyload.files.validators.ValidadorArchivo;
import com.techandsolve.lazyload.dto.InformacionRespuesta;
import com.techandsolve.lazyload.entities.ProcesoLazyLoad;
import com.techandsolve.lazyload.exceptions.BusinessException;
import com.techandsolve.lazyload.files.ProcesadorArchivos;
import com.techandsolve.lazyload.repositories.ProcesoLazyLoadRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProcesadorLazyLoadTest {

    @InjectMocks
    ProcesadorLazyLoad procesadorLazyLoad;

    @Mock
    ProcesoLazyLoadRepository procesoLazyLoadRepository;
    @Mock
    ProcesadorArchivos procesadorArchivos;
    @Mock
    ValidadorArchivo validadorArchivo;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<String> contenidoArchivo;
    private List<ProcesoLazyLoad> listaProcesosTrabajador;
    private ProcesoLazyLoad proceso1;

    @Before
    public void setUp(){
        contenidoArchivo = Arrays.asList("5","4","30","30","1","1","3","20","20","20","11","1","2","3","4","5","6","7",
            "8","9","10","11","6","9","19","29","39","49","59","10","32","56","76","8","44","60","47","85","71","91");
        listaProcesosTrabajador = buildProcesosTrabajador();
    }

    private List<ProcesoLazyLoad> buildProcesosTrabajador() {
        proceso1 = new ProcesoLazyLoad();
        proceso1.setNombreArchivoCargado("archivo1.txt");
        proceso1.setIdTrabajador("1");
        proceso1.setId("1");
        proceso1.setFechaEjecucion(LocalDateTime.now());
        proceso1.setCasosProcesados("1");

        ProcesoLazyLoad proceso2 = new ProcesoLazyLoad();
        proceso2.setNombreArchivoCargado("archivo.txt");
        proceso2.setIdTrabajador("1");
        proceso2.setId("2");
        proceso2.setFechaEjecucion(LocalDateTime.now());
        proceso2.setCasosProcesados("1,2");

        return Arrays.asList(proceso1,proceso2);
    }


    @Test
    public void procesarLazyLoad_parametros_no_validos() throws TechnicalException, BusinessException {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_PARAMETROS_ENTRADA);
        procesadorLazyLoad.procesarLazyLoad(null,null);
    }

    @Test
    public void procesarLazyLoad_parametros_validos() throws BusinessException, TechnicalException, UnsupportedEncodingException {
        String content = "5\n4\n30\n30\n1\n1\n3\n20\n20\n20\n11\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n6\n9\n19\n29\n" +
                "39\n49\n59\n10\n32\n56\n76\n8\n44\n60\n47\n85\n71\n91\n";
        String idTrabajador = "1";
        MultipartFile mockFile=new MockMultipartFile("mockFile","test.txt","text/plain",
                content.getBytes("utf-8"));
        Mockito.when(procesadorArchivos.obtenerContenidoArchivo(Mockito.any(MultipartFile.class)))
                .thenReturn(contenidoArchivo);
        Mockito.when(procesoLazyLoadRepository.findByIdTrabajador(idTrabajador)).thenReturn(listaProcesosTrabajador);
        InformacionRespuesta respuesta = procesadorLazyLoad.procesarLazyLoad(idTrabajador,mockFile);
        Mockito.verify(procesoLazyLoadRepository).save(Mockito.any(ProcesoLazyLoad.class));
        Mockito.verify(validadorArchivo).validarContenidoArchivo(Mockito.anyList());

        Assert.assertNotNull(respuesta);
        Assert.assertFalse(respuesta.getListaProcesos().isEmpty());
        Assert.assertTrue(respuesta.getListaProcesos().size()==2);
    }

    @Test
    public void obtenerInformacionRespuesta_parametros_no_validos() throws BusinessException, TechnicalException{
        thrown.expect(BusinessException.class);
        thrown.expectMessage(MensajesError.ERROR_ID_PROCESO_NO_VALIDO);
        procesadorLazyLoad.obtenerInformacionRespuesta(null);
    }

    @Test
    public void obtenerInformacionRespuesta_proceso_null() throws BusinessException, TechnicalException {
        thrown.expect(BusinessException.class);
        thrown.expectMessage(String.format(MensajesError.ERROR_PROCESO_NO_ENCONTRADO,"1"));
        Mockito.when(procesoLazyLoadRepository.findById("1")).thenReturn(Optional.empty());
        procesadorLazyLoad.obtenerInformacionRespuesta("1");
    }

    @Test
    public void obtenerInformacionRespuesta_parametros_validos() throws BusinessException, TechnicalException {
        Mockito.when(procesoLazyLoadRepository.findById("1")).thenReturn(Optional.of(proceso1));
        List<String> infoRespuesta = procesadorLazyLoad.obtenerInformacionRespuesta("1");

        Assert.assertNotNull(infoRespuesta);
        Assert.assertFalse(infoRespuesta.isEmpty());
        Assert.assertEquals("Case #1: 1"+System.lineSeparator(),infoRespuesta.get(0));
    }

}
