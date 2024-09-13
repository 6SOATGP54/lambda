package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.model.Event;
import com.tech.lambda.model.Response;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoUtil;

import java.util.Map;
import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<Map<String, Object>, Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());

    @Override
    public Response handleRequest(Map<String, Object> event, Context context) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> headers = (Map<String, String>) event.get("headers");


        logger.info("Headers: " + headers);

        try {
            Usuario usuario =  objectMapper.convertValue(event.get("body"), Usuario.class);

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
