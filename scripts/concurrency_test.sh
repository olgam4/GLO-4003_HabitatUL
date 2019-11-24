response=$(curl -X POST \
  http://localhost:8080/v1/quotes \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c818a60a-327b-4c2c-9498-bfcd525cb540' \
  -H 'cache-control: no-cache' \
  -d '{
	"personalInformation": {
		"firstName": "Samuel",
		"lastName": "Cabral Cruz",
		"birthDate": "1992-05-14",
		"gender": "MALE",
		"universityProfile": {
			"idul": "SACAC1",
			"ni": "111006369",
			"program": {
				"cycle": "2e",
				"degree": "baccalauréat",
				"major": "génie logiciel"
			}
		}
	},
	"additionalInsured": {
		"firstName": "Julie",
		"lastName": "Tétrault",
		"birthDate": "1991-10-15",
		"gender": "FEMALE",
		"universityProfile": {
			"idul": "JUTET2",
			"ni": "111111111",
			"program": {
				"cycle": "2e",
				"degree": "maîtrise",
				"major": "informatique"
			}
		}
	},
	"location": {
		"zipCode": "g3A 0J5",
		"streetNumber": "35",
		"apartmentNumber": "402",
		"floor": "1ST"
	},
	"effectiveDate": "2019-11-23",
	"building": {
		"numberOfUnits": 35,
		"preventionSystems": ["SPRINKLER"],
		"commercialUse": "OTHER"
	},
	"personalProperty": {
		"coverageAmount": 10000,
		"animals": [
			{
				"breed": "cat",
				"quantity": 1
			},
			{
				"breed": "dog",
				"quantity": 3
			},
			{
				"breed": "cat",
				"quantity": 3
			},
			{
				"breed": "wapiti",
				"quantity": 5
			}
		]
	},
	"civilLiability": {
		"limit": "1M"
	}
}')
quoteId=$(echo $response | jq -r ".quoteId")

curl -X POST \
  http://localhost:8080/v1/users \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 695a676a-9f24-4e2f-b5cc-0b149d514c59' \
  -H 'cache-control: no-cache' \
  -d '{
	"username": "samuel",
	"password": "qwert"
}'

response=$(curl -X POST \
  http://localhost:8080/v1/users/authenticate \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7712a05a-940b-41c1-aeed-4a1a2a9cdbba' \
  -H 'cache-control: no-cache' \
  -d '{
	"username": "samuel",
	"password": "qwert"
}')
accessToken=$(echo $response | jq -r ".accessToken")

url=$(printf "http://localhost:8080/v1/quotes/%s/purchase" $quoteId)
echo $url
curl -o concurrency1.txt -X POST \
  $url \
  -H "Authorization: Bearer $accessToken" \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 38933482-3ade-48fa-a135-66a23be768e5' \
  -H 'cache-control: no-cache' & curl -o concurrency2.txt -X POST \
  $url \
  -H "Authorization: Bearer $accessToken" \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 38933482-3ade-48fa-a135-66a23be768e5' \
  -H 'cache-control: no-cache'
