package com.tech.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.lambda.execeptions.CriarUsuarioExeception;
import com.tech.lambda.execeptions.LoginExeception;
import com.tech.lambda.model.Login;
import com.tech.lambda.model.Response;
import com.tech.lambda.model.Usuario;
import com.tech.lambda.setup.CognitoUtil;
import software.amazon.awssdk.http.HttpStatusCode;

import java.net.http.HttpClient;
import java.util.logging.Logger;


public class Cognito extends CognitoUtil implements RequestHandler<Object, Response> {

    private static final Logger logger = Logger.getLogger(Cognito.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Response handleRequest(Object object, Context context) {

        Usuario usuario;
        Login login;
        String mensagemErro = "";

        try {
            login = objectMapper.convertValue(object, Login.class);
            if (login != null) {
                return new Response(login(login), HttpStatusCode.OK);
            }
        } catch (LoginExeception e) {
            return new Response(e.getMessage(), HttpStatusCode.BAD_REQUEST);
        }catch (Exception e){
            mensagemErro = e.getMessage();
        }

        try {
            usuario = objectMapper.convertValue(object, Usuario.class);
            if (usuario != null) {
                return new Response(createUser(usuario), HttpStatusCode.CREATED);
            }
        } catch (CriarUsuarioExeception e) {
            return new Response( e.getMessage(), HttpStatusCode.BAD_REQUEST);
        } catch (Exception e){
            mensagemErro = e.getMessage();
        }

        return new Response( mensagemErro, 400);

    }

}
