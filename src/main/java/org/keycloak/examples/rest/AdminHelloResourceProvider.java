package org.keycloak.examples.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.permissions.AdminPermissionEvaluator;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class AdminHelloResourceProvider implements AdminRealmResourceProvider {

    private KeycloakSession session;
    private static final Logger logger = Logger.getLogger(AdminHelloResourceProvider.class);

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

    @Override
    public void close() {
    }

}
