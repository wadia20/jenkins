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
                jacoco execPattern: 'target/jacoco.exec',
                       classPattern: 'target/classes',
                       sourcePattern: 'src/main/java'
                bat 'mvn jacoco:report'
            }
        }

        stage('Zip Jacoco Report') {
            steps {
                // Zip le dossier HTML généré
                bat 'powershell Compress-Archive -Path target\\site\\jacoco\\* -DestinationPath target\\jacoco-report.zip'
            }
        }

        stage('Verify ZIP') {
            steps {
                // Vérifie que le fichier ZIP existe avant envoi
                bat 'if exist target\\jacoco-report.zip (echo ZIP exists) else (echo ZIP missing & exit 1)'
            }
        }
    }

    post {
        always {
            // Envoie le mail avec le ZIP attaché
            emailext(
                to: "${RECIPIENTS}",
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.
Statut: ${currentBuild.currentResult}
Voir les détails: ${env.BUILD_URL}""",
                attachmentsPattern: 'target\\jacoco-report.zip'
            )
        }
    }
}
