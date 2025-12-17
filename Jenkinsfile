pipeline {
    agent any

    environment {
        RECIPIENTS = 'wadiasouiki@gmail.com'
        WKHTMLTOPDF = '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe"'
    }

    tools {
        maven 'Maven_3.8'
        jdk 'JDK_11'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                jacoco execPattern: 'target/jacoco.exec',
                       classPattern: 'target/classes',
                       sourcePattern: 'src/main/java'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat """
                    mvn sonar:sonar ^
                      -Dsonar.projectKey=FinanceApp ^
                      -Dsonar.projectName=FinanceApp ^
                      -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                bat '''
                docker build -t financeapp:1.0 .
                '''
            }
        }

        stage('Docker Run') {
            steps {
                bat '''
                docker rm -f financeapp_container || exit 0
                docker run -d -p 8080:8080 --name financeapp_container financeapp:1.0
                '''
            }
        }
    }

    post {
        always {
            emailext(
                to: "${RECIPIENTS}",
                subject: "Jenkins Build: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.

Statut: ${currentBuild.currentResult}
Job: ${env.JOB_NAME}
Build: #${env.BUILD_NUMBER}

Voir les détails :
${env.BUILD_URL}
"""
            )
        }
    }
}
