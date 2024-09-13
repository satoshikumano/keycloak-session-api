package com.example.keycloak.rest;

import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProvider;
import org.keycloak.services.resources.admin.ext.AdminRealmResourceProviderFactory;

public class AdminSessionResourceProviderFactory implements AdminRealmResourceProviderFactory {

    public static final String ID = "sessions";
    private static final Logger logger = Logger.getLogger(AdminSessionResourceProviderFactory.class);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public AdminRealmResourceProvider create(KeycloakSession session) {
        return new AdminHelloResourceProvider(session);
    }

    @Override
    public void init(Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

}
