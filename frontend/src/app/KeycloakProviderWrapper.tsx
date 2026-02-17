"use client";
import { ReactKeycloakProvider } from '@react-keycloak/web';
import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
  url: process.env.NEXT_PUBLIC_KEYCLOAK_URL || "https://keycloak-mario-rubio-dev.apps.rm1.0a51.p1.openshiftapps.com/",
  realm: process.env.NEXT_PUBLIC_KEYCLOAK_REALM || 'testapp',
  clientId: process.env.NEXT_PUBLIC_KEYCLOAK_CLIENTID || 'testappclient',
});

export default function KeycloakProviderWrapper({ children }: { children: React.ReactNode }) {
  return (
    <ReactKeycloakProvider authClient={keycloak}>
      {children}
    </ReactKeycloakProvider>
  );
}
