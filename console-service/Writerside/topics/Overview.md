# BioID System

## Overview

The `BioID system` is a client management system that supports the registration of client's fingerprints 
together with their basic demographic information. Once a client has been registered, they can be 
searched from any system that connects with the BioID system. This introduces an efficient 
`centralized approach to client profile management` which promotes continuity of service provision 
across system boundaries.

#### Challenges the BioID address:
- Siloed client information captured in different systems. 
- Duplicate client profile. 
- Ineffective client verification procedures. 
- Administrative burden by multiple manual data entry. 
- Un-streamlined workflows - spending more time on client management than actual service delivery. 
- Provide some background and context, explain choices and alternatives.

## Architecture

The system has been built on a microservice architecture which enables services to be scaled 
independently, allowing for efficient resource utilization and handling of increased load for 
specific components.

Since functionalities are isolated in dedicated services, each service can be efficiently scaled 
up in isolation in response to traffic accordingly - leading to optimized performance and 
cost-efficiency.

#### System interaction

The logical diagram below depicts the interaction between source systems with the BioID 
microservice-based client management system.

![BioID logical diagram](bioid-logical-diagram.svg)

The system exposes three primary services accessible to client applications: Identity, 
Enrolment, and Search. All client requests are managed through a single entry 
point - the API Gateway, which implements crosscutting concerns such as authentication, 
logging, routing etc.

## Public services

The following are the publicly accessible services to client applications.

Identity service
: Handles source systems identity management functionalities such as authentication and authorization.

Enrolment Service
: Handles the registration of source systems client's basic demographic information together 
with their fingerprint images, and attached auxiliary IDs.

Search service
: Handles the verification of clients using biometric fingerprints.
