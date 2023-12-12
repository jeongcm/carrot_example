package com.carrot.example.keycloak;

import lombok.Getter;
import org.keycloak.admin.client.Keycloak;


@Getter
public class KeycloakResource implements AutoCloseable {
    private final Keycloak keycloak;

    public KeycloakResource(String serverUrl, String realm, String username, String password, String clientId) {
        this.keycloak = Keycloak.getInstance(serverUrl, realm, username, password, clientId);
    }

    @Override
    public void close() {
        keycloak.close();
    }
}