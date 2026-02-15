"use client";
import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://keycloak/',
  realm: 'testapp',
  clientId: 'testappclient', // Cambia por el clientId configurado en Keycloak
});

export default function KeycloakProviderWrapper({ children }: { children: React.ReactNode }) {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      {children}
    </ReactKeycloakProvider>
  );
}

