package com.example.keycloak.rest;

import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.managers.IdentityCookieToken;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import org.jboss.logging.Logger;

public class AdminSessionResourceProvider implements AdminRealmResourceProvider {

    private KeycloakSession session;
    private static final Logger logger = Logger.getLogger(AdminSessionResourceProvider.class);
    private static final String CLIENT_ID_PREFIX = "test";

    public AdminSessionResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource(KeycloakSession session, RealmModel realmModel, AdminPermissionEvaluator adminPermissionEvaluator,
            AdminEventBuilder adminEventBuilder) {
        return this;
    }

    @PUT
    @Path("/{user_name}")
    @Produces("text/plain; charset=utf-8")
    public String createSession(@PathParam("user_name") String user_name) {
        RealmModel realm = session.getContext().getRealm();
        ClientModel client = realm.getClientsStream().filter(c -> c.getClientId().startsWith(CLIENT_ID_PREFIX)).findFirst().get();
        UserModel user =session.users().getUserByUsername(realm, user_name);
        UserSessionModel userSession = session.sessions().createUserSession(null, realm, user, user.getUsername(), null, "back channel", false, null, null, null);

        session.sessions().createClientSession(realm, client, userSession);
        // Response: Cookie bounds to the session created. Can be used to KEYCLOAK_IDENTITY cookie.
        IdentityCookieToken token = AuthenticationManager.createIdentityToken(session, realm, user, userSession, getIssuer(session));
        return session.tokens().encode(token);
    }

    private String getIssuer(KeycloakSession session) {
        String base = session.getContext().getUri().getBaseUri().toString();
        String issuer = base + "realms/" + session.getContext().getRealm().getName();
        return issuer;
    }

    @Override
    public void close() {
    }

}
