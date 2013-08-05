Deploying a jar to local repositoy
==================================

### Reference

https://devcenter.heroku.com/articles/local-maven-dependencies#update-pom-file

### Use the following command to deploy the jar to local repository of the project

   mvn deploy:deploy-file -Durl=file:///path/to/local/repo -Dfile=myfile.jar \\
     -DgroupId=com.example -DartifactId=myartifact -Dpackaging=jar -Dversion=0.1

### Add the local repository in the project's pom.xml

   <repositories>
       <!--other repositories if any-->
       <repository>
           <id>project.local</id>
           <name>project</name>
           <url>file:${project.basedir}/repo</url>
       </repository>
   </repositories>

And then add the corresponding dependencies in the pom.xml
