# Identity Service API Reference

The `Identity service` provides functionalities for user identity management of the BioID system. 
All service endpoints (resources) of the system (except `user login` and `validate token`) are secured and requires a 
user to be authenticated first before access can be granted.


Operations available:

User login
: For authenticating user before access to system resources can be granted.

Validate token
: For verifying the validity of the `token` shared after a successful login.

Update password
: For updating the user account's password.

When a user has been successfully authenticated, a JWT token is returned together with basic client information (name, 
and username). To access a secured resource, the client application need to send a request with the `HTTP` `Authorization` 
header set to the auth token: `Authorization: Bearer xxxx`, where `xxxx` is the auth token return after a 
successful login.