pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/HeshaniThennakoon/Devops-BookStore.git'
        BRANCH = 'master'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master', url: 'https://github.com/HeshaniThennakoon/Devops-BookStore.git'
            }
        }
        
        stage('Build Backend Docker Image') {
            steps {
                script {
                    bat '''
                    cd backend
                    docker build -t heshani123/devops_backend .
                    '''
                }
            }
        }
        
        stage('Build Frontend Docker Image') {
            steps {
                script {
                    echo "Building frontend Docker image"
                    bat '''
                    @echo off
                    cd frontend
                    docker build -t heshani123/devops_frontend .
                    '''
                }
            }
        }
        
        stage('Push Docker Images') {
            steps {
                bat '''
                docker login -u heshani123 -p Heshani123
                echo "Pushing backend Docker image"
                docker push heshani123/devops_backend:latest
                echo "Pushing frontend Docker image"
                docker push heshani123/devops_frontend:latest
                '''
            }
        }
        
        stage('Deploy with Docker Compose') {
            steps {
                echo "Deploying with Docker Compose"
                bat 'docker-compose down'
                bat 'docker-compose up -d'
            }
        }
    }
}