Todo-Backend in Clojure
====================

This is a implementation of the [Todo-Backend API spec](https://www.todobackend.com/), using Clojure, Ring/Reitit, Clip and next-jdbc.

![Todo Backend](https://github.com/PrestanceDesign/todo-backend-clojure-reitit/blob/master/todobackend.png)

The API can be hit directly at [http://todo-backend-clojure-reitit.herokuapp.com/todos](http://todo-backend-clojure-reitit.herokuapp.com/todos) or interactively used with the front end client http://www.todobackend.com/client/index.html?https://todo-backend-clojure-reitit.herokuapp.com/todos

It also offers a self-hosted OpenAPI documentation, accessible via Swagger UI.
This API is hosted on Heroku here: http://todo-backend-clojure-reitit.herokuapp.com (opens Swagger UI)

It persists todos to Postgres via [next.jdbc](https://github.com/seancorfield/next-jdbc).

On the `clip` branch, depencies injection, application state and reloaded workflow is managed with the [Juxt Clip](https://github.com/juxt/clip) library.

# Run on localhost

## Configure PostgreSQL server
First you must run a Postgres server with Docker for eg.:

```
$ docker run --name some-postgres -e POSTGRES_DB=todos -e POSTGRES_PASSWORD=mypass -d -p 5432:5432 postgres
```

## Run the application

```
$ export JDBC_DATABASE_URL="jdbc:postgresql://localhost/todos?user=postgres&password=mypass"
$ clj -m todo-backend.core
```

If that port is in use, start it on a different port. For example, port 8100:

```
$ export PORT=8100
$ clj -m todo-backend.core
```

## Run the Application in REPL

```
$ clj -A:dev
```

Once REPL starts, run the system:

```
user=> (start)
```
Now acces the app at: [[http://localhost:3000/][http://localhost:3000/]].

You can also resetting app state after changing code, stopping the app:


```
user=> (reset)
user=> (stop)
```

For Emacs CIDER users, a `.dir-locals.el` file is configured to load the `dev` alias automatically when `cider-jack-in`.

For code reloading, `cider-ns-refresh` hook is configured.
Calling `cider-ns-refresh` (Spacemacs: `, e n r`) will stop the system, reload all namespaces, and restart the app.

# License & Copyright

Copyright (c) 2020 Michaël SALIHI.
Distributed under the Apache Source License 2.0.
