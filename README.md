# spring-security-sso



### TODO LIST

- ~~消灭sout~~
- ~~permit all 提供外部化配置~~
- ~~queryString 加密~~
- ~~client / server 提供 Enable* 注解  替换 @ComponentScan~~
- ~~去除无用注释~~
- ~~类重命名~~
- ~~完善 OAuth2ClientAuthenticationToken~~

- 测试 logout （登出删除cookie）
- 提供log 工具类





### 重构
- OAuth2ClientContextFilter ，UserRedirectRequiredException 来替代 provider 方式




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