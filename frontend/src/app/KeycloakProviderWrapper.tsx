"use client";
import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: 'https://keycloak-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com/',
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

