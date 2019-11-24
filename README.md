## spring-security-sso




### 授权码模式

~~~
- 获取授权码
	-> http://localhost:8080/sso/oauth/authorize?client_id=client1&response_type=code&redirect_uri=http://localhost:8081/login?next=admin
	<- http://localhost:8081/login?next=/admin&code=skD0u7

- 获取token
	http://localhost:8080/sso/oauth/token?client_id=client1&client_secret=clientSecret&grant_type=authorization_code&redirect_uri=http://localhost:8081/login?next=/admin&code=skD0u7
~~~



### 数据库存储
~~~sql
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(48) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
);
~~~
