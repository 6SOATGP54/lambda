package com.tech.lambda.setup;

import com.tech.lambda.execeptions.CriarUsuarioExeception;
import com.tech.lambda.execeptions.LoginExeception;
import com.tech.lambda.model.Login;
import com.tech.lambda.model.Usuario;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import java.util.HashMap;
import java.util.Map;

public abstract class CognitoUtil {


    private static final String userPoolId = "us-east-1_XKNpKHM2m";
    private static final CognitoIdentityProviderClient cognitoClient = new CognitoClient().createCognitoClient();

    public static String createUser(Usuario usuario) {
        try {
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(usuario.cpf())
                    .userAttributes(
                            AttributeType.builder()
                                    .name("email")
                                    .value(usuario.email())
                                    .name("phone_number")
                                    .value(usuario.telefone())
                                    .build()
                    ).messageAction("SUPPRESS")
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(createUserRequest);

            AdminSetUserPasswordRequest setPasswordRequest = AdminSetUserPasswordRequest.builder()
                    .userPoolId(userPoolId)
                    .username(usuario.cpf())
                    .password("techFoodpass@2025!")
                    .permanent(true)
                    .build();

            cognitoClient.adminSetUserPassword(setPasswordRequest);

            return response.user().username();

        } catch (Exception e) {
            throw new CriarUsuarioExeception("Falha na criação " + usuario.cpf());
        }
    }

    public static String login(Login login){

        try{
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", login.usuario());
            authParams.put("PASSWORD", login.senha());


            AdminInitiateAuthRequest authRequest = AdminInitiateAuthRequest.builder()
                    .userPoolId(userPoolId) // ID do pool de usuários
                    .clientId("6ccn1geh1me4dlak1gt32pc1n8") // ID do cliente Cognito
                    .authFlow(AuthFlowType.ADMIN_NO_SRP_AUTH) // Tipo de fluxo de autenticação
                    .authParameters(authParams)
                    .build();

            AdminInitiateAuthResponse  authResponse = cognitoClient.adminInitiateAuth(authRequest);
            return authResponse.authenticationResult().refreshToken();
        }catch (Exception e){
            throw new LoginExeception("Falha no Login " + login.usuario());
        }

    }
}
