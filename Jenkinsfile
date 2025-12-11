pipeline {
    agent any

    tools {
        maven 'Maven_3.8'
        jdk 'JDK_11'
    }

    environment {
        RECIPIENTS = "wadiasouiki@gmail.com"
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Tests') {
            steps {
                bat 'mvn clean install -DskipTests=false'
            }
        }

        stage('Generate Jacoco Report') {
            steps {
                // Copier seulement le fichier index.html
                powershell '''
                    Copy-Item -Path "target/site/jacoco/index.html" -Destination "jacoco-report.html" -Force
                '''

                archiveArtifacts artifacts: 'jacoco-report.html', fingerprint: true
            }
        }
    }

    post {
        always {
            emailext(
                to: RECIPIENTS,
                subject: "Jenkins Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: """
Bonjour,

Le build Jenkins est terminé.

Statut : ${currentBuild.currentResult}

Vous trouverez en pièce jointe le rapport Jacoco (version légère).

Cordialement,
Jenkins
""",
                attachmentsPattern: "jacoco-report.html"
            )
        }
    }
}
