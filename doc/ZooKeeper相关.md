# Zookeeper 相关

mini-rpc-framework 用 ZooKeeper 来存储服务提供者的相关信息，这里就简单介绍下 ZooKeeper，及安装、常用命令 和使用 Apache Curator。

## 介绍

ZooKeeper 是一个开源的分布式协调服务，它的设计目的是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。

ZooKeeper 提供了高可用、高性能、稳定的分布式数据一致性解决方案，通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等。

另，ZooKeeper 的数据保存在内存中，性能非常好，非常适用于"读"多于"写"的应用中。

特点:

* 顺序一致性: 从同一客户端发起的事务请求，最终将会严格地按照顺序；
* 原子性: 所有事务请求的处理结果在整个集群中所有机器上的应用是一致的；
* 单一系统映像: 无论客户端连接到哪一个ZooKeeper服务上，查得数据模型是一致的；
* 可靠性: 每次更改请求结果都会被持久化

ZooKeeper 的应用场景:

ZooKeeper 通常被用于实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master选举、分布式锁和分布式队列等功能。

* 分布式锁: 通过创建唯一节点获得分布式锁；
* 命名服务: 通过顺序节点生成全局唯一ID；
* 数据发布/订阅: 通过 Watcher 机制可以实现数据发布/订阅；

注: ZooKeeper 不适合保存大量数据。

知名项目: Kafka、Hbase、Hadoop

### 核心概念

数据模型(Data model): 采用层次化的多叉树形结构，每个节点(znode)都可以储存数据。znode是ZooKeeper的最小单元，需注意不要放大数据，每个节点的数据最大为1M;

![数据模型](../img/zknamespace.jpg)

znode 有4大类:

* 持久节点(PERSISTENT)
* 临时节点(EPHEMERAL): 与客户端会话绑定
* 持久顺序(PERSISTENT_SEQUENTIAL): 在持有节点的基础上，还具有顺序性。i.e. /node1/app000000001、/node1/app000000002
* 临时顺序(EPHEMERAL_SEQUENTIAL)

znode 数据结构 由2部分组成:

* stat - 状态信息
* data - 数据

权限控制(ACL): ZooKeeper 采用ACL(AccessControlLists)策略进行权限控制，提供5中操作权限:

* CREATE: 能创建子节点
* READ: 能获取节点数据和列出子节点
* WRITE: 能设置/更新节点数据
* DELETE: 能删除子节点
* ADMIN: 能设置节点ACL的权限

身份认证方式:

* world: 默认，所有用户都可以无条件访问
* auth: 已认证的用户
* digest: 用户名:密码认证
* ip: 指定ip限制

事件监听器(Watcher), 一个非常重要的特性。ZooKeeper 允许用户在指定节点上注册 事件监听，在一些特定事件触发时，ZooKeeper服务会讲事件通知到注册的客户端。事件监听机制是 ZooKeeper 实现分布式协调服务的核心。

### 集群

集群部署 ZooKeeper 以保证高可用性，通常为3台服务器集群。集群间通过 ZAB协议(ZooKeeper Atomic Broadcast) 相互通信，保持数据一致性。

![集群](../img/zkservice.jpg)

*典型的集群模型是 Master/Slave模式(主备模式)*

ZooKeeper 没有使用传统的 Master/Slave 模式，而是引入了 Leader、Follower、Observer三种角色。

通过 Leader 选举过程 定义一台为 "Leader"，提供写服务和读服务。另外两个角色 Follower 和 Observer 只提供读服务。Follower 和 Observer 的区别在于 Observer 不参与 Leader的选举。

Leader 选举过程:

* 选举阶段(Leader election): 节点都处于选举阶段，当一个节点得到超半数节点的票数，成为准Leader
* 发现阶段(Discovery): followers 与 准Leader 通信，同步最近接收的事务提议
* 同步阶段(Synchronization): Leader 将获得的提议历史，同步集群中所有的副本，同步完成后 成为真正的Leader
* 广播阶段(Broadcast): ZooKeeper开始对外提供事务服务

ZooKeeper集群服务的状态:

* LOOKING: 寻找Leader
* LEADING: Leader状态
* FOLLOWING: Follower状态
* OBSERVING: Observer状态

问题: 为什么ZooKeeper集群最好是奇数台？

### ZAB协议和Paxos算法

ZooKeeper 的数据一致性核心算法基于 Paxos算法，但没有完全采用，而是采用了ZAB协议。

ZAB(ZooKeeper Atomic Broadcast 原子广播)协议是专门设计的一个支持崩溃恢复的原子广播协议。ZooKeeper 基于该协议 实现了一种主备模式的系统结构来保持集群中各个副本之间的数据一致性。

ZAB协议的两种基本模式:

* 崩溃恢复: 当启动或者异常情况时，进入该模式并选举产生新的Leader服务器，当过半节点服务与Leader完成状态同步之后，退出本模式。
* 消息广播: 集群中有过半的Follower和Leader状态同步完成，就进入到消息广播模式。当新的节点加入集群中，新节点先进入恢复模式，与Leader同步完成后，也参与到消息广播。

## 安装

Docker 下载ZooKeeper:

```shell script
docker pull zookeeper:3.5.8
```

启动:

```shell script
docker run -d --name zookeeper -p 2181:2181 zookeeper:3.5.8
```

## 常见操作命令

进入容器:

```shell script
// docker ps 找到 ZooKeeper 的 ContainerID
// docker exec -it ContainerID /bin/bash 进入容器

root@ef24bcd89a32:/apache-zookeeper-3.5.8-bin# cd bin/
root@ef24bcd89a32:/apache-zookeeper-3.5.8-bin/bin# ./zkCli.sh -server 127.0.0.1:2181
```

### 创建节点 - create

通过 `create` 命令在跟目录创建 node1 节点，并关联字符串"node1"

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 2] create /node1 "node1"
Created /node1
```

再创建 node1.1 节点，关联数字123

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 3] create /node1/node1.1 123
Created /node1/node1.1
```

### 更新节点数据 - set

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 4] set /node1 "update node1"
```

### 获取节点的数据 - get

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 5] get /node1
update node1
```

### 查看某个目录下的子节点 - ls

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 6] ls /
[node1, zookeeper]
[zk: 127.0.0.1:2181(CONNECTED) 7] ls /node1
[node1.1]
```

### 查看节点状态 - stat

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 8] stat /node1
cZxid = 0x4
ctime = Sun Jan 17 13:18:35 UTC 2021
mZxid = 0x6
mtime = Sun Jan 17 13:20:39 UTC 2021
pZxid = 0x5
cversion = 1
dataVersion = 1
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 12
numChildren = 1
```

### 删除节点 - delete

```shell script
[zk: 127.0.0.1:2181(CONNECTED) 11] delete /node1/node1.1
```

## Java客户端 Curator

...


