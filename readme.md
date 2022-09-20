# Tan Ky Restaurant project

## Backend Plan:
- Implement SwaggerUI for API testing
- Enable Hibernate level 2 cache using EhCache
- Using Spring Security and Jwt. Only Admin can delete, update, insert Dish, Dish Category, Admin and Customer
- Using HATEOS for self-discovering API and to make sure any API changes that have an effect on the workflow would not break the client application
- Use Jasypt for DB username and password encryption  
- Each Category will have a list of Dishes to displace
- Customer can add Dish to Cart, fill out Customer Form and make a Payment
- Using Paypal as payment gateway
- Break into Micro Services if have time