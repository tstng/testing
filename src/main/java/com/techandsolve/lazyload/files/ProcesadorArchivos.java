package com.techandsolve.lazyload.files;

import com.techandsolve.lazyload.constants.MensajesError;
import com.techandsolve.lazyload.exceptions.TechnicalException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProcesadorArchivos {

    public List<String> obtenerContenidoArchivo(MultipartFile archivo) throws TechnicalException{
            try {
                return new BufferedReader(new InputStreamReader(archivo.getInputStream()))
                        .lines().collect(Collectors.toList());
            } catch (IOException e) {
                throw new TechnicalException(String.format(MensajesError.ERROR_CONTENIDO_ARCHIVO,archivo.getOriginalFilename()),e);
            } catch (NullPointerException e) {
                throw new TechnicalException(MensajesError.ERROR_ARCHIVO_NULO,e);
            }
    }
}
