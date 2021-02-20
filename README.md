# manager-patrimony

# Description
api for a manage of patrimony

# Endpoints 


* **Brand**

POST /v1/brand create brand

GET /v1/brand list all brands

GET /v1/brand/{id} get a brand for id

DELETE /v1/brand/{id} delete a brand

PUT /v1/brand/{id} update a brand

GET /v1/brand/{name}/name get a brand for name


* **Patrimony**

POST /v1/patrimony create patrimony

GET /v1/patrimony get all patrimonys

GET /v1/patrimony/{id} get patrimony by id

DELETE /v1/patrimony/{id} delete patrimony

PUT /v1/patrimony/{id} update a patrimony

GET /v1/patrimony/{name}/name get a patrimony by name

GET /v1/patrimony/{brandId}/patrimonys get a patrimony by brand

GET /v1/patrimony/{numberFall}/patrimony get a patrimony by number fall


* **User**

POST /v1/users create a user

GET /v1/users get all users

GET /v1/users/{id} get user by id

DELETE /v1/users/{id} delete user

PUT /v1/users/{id} update user

GET /v1/users/{name}/name get user by name

GET /v1/users/{name}/email get user by email

## Autenticação:

* **URL:** 
http://localhost:8080/login

* **Body:**
{
    "username" : "name",
    "password" : "password"
}

* **Authorization Type:** Bearer Token

## Autor

* **Anderson Almeida** - Desenvolvedor
