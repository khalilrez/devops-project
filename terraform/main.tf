terraform {
  backend "remote" {
    organization = "gatri"
    workspaces {
      name = "devops-tf-cloud"
    }
  }
}


provider "helm" {
  kubernetes {
    config_path = "~/.kube/config" 
  }
}
provider "kubernetes" {
  config_path = "~/.kube/config"
}

resource "helm_release" "prometheus" {
  name       = "prometheus"
  repository = "https://prometheus-community.github.io/helm-charts"
  chart      = "kube-prometheus-stack"
  namespace  = "monitoring"
  create_namespace = true
  version    = "45.7.1"
}

resource "kubernetes_namespace" "prod" {
  metadata {
    name = var.namespace
  }
}
resource "kubernetes_service" "spring_boot_service" {
  metadata {
    name = "spring-boot-service"
    namespace = kubernetes_namespace.prod.metadata.0.name
  }
  spec {
    selector = {
      component = "spring"
    }
    port {
      name       = "http"
      protocol   = "TCP"
      port       = 8089
      target_port = 8089
    }
    type = "ClusterIP"
  }
}

resource "kubernetes_service" "angular_service" {
  metadata {
    name = "angular-service"
    namespace = kubernetes_namespace.prod.metadata.0.name
  }
  spec {
    selector = {
      component = "angular"
    }
    port {
      name       = "http"
      protocol   = "TCP"
      port       = 80
      target_port = 80
    }
    type = "NodePort"
  }
}

resource "kubernetes_deployment" "app_deployment" {
  metadata {
    name = "app-deployment"
    namespace = kubernetes_namespace.prod.metadata.0.name
  }
  spec {
    selector {
      match_labels = {
        app       = "app"
        component = "spring"
      }
    }
    template {
      metadata {
        labels = {
          app       = "app"
          component = "spring"
        }
      }
      spec {
        container {
          name  = "mysql"
          image = "mysql:5.7"
          env {
            name = "MYSQL_ROOT_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.mysql_secret.metadata.0.name
                key  = "password"
              }
            }
          }
          env {
            name = "MYSQL_DATABASE"
            value = "db"
          }
        }
        container {
          name  = "spring-app"
          image = var.imageName
          env {
            name = "DB_USERNAME"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.mysql_secret.metadata.0.name
                key  = "username"
              }
            }
          }
          env {
            name = "DB_PASSWORD"
            value_from {
              secret_key_ref {
                name = kubernetes_secret.mysql_secret.metadata.0.name
                key  = "password"
              }
            }
          }
          env {
            name = "DB_HOST"
            value_from {
              field_ref {
                field_path = "status.podIP"
              }
            }
          }
          env {
            name  = "DB_PORT"
            value = "3306"
          }
          env {
            name  = "DB_NAME"
            value = "db"
          }
          port {
            container_port = 8089
          }
        }
      }
    }
  }
}

resource "kubernetes_secret" "mysql_secret" {
  metadata {
    name = "mysql-secret"
    namespace = kubernetes_namespace.prod.metadata.0.name
  }
  type = "Opaque"
  data = {
    username = var.sqlUsername
    password = var.sqlPassword
  }
}


resource "kubernetes_deployment" "angular_app" {
  metadata {
    name = "angular-app"
    namespace = kubernetes_namespace.prod.metadata.0.name
  }
  spec {
    selector {
      match_labels = {
        component = "angular"
      }
    }
    template {
      metadata {
        labels = {
          app       = "app"
          component = "angular"
        }
      }
      spec {
        container {
          name  = "angular-app"
          image = "gatrimohamedali/front-devops:v1.0.3"
          env {
            name  = "API_URL"
            value = "http://spring-boot-service:8089"
          }
          port {
            container_port = 80
          }
        }
      }
    }
  }
}