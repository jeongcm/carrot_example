package com.carrot.keycloak;

import com.carrot.keycloak.keycloak.KeycloakManager;
import com.carrot.keycloak.keycloak.KeycloakResource;
import org.keycloak.admin.client.Keycloak;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class keycloakApplication {

	public static void main(String[] args) {
		try (Keycloak keycloak = Keycloak.getInstance(
                "https://keycloak.claion.io",
                "Claiops_Realm",
                "claiops_realm_admin",
                "admin",
                "admin-cli")) {
			var keycloakManager = new KeycloakManager();
			keycloakManager.createUser(keycloak, "test_user", "123");
			keycloakManager.createRole(keycloak, "test_role");
			keycloakManager.assignRoleToUser(keycloak, "test_user", "test_role");
//			keycloakManager.deleteRole(keycloak, "test_role");
//			keycloakManager.deleteUser(keycloak, "test_user");
		}

		SpringApplication.run(keycloakApplication.class, args);

	}

}


