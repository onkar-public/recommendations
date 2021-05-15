pipeline {
    agent any
    stages {
        stage('Build The Image') {
            steps {
                sh 'mvn clean install -Ddocker'
            }
        }

        stage('Push The Image') {
            steps {
                sh 'docker tag teamteach/recommendations:latest 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-recommendations'
                sh '$(aws ecr get-login --no-include-email --region ap-south-1)'
                sh 'docker push 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-recommendations'
            }
        }

        stage('Pull and Run (ssh to ec2)') {
            steps {
                sh 'ssh ec2-user@ms.digisherpa.ai \'$(aws ecr get-login --no-include-email --region ap-south-1) ; docker pull 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-recommendations:latest; docker stop teamteach-recommendations ; docker rm teamteach-recommendations; docker run --net=host -d --name teamteach-recommendations 333490196116.dkr.ecr.ap-south-1.amazonaws.com/teamteach-recommendations:latest; docker rmi $(docker images --filter "dangling=true" -q) \''
            }
        }

    }
}
