# URL Shortener
Implementation for the CodeFactory coding challenge.

## TL;DR
Executing tests: `./gradlew test`

Running Application: `./gradlew bootRun`
- Prerequisite: Running MongoDB [e.g. on macOS](https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/#run-mongodb-community-edition)

API Documentation: http://localhost:8080/swagger-ui/index.html

## Implementation
I'm using the MongoDB Document id as HexString as the shortened link. This is a straight forward approach but there are also many other ways to solve this. Generating ObjectIds seems to me a quite good approach to achieve scalability due to the random factor and the uniqueness including time. With other approaches you might also think of reserving IDs/Shortened versions for scalability. But maybe I'm forgetting something here - I'm open to discuss and hear your opinion on this.

This implementation is generating a unique shortened version for an url only once. Bit.ly is creating everytime a new one, this might be also due to them collecting further data while generating them (maybe due to some advertising reasons).

Thoughts about the shortened url as this HexString:
- it is still quite long like `62ecc610216a86795ac7f66f` - comparing to bit.ly where they can achieve something like this: https://bit.ly/3vFJhHO
- what I like about my implementation is that the shortened version is case-insensitive, thinking of e.g. mobile devices where you would have some pain writing this case-sensitive

To prevent e.g. security issues I would never log or respond with unvalidated user input. Therefore, I decided to not log e.g. the input parameter for the InvalidObjectIdException.

Features that might come in the feature could be deleting generated shorts after a retention time, validating user input for malicious code and many more.

Since I really just wanted to spend the 2h that were recommended for this task I will not implement a [Functional Test](https://engineering.zalando.com/posts/2022/04/functional-tests-with-testcontainers.html) - but I wanted you to know that I've heard of them and also implemented them in the past :)