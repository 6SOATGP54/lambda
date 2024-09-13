package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.model.Event;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoUtil;

import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<Object, Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());

    @Override
    public Response handleRequest(Object object, Context context) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Usuario usuario =  objectMapper.convertValue(object, Usuario.class);

            if(usuario.evento().equals(Event.LOGIN)){
                return new Response("Usuario LOGIN", 200);
            } else if (usuario.evento().equals(Event.REGISTRO)) {
                return new Response(createUser(usuario), 200);
            }else{
                return new Response("PATH NÃO CADASTRADO", 400);
            }
        } catch (Exception e) {
            logger.severe("Erro ao processar a requisição: " + e.getMessage());
        }

        return new Response("Deu ruim", 400);

    }

}
