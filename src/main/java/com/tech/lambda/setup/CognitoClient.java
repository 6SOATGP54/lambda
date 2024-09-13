package com.tech.lambda.setup;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

public class CognitoClient {

    public CognitoIdentityProviderClient createCognitoClient() {
        Region region = Region.US_EAST_1; // Defina sua região
        return CognitoIdentityProviderClient.builder()
                .region(region)
                .build();
    }
}
