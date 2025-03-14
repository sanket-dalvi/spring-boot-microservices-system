pipeline {
    agent any

    environment {
        // Define the Docker images for all services
        EUREKA_IMAGE = 'eureka-server:latest'
        API_GATEWAY_IMAGE = 'api-gateway:latest'
        PRODUCT_QUOTE_SERVICE_IMAGE = 'product-quote-service:latest'
        ORDER_SERVICE_IMAGE = 'order-service:latest'
        DOCGEN_SERVICE_IMAGE = 'docgen-service:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from GitHub
                git 'https://github.com/sanket-dalvi/spring-boot-microservices-system.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Build all the services using Gradle
                    sh './gradlew clean build'
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    // Build Docker images for each service
                    sh '''
                        docker build -t ${EUREKA_IMAGE} ./eureka-server
                        docker build -t ${API_GATEWAY_IMAGE} ./api-gateway
                        docker build -t ${PRODUCT_QUOTE_SERVICE_IMAGE} ./product-quote-service
                        docker build -t ${ORDER_SERVICE_IMAGE} ./order-service
                        docker build -t ${DOCGEN_SERVICE_IMAGE} ./docgen-service
                    '''
                }
            }
        }

        stage('Docker Compose Up') {
            steps {
                script {
                    // Start all services using docker-compose
                    sh '''
                        docker-compose -f docker-compose.yml up -d
                    '''
                }
            }
        }
    }

    post {
        always {
            // Clean workspace and stop containers after the pipeline completes
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded, application deployed successfully!'
        }
        failure {
            echo 'Pipeline failed, please check the logs.'
        }
    }
}
