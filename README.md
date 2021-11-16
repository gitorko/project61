# Project61

VSCode Feature Walkthrough

curl -X GET http://localhost:9090/api/age/01-01-1980

```bash
docker build -f k8/Dockerfile -t project61:latest .
docker images
docker run -p 8080:8080 project61:latest
kubectl run project61-k8 --image project61:latest --image-pull-policy=Never --port=8080
kubectl expose pod project61-k8 --type=NodePort

kubectl get pods
kubectl get services

kubectl delete pod project61-k8
kubectl delete service project61-k8

kubectl apply -f Deployment.yaml 
```
