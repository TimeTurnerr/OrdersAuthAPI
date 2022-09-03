# OrdersAuthApi
API

To Login and generate authentication token 

Go to /auth endpoint
And use Post request to generate access token

In Post request pass user in body as json format
user set 1 - {
                name = test
                password = test
                userType = name
              }
              
user set 2 - {
                name = 1234567890
                password = test
                userType = phone
              }
              
EndPoints - 

/users              

/users/id           

/orders             

/orders/id          

/users/id/orders    

/users/id/orders/id 


All id starts with 1

To access endpoints pass Autharization as key in header and value as 

"Bearer access-token"
