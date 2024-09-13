package com.tech.lambda.setup;

import com.tech.lambda.model.Usuario;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

public abstract class CognitoUtil {


    private static final String userPoolId = "us-east-1_nbQeLcPNI";
    private static final CognitoIdentityProviderClient cognitoClient = new CognitoClient().createCognitoClient();

    public static String createUser(Usuario usuario) {
        try {
            AdminCreateUserRequest createUserRequest = AdminCreateUserRequest.builder()
                    .userPoolId(userPoolId)
                    .username(usuario.nome())
                    .userAttributes(
                            AttributeType.builder()
                                    .name("email")
                                    .value(usuario.email())
                                    .name("phone_number")
                                    .value(usuario.cpf())
                                    .build()
                    )
                    .temporaryPassword("TemporaryPass123!")
                    .messageAction("SUPPRESS")
                    .build();

            AdminCreateUserResponse response = cognitoClient.adminCreateUser(createUserRequest);
            return response.user().username();

        } catch (CognitoIdentityProviderException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
        return "Não cadastradao";
    }
}
