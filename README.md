# Cisco MCU and TCS

标签（空格分隔）： Cisco MCU TCS

---
[toc]
## 综述
此文档是基于郑州儿童医院关于MCU相关的资料。描述了如何调用Cisco TelePresence Server API并结合实际项目中的会议去控制思科网真设备的启动关停以及其他操作。

* 第一部分：APIs的简单说明
* 第二部分：结合项目使用说明
* 第三部分：MCU服务器相关
* 第四部分：TCS相关

## 第一部分
### Conference
Conference是会议项目中的核心类，所有通过网真进行的一次视频会议都可以看做是一个Conference对象。

Conference包括会议名称（conferenceName）、是否是持久会议（persistent）、持续时间（duration）、会议数字ID（numericID）等。详细可以查看Conference类。

同时Conference提供一些方法，可以对它进行一般对象的增删改查，这些方法被封装在ConferecneService接口中。
### Participant
Participant是会议的参会人，所有通过网真会议连接起来的设备、电话和视频终端都可以看做是参会人（这里Participant指的是设备，不是具体使用设备的人）。

Participant拥有设备类型（type）、是否主要终端（master）、视频在终端显示的位置（oneTableIndex）以及信号传输比特率（maxBitRate）等。详细的可以查看Participant类。

相应的针对Participant有一些的增加参会人，删除参会人等等的操作，被封装在ParticipantService接口中。

### Other
还有一些其他类的包括Device、cdrlog以及system可以更加细致通过MCU来控制网真设备，本次项目暂时没有用到。

## 第二部分
项目中通过一个定时器来实时扫描是否有新增加的网真会议，如果存在则启动MCU去呼叫网真各个设备。定时器类为MCUJob，所有的入口都在这里。

所有的Java源代码都在`../mcu/`包下面

## 第三部分
MCU服务器地址为`10.0.31.3`
用户名为`admin`
密码为`Etyy@2014*`
其他相关参数配置，可以查看`mcu.properties`文件

## 第四部分
TCS是一个录播服务，可以在网真视频会议中，进行录播，以便会议结束之后，可以线下回放。

TCS相关的所有源代码都在`../tcs/`包。
TCS服务器地址为：`10.0.31.14`
用户名为：`administrator`
密码为：`Etyy@2014*`
其他相关参数配置，可以查看`mcu.properties`文件



