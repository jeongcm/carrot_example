package com.carrot.keycloak;

import com.carrot.keycloak.keycloak.KeycloakManager;
import com.carrot.keycloak.keycloak.KeycloakResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class keycloakApplication {

	public static void main(String[] args) {
		try (KeycloakResource resource = new KeycloakResource(
				"https://keycloak.claion.io",
				"Claiops_Realm",
				"claiops_realm_admin",
				"admin",
				"admin-cli")) {
			var keycloakManager = new KeycloakManager();
			keycloakManager.createUser(resource.getKeycloak(), "testUser", "123");
			keycloakManager.createRole(resource.getKeycloak(), "testRole");
			keycloakManager.assignRoleToUser(resource.getKeycloak(), "testUser", "testRole");
//			keycloakManager.deleteRole(resource.getKeycloak(), "testRole");
		}

		SpringApplication.run(keycloakApplication.class, args);

	}

}


