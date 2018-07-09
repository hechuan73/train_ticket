# Tag and Uplaod your docker image.
The address of my docker registry is 10.141.211.160:5000. <br>  
You could replace all 10.141.211.160:5000 with you own docker registry address. <br>  

## Part 1:
docker tag ts/ts-admin-basic-info-service 10.141.211.160:5000/master/cluster-ts-admin-basic-info-service <br>  
docker tag ts/ts-admin-order-service 10.141.211.160:5000/master/cluster-ts-admin-order-service <br>  
docker tag ts/ts-admin-route-service 10.141.211.160:5000/master/cluster-ts-admin-route-service <br>  
docker tag ts/ts-admin-travel-service 10.141.211.160:5000/master/cluster-ts-admin-travel-service <br>  
docker tag ts/ts-admin-user-service 10.141.211.160:5000/master/cluster-ts-admin-user-service <br>  
docker tag ts/ts-assurance-service 10.141.211.160:5000/master/cluster-ts-assurance-service <br>  
docker tag ts/ts-basic-service 10.141.211.160:5000/master/cluster-ts-basic-service <br>  
docker tag ts/ts-cancel-service 10.141.211.160:5000/master/cluster-ts-cancel-service <br>  
docker tag ts/ts-config-service 10.141.211.160:5000/master/cluster-ts-config-service <br>  
docker tag ts/ts-consign-price-service 10.141.211.160:5000/master/cluster-ts-consign-price-service <br>  
docker tag ts/ts-consign-service 10.141.211.160:5000/master/cluster-ts-consign-service <br>  
docker tag ts/ts-contacts-service 10.141.211.160:5000/master/cluster-ts-contacts-service <br>  
docker tag ts/ts-execute-service 10.141.211.160:5000/master/cluster-ts-execute-service <br>  
docker tag ts/ts-food-map-service 10.141.211.160:5000/master/cluster-ts-food-map-service <br>  
docker tag ts/ts-food-service 10.141.211.160:5000/master/cluster-ts-food-service <br>  
docker tag ts/ts-inside-payment-service 10.141.211.160:5000/master/cluster-ts-inside-payment-service <br>  
docker tag ts/ts-login-service 10.141.211.160:5000/master/cluster-ts-login-service <br>  
docker tag ts/ts-news-service 10.141.211.160:5000/master/cluster-ts-news-service <br>  
docker tag ts/ts-notification-service 10.141.211.160:5000/master/cluster-ts-notification-service <br>  
docker tag ts/ts-order-other-service 10.141.211.160:5000/master/cluster-ts-order-other-service <br>  
docker tag ts/ts-order-service 10.141.211.160:5000/master/cluster-ts-order-service <br>  
docker tag ts/ts-payment-service 10.141.211.160:5000/master/cluster-ts-payment-service <br>  
docker tag ts/ts-preserve-other-service 10.141.211.160:5000/master/cluster-ts-preserve-other-service <br>  
docker tag ts/ts-preserve-service 10.141.211.160:5000/master/cluster-ts-preserve-service <br>  
docker tag ts/ts-price-service 10.141.211.160:5000/master/cluster-ts-price-service <br>  
docker tag ts/ts-rebook-service 10.141.211.160:5000/master/cluster-ts-rebook-service <br>  
docker tag ts/ts-register-service 10.141.211.160:5000/master/cluster-ts-register-service <br>  
docker tag ts/ts-route-plan-service 10.141.211.160:5000/master/cluster-ts-route-plan-service <br>  
docker tag ts/ts-route-service 10.141.211.160:5000/master/cluster-ts-route-service <br>  
docker tag ts/ts-seat-service 10.141.211.160:5000/master/cluster-ts-seat-service <br>  
docker tag ts/ts-security-service 10.141.211.160:5000/master/cluster-ts-security-service <br>  
docker tag ts/ts-sso-service 10.141.211.160:5000/master/cluster-ts-sso-service <br>  
docker tag ts/ts-station-service 10.141.211.160:5000/master/cluster-ts-station-service <br>  
docker tag ts/ts-ticket-office-service 10.141.211.160:5000/master/cluster-ts-ticket-office-service <br>  
docker tag ts/ts-ticketinfo-service 10.141.211.160:5000/master/cluster-ts-ticketinfo-service <br>  
docker tag ts/ts-train-service 10.141.211.160:5000/master/cluster-ts-train-service <br>  
docker tag ts/ts-travel2-service 10.141.211.160:5000/master/cluster-ts-travel2-service <br>  
docker tag ts/ts-travel-service 10.141.211.160:5000/master/cluster-ts-travel-service <br>  
docker tag ts/ts-travel-plan-service 10.141.211.160:5000/master/cluster-ts-travel-plan-service <br>  
docker tag ts/ts-ui-dashboard 10.141.211.160:5000/master/cluster-ts-ui-dashboard <br>  
docker tag ts/ts-verification-code-service 10.141.211.160:5000/master/cluster-ts-verification-code-service <br>  
docker tag ts/ts-voucher-service 10.141.211.160:5000/master/cluster-ts-voucher-service <br>  
docker tag mongo 10.141.211.160:5000/master/cluster-ts-mongo <br>  
docker tag mysql 10.141.211.160:5000/master/cluster-ts-mysql <br>  
docker tag rabbitmq:management 10.141.211.160:5000/master/cluster-ts-rabbitmq-management <br>  
docker tag redis 10.141.211.160:5000/master/cluster-ts-redis <br>  
docker tag openzipkin/zipkin 10.141.211.160:5000/master/cluster-ts-openzipkin-zipkin <br>  

## Part2
docker push 10.141.211.160:5000/master/cluster-ts-admin-basic-info-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-admin-order-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-admin-route-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-admin-travel-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-admin-user-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-assurance-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-basic-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-cancel-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-config-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-consign-price-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-consign-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-contacts-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-execute-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-food-map-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-food-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-inside-payment-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-login-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-news-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-notification-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-order-other-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-order-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-payment-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-preserve-other-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-preserve-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-price-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-rebook-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-register-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-route-plan-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-route-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-seat-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-security-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-sso-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-station-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-ticket-office-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-ticketinfo-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-train-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-travel2-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-travel-service <br>   
docker push 10.141.211.160:5000/master/cluster-ts-travel-plan-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-ui-dashboard <br>  
docker push 10.141.211.160:5000/master/cluster-ts-verification-code-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-voucher-service <br>  
docker push 10.141.211.160:5000/master/cluster-ts-mongo <br>  
docker push 10.141.211.160:5000/master/cluster-ts-mysql <br>  
docker push 10.141.211.160:5000/master/cluster-ts-rabbitmq-management <br>  
docker push 10.141.211.160:5000/master/cluster-ts-redis <br>  
docker push 10.141.211.160:5000/master/cluster-ts-openzipkin-zipkin <br>  