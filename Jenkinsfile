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
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'mvn clean install -DskipTests=false'
                }
            }
        }

        stage('Run Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'mvn test'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    jacoco execPattern: 'target/jacoco.exec',
                           classPattern: 'target/classes',
                           sourcePattern: 'src/main/java'
                }
            }
        }

        stage('Generate JaCoCo PDF') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'if not exist target\\site\\jacoco\\index.html exit 0'
                    bat '"C:\\Program Files\\wkhtmltopdf\\bin\\wkhtmltopdf.exe" --enable-local-file-access --javascript-delay 500 target\\site\\jacoco\\index.html target\\jacoco-report.pdf'
                }
            }
        }
    }

    post {
        always {
            emailext(
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "Le build Jenkins est terminé.\nStatut: ${currentBuild.currentResult}\nVoir les détails: ${env.BUILD_URL}",
                to: "${RECIPIENTS}",
                attachmentsPattern: "target/jacoco-report.pdf"
            )
        }
    }
}
