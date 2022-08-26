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

