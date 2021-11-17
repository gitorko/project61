# Project61

Rest Api + Unit Test + Code Coverage + SpotBugs + Docker + Kubernetes + Helm + Debugging

```bash
curl -X GET http://localhost:9090/api/age/01-01-1980
curl -X GET http://localhost:9090/api/time
```

## Deployment

Build the project

```bash
git clone https://github.com/gitorko/project61.git
cd project61
./gradlew clean build
```

### Docker

Run the following commands to build the docker image

```bash
docker build -f k8s/Dockerfile --force-rm -t project61:1.0.0 .
docker images
```

You can also build via gradle via the jib plugin

```bash
./gradlew jibDockerBuild
```

[https://github.com/GoogleContainerTools/jib](https://github.com/GoogleContainerTools/jib)

Test if the docker image is working

```bash
docker run -p 9090:9090 --name project61 project61:1.0.0
```

[http://localhost:9090/api/time](http://localhost:9090/api/time)

Daemon mode

```bash
docker run -d -p 9090:9090 --name project61 project61:1.0.0
docker image prune 
```

### Kubernetes

Now let's deploy the project on a kubernetes cluster. Check if kubernetes commands work

```bash
kubectl version
kubectl config get-contexts
kubectl config set-context --current --namespace=default
kubectl get nodes
kubectl get ns
```

Deploy the docker image in kubernetes, get the port from the NodePort

```bash
kubectl run project61-k8s --image project61:1.0.0 --image-pull-policy=Never --port=9090
kubectl expose pod project61-k8s --type=NodePort
kubectl get -o jsonpath="{.spec.ports[0].nodePort}" services project61-k8s
```

Change the port and test this api: http://localhost:3000/api/time

Check the pods,services & deployments.

```bash
kubectl get pods
kubectl get services
kubectl get deployment
```

Clean up.

```bash
kubectl delete pod project61-k8s
kubectl delete service project61-k8s
```

Now deploy via the kubernetes yaml file.

```bash
kubectl apply -f k8s/Deployment.yaml
```

[http://localhost:9090/api/time](http://localhost:9090/api/time)

Scale the deployment

```bash
kubectl scale deployment project61-k8s --replicas=3
```

Look at the logs

```bash
kubectl logs -f deployment/project61-k8s --all-containers=true --since=10m
```

Clean up

```bash
kubectl delete -f k8s/Deployment.yaml
```

### Helm

Now lets deploy the same project via helm charts

[https://helm.sh/](https://helm.sh/)

```bash
brew install helm
```

```bash
helm version
helm install project61 mychart
helm list
kubectl get pod,svc,deployment
```

Get the url and invoke the api

```bash
curl http://$(kubectl get svc/project61-k8s -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'):9090/api/time
```

Clean up
```bash
helm uninstall project61
```
