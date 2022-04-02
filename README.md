# strava-client-comments
Expects hhtp post in "/strava/comments" 

Expecting a json object with 2 fields: email and comments
Saves the information in a database with no validations. If the fields don't exist may fail
Publishing a message in Kafka with the received comments
Expected kafka consumers are for sending ack to the customer and warning to application admin about a message to read. 

TODO's:
- Consumers for the events to ack to the customer and to warn to the app admin
- Validations and security to prevent any possible attack
- Some kind of authentication to know who really submitted the info

Disclaimer: This was just when I decided to self training and come back to be in the development space with Java and microservices.
Don't try to learn from here, it was my learning and it shouldn't be yours. 

