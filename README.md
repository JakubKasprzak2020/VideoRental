# Video Rental
REST Application for renting movies recorded on physical copies (VHS, DVD, BlueRay etc.) delivered by couriers to clients homes. Buisness logic was inspired by the early activity of Netflix company.

## How to run the app
1. Either fork or download the app.
2. If you want using mail service of the app you need to fill data fields in application.properties in resources folder (host, port, username and password). For testing you can use account on www.mailtrap.io. Without this step the app is still working fine.
3. Run main method in VideoRentalApplication class.
4. The app will be served at http://localhost:8080/
5. Use Postman or other REST client for sending requests.

## User stories

### What can do everyone (included not logged in user)?
- Register as a new user
- Logged in
- See a list of movies sorted alphabetical, chronological or by genre
- See details about specific movie
- See ranking list of movie raitings

### What can do logged in user and admin?
- See his profile
- See a list of current rented by homself movie copies
- Add a movie to his cart
- See a content of hist cart
- Remove the movie from the cart
- Empty full of his cart
- Order the content of his cart
- Give a rating of the movie

### What can do only admin?
- See all: users, movie copies, deliveries, movie raitings, orders
- See all movie copies of specific movie
- See details of specific: user, movie copy, delivery, order
- Create new: movie, movie copies
- Delete: user, movie, movie copies, delivery, movie ratings, orders 
- Update: user, movie, movie copy, delivery, orders
- Transform order to delivery
- Mark delivery as delivered
- Mark borrowed copy as returned

## Features

### Creating new movies and copies:
- Movie represent an abstract work of art
- Copy represent a physical object on which a movie is recorded
- Movie title mus be unique

### Renting a movie:
- When user adds movie to his cart application booked for him the first free copy of the movie and count charge for renting. If there is no free copy of the movie adding this movie to the cart is impossible.
- When user order the content of his cart, application create an order object
- When order is ready to sent, admin transforms an order to delivery
- When delivery is delivered to user, admin mark delivery as delivered
- When a delivered to user movie copy returned to rental, admin mark this copy as returned
- After returning copy, users indicator of amount that he spent grows up. If indicator reaches specific level user receives new user type

### User types:
- Regular - basic type with no right to discount
- Silver - type of user that has spent at least 300 unit of currency with right to 5% discount
- Gold - type of user that has spent at least 1000 unit of currency with right to 15% discount
- Platinum - type of user that has spent at least 2000 unit of currency with right to 30% discount

### Counting charges:
- Basic cost is equal to 5 unit of currency
- The longer is rental the cost for day is lower. First three days of renting movie have cost multiplier equal to 2, next three days have cost multiplier equal to 1 and all other days have cost multiplier equal to 0.5
- The older is movie, the cheaper it is. Movies have four different categories based on number of days that passed after their lounched. Premiere movies have cost multiplier equal to 3, new movies cost multiplier equal to 2, standard movies cost multiplier equal to 1 and classical movies cost multiplier equal to 0.5.
- When application calculate cost it includes discounts caused by user types. 

### Mail service:
User received this types of an email from appication:
- Confirmation after placing an order.
- Congratulations after reaching new user type.
- Thanks after returning borrowed copies.

### Movie raiting:
- All users can give a raiting to a movie in scale from 1 to 10.
- Application counts average rating for each movie. This average rating is avaiable for everyone. Only admin can see specific rating gived by the user.

### Register as a new user:
- email address must be unique
- email address is validated as being email
- new user has a regular user type

### Sample Data:
- Application has uploaded sample data whith 20 movies, 2 normal users, 1 admin and a few movie ratings gave by users.
- "Inglourious Basterds" movie has 3 copies, "E.T." has 2 copies, and all other movies has one copy. All copies are free.
- There are no orders and deliveries in sample data. 
- Users in sample data havn't got any borrowed copies. Theire user type is regular and amount spent is 0.

## Login data
- For log in as admin:
user name: admin@admin.com
password: password

- For log in as user 1:
user name: quentin@quentin.com
password: password

- For log in as user 2:
user name: queen@elizabeth.gb
password: longlive

## List of avaiable requests:

### For everyone
- See all movies in alphabetical order: (GET) /api/movies
- See all movies in chronological order: (GET) /api/movies/chrono
- See all movies in specific genre: (GET) /api/movies/genre/{genreName} (List of genre: action, cartoon, comedy, crime_story, drama, historical, horror, musical, sci_fi, western, others)
- See details of specific movie: (GET) /api/movies/{title}
- Create new user: (POST + user sign in data) /registration
- See all average movie raitings: (GET) /api/ratings

### For logged in users
- See your profile: (GET) /api/my_profile
- See your currently borrowed copies: (GET) /api/my_copies
- See your cart: (GET) /api/cart
- Add movie to cart: (PUT + number of rental days) api/cart/{movieId}
- Create an order from cart content: (PUT) /api/orders
- Give movie rating: (POST + number of rating) /api/ratings/{movieId}

### For admin only
- Create new movie: (POST with Json of movie) /admin/movies
- Delete movie: (DELETE) /admin/movies/{id}
- Update movie: (PUT with Json of movie) admin/movies/{id}
- See all users: (GET) /admin/users
- See details of specific user: (GET) /admin/users/{id}
- Delete user: (DELETE) api/users/{id}
- Update userL: (PUT + Json of user) api/users/{id}
- Remove movie from cart: (PUT) /api/cart/out/{copyId}
- Empty whole cart: (PATCH) /api/cart
- See all copies: (GET) /admin/copies
- See all copies of the specific movie: (GET) /admin/copies/movie/{title}
- See details of specific copy: (GET) /admin/copies/{id}
- Create new copy of the movie: (POST) /admin/copies/{movieId}
- Delete copy: (DELETE) /admin/copies/{copyId}
- Update copy: (PUT + Json of copy) /admin/copies/{copyId}
- Mark rented copy as returned: (PUT) /admin/return/{copyId}
- See all orders: (GET) /admin/orders
- See details of specific order:(GET) /admin/orders/{id}
- Delete an order: (DELETE) /admin/orders/{id}
- Update an order: (PUT + Json of order) /admin/orders/{id}
- Create a delivery from an order:(POST) /admin/deliveries/{orderId}
- Delete a delivery: (DELETE) /admin/deliveries/{deliveryId}
- Mark a delivery as delivered: (PUT) /admin/deliveries/{deliveryId}
- See all deliveries: (GET) /admin/deliveries
- See details of specific delivery: (GET) /admin/deliveries/{id}
- Update delivery: (PATCH + Json of delivery) /admin/deliveries/{id}"
- See all raitings: (GET) /admin/ratings
- Delete a raiting: (DELETE) /admin/ratings/{id}

## Sample Jsons:
- Movie
  {
        "title": "Grand Budapest Hotel",
        "description": "Great movie directed by Wes Anderson.",
        "releaseDate": "2014-01-01",
        "genre": "DRAMA"
    }
- User sign in data
  {
        "name": "John",
        "lastName": "Kennedy",
        "password": "pass",
        "email": "john@gmail.com",
	"address": "Washington"
    }
- User
    {
        "name": "John",
        "lastName": "Kennedy",
        "email": "john@gmail.com",
        "address": "Washington"
        "userType": "REGULAR",
        "amountSpent": 0.00
    }
- Copy
  {
        "rentalDays": 0,
        "rentalDate": null,
        "movie": {
            "id": 1,
            "title": "Inglourious Basterds",
            "description": "Second World War in Europe...",
            "releaseDate": "2009-01-01",
            "genre": "ACTION"
        },
        "user": null,
        "available": true
    }
- Movie Raiting
    {
        "userId": 999,
        "movieId": 1,
        "rating": 10
    }

## Dependencies
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-starter-mail
- spring-boot-starter-test
- spring-boot-starter-security
- spring-security-test
- h2database
- lombok
- junit test
- gson
- hibernate-validator

