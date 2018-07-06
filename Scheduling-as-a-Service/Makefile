GCLOUD_PROJECT:=$(shell gcloud config list project --format="value(core.project)")

.PHONY: all
all: deploy

.PHONY: create-cluster
create-cluster:
	gcloud container clusters create aspaas \
		--scopes "cloud-platform" \
		--num-nodes 2 \
    	--enable-autoscaling --min-nodes 2 --max-nodes 20
	gcloud container clusters get-credentials aspaas
	kubectl create rolebinding clusteradmins --clusterrole=admin \
	--serviceaccount=default:default \
	--namespace=default


.PHONY: delete-cluster
delete-cluster:
	gcloud container clusters delete aspaas

.PHONY: build-frontend
build-frontend:
	docker build -t gcr.io/$(GCLOUD_PROJECT)/frontend -f Dockerfile_frontend .

.PHONY: build-worker
build-worker:
	docker build -t gcr.io/$(GCLOUD_PROJECT)/worker -f Dockerfile_worker .

.PHONY: build
build: build-worker build-frontend

.PHONY: push-frontend
push-frontend: build-frontend
	gcloud docker -- push gcr.io/$(GCLOUD_PROJECT)/frontend

.PHONY: push-worker
push-worker: build-worker
	gcloud docker -- push gcr.io/$(GCLOUD_PROJECT)/worker

.PHONY: template
template:
	sed "s/\[GCLOUD_PROJECT\]/$(GCLOUD_PROJECT)/g" aspaas-frontend.yaml.tmpl > .aspaas-frontend.yaml

.PHONY: create-service
create-service:
	kubectl create -f aspaas-service.yaml

.PHONY: deploy-frontend
deploy-frontend: push-frontend template
	kubectl create -f .aspaas-frontend.yaml

.PHONY: deploy
deploy: deploy-frontend push-worker create-service

.PHONY: delete
delete: template
	-kubectl delete -f aspaas-service.yaml
	-kubectl delete -f .aspaas-frontend.yaml
