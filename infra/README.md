# Infrastructure

This folder contains the infrastructure code for the project. 
It includes Terraform configurations for provisioning resources on cloud providers, 
as well as any scripts or tools needed to manage the infrastructure.


### OpenShift

- PostgreSQL port fordwarding
```bash title:PostgreSQL port fordwarding
oc port-forward svc/postgresql 5432:5432
```

- Build backend container image
```bash title:Build backend container image
cd backend  
docker build -t container-sample-app-back:latest -f docker/Dockerfile .
```
