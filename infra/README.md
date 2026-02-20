# Infrastructure

This folder contains the infrastructure code for the project. 
It includes Terraform configurations for provisioning resources on cloud providers, 
as well as any scripts or tools needed to manage the infrastructure.


## OpenShift

### Deploy PostgreSQL in OpenShift
```bash title:Deploy PostgreSQL in OpenShift
oc apply -f configmap-init-postgres.yaml
oc apply -f secret-postgres.yaml
oc apply -f deploy-postgresql.yaml
```

### Deploy keycloak in OpenShift
```bash title:Deploy PostgreSQL in OpenShift
oc apply -f secret-keycloak.yaml
oc apply -f route-keycloak.yaml
oc apply -f deploy-keycloak.yaml
```

### Deploy backend in OpenShift
```bash title:Deploy backend in OpenShift
oc apply -f secret-backend.yaml
oc apply -f deploy-backend.yaml
```

### Deplopy frontend in OpenShift
```bash title:Deploy frontend in OpenShift
oc apply -f deploy-frontend.yaml
```

#### PostgreSQL port fordwarding
```bash title:PostgreSQL port fordwarding
oc port-forward svc/postgresql 5432:5432
```

## Contribution & Documentation Guidelines

- All source code, comments, and documentation must be written in **English**.
