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

        stage('Extract Jacoco Report') {
            steps {
                // Copier le fichier principal du rapport Jacoco
                powershell '''
                    Copy-Item -Path target/site/jacoco/index.html -Destination jacoco-report.html -Force
                '''

                // Archiver pour Jenkins (historique)
                archiveArtifacts artifacts: 'jacoco-report.html', fingerprint: true
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

Lien vers le Build :
${env.BUILD_URL}

Le rapport de couverture Jacoco est attaché au mail (fichier HTML).""",
                attachmentsPattern: "jacoco-report.html"
            )
        }
    }
}
