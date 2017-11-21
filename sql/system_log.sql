CREATE TABLE system_log (
  id bigint(10) PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',;
  type varchar(10) NOT NULL COMMENT '日志类型',
  title varchar(30) NOT NULL COMMENT '标题',
  remote_addr varchar(200) NOT NULL COMMENT '请求地址',
  remote_uri varchar(100) NOT NULL COMMENT '请求uri',
  method varchar(10) NOT NULL COMMENT '请求方式',
  params varchar(200) NOT NULL COMMENT '请求参数',
  exception varchar(500) NOT NULL COMMENT '异常信息',
  operate_time DATE NOT NULL COMMENT '开始时间',
  timeout bigint(10) NOT NULL COMMENT '请求时长',
  user_id varchar(20) NOT NULL COMMENT '用户ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;