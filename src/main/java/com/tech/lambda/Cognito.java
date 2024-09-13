package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoUtil;

import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<APIGatewayProxyRequestEvent , Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());


    @Override
    public Response handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        String path = event.getPath();
        logger.info("Request detected: " + event);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if(("/food-api-autenticacao/register".equals(path))){
                logger.info("Processando registro");
                Usuario usuario = objectMapper.readValue(event.getBody(), Usuario.class);
                String user = createUser(usuario);
                return new Response(user, 200);
            }
        } catch (Exception e) {
            logger.severe("Erro ao processar a requisição: " + e.getMessage());
        }

        return new Response("Deu ruim", 400);

    }
}
