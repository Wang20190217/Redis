# Redis文档学习

## 一、Redis 简介

​       Redis 是一个开源的**内存数据结构存储**，可以用作数据库、缓存、消息代理和流引擎。[redis官网](https://redis.io)

​       Redis 提供了value数据类型有以下几种，例如string  [字符串](https://redis.io/topics/data-types-intro#strings)、hash[散列](https://redis.io/topics/data-types-intro#hashes)、list[列表](https://redis.io/topics/data-types-intro#lists)、set[集合](https://redis.io/topics/data-types-intro#sets)、带范围查询的[排序集合、](https://redis.io/topics/data-types-intro#sorted-sets)[位图](https://redis.io/topics/data-types-intro#bitmaps)、[超日志](https://redis.io/topics/data-types-intro#hyperloglogs)、[地理空间索引](https://redis.io/commands/geoadd)和[流](https://redis.io/topics/streams-intro)。Redis 内置了[复制](https://redis.io/topics/replication)、[Lua 脚本](https://redis.io/commands/eval)、[LRU 驱逐](https://redis.io/topics/lru-cache)、[事务](https://redis.io/topics/transactions)和不同级别的[磁盘持久性](https://redis.io/topics/persistence)，并通过以下方式提供高可用性[Redis Sentinel](https://redis.io/topics/sentinel)和[Redis Cluster](https://redis.io/topics/cluster-tutorial)的自动分区。 

  例如[附加到字符串](https://redis.io/commands/append)； [增加哈希值](https://redis.io/commands/hincrby)；[将元素推入列表](https://redis.io/commands/lpush)；[计算集交](https://redis.io/commands/sinter)[、](https://redis.io/commands/sunion)并 、[差](https://redis.io/commands/sdiff)；或[获取排序集中排名最高的成员](https://redis.io/commands/zrangebyscore) 都是**原子操作**

为了达到最佳性能，Redis 使用 **内存中的数据集**。根据您的用例，Redis 可以通过定期**RDB**[将数据集转储到磁盘](https://redis.io/topics/persistence#snapshotting) 或**AOF**[将每个命令附加到基于磁盘的日志](https://redis.io/topics/persistence#append-only-file)来持久化您的数据。如果您只需要一个功能丰富的网络内存缓存，您也可以禁用持久性。

Redis 支持[异步复制](https://redis.io/topics/replication)，具有快速非阻塞同步和自动重新连接以及网络拆分上的部分重新同步。

Redis 还包括：

- [交易](https://redis.io/topics/transactions)
- [发布/订阅](https://redis.io/topics/pubsub)
- [Lua 脚本](https://redis.io/commands/eval)
- [生命周期有限的密钥](https://redis.io/commands/expire)
- [LRU 驱逐密钥](https://redis.io/topics/lru-cache)
- [自动故障转移](https://redis.io/topics/sentinel)

[您可以从大多数编程语言](https://redis.io/clients)中使用 Redis 。



## 二、安装 Redis

### Linux上安装Redis教程 

#### 一、下载并解压Redis

1、执行下面的命令下载redis：

**wget https://download.redis.io/releases/redis-6.2.6.tar.gz**

2、解压redis：

**tar xzf redis-6.2.6.tar.gz**

3、移动redis目录，一般都会将redis目录放置到 /usr/local/redis目录：

**mv redis-6.2.6 /usr/local/redis**

#### 二、编译并安装redis

1、进入redis安装目录，执行make命令编译redis：

**cd /usr/local/redis**

**make**

等待make命令执行完成即可。

如果执行make命令报错：cc 未找到命令，原因是虚拟机系统中缺少gcc，执行下面命令安装gcc：

**yum -y install gcc automake autoconf libtool make**

如果执行make命令报错：致命错误:jemalloc/jemalloc.h: 没有那个文件或目录，则需要在make指定分配器为libc。执行下面命令即可正常编译：

**make MALLOC=libc**

make命令执行完，redis就编译完成了。

2、执行下面命令安装redis，并指定安装目录

**make install PREFIX=/usr/local/redis**

至此，redis即安装成功 

#### 三、启动redis

1、进入redis安装目录，执行下面命令启动redis服务

**./bin/redis-server redis.conf**

此时，可以看到redis服务被成功启动：

但这种启动方式不能退出控制台，如果退出，那么redis服务也会停止。如果想要redis以后台方式运行，需要修改redis的配置文件：**redis.conf**。将该配置文件中的**daemonize no**改为**daemonize yes**即可： 

修改完成后，重新执行启动命令启动redis，然后通过下面命令查看redis进程，可以发现redis服务已经被启动了 

**ps -ef | grep redis** 

2、通过redis-cli测试redis是否可用，在redis安装目录执行下面命令：

**./bin/redis-cli**

#### 四、redis变成一个服务

来到 **/etc/profile**文件配置一下环境变量 vim /etc/profile 在文件的末尾添加

```properties
export REDIS_HOME=/usr/local/redis
export PATH=$PATH:$REDIS_HOME/bin
```

回到安装目录**/usr/local/redis/utils**执行instll_server.sh

进行到`./install_server.sh`时报错 

```properties
This systems seems to use systemd.
Please take a look at the provided example service unit files in this directory, and adapt and install them. Sorry!
```

解决方案： 

vim ./install_server.sh  注释掉下面的内容

```properties
#bail if this system is managed by systemd
#_pid_1_exe="$(readlink -f /proc/1/exe)"
#if [ "${_pid_1_exe##*/}" = systemd ]
#then
#       echo "This systems seems to use systemd."
#       echo "Please take a look at the provided example service unit files in this directory, and adapt and install them. Sorry!"
#       exit 1
#fi
```

![](redisImg\redis服务配置.png)

至此可以在服务器上开启多个实例并且实例之间用端口号区分。

配置文件在 /etc/redis/端口.conf

日志文件在/var/log/redis_6379.log

数据文件在/var/lib/redis/6379

服务启动路径在 /usr/local/redis/rediss-server

客户端连接服务器路径在 /usr/local/redis/rediss-cli



cd /etc/init.d 路径下就会防止一个redis_6379一个可执行脚本

我们可以在任意路径下启动这个脚本来启动redis

redis启动  **service redis_6379 start**

redis关闭  **service redis_6379 stop**

redis状态  **service redis_6379 status**

连接客户端



#### 五、设置远程连接

```properties
1.bind 127.0.0.1 修改为 bind 0.0.0.0
127.0.0.1  	表示只允许本地访问,无法远程连接
0.0.0.0     表示任何ip都可以访问

2.protected-mode yes 改为 protected-mode no
yes			  保护模式，只允许本地链接
no			  保护模式关闭

3.daemonize yes 改为 daemonize no 
yes： 代表开启守护进程模式。此时是单进程多线程的模式，redis将在后台运行。
no： 当前界面将进入redis的命令行界面，exit强制退出或者关闭连接工具都会导致redis进程退出

4.修改密码，查找# requirepass foobared，添加如下命令：requirepass 123456  设置密码为：123456
```



### Docker安装redis 

一、直接使用docker命令下一个redis的镜像： 

```
docker pull redis
```

二、在/home目录里创建一个redis目录，里面放一个redis.conf文件和一个data目录，如下： 

redis.conf文件可以到官网里下，然后再上传到服务器：<https://hub.docker.com/_/redis?tab=tags>，下载后找到redis.conf这个文件上传到服务器

三、修改redis.conf配置文件的几个地方：vim redis.conf  

```properties
bind 127.0.0.1 #注释掉这部分，使redis可以外部访问
daemonize no#用守护线程的方式启动
requirepass 你的密码#给redis设置密码
appendonly yes#redis持久化　　默认是no
tcp-keepalive 300 #防止出现远程主机强迫关闭了一个现有的连接的错误 默认是300
```

四、使用docker命令启动redis： 

```properties
docker run -p 6379:6379 --name redis -v /home/redis/redis.conf:/etc/redis/redis.conf  -v /home/redis/data:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes
```

五、查看是否启动成功 

```properties
docker ps   #查看正在运行的窗口

docker logs redis    #或打印一下redis的启动日志
```

### Windows安装redis  

发现官网名没有提供Windows版本，只有Linux版本，费了一番周折，说是GitHub上才有 

一、进入https://github.com/microsoftarchive/redis/releases下载zip后缀

二、安装教程

双击，一路next，选择安装文件夹，**并勾选配置path（环境变量）**，切记，不然你每次都只能在安装目录下启动命令redis-server 

三、运行

两种方式：

1.命令行的方式

2.直接来到安装目录双击 redis-server.exe 然后双击redis-cli.exe

## 三、Java客户端

**java客户端就是对redis命令进行封装成api，通过这些客户端可以连接到redis服务器，并进行操作**

常用的有以下三种：

#### 1.Redisson

Redisson - 具有内存数据网格功能的 Redis Java 客户端。超过 50 个基于 Redis 的 Java 对象和服务：Set、Multimap、SortedSet、Map、List、Queue、Deque、Semaphore、Lock、AtomicLong、Map Reduce、发布/订阅、Bloom filter、Spring Cache、Tomcat、Scheduler、JCache API、Hibernate , MyBatis, RPC, 本地缓存... [ Apache-2.0](https://github.com/mrniko/redisson)

使用指数 18712

#### 2.Jedis

Redis Java 客户端专为提高性能和易用性而设计。 [ MIT](https://github.com/redis/jedis)

使用指数 10362

#### 3.lettuce

用于线程安全同步、异步和反应式使用的高级 Java Redis 客户端。支持集群、哨兵、流水线和编解码器。 

[ Apache-2.0](https://github.com/lettuce-io/lettuce-core)

使用指数 4496

## 四、常用的库

**java语言：**

​     Spring Data Redis   提供支持以在使用 Redis（一种键值存储）时提高 Java 开发人员的工作效率。使用熟悉的 Spring 概念，例如用于核心 API 使用的模板类和轻量级存储库样式数据访问 

**Javascript：**

​     RedisDesktopManager    Redis桌面管理器【GUI客户端】 

## 五、redis中NIO原理

### 1.常识：

**磁盘**：1.寻址 毫秒级，带宽 G/M

**内存**：1.寻址 纳秒级，带宽很大    磁盘比内存在寻址慢10w倍 

I/O buffer：成本问题。处于内存和磁盘之间的一个缓冲区

磁盘有磁道和扇区的概念，一扇区512byte，带来成本变大，在读取数据时，无论你读多小，都会至少从磁盘拿4k数据。 

随着文件越来越大，速度变慢，磁盘I/O成为瓶颈

### 2.关系型数据库

​       数据库的出现为了解决磁盘I/O瓶颈问题这一问题，让数据以4K为单位进行存储刚刚好等同于一次I/O。但是查询还是从头到位的把这些页加载进内存并没有解决问题，因此出现了**索引**的结构。当我们查询的时候命中索引了，就会顺着B+T的根节点找到磁盘中的数据并加载进内存。

带好的好处：减少了I/O的次数，加快了查询的效率（用空间换时间）

![](redisImg\数据库结构.png)

关系型数据库建表：必须给出数据类型，也就是明确字节宽度

因为这样数据的宽度就确定了，数据存储时字节宽度不够的会以空数据占位，为的是然后数据的覆盖不会导致数据的位置改变 

1.当数据库表变大的时候，增删改变慢

2.查询速度（分两种情况）：

- 一个或少量查询依然很快
- 并发大的时候需要加载很多数据进内存会受到磁盘带宽影响速度

### 3.基于内存的关系型数据库

**SAPHANA**内存级别的关系型数据库（内存2T），非常贵

数据在磁盘和内存体积不一样，磁盘中没有指针的概念。所以磁盘的数据放在内存体积会变小

### 4.缓存

为了解决上述问题，提出了折中的方案。拿出一部分内存进行存储数据

实现：

#### 1.memcache

#### 2.redis

## 六、Redis配置文件详情

Redis 可以在没有配置文件的情况下使用内置的默认配置启动，但是此设置仅建议用于测试和开发目的。

配置 Redis 的正确方法是提供一个 Redis 配置文件，通常称为`redis.conf`.

配置指令的示例 

```properties
replicaof 127.0.0.1 6380
```

可以使用（双引号或单引号）提供包含空格的字符串作为参数，如下例所示： 

```properties
requirepass "hello world"
```

###  Redis 6.2版本[redis.conf](https://raw.githubusercontent.com/redis/redis/6.2/redis.conf) 

```properties
# Redis 配置文件示例。
# 请注意，为了读取配置文件，Redis 必须以文件路径作为第一个参数启动：
# ./redis-server /path/to/redis.conf 

# 注意单位：何时需要内存大小, 可以指定
# 它以通常的形式 1k 5GB 4M 等等：
# 
# 1k => 1000 bytes 
# 1kb => 1024 bytes 
# 1m => 1000000 bytes 
#1mb => 1024*1024 bytes 
# 1g = > 1000000000 bytes 
# 1gb => 1024*1024*1024 bytes 
# 单位不区分大小写，所以 1GB 1Gb 1gB 都是一样的。

################################# include ############### ####################
# 如果一台服务器部署多个redis可以采用include将共同配置放在一个文件共用，个性化配置放在另一个文件
# 注意选项 "include" 不会被来自管理员或 Redis Sentinel 的命令 "CONFIG REWRITE" # 重写。
#由于 Redis 始终以最后的配置指令的值为准，所以include放在该文件的开头以避免在运行时覆盖配置更改。如果您有兴趣使用包含来覆盖配置选项，最好使用包含作为最后一行。
 include  /path/to/local.conf 
 include  /path/to/other.conf 
 
 ############################# #### MODULES #################################### 

# 启动时加载模块。如果服务器无法加载模块，它将中止。可以使用多个loadmodule指令
# 模块是redis4.0以上版本新增的特性，可以编写自己的扩展模块，加载进redis。
# 
loadmodule /path/to/my_module.so 
loadmodule /path/to/other_module.so 

# 在启动时加载模块。如果服务器无法加载模块
# 它将中止。可以使用多个 loadmodule 指令。
# 
# loadmodule /path/to/my_module.so 
# loadmodule /path/to/other_module.so 

############################ ##### NETWORK #################################### 

# 默认情况下，如果没有 "绑定”配置指令，Redis侦听来自主机上所有可用网络接口的连接。
# 可以使用监听一个或多个选定的接口
# "bind" 配置指令，后跟一个或多个 IP 地址。
# 每个地址都可以加上“-”前缀，表示如果地址不可用，redis 不会启动失败。不可用仅指任何网络接口不对应的#地址。已经在使用的地址总是会失败，不受支持的协议总是会被默默地跳过。
# 
# 示例：
# bind 192.168.1.100 10.0.0.1 # 监听两个特定的 IPv4 地址
# bind 127.0.0.1 ::1 # 监听环回 IPv4 和 IPv6 
# bind * -::* # 和默认一样，所有可用的接口
#
#~~~警告~~~如果运行Redis的计算机直接暴露在#internet上绑定到所有接口是危险的，并且会将#instance暴露给internet上的每个人。因此，默认情况下，我们取消注释
# 以下绑定指令，这将强制 Redis 仅侦听
# IPv4 和 IPv6（如果可用）环回接口地址（这意味着 Redis将只能接受来自与它相同的主机的客户端连接正在运行）。
# 
# 如果您确定希望您的实例收听所有接口
# 只需注释掉以下行。
# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ ~~~~~~~~~~~~~~~~~~~~~~~
bind 127.0.0.1 -::1

# 保护模式是一层安全保护，为了避免
#Redis 实例在互联网上保持打开状态被访问和利用。
# 
# 当保护模式开启并且如果：
# 
# 1) 服务器没有使用
# "bind" 指令显式绑定到一组地址。
# 2) 没有配置密码。
# 
# 服务器只接受来自
#IPv4 和 IPv6 环回地址 127.0.0.1 和 ::1 以及来自 Unix 域
# 套接字的客户端的连接。
# 
# 默认情况下启用保护模式。仅当您确定希望其他主机的客户端连接到 Redis时才应禁用它
# 即使没有配置身份验证，也没有使用“绑定”指令明确列出一组特定的接口。
protected-mode yes 

# 接受指定端口上的连接，默认为 6379 (IANA #815344)。
# 如果指定端口 0，Redis 将不会监听 TCP 套接字。
port  6379 

# TCP 侦听（）积压。
# 
# 在每秒请求数较高的环境中，您需要大量积压以避免客户端连接速度慢的问题。请注意，Linux 内核会默默地将其截断为 /proc/sys/net/core/somaxconn 的值，因此确保提高 somaxconn 和 tcp_max_syn_backlog 的值以获得所需的效果。
tcp-backlog 511

# Unix 套接字。
# 
# 指定用于侦听
# 传入连接的 Unix 套接字的路径。没有默认值，因此 Redis在未指定时不会在 unix 套接字上侦听 
# 
# unixsocket /run/redis.sock 
# unixsocketperm 700 

# 在客户端空闲 N 秒后关闭连接（0 表示禁用）
timeout 0 

# TCP keepalive。
# 
# 如果非零，在没有通信的情况下使用 SO_KEEPALIVE 向客户端发送 TCP ACK。这很有用，原因有两个：
# 1) 检测死节点。
# 2) 强制中间的网络设备认为连接是活着的。
#
# 在 Linux 上，指定的值（以秒为单位）是用于发送 ACK 的周期。
# 注意关闭连接需要双倍的时间。
# 在其他内核上，周期取决于内核配置。
# 
# 此选项的合理值是 300 秒，这是从 Redis 3.2.1 开始的新 #Redis 默认值。
tcp-keepalive 300 

(未完待续)
 
```

## 七、Redis单进程为何可以高并发

Redis是单进程单实例，可以并发很多的请求如何变得很快的呢？

多个连接先到操作系统的内核，内核和redis采用的是epoll，采用的是epoll多路复用

插曲：

多个连接先到达计算机的内核，进程过来想要读取某个连接数据，给内核发送一个read命令，当数据包没有来之前，这个线程一直是阻塞的状态。这也就是BIO时期，资源的浪费。在高并发下，回到是大量的线程被阻塞，而真正准备好的连接无法进行

### I/O的发展

![](redisImg\IO发展.png)



## 八、Redis的数据类型命令及使用场景

### 参考命令手册

Redis 数据类型以及应用场景

## 九、Redis管道

### **一、为什么要使用Pipeline？**

Redis是采用基于C/S模式的请求/响应协议的TCP服务器。
           性能问题一：redis客户端发送多条请求，后面的请求需要等待前面的请求处理完后，才能进行处理，而且每个请求都存在往返时间RRT（Round Trip Time），即使redis性能极高，当数据量足够大，也会极大影响性能，还可能会引起其他意外情况。
           性能问题二：性能问题一，我们可以通过scan命令来解决，如何来设置count又是一个问题，设置不好，同样会有大量请求存在，即使设置到1w(推荐最大值)，如果扫描的数据量太大，这个问题同样不能避免。每个请求都会经历三次握手、四次挥手，在处理大量连接时，处理完后，挥手会产生大量time-wait，如果该服务器提供其他服务，可能对其他服务造成影响。

### 二、如何在使用Pipeline？



## 十、Redis冷启动

大量插入数据

从文件中批量插入数据

## 十一、Redis消息订阅



## 十二、Redis事务

## 十三、Redis 模块

### RedisBloom 

## 十四、Redis移出策略

## 十五、Redis的持久化

### RDB 

实现原理：fork出子进程，共享父进程的内存

落地实现：

两种方式

1.通过命令主动持久化  save bgsave

2.配置文件中给出触发条件

弊端：

不支持拉链，永远只有一个dump.rdb   解决：写定时，拷贝到其他的机器

丢失数据多一些，时点与时点之间窗口数据容易丢失

优点：从硬盘恢复速度快，类似java的序列化



### AOF

redis的写操作记录到文件中

丢失数据少

redis中可以同时开启，如果开启了AOF只会用AOF恢复，4.0以后AOF包含RDB全量，增加记录新的写操作

体量无限变大，恢复慢 

设计一个让日志足够小的方案

 解决方式： 

hdfs fsimage +edits.log

4.0以前 重写（抵消和整合命令）

4.0以后 重写 将老的数据RDB到aof文件中，将增量的以指令的方式append到AOF



### 混合模式

全量复制和增量复制



## 十六、主从复制

一般提到单机，单实例，单节点会存在哪些问题

1.单点故障

2.容量有限

3.压力



同步阻塞进行同步   强一致性

异步 会丢失数据      弱一致性

可以通过



## 十七、AKF原则

X轴：复制多个实例

Y轴：拆分业务，根据不同业务访问不同redis

​        业务不能拆分的，算法hash+取模 访问不同的客户端（弊端：模数值是固定的，会影响分布式下扩展性）

​        使用random的方式随即放在redis中，主要用于消息队列

​        使用一致性哈希算法   优点：你加节点，的确可以分担其他节点的压力，不会造成全局洗牌

​                                              缺点：新增节点会造成有一小部分数据不能命中 1.问题 会缓存击穿，压到mysql

​                                                          没法取离我最近的2个物理节点

​                                               虚拟节点解决数据倾斜的问题

为了解决一个redis服务器太多的客户端连接（太多的链接对服务器来说成本很高，不管是长连接，还是短链接都需要进行三次握手，四次挥手），我们可以通过nginx服务器来进行代理。如果代理服务器也扛不住可以进行nginx集群，还可以在nginx前中 LVS   

  https://lupengfei.blog.csdn.net/article/details/86514445?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_paycolumn_v3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1.pc_relevant_paycolumn_v3&utm_relevant_index=2       



## 十八、Redis常见问题

Redis 击穿、穿透、雪崩产生原因以及解决思路 

https://baijiahao.baidu.com/s?id=1716363516070306430&wfr=spider&for=pc

Redis 分布式锁

Redisson

zookeeper0



## 十九、SpringBoot整合redis

### 1. 概述

市面上已经有 Redis、Redisson、Lettuce 等优秀的 Java Redis 工具库，为什么还要有 Spring Data Redis 呢？ 学不动了，头都要秃了！不要慌，我们先来看一张图： 

![](E:\Redis\redisImg\SpringDataRedis作用.jpg)

- 对于下层，Spring Data Redis 提供了统一的操作模板（后文中，我们会看到是 RedisTemplate 类），封装了 Jedis、Lettuce 的 API 操作，访问 Redis 数据。所以，**实际上，Spring Data Redis 内置真正访问的实际是 Jedis、Lettuce 等 API 操作**。
- 对于上层，开发者学习如何使用 Spring Data Redis 即可，而无需关心 Jedis、Lettuce 的 API 操作。甚至，未来如果我们想将 Redis 访问从 Jedis 迁移成 Lettuce 来，无需做任何的变动。😈 相信很多胖友，在选择 Java Redis 工具库，也是有过烦恼的。
- 目前，Spring Data Redis 暂时只支持 Jedis、Lettuce 的内部封装，而 Redisson 是由 redisson-spring-data 来提供。

### 2.pom文件导入依赖

```xml
<!-- 实现对 Spring Data Redis 的自动化配置 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
        <exclusions>
            <!-- 去掉对 Lettuce 的依赖，因为 Spring Boot 优先使用 Lettuce 作为 Redis 客户端 -->
            <exclusion>
                <groupId>io.lettuce</groupId>
                <artifactId>lettuce-core</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- 引入 Jedis 的依赖，这样 Spring Boot 实现对 Jedis 的自动化配置 -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
    </dependency>

    <!-- 方便等会写单元测试 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- 等会示例会使用 fastjson 作为 JSON 序列化的工具 -->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.61</version>
    </dependency>

    <!-- Spring Data Redis 默认使用 Jackson 作为 JSON 序列化的工具 -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>

```

### 3.配置文件

```yaml
spring:
  # 对应 RedisProperties 类
  redis:
    host: 39.96.50.166 #服务器ip
    port: 6379      #端口
    password: 123456 # Redis 服务器密码，默认为空。生产中，一定要设置 Redis 密码！
    database: 0 # Redis 数据库号，默认为 0 。
    timeout: 0 # Redis 连接超时时间，单位：毫秒。
    # 对应 RedisProperties.Jedis 内部类
    jedis:
      pool:
        max-active: 8 # 连接池最大连接数，默认为 8 。使用负数表示没有限制。
        max-idle: 8 # 默认连接数最小空闲的连接数，默认为 8 。使用负数表示没有限制。
        min-idle: 0 # 默认连接池最小空闲的连接数，默认为 0 。允许设置 0 和 正数。
        max-wait: -1 # 连接池最大阻塞等待时间，单位：毫秒。默认为 -1 ，表示不限制

 
```

### 4.简单测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
publicclass Test01 {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStringSetKey() {
        stringRedisTemplate.opsForValue().set("yunai", "shuai");
    }
}
```

### 5.RedisTemplate

```java
// RedisTemplate.java
// 艿艿省略了一些不重要的属性。

// <1> 序列化相关属性
@SuppressWarnings("rawtypes") private@Nullable RedisSerializer keySerializer = null;
@SuppressWarnings("rawtypes") private@Nullable RedisSerializer valueSerializer = null;
@SuppressWarnings("rawtypes") private@Nullable RedisSerializer hashKeySerializer = null;
@SuppressWarnings("rawtypes") private@Nullable RedisSerializer hashValueSerializer = null;
private RedisSerializer<String> stringSerializer = RedisSerializer.string();

// <2> Lua 脚本执行器
private@Nullable ScriptExecutor<K> scriptExecutor;

// <3> 常见数据结构操作类
// cache singleton objects (where possible)
private@Nullable ValueOperations<K, V> valueOps;
private@Nullable ListOperations<K, V> listOps;
private@Nullable SetOperations<K, V> setOps;
private@Nullable ZSetOperations<K, V> zSetOps;
private@Nullable GeoOperations<K, V> geoOps;
private@Nullable HyperLogLogOperations<K, V> hllOps;
```

- `<1>` 处，看到了四个序列化相关的属性，用于 KEY 和 VALUE 的序列化。

- - 例如说，我们在使用 POJO 对象存储到 Redis 中，一般情况下，会使用 JSON 方式序列化成字符串，存储到 Redis 中。详细的，我们在 「3. 序列化」 小节中来说明。
  - 在上文中，我们看到了 `org.springframework.data.redis.core.StringRedisTemplate` 类，它继承 RedisTemplate 类，使用 `org.springframework.data.redis.serializer.StringRedisSerializer` 字符串序列化方式。直接点开 StringRedisSerializer 源码，看下它的构造方法，瞬间明明白白。

- `<2>` 处，Lua 脚本执行器，提供 Redis scripting API 操作。

- `<3>` 处，Redis 常见数据结构操作类。

- - ValueOperations 类，提供 Redis String API 操作。
  - ListOperations 类，提供 Redis List API 操作。
  - SetOperations 类，提供 Redis Set API 操作。
  - ZSetOperations 类，提供 Redis ZSet(Sorted Set) API 操作。
  - GeoOperations 类，提供 Redis Geo API 操作。
  - HyperLogLogOperations 类，提供 Redis HyperLogLog API 操作。

那么 Pub/Sub、Transaction、Pipeline、Keys、Cluster、Connection 等相关的 API 操作呢？它在 RedisTemplate 自身提供，因为它们不属于具体每一种数据结构，所以没有封装在对应的 Operations 类中。哈哈哈，胖友打开 RedisTemplate 类，去瞅瞅，妥妥的明白。

### 6. 序列化(重要)

#### 6.1 RedisSerializer

```java
// RedisSerializer.java
publicinterface RedisSerializer<T> {

	@Nullable
	byte[] serialize(@Nullable T t) throws SerializationException;

	@Nullable
	T deserialize(@Nullable byte[] bytes) throws SerializationException;

}
```

定义了对象 `<T>` 和二进制数组的转换。 

啊，可能有胖友会有疑惑了：我们在 `redis-cli` 终端，看到的不都是字符串么，怎么这里是序列化成二进制数组呢？实际上，Redis Client 传递给 Redis Server 是传递的 KEY 和 VALUE 都是二进制值数组。好奇的胖友，可以打开 Jedis `Connection#sendCommand(final Command cmd, final byte[]... args)` 方法，传入的参数就是二进制数组，而 `cmd` 命令也会被序列化成二进制数组。

RedisSerializer 的实现类，如下图：  

![](E:\Redis\redisImg\RedisSerializer.jpg)

主要分成四类：

- JDK 序列化方式
- String 序列化方式
- JSON 序列化方式
- XML 序列化方式

##### 6.1.1 JDK 序列化方式

`org.springframework.data.redis.serializer.JdkSerializationRedisSerializer` ，默认情况下，RedisTemplate 使用该数据列化方式。具体的，可以看看 `RedisTemplate#afterPropertiesSet()` 方法，在 RedisTemplate 未设置序列化的情况下，使用 JdkSerializationRedisSerializer 作为序列化实现。在 Spring Boot 自动化配置 RedisTemplate Bean 对象时，就未设置。

绝大多数情况下，可能 99.9999% ，我们不会使用 JdkSerializationRedisSerializer 进行序列化。为什么呢？我们来看一个示例，代码如下：

```java
// Test01.java
@RunWith(SpringRunner.class)
@SpringBootTest
publicclass Test01 {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStringSetKey02() {
        redisTemplate.opsForValue().set("yunai", "shuai");
    }

}
```

在执行代码前我们通过flushdb清空了所有的key

然后执行上面的代码，我们发现不指定泛型和不使用StringRedisTemplate会使用默认的jdk序列化，key 和value都会变成 16 进制字符。而后，我们使用这个奇怪的 KEY 去获取对应的 VALUE ，结果前面也是一串奇怪的 16 进制字符。 

```properties
具体为什么是这样一串奇怪的 16 进制，可以看看 `ObjectOutputStream#writeString(String str, boolean unshared)` 的代码，实际就是标志位 + 字符串长度 + 字符串内容。 
```

![1651246713976](redisImg\2.png)

对于 KEY 被序列化成这样，我们线上通过 KEY 去查询对应的 VALUE 势必会非常不方便，所以 KEY 肯定是不能被这样序列化的。

对于 VALUE 被序列化成这样，除了阅读可能困难一点，不支持跨语言外，实际上也没啥问题。不过，实际线上场景，还是使用 JSON 序列化居多。

这是[RestTemplate](https://so.csdn.net/so/search?q=RestTemplate&spm=1001.2101.3001.7020)类默认的序列化方式。

###### 优点：

- 反序列化时不需要提供类型信息(class)，

###### 缺点：

- 需要实现Serializable接口
- 存储的为二进制数据
- 序列化后的结果非常庞大，是JSON格式的5倍左右，这样就会消耗redis服务器的大量内存

##### 6.1.2 String 序列化方式

① `org.springframework.data.redis.serializer.StringRedisSerializer` ，字符串和二进制数组的**直接**转换。代码如下： 

```java
// StringRedisSerializer.java

privatefinal Charset charset;

@Override
public String deserialize(@Nullable byte[] bytes) {
	return (bytes == null ? null : new String(bytes, charset));
}

@Override
publicbyte[] serialize(@Nullable String string) {
	return (string == null ? null : string.getBytes(charset));
}
```

**绝大多数情况下，我们 KEY 和 VALUE 都会使用这种序列化方案**。而 VALUE 的序列化和反序列化，自己在逻辑调用 JSON 方法去序列化。为什么呢？继续往下看。 

② `org.springframework.data.redis.serializer.GenericToStringSerializer<T>` ，使用 Spring ConversionService 实现 `<T>` 对象和 String 的转换，从而 String 和二进制数组的转换。 

例如说，序列化的过程，首先 `<T>` 对象通过 ConversionService 转换成 String ，然后 String 再序列化成二进制数组。反序列化的过程，胖友自己结合源码思考下 🤔 。 

当然，GenericToStringSerializer 貌似基本不会去使用，所以不用去了解也问题不大，哈哈哈。 

##### 6.1.3 JSON 序列化方式

① `org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer` ，使用 Jackson 实现 JSON 的序列化方式，并且从 Generic 单词可以看出，是支持所有类。怎么体现呢？参见构造方法的代码： 

```java
// GenericJackson2JsonRedisSerializer.java

public GenericJackson2JsonRedisSerializer(@Nullable String classPropertyTypeName) {

	this(new ObjectMapper());

	// simply setting {@code mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)} does not help here since we need
	// the type hint embedded for deserialization using the default typing feature.
	mapper.registerModule(new SimpleModule().addSerializer(new NullValueSerializer(classPropertyTypeName)));

	// <1>
	if (StringUtils.hasText(classPropertyTypeName)) {
		mapper.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, classPropertyTypeName);
	// <2>
	} else {
		mapper.enableDefaultTyping(DefaultTyping.NON_FINAL, As.PROPERTY);
	}
}
```

- `<1>` 处，如果传入了 `classPropertyTypeName` 属性，就是使用使用传入对象的 `classPropertyTypeName` 属性对应的值，作为默认类型（Default Typing）。
- `<2>` 处，如果未传入 `classPropertyTypeName` 属性，则使用传入对象的类全名，作为默认类型（Default Typing）。

那么，胖友可能会问题，什么是**默认类型（Default Typing）**呢？我们来思考下，在将一个对象序列化成一个字符串，怎么保证字符串反序列化成对象的类型呢？Jackson 通过 Default Typing ，会在字符串多冗余一个类型，这样反序列化就知道具体的类型了。来举个例子，使用我们等会示例会用到的 UserCacheObject 类。

- 标准序列化的结果，如下： 

```json
{
    "id": 1,
    "name": "Curley G",
    "gender": 1
}
```

使用 Jackson Default Typing 机制序列化的结果，如下： 

```json
{
        "@class": "cn.iocoder.springboot.labs.lab10.springdatarediswithjedis.cacheobject.UserCacheObject",
        "id": 1,
        "name": "芋道源码",
        "gender": 1
    }
```

 **GenericJackson2JsonRedisSerializer  示例** 

1.创建 RedisConfiguration 配置类 

```java
@Configuration
publicclass RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
        template.setConnectionFactory(factory);

        // 使用 String 序列化方式，序列化 KEY 。
        template.setKeySerializer(RedisSerializer.string());

        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }

}
```

`RedisSerializer#string()` 静态方法，返回的就是使用 UTF-8 编码的 StringRedisSerializer 对象。代码如下： 

```java
// RedisSerializer.java
static RedisSerializer<String> string() {
	return StringRedisSerializer.UTF_8;
}

// StringRedisSerializer.java
publicstaticfinal StringRedisSerializer ISO_8859_1 = new StringRedisSerializer(StandardCharsets.ISO_8859_1);
```

`RedisSerializer#json()` 静态方法，返回 GenericJackson2JsonRedisSerializer 对象。代码如下： 

```java
// RedisSerializer.java

static RedisSerializer<Object> json() {
	returnnew GenericJackson2JsonRedisSerializer();
}
```

测试代码：

```java
// Test01.java

@Autowired
private RedisTemplate redisTemplate;

@Test
public void testStringSetKeyUserCache() {
    UserCacheObject object = new UserCacheObject()
            .setId(1)
            .setName("芋道源码")
            .setGender(1); // 男
    String key = String.format("user:%d", object.getId());
    redisTemplate.opsForValue().set(key, object);
}

@Test
public void testStringGetKeyUserCache() {
    String key = String.format("user:%d", 1);
    Object value = redisTemplate.opsForValue().get(key);
    System.out.println(value);
}
```

测试结果

![](redisImg\1651248853083.png)

我们在回过头来看看 `@class` 属性，它看似完美解决了反序列化后的对象类型，但是带来 JSON 字符串占用变大，所以实际项目中，我们也并不会采用 Jackson2JsonRedisSerializer 类。 

② `org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer<T>` ，使用 Jackson 实现 JSON 的序列化方式，并且显示指定 `<T>` 类型。代码如下： 

```java
// Jackson2JsonRedisSerializer.java
publicclass Jackson2JsonRedisSerializer<T> implements RedisSerializer<T> {
    // ... 省略不重要的代码

    publicstaticfinal Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * 指定类型，和 <T> 要一致。
     */
    privatefinal JavaType javaType;

    private ObjectMapper objectMapper = new ObjectMapper();

}
```

因为 Jackson2JsonRedisSerializer 序列化类里已经声明了类型，所以序列化的 JSON 字符串，无需在存储一个 `@class` 属性，用于存储类型。

但是，我们抠脚一想，如果使用 Jackson2JsonRedisSerializer 作为序列化实现类，那么如果我们类型比较多，岂不是每个类型都要定义一个 RedisTemplate Bean 了？！所以实际场景下，我们也并不会使用 Jackson2JsonRedisSerializer 类。😈

③ `com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer` ，使用 FastJSON 实现 JSON 的序列化方式，和 GenericJackson2JsonRedisSerializer 一致，就不重复赘述。

④ `com.alibaba.fastjson.support.spring.FastJsonRedisSerializer<T>` ，使用 FastJSON 实现 JSON 的序列化方式，和 Jackson2JsonRedisSerializer 一致，就不重复赘述。  

##### 6.1.4 XML 序列化方式

`org.springframework.data.redis.serializer.OxmSerializer` ，使用 Spring OXM 实现将对象和 String 的转换，从而 String 和二进制数组的转换。

因为 XML 序列化方式，暂时没有这么干过，我自己也没有，所以就直接忽略它吧。

##### 6.1.5自定义序列化方式

可参考其他的实现类

#### 6.2 序列化选择

我们仔细翻看了每个序列化方式，暂时没有一个能够完美的契合我们的需求，所以我们直接使用最简单的 **StringRedisSerializer** 作为序列化实现类。而真正的序列化，我们在各个 Dao 类里，自己**手动**来调用。

例如说，在 UserCacheDao 示例中，已经看到了这么做了。这里还有一个细化点，虽然我们是自己**手动**序列化，可以自己简单封装一个 JSONUtil 类，未来如果我们想换 JSON 库，就比较方便了。其实，这个和 Spring Data Redis 所做的封装是一个思路。

### 7.Redis工具类

## 二十、 项目实践

### 1.建立缓存实例

在我们使用数据库时，我们会创建 `dataobject` 包，存放 DO（Data Object）数据库实体对象。

那么同理，我们缓存对象，怎么进行对应呢？对于复杂的缓存对象，我们创建了 `cacheobject` 包，和 `dataobject` 包同层

```properties
service # 业务逻辑层
dao # 数据库访问层
dataobject # DO
cacheobject # 缓存对象
```

并且所有的 Cache Object 对象使用 CacheObject 结尾，例如说 UserCacheObject、ProductCacheObject 。 

### 2.Pipeline（管道）使用



