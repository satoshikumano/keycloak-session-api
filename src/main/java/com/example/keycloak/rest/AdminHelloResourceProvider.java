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

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import org.jboss.logging.Logger;

public class AdminHelloResourceProvider implements AdminRealmResourceProvider {

    private KeycloakSession session;
    private static final Logger logger = Logger.getLogger(AdminHelloResourceProvider.class);
    private static final String CLIENT_ID_PREFIX = "test";

    public AdminHelloResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource(KeycloakSession session, RealmModel realmModel, AdminPermissionEvaluator adminPermissionEvaluator,
            AdminEventBuilder adminEventBuilder) {
        return this;
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String get() {
        String name = session.getContext().getRealm().getName();
        return "Hello Admin " + name;
    }

    @POST
    @Path("sessions/{user_name}")
    @Produces("text/plain; charset=utf-8")
    public String createSession(@PathParam("user_name") String user_name) {
        RealmModel realm = session.getContext().getRealm();
        ClientModel client = realm.getClientsStream().filter(c -> c.getClientId().startsWith(CLIENT_ID_PREFIX)).findFirst().get();
        UserModel user =session.users().getUserByUsername(realm, user_name);
        UserSessionModel userSession = session.sessions().createUserSession(null, realm, user, user.getUsername(), null, "back channel", false, null, null, null);

        session.sessions().createClientSession(realm, client, userSession);
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
