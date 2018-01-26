# HTML to PDF Conversion AWS Lambda Service

This a project containing source code to convert HTML to PDF file using AWS Serverless service. 

Project generated using the AEM Multimodule Lazybones template and follows best practices and guidelines listed in Adobe AEM documentation.

## Prerequisite

You must have a valid AWS developer account. If you don't have an account yet, you get started for free using the below link

    http://aws.amazon.com/free

## Building

This project uses Maven for building. Common commands:

From the root directory, run ``mvn clean install`` to build the project and generate the jar file in Target folder. This jar file can be manually uploaded to AWS Lambda console for API consumption.

Alternatively you can use AWS SDK or AWS CLI to build the project and deploy to AWS. Refer AWS Lambda documentation for detailed guide.

## Project Details & Frameworks Used

The project uses preset HTML templates which are stored under /resources directory. Multiple templates can be stored and it is necessary to maintain an unique name for the templates. The source code expected the template name as part of request URI path parameter.

Library used for HTML to PDF conversion : Flying Saucer which internally makes use of iText.

Template framework to edit HTML markup  : Freemarker

## AWS Lambda Stack Creation

Refer the below AWS documentation guide to setup Lambda stack and generate API endpoint.

    https://docs.aws.amazon.com/lambda/latest/dg/serverless-deploy-wt.html