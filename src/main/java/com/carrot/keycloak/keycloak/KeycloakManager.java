package com.carrot.keycloak.keycloak;


import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class KeycloakManager {
    public void createRole(Keycloak keycloak, String roleName) {
        var realm = keycloak.realm("Claiops_Realm");
        var roles = realm.roles().list();

        boolean roleExists = roles.stream().anyMatch(role -> Objects.equals(role.getName(), roleName));

        if(!roleExists) {
            // Only create the role if it doesn't already exist
            RoleRepresentation newRole = new RoleRepresentation();
            newRole.setName(roleName);
            newRole.setDescription("test api role2");
            newRole.setComposite(false);
            realm.roles().create(newRole);
        }
    }
    public void deleteRole(Keycloak keycloak, String roleName) {
        var realm = keycloak.realm("Claiops_Realm");

        var roles = realm.roles().list();

        boolean roleExists = roles.stream().anyMatch(role -> Objects.equals(role.getName(), roleName));
        if (roleExists) {
            realm.roles().deleteRole(roleName);
        }
    }

    public void deleteUser(Keycloak keycloak, String username) {
        var realm = keycloak.realm("Claiops_Realm");

        var users = realm.users();

        var searchedUser = users.search(username);

        if (!searchedUser.isEmpty()) {
            var user = searchedUser.get(0);
            Response response = realm.users().delete(user.getId());
            System.out.println("deleteUser Response:" + response.getStatus() + response.getStatusInfo());

        }
    }

    public void createUser(Keycloak keycloak, String username, String password) {
        // create user representation
        RealmResource realm = keycloak.realm("Claiops_Realm");
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(username);
        newUser.setEnabled(true);

        // create credentials
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        newUser.setCredentials(List.of(credential));
        var users = realm.users().list();

        // get realm and create user
        boolean userExists = users.stream().anyMatch(user -> Objects.equals(user.getUsername(), username));

        if (!userExists) {
            Response response = realm.users().create(newUser);
            System.out.println("createUser Response:" + response.getStatus());
        }

    }

    public void assignRoleToUser(Keycloak keycloak, String username, String roleName) {
        // get realm and users
        RealmResource realm = keycloak.realm("Claiops_Realm");
        UsersResource users = realm.users();

        // get user
        UserRepresentation user = users.search(username).getFirst();
        if (user != null) {
            System.out.println("user exist");
        }

        // get role
        RoleRepresentation role = realm.roles().get(roleName).toRepresentation();

        // assign role to user
        users.get(user.getId()).roles().realmLevel().add(Collections.singletonList(role));
    }

}

