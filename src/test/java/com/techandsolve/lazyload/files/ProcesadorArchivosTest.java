package com.techandsolve.lazyload.files;

import com.techandsolve.lazyload.exceptions.TechnicalException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProcesadorArchivosTest {

    @InjectMocks
    ProcesadorArchivos procesadorArchivos;

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void obtenerContenidoArchivo_parametros_no_validos() throws TechnicalException {
        thrown.expect(TechnicalException.class);
        thrown.expectMessage("Error obteniendo el contenido del archivo cargado: el archivo es nulo");

        procesadorArchivos.obtenerContenidoArchivo(null);
    }

    @Test
    public void obtenerContenidoArchivo_parametros_validos() throws UnsupportedEncodingException, TechnicalException{
        String content = "5\n4\n3";
        String idTrabajador = "1";
        MultipartFile mockFile=new MockMultipartFile("mockFile","test.txt", "text/plain",
                content.getBytes("utf-8"));

        List<String> contenido = procesadorArchivos.obtenerContenidoArchivo(mockFile);

        Assert.assertNotNull(contenido);
        Assert.assertFalse(contenido.isEmpty());
        Assert.assertTrue(contenido.size()==3);
        Assert.assertTrue(contenido.stream().anyMatch(s -> "5".equals(s)));
        Assert.assertTrue(contenido.stream().anyMatch(s -> "4".equals(s)));
        Assert.assertTrue(contenido.stream().anyMatch(s -> "3".equals(s)));
    }

}
