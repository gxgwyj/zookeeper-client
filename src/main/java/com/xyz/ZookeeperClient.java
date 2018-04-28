package com.xyz;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 类: ZookeeperClient <br>
 * 描述: zookeeper客户端<br>
 * 作者: gaoxugang <br>
 * 时间: 2018年04月28日 17:57
 */
public class ZookeeperClient {

    private static final String HOST_PORT = "192.168.202.128:2181";
    private static final int TIME_OUT = 5000;
    private ZooKeeper zooKeeper;
    private ZookeeperWatcher zookeeperWatcher = new ZookeeperWatcher();

    /**
     * 构造方法私有化
     */
    private ZookeeperClient(){
        zookeeperInit();
    }

    private void zookeeperInit(){
        try {
            zooKeeper = new ZooKeeper(HOST_PORT,TIME_OUT,zookeeperWatcher);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class ZookeeperClientHolder{
        private static ZookeeperClient zookeeperClient = new ZookeeperClient();
    }

    public static ZookeeperClient getZkInstance(){
        return ZookeeperClientHolder.zookeeperClient;
    }

    public void watchChanged(String path){
        try {
            zooKeeper.exists(path,zookeeperWatcher);
            zooKeeper.getChildren(path,zookeeperWatcher);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
