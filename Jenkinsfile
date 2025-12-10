pipeline {
    agent any

    environment {
        // Ton email pour notifications
        RECIPIENTS = 'wadiasouiki@gmail.com'
    }

    tools {
        // Assure-toi que ces noms correspondent aux installations Jenkins
        maven 'Maven_3.8' // Change selon ta configuration
        jdk 'JDK_11'       // Change selon ta configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Compile et package en exécutant les tests
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
                // Utilise la syntaxe compatible avec le plugin Jacoco
                step([$class: 'JacocoPublisher',
                      execPattern: 'target/jacoco.exec',
                      classPattern: 'target/classes',
                      sourcePattern: 'src/main/java'])
            }
        }
    }

    post {
        always {
            // Notification par email via Gmail
            emailext(
                to: "${RECIPIENTS}",
                subject: "Build Jenkins: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """Le build Jenkins est terminé.
Statut: ${currentBuild.currentResult}
Voir les détails: ${env.BUILD_URL}"""
            )
        }
    }
}
