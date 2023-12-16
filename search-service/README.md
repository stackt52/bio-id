## Search Service

The Search service is a component part of the National Patient Biometric Verification System (NPBVS). It determines
whether a client's fingerprint information is registered within the system. It's main function involves retrieving
fingerprint data from the bio-data-service and conducting a comparison with a search payload that includes a fingerprint
image parameter. If a match is identified, the search service interfaces with the enrolment service to retrieve the
client's patient profile using their unique identifier(ID). Subsequently, the service returns the patient profile to
facilitate seamless data access.

## Features

1. **Fingerprint Verification:** It utilises the [sourceAFIS](https://sourceafis.machinezoo.com/java) library to validate the existence of a client's fingerprint information by cross-referencing it with data stored in the bio-data-service.
2. **Client Identification:** It employs the client's ID to query the enrolment service.

## Dependencies

The Search Service relies on the following components:

- **Bio-data-service:** Provides access to fingerprint data for comparison.
- **Enrolment-service:** Facilitates the retrieval of patient profiles based on client identification.