The application receives requests in the form of json files in the "requests" folder. 

For every file processed, a "response_<filename>" file will be created with it's response in the "responses" folder.

The dummy "database", represented by DatabaseAccess class, is populated with 8 customers with balances from 1000 to 8000.

From the 8 example request files provided, 5 will succeed, while 3 should fail and return error messages.


What I haven't had time to do:
-Use maven
-Have an actual database
-Expose the application to receive the json requests as http requests
-Java docs
-Unit tests
-Make the statement list show positive and negative values, instead of just "from" and "to" ids plus a value
-Add concurrency/threads
-Optimizations