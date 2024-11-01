import http.client

conn = http.client.HTTPSConnection("api.openweathermap.org")
payload = ''
headers = {}
conn.request("GET", "/data/2.5/weather?lat=48&lon=9&appid={}", payload, headers)
res = conn.getresponse()
data = res.read()

print(data.decode("utf-8"))