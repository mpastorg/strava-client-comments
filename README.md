# strava-client-comments
Expects hhtp post in "/strava/comments".

Expects a json object with 2 fields: email and comments.

Saves the information in a database with no validations. If the fields don't exist may fail

Publish a message in Kafka with the received comments.
Originally was expected kafka consumers are reading the topic and sending 
confirmation to the final user and warning to the application admin
about a new message to answer. 

TODO's:
- Consumers for the events to ack to the customer and to warn to the app admin
- Validations and security to prevent any possible attack
- Some kind of authentication to know who really submitted the info
- Save the message with the system date

Disclaimer: This was just when I decided to self training and come back to be in the development space with Java and microservices.
Don't try to learn from here, it was my learning and it shouldn't be yours. 

