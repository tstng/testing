package com.techandsolve.lazyload.controllers;

import com.techandsolve.lazyload.business.ProcesadorLazyLoad;
import com.techandsolve.lazyload.constants.MensajesError;
import com.techandsolve.lazyload.constants.MensajesExito;
import com.techandsolve.lazyload.dto.InformacionRespuesta;
import com.techandsolve.lazyload.exceptions.BusinessException;
import com.techandsolve.lazyload.exceptions.TechnicalException;
import com.techandsolve.lazyload.files.ProcesadorArchivos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class LazyLoadContoller {
    private static final Logger LOGGER = Logger.getLogger( LazyLoadContoller.class.getName() );

    @Autowired
    private ProcesadorArchivos procesadorArchivos;
    @Autowired
    private ProcesadorLazyLoad procesadorLazyLoad;


    @PostMapping(path = "/uploadfile", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<InformacionRespuesta> uploadFile(@RequestPart("file") MultipartFile file,
                                                                 @RequestPart("idTrabajador") String idTrabajador) {
        InformacionRespuesta informacionRespuesta;
        try {
            informacionRespuesta = procesadorLazyLoad.procesarLazyLoad(idTrabajador,file);
            informacionRespuesta.setMensaje(String.format(MensajesExito.ARCHIVO_PROCESADO_CORRECTAMENTE,
                    file.getOriginalFilename()));

            return ResponseEntity.status(HttpStatus.OK).body(informacionRespuesta);
        } catch (TechnicalException e){
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
            informacionRespuesta = new InformacionRespuesta();
            informacionRespuesta.setMensaje(MensajesError.ERROR_GENERICO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(informacionRespuesta);
        } catch (BusinessException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
            informacionRespuesta = new InformacionRespuesta();
            informacionRespuesta.setMensaje(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(informacionRespuesta);
        }
    }

    @GetMapping(path = "/downloadfile/{idProceso}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity downloadFile(@PathVariable("idProceso")String idProceso){
        try{
            List<String> archivoRespuesta = procesadorLazyLoad.obtenerInformacionRespuesta(idProceso);
            return ResponseEntity.status(HttpStatus.OK).body(archivoRespuesta);
        }catch (TechnicalException e){
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }catch (BusinessException e){
            LOGGER.log(Level.SEVERE,e.getMessage(),e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }
}



