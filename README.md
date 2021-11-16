# Project61

VSCode Feature Walkthrough

curl -X GET http://localhost:9090/api/age/01-01-1980

```bash
docker build -f k8s/Dockerfile --force-rm -t project61:1.0.0 .
docker images
docker run -p 9090:9090 --name project61 project61:1.0.0
docker run -d -p 9090:9090 --name project61 project61:1.0.0
docker image prune 

kubectl run project61-k8s --image project61:1.0.0 --image-pull-policy=Never --port=9090
kubectl expose pod project61-k8s --type=NodePort

kubectl get pods
kubectl get services

kubectl delete pod project61-k8s
kubectl delete service project61-k8s

kubectl apply -f k8s/Deployment.yaml
kubectl delete -f k8s/Deployment.yaml

kubectl scale deployment project61-k8s --replicas=3

kubectl get pods


./gradlew jibDockerBuild

```
