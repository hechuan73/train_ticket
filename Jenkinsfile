pipeline {
    agent any
    tools {
	    jdk 'jdk8'
      maven 'maven-3.6.1'        
    }
    stages {
      stage('Checkout') {
        steps {
	        checkout scm
	      }
      }
      stage('Clone') {
        steps {
          echo "Clone..."
          git url: "https://github.com/zhongyuanzhao000/jx-spring.git"
          echo "Clone Successful"
        }
      }
      stage('Build') {
        steps {
          echo "Build..."
          sh 'mvn clean package -DskipTests'
          echo "Package Successful"
        }
      }
      stage('Test') {
        steps {
          echo "Test..."
          sh 'mvn test'
          echo "Test Successful"
        }
      }
    }
}
