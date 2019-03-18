# 游戏商户接入API文档1.0
---

## 1. 修订控制页

| 编号 | 文档版本 | 修订章节 | 修订原因 | 修订日期  | 修订人 |
| ---- | -------- | -------- | -------- | --------- | ------ |
| 1    | V1.0     | 新增     | 新增     | 2019-3-18 | ljc    |

## 2. 环境地址

| 环境       | 请求地址                      |
| ---------- | :---------------------------- |
| 开发环境   | http://192.168.1.54:8200      |
| 测试环境   | http://192.168.1.57:8200      |
| 外网验证   | http://61.186.204.154:8200    |
| 预发布环境 | https://api.chainlong.io:1443 |
| 生产环境   | https://api.chainlong.io      |

## 3. 公共参数说明

| 参数名字       | 参数类型 | 参数描述       |
| -------------- | -------- | :------------- |
| body           | string   | 封装的json数据 |
| result         | string   | 返回的json数据 |
| url            | string   | 请求地址       |
| params         | string   | 请求参数       |
| token          | string   | 请求头部信息   |
| mbr_public_key | string   | 平台公钥       |
| private_key    | string   | 商户私钥       |



## 4. 数据签名
+ 本文档所定义之接口，是按请求参数值拼接商家私钥之后的字符串，再MD5
+ 例如,请求参数如下：
  ```
  aid: 89898477937650 
  ts: 1552913521
  uid: 100042
  # 商户私钥是 MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpRQRz9cwhgs0B511
  ```
+ 字段排序是按字段名的字母由a-z顺序排列，如上拼接之后待加密的字符串为
  ```
  898984779376501552913521100042MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpRQRz9cwhgs0B511
  ```
+ MD5加密之后的sign为(不区分大小写)
  ```
  c8af37207c34035edfbf7216b6b9cdaa
  ```
+ 所以最终的请求参数为：
  ```
  aid=89898477937650 
  ts=1552913521
  uid=100042
  sign=c8af37207c34035edfbf7216b6b9cdaa
  ```

## 5.接口说明
### 1. 获取用户信息
+ 请求地址： https://api.chainlong.io/app/api/member/info
+ 请求方式：GET(form格式提交)/POST(JSON格式提交)
+ 请求参数

    | 参数名字 | 参数类型 | 参数描述                                 |
    | -------- | -------- | :--------------------------------------- |
    | aid      | long     | 商家APPID                                |
    | ts       | long     | 时间戳(秒),与服务器时间不能超过前后2分钟 |
    | uid      | string   | 用户ID                                   |
    | sign     | string   | 请求值                                   |

+ 请求示例: [点击查看](https://api.chainlong.io/app/api/member/info?aid=89898477937650&ts=1552911881&uid=100042&sign=1698ff6ae446efcec10bc209ce8471f9)
  ```html
  https://api.chainlong.io/app/api/member/info?aid=89898477937650&ts=1552911881&uid=100042&sign=1698ff6ae446efcec10bc209ce8471f9
  ```
-----------------------------------------------------------
+ 返回参数

    | 参数名字      | 参数类型 | 参数描述     |
    | ------------- | -------- | :----------- |
    | code          | int      | 状态码       |
    | message       | string   | 错误描述     |
    | data          | user     | 对象JSON数据 |
    | user.userId   | long     | 用户ID       |
    | user.userName | string   | 用户名       |
    | user.level    | string   | 用户等级     |

+ 返回示例

  ```json  
  {
    code: "200",
    message: "操作成功!",
    data: {
        userId: 100042,
        userName: "16696787585",
        level: "普通会员"
    }
  }
  ```
------------------------------------------------------------
## 6 状态码说明

| 错误码 | 描述                 |
| ------ | :------------------- |
| 200    | 成功                 |
| -1000  | APPID不存在          |
| -1001  | 时间戳与服务器不一致 |
| -1002  | 签名校验失败         |
| -1003  | 用户不存在           |

## 说明
+ 其它如充值提现接口请参考原[SDK开发文档](./README.md)
