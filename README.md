# Zeta-mqtt-sdk Zeta网管平台mqtt数据对接

Common lib for Zeta Java mqttclient.

** 注意：通配符订阅只支持mqtt3.1协议 **
fusesource库默认mqtt协议版本为3.1
paho库默认mqtt协议版本为3.1.1，如需进行通配符订阅，需显式指定mqtt协议版本为3.1

## 概述
这是对网管平台的mqtt对接方式的示例代码封装mqttclient方法

只需使用一种库即可。调用方式参考：
### fusesource库
- 原始库调用示例：
https://github.com/fusesource/mqtt-client

- Zifi封装后的调用示例：
/zeta-mqtt-sdk/src/test/java/com/zifisense/zeta/mqtt/api/fusesource/ExeSample.java

### paho库调用示例：
- 原始库调用示例：
https://github.com/eclipse/paho.mqtt.java

- Zifi封装后的调用示例：
/zeta-mqtt-sdk/src/test/java/com/zifisense/zeta/mqtt/api/paho/ExeSample.java

