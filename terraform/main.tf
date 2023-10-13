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

/*
resource "kubernetes_deployment" "example" {
  metadata {
    name = "terraform-example"
    labels = {
      test = "MyExampleApp"
    }
  }

  spec {
    replicas = 3

    selector {
      match_labels = {
        test = "MyExampleApp"
      }
    }

    template {
      metadata {
        labels = {
          test = "MyExampleApp"
        }
      }

      spec {
        container {
          image = "nginx:1.21.6"
          name  = "example"

          resources {
            limits = {
              cpu    = "0.5"
              memory = "512Mi"
            }
            requests = {
              cpu    = "250m"
              memory = "50Mi"
            }
          }

          liveness_probe {
            http_get {
              path = "/"
              port = 80

              http_header {
                name  = "X-Custom-Header"
                value = "Awesome"
              }
            }

            initial_delay_seconds = 3
            period_seconds        = 3
          }
        }
      }
    }
  }
}

resource "kubernetes_secret" "example" {
  metadata {
    name = "basic-auth"
  }

  data = {
    username = "admin"
    password = "P4ssw0rd"
  }

  type = "kubernetes.io/basic-auth"
}

resource "kubernetes_service" "example" {
  metadata {
    name = "terraform-example"
  }
  spec {
    selector = {
      app = kubernetes_pod.example.metadata.0.labels.app
    }
    session_affinity = "ClientIP"
    port {
      port        = 8080
      target_port = 80
    }

    type = "LoadBalancer"
  }
}





resource "kubernetes_deployment" "angular_app" {
  metadata {
    name = "angular-app"
  }
  spec {
    selector {
      match_labels = {
        app       = "app"
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
          image = "mohamedaligatri/angularcloudit:v2.0.3"
          env {
            name  = "API_URL"
            value = "http://spring-boot-service:8083"
          }
          port {
            container_port = 80
          }
        }
      }
    }
  }
}*/