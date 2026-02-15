"use client";
import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'http://keycloak/',
  realm: 'testapp',
  clientId: 'testappclient',
});

export default function KeycloakProviderWrapper({ children }: { children: React.ReactNode }) {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      {children}
    </ReactKeycloakProvider>
  );
}

