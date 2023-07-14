/*
questa classe di eccezione consente di generare un'eccezione di tipo ResourceNotFoundException nella tua applicazione Spring quando una risorsa richiesta non viene trovata. Il framework si occuperà di gestire questa eccezione e restituire una risposta 404 Not Found al client.
 */


package com.fabbluca.springbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message){
        super(message);
    }
}
