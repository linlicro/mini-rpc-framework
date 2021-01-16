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

...
