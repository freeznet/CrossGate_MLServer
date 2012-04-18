CrossGate MLServer 简易使用说明
By Free (http://cgdev.me/)

========================================
这个版本的MLServer是用Java写的,采用多线程Socket底层,所以在Linux中使用是完全正常的
目前包含的功能有
1.名片通知
2.缓存邮件
3.排行榜修复
4.卡号自动踢出

部分常见的如自动公告,喇叭的功能暂时没有写

P.S. 端口号限制为9650

使用说明:
安装Java Runtime 1.6以上的版本,并且设置好Java的环境变量
在windows中,打开cmd,输入
java -jar mlsv.jar

Linux中,bash中直接输入
java -jar mlsv.jar

常见问题:
Q : Linux中中文变为乱码
A : 设置系统的locale为zh_CN.gbk既可

Q : 本软件是否会持续更新,以支持更多的功能
A : 看心情

Q : 本软件是否收费
A : 完全免费,可以按需求定制,前提是我闲的没事做且你的定制比较有新意

Q : 这个程序稳定么
A : 我也不知道,整个程序是用不到一天的时间写出来的,自行测试吧,如果有什么问题欢迎反馈到魔力开发者论坛(http://cgdev.me/)中

Q : 程序为什么灭有Mysql的设置 
A : 因为压根没处理数据库信息

Q : 修复了家族么
A : 没

2011/08/04
Free
http://cgdev.me/