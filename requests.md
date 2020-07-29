### All
curl http://localhost:8080/topjava/rest/meals

### Get
curl http://localhost:8080/topjava/rest/meals/100007

### getBetween
curl http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-31

### Delete
curl -X DELETE http://localhost:8080/topjava/rest/meals/100007

### Create
curl -X POST -H "Content-Type: application/json" -d '{"dateTime": "2020-01-31T12:12:12","description": "Ланч","calories": "777"}' http://localhost:8080/topjava/rest/meals

### Update
curl -X PUT -H "Content-Type: application/json" -d '{"dateTime": "2020-01-31T15:15:15","description": "Ужин01","calories": "100"}' http://localhost:8080/topjava/rest/meals/100008