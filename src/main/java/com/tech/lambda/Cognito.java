package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.model.Login;
import com.tech.lambda.model.Response;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoUtil;

import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<Object, Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Response handleRequest(Object object, Context context) {

        Usuario usuario = null;
        Login login = null;

        try {
            login = objectMapper.convertValue(object, Login.class);
            if (login != null) {
                return new Response(login(login), 200);
            }
        } catch (Exception e) {
            logger.warning("Falha ao converter para Login: " + e.getMessage());
        }

        try {
            usuario = objectMapper.convertValue(object, Usuario.class);
            if (usuario != null) {
                return new Response(createUser(usuario), 200);
            }
        } catch (Exception e) {
            logger.warning("Falha ao converter para Usuario: " + e.getMessage());
        }

        logger.warning("PATH NÃO CADASTRADO");
        return new Response("PATH NÃO CADASTRADO", 400);

    }

}
