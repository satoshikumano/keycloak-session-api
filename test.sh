access_token=$(curl -X POST "http://localhost:8080/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=admin-cli" \
  -d "username=admin" \
  -d "password=admin" \
  -d "grant_type=password" | jq -r '.access_token')

echo "----- Admin Endpoint -----"
curl -X GET "http://localhost:8080/admin/realms/master/hello-admin" \
  -H "Authorization: Bearer ${access_token}"

echo ""
echo "----- Public Endpoint -----"
curl -X GET "http://localhost:8080/realms/master/hello"
