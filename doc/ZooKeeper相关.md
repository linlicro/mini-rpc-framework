# Zookeeper 相关

mini-rpc-framework 用 ZooKeeper 来存储服务提供者的相关信息，这里就简单罗列下 ZooKeeper的常用命令。

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


