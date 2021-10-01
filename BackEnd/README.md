# BackEnd

pom.xml is the dependency file which is organized by Maven for this project.

BackEnd enables users to search twitch resources (stream/video/clip) and set favorite contents after login/logout by session-based authentication. With previous history of favoirtes, we recommend the user more twitch resources by content-based recommendation algorithm.

1. The files under entity file define the classes for all potential entities and their mappings from JSON to class and class to JSON by jackson. Builder pattern is used in this part.
2. The files under db file enable the potential operations(create, insert, delete) on AWS MySQL (RDS service) by JDBC. Three tables are used to save all related information. 
   * user saves the user_id, user_name, user_password for login services (verification, user_id as primary key) 2) item table to store the Twitch resources that are set by some users as favorites (items_id as primary key) 3) favorties_record table stores the user_id, items_id (user_id, items_id together as a primary key, each of them are foreigh keys for the residual two tables).
4. The files under external file help to assemble and resolve the JSON reply for Twitch API by JDBC. 
5. The files under recommendation implement item based recommendation according to the favorites of each user. If there is no favorite information for this user, we will recommend the top-n contents from Twitch.
6. Servlet helps to assemble HTTP requests from front end by Java Servelet.
