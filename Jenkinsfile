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

        stage('Code Coverage') {
            steps {
                jacoco execPattern: 'target/jacoco.exec',
                       classPattern: 'target/classes',
                       sourcePattern: 'src/main/java'
            }
        }

        stage('Archive Jacoco Report') {
            steps {
                // Compresser le rapport Jacoco HTML en ZIP
                powershell 'Compress-Archive -Path target/site/jacoco/* -DestinationPath jacoco-report.zip -Force'

                // Archiver dans Jenkins (utile pour historique)
                archiveArtifacts artifacts: 'jacoco-report.zip', fingerprint: true
            }
        }
    }

    post {
        always {
            emailext(
                to: "${RECIPIENTS}",
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.
    Statut: ${currentBuild.currentResult}
    Voir les détails: ${env.BUILD_URL}""",
                attachmentsPattern: "jacoco-report.zip"
            )
        }
    }

}
