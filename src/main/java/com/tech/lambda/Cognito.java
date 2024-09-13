package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoClient;
import com.tech.lambda.setup.CognitoUtil;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<Object, Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());

    @Override
    public Response handleRequest(Object object, Context context) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Usuario usuario =  objectMapper.convertValue(object, Usuario.class);
            logger.info(usuario.nome());
            logger.info(usuario.email());
            logger.info(usuario.cpf());
            String user = createUser(usuario);
            return new Response(user, 200);
        } catch (Exception e) {
            logger.severe("Erro ao processar a requisição: " + e.getMessage());
        }

        return new Response("Deu ruim", 400);

    }

}
