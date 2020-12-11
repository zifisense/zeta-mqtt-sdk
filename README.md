# Zeta-mqtt-sdk Zeta网管平台mqtt数据对接

Common lib for Zeta Java mqttclient.

**注意：通配符订阅只支持mqtt3.1协议**
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


### python简单连接订阅示例：
- 前提准备：
```
mkdir subExample
sudo yum -y install python-pip
sudo pip install paho-mqtt
python sub.py 
```
- sub.py代码：
```
import paho.mqtt.client as mqtt
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

def on_connect(client, userdata, flags, rc):
        print("Connected with result code: " + str(rc))

def on_message(client, userdata, msg):
        print(msg.topic + " " + str(msg.payload))

client = mqtt.Client('用户名apikey:密码secretkey+随机数', clean_session=True, userdata=None, protocol=3, transport="tcp") #指定mqtt协议为3.1版本，满足模糊匹配需求
client.username_pw_set('用户名apikey', password='密码secretkey')
client.on_connect = on_connect
client.on_message = on_message
client.connect('连接地址host', 1883, 60)
client.subscribe('#', qos=0)  #订阅的topic
client.loop_forever()
```

