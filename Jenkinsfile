pipeline {
    agent any

    environment {
        RECIPIENTS = 'wadiasouiki@gmail.com'
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
                bat 'mvn clean install -DskipTests=false'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Generate Jacoco Report') {
            steps {
                // Copier uniquement le rapport principal
                powershell '''
                    Copy-Item -Path target/site/jacoco/index.html -Destination jacoco-report.html -Force
                '''

                // Archive dans Jenkins
                archiveArtifacts artifacts: 'jacoco-report.html', fingerprint: true
            }
        }
    }

    post {
        always {
            emailext(
                to: RECIPIENTS,
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: """
Le build Jenkins est terminé.

Statut : ${currentBuild.currentResult}

Lien du build :
${env.BUILD_URL}

Le rapport de couverture Jacoco est joint en HTML.
""",
                attachmentsPattern: "jacoco-report.html"
            )
        }
    }
}
