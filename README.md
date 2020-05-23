Todo-Backend in Clojure
====================

This is a simple implementation of the [Todo-Backend API spec](https://www.todobackend.com/), using Clojure, Ring/Reitit and next-jdbc.

The API can be hit directly at [http://todo-backend-clojure-reitit.herokuapp.com/todos](http://todo-backend-clojure-reitit.herokuapp.com/todos)

It persists todos to Postgres via [next.jdbc](https://github.com/seancorfield/next-jdbc).
