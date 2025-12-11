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

        stage('Generate PDF') {
            steps {
                // Exemple pour créer un pdf fictif
                powershell '''
                    New-Item -Path "report.pdf" -ItemType File -Force
                    Add-Content -Path "report.pdf" -Value "Rapport PDF Jenkins"
                '''
            }
        }

        stage('Archive PDF') {
            steps {
                archiveArtifacts artifacts: 'report.pdf', fingerprint: true
            }
        }
    }

    post {
        always {
            emailext(
                to: RECIPIENTS,
                subject: "Build #${BUILD_NUMBER} - Rapport PDF",
                body: "Bonjour,\n\nVeuillez trouver ci-joint le rapport PDF.\n\nCordialement.",
                attachmentsPattern: "report.pdf"
            )
        }
    }
}
