access_token=$(curl -X POST "http://localhost:8080/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=admin-cli" \
  -d "username=admin" \
  -d "password=admin" \
  -d "grant_type=password" | jq -r '.access_token')

echo "----- Session Endpoint -----"
curl -X PUT "http://localhost:8080/admin/realms/master/sessions/admin" \
  -H "Authorization: Bearer ${access_token}"
