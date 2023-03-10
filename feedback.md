Received Feedback:

### Good:
- Working code with pagination.
- Reasonable technology choices.
- Mostly leveraged framework capabilities.

### Bad:
- API-Design:
  - GET method should not have a request body. (note by me: alternatively I could have used the new HTTP QUERY method)
  - No default limit leads to loading of the whole database into memory in case no limit is given.
- Tests:
  - Testcases always use random input for single run, this is in our opinion an anti-pattern.
  - No unit-tests implemented. Instead, integration tests on web controller level are labeled as unit tests.
