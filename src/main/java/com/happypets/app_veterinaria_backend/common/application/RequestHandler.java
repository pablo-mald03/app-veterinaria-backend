package com.happypets.app_veterinaria_backend.common.application;

/**
 * Principal request handler class for the different handler ports
 * */
public interface RequestHandler <T extends Request<R>, R> {

    /**
     * Handle request method
     * */
    R handle(T request);

    /**
     * Identifier for the hashmap collection dispatcher mediator
     * */
    Class<T> getRequestType();
}
