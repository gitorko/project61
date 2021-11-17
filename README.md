# Project61

VSCode Feature Walkthrough

curl -X GET http://localhost:9090/api/age/01-01-1980

```bash
docker build -f k8s/Dockerfile --force-rm -t project61:1.0.0 .
docker images
docker run -p 9090:9090 --name project61 project61:1.0.0
docker run -d -p 9090:9090 --name project61 project61:1.0.0
docker image prune 

kubectl version
kubectl config get-contexts
kubectl config set-context --current --namespace=default
kubectl get nodes
kubectl get ns

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
kubectl get deployment
kubectl get service
kubectl logs -f deployment/project61-k8s --all-containers=true --since=10m

./gradlew jibDockerBuild

```


[https://helm.sh/](https://helm.sh/)


```bash
brew install helm
```

```bash
helm create mychart
helm install project61 mychart
helm list
kubectl get pod,svc,deployment
helm uninstall project61
```

```bash
curl http://$(kubectl get svc/project61-k8s -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'):9090/api/time
```

