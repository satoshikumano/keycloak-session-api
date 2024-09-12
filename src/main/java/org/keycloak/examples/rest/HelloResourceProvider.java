package org.keycloak.examples.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

@Provider
public class HelloResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;
    private static final Logger logger = Logger.getLogger(HelloResourceProvider.class);

    public HelloResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @GET
    @Produces("text/plain; charset=utf-8")
    public String get() {
        String name = session.getContext().getRealm().getName();
        return "Hello " + name;
    }

    @Override
    public void close() {
    }

}
