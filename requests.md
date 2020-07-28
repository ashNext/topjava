### All
GET http://localhost:8080/topjava/rest/meals

### Get
GET http://localhost:8080/topjava/rest/meals/100007

### getBetween
GET http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31T10:00:00&startTime=&endDate=&endTime=

### Create
POST http://localhost:8080/topjava/rest/meals
Content-Type: application/json

{
  "dateTime": "2020-01-31T12:12:12",
  "description": "Ланч",
  "calories": 777
}

### Update
PUT http://localhost:8080/topjava/rest/meals/100007
Content-Type: application/json

{
  "id": 100007,
  "dateTime": "2020-01-31T14:00:00",
  "description": "Обед",
  "calories": 2000
}