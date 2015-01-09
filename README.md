# Introduction

This is a suite of automated BDD acceptance tests for the sample `Task Manager` example Java EE 7 application that can be found here: https://github.com/MikeHake/task-manager-jee 

This test suite makes use of Thucydides, JBehave and RESTAssured to provide a suite of automated BDD (Behavior Driven Development) tests. The structure of this projects follows the Thucydides recommended project structure as described in the Thucydides documentation here: http://www.thucydides.info/ 

# Basic Usage

To run these test, you first must follow the instructions in the README for running the sample `Task Manager` applicaiton here: https://github.com/MikeHake/task-manager-jee 

Once you have the `Task Manager` application up and running on your local host, you can launch this suite of tests from the command line using the following Maven command: 

`mvn clean verify thucydides:aggregate` 

Once the test run completes a very nice report can be found here:

`target/site/thucydides/index.html`
