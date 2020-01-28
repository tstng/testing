package com.techandsolve.lazyload.constants;

public final class MensajesError {

    public static final String ERROR_PARAMETROS_ENTRADA = "No se puede procesar el archivo, no se recibió el archivo " +
            "o el id del trabajador";
    public static final String ERROR_PROCESO_NO_ENCONTRADO = "No existe un proceso con el id: %s";
    public static final String ERROR_ID_PROCESO_NO_VALIDO = "Id del proceso no válido";
    public static final String ERROR_GENERICO = "Ha ocurrido un error inesperado, contacte al área de soporte!";
    public static final String ERROR_CONTENIDO_ARCHIVO = "Error obteniendo el contenido del archivo cargado: %s";
    public static final String ERROR_ARCHIVO_NULO = "Error obteniendo el contenido del archivo cargado: el archivo es nulo";
    public static final String ERROR_NUMERO_LINEAS_ARCHIVO_NO_VALIDAS = "El número de líneas del archivo no son válidas, se esperaban %s y tiene %s";
    public static final String ERROR_DIAS_TRABAJO_NO_VALIDOS = "La información de los días de trabajo en el archivo no es válida";
    public static final String ERROR_DIAS_TRABAJO_CERO = "El número de días de trabajo debe ser mayor que 0";
    public static final String ERROR_LINEAS_DEBEN_SER_NUMEROS = "Las líneas del archivo deben ser números";
    public static final String ERROR_MONGO_DB = "Error conexion con MongoDB";
    public static final String ERROR_ARCHIVO_SIN_CONTENIDO = "El archivo no tiene contenido";
}
