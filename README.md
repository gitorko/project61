# Project61

Rest Api + Unit Test + Code Coverage + Check Style + SpotBugs + Docker + Kubernetes + Helm + Debugging + OpenAPI 3.0 + Jenkins 

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
docker images | grep project61
```

You can also build via gradle via the jib plugin

```bash
./gradlew jibDockerBuild
```

[https://github.com/GoogleContainerTools/jib](https://github.com/GoogleContainerTools/jib)

Test if the docker image is working

```bash
docker rm project61
docker run -p 9090:9090 -p 5005:5005 -p 9095:9095 --name project61 project61:1.0.0
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

Port forward

```bash
kubectl port-forward pod/<pod-name> 9095:9095
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

### Kubernetes Samples

Build a custom nginx image

```bash
docker build -f k8s-manifest/Dockerfile1 --force-rm -t my-nginx:1 .
docker build -f k8s-manifest/Dockerfile2 --force-rm -t my-nginx:2 .
```

Create an alias for kubectl as k

```bash
alias k="kubectl"
```

[https://kubernetes.io/docs/reference/kubectl/cheatsheet/](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)

#### 01. Create a simple pod

```bash
k apply -f k8s-manifest/01-create-pod.yaml
k get all
k delete -f k8s-manifest/01-create-pod.yaml
k logs pod/counter
k describe pod/counter
```

#### 02. Create ngnix pod, use port forward to access

Create nginx pod and enter pods bash prompt

```bash
k apply -f k8s-manifest/02-nginx-pod.yaml
k get all
k port-forward pod/nginx 8080:80
k exec -it pod/nginx -- /bin/sh
k delete -f k8s-manifest/02-nginx-pod.yaml
```

[http://localhost:8080/](http://localhost:8080/)

#### 03. Create ngnix pod with html updated by another container in same pod

```bash
k apply -f k8s-manifest/03-nginx-pod-volume.yaml
k get pods -w
kubectl get -o jsonpath="{.spec.ports[0].nodePort}" service/nginx-service
k delete -f k8s-manifest/03-nginx-pod-volume.yaml
```

[http://localhost:31000/](http://localhost:31000/)

#### 04. Create job

Run once and stop. output is kept till you delete it.

```bash
k apply -f k8s-manifest/04-job.yaml
k get all
k delete -f k8s-manifest/04-job.yaml
```

#### 05. Liveness probe

Liveness probe determines when pod is healthy, here file is deleted after 30 seconds causing pod to restart

```bash
k apply -f k8s-manifest/05-liveness-probe.yaml
k get pods -w
k delete -f k8s-manifest/05-liveness-probe.yaml
```

#### 06. Readiness probe

Readiness probe determines when to send traffic

```bash
k apply -f k8s-manifest/06-readiness-probe.yaml
k port-forward pod/nginx 8080:80
k delete -f k8s-manifest/06-readiness-probe.yaml
```

[http://localhost:8080/](http://localhost:8080/)

#### 07. Cron Job

Cron job runs every minute

```bash
k apply -f k8s-manifest/07-cron-job.yaml
k get job.batch -w
k delete -f k8s-manifest/07-cron-job.yaml
```

#### 08. Config

Configure configMap and secrets.

```bash
k apply -f k8s-manifest/08-config.yaml
k logs pod/busybox
k delete -f k8s-manifest/08-config.yaml
```

config map as volume

```bash
k apply -f k8s-manifest/08-config-volume.yaml
k logs -f pod/busybox
k edit configmap app-setting
k get configmap app-setting -o yaml
k exec -it pod/busybox -- /bin/sh

k delete -f k8s-manifest/08-config-volume.yaml
```

#### 09. Deployment with Load Balancer

```bash
k apply -f k8s-manifest/09-deployment.yaml
k get all
k port-forward service/nginx-service 8080:8080

k scale deployment.apps/nginx --replicas=0
k scale deployment.apps/nginx --replicas=3

k delete -f k8s-manifest/09-deployment.yaml
```

[http://localhost:8080/](http://localhost:8080/)

#### 10. External service

Proxies to external name

```bash
k apply -f k8s-manifest/10-external-service.yaml
k get services
k delete -f k8s-manifest/10-external-service.yaml
```

#### 11. Host Path Volume

```bash
k apply -f k8s-manifest/11-volume-host-path.yaml
k get all
k delete -f k8s-manifest/11-volume-host-path.yaml
```

[http://localhost:31000/](http://localhost:31000/)

#### 12. Persistent Volume & Persistent Volume Claim

```bash
k apply -f k8s-manifest/12-pesistent-volume.yaml
k get pv
k get pvc
k get all
k delete -f k8s-manifest/12-pesistent-volume.yaml
```

[http://localhost:31000/](http://localhost:31000/)

#### 14. Blue Green Deployment

```bash
k apply -f k8s-manifest/14-deployment-blue-green.yaml
k apply -f k8s-manifest/14-deployment-blue-green-flip.yaml

k delete service/nginx-blue
k delete deployment/nginx-v1 

k delete -f k8s-manifest/14-deployment-blue-green.yaml
```

[http://localhost:31000/](http://localhost:31000/)
[http://localhost:31000/](http://localhost:32000/)


#### 15. Canary Deployment

```bash
k apply -f k8s-manifest/15-deployment-canary.yaml

k delete deployment/nginx-v2

k delete -f k8s-manifest/15-deployment-canary.yaml
```

```bash
while true; do curl http://localhost:31000/; sleep 2; done
```

[http://localhost:31000/](http://localhost:31000/)

