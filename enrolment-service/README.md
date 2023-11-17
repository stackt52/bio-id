## Enrolment Service

The `enrolment service` is a service part of the NPBVS

info:
  title: `Enrollment Service API`
  version: `1.0.0`
  description: `API documentation for the NPBVS Enrollment Service`

paths:
    `/enrolments:`
    post:
      summary: `Enroll a new client`
      description: `Endpoint for enrolling a new client in the NPBVS system.`
      requestBody:
        description: `Client information for enrollment`
        required: `true`
        content:
          application/json:
            schema:
              $ref: `'#/components/schemas/ClientEnrollmentRequest'`
      responses:
        `200`:
          description: `Enrollment successful`
          content:
            application/json:
              schema:
                $ref: `'#/components/schemas/EnrollmentResponse'`
        `400`:
          description: `Bad request`
          content:
            application/json:
              schema:
                $ref: `'#/components/schemas/Error'`

components:
  schemas:
    ClientEnrollmentRequest:
      type: `object`
      properties:
        firstname:
            type: `string`
            description: `The firstname of the client being enrolled`
        lastname:
            type: `string`
            description: `The lastname of the client being enrolled`
        sex:
            type: `char`
            description: `The sex of the client being enrolled`
        dateOfBirth:
            type: `date`
            description: `The date of birth of the client being enrolled`
        bioFingerprints:
            type: `object`
            description: `A list of fingerprint data of the client being enrolled`
            content:
                application/json:
                    schema:
                        $ref: `'#/components/schemas/ClientFingerPrintData'`
        auxiliaryIds:
            type: `object`
            description: `A list of auxiliary Ids of the client being enrolled`
            content:
                application/json:
                    schema:
                        $ref: `'#/components/schemas/ClientAuxiliaryData'`


components:
    schemas:
        ClientFingerPrintData:
            type: `object`
            properties:
                pos:
                    type: `string`
                    description: `The auxiliary Id of the client being enrolled`
                data:
                    type: `string`
                    description: `The type of the auxiliary Id for the client being enrolled`

components:
    schemas:
        ClientAuxiliaryData:
            type: `object`
            properties:
                auxiliaryId:
                    type: `long`
                    description: `The auxiliary Id of the client being enrolled`
                type:
                    type: `string`
                    description: `The type of the auxiliary Id for the client being enrolled`
                value:
                    type: `string`
                    description: `The value of the  auxiliary Id for the client being enrolled`

    EnrollmentResponse:
      type: object
      properties:
        content:
          application/json:
            schema:
              $ref: `'#/components/schemas/ClientEnrollmentRequest'`

    Error:
      type: object
      properties:
        errorCode:
          type: string
          description: A code representing the error
        errorMessage:
          type: string
          description: A human-readable error message
